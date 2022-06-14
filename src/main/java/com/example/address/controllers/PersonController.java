package com.example.address.controllers;

import java.util.Optional;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.example.address.models.Person;
import com.example.address.repositories.PersonRepository;

@Controller
public class PersonController {
  @Autowired
  PersonRepository repository;
  
  @GetMapping("/")
  public ModelAndView index( @ModelAttribute Person person, ModelAndView mav){
   
    mav.setViewName("person/index");
    mav.addObject("msg", "ContactList");
    Iterable<Person> everyone = repository.findAll();
    mav.addObject("people", everyone);
    return mav;
  }

  @PostMapping("/create")
  @Transactional(readOnly = false)
  public ModelAndView create(@ModelAttribute @Validated Person person,
                             BindingResult result,
                             ModelAndView mav){
    ModelAndView res = null;
    if (!result.hasErrors()){
      repository.saveAndFlush(person);
      res = new ModelAndView("redirect:/");
    }else{
      mav.setViewName("person/index");
      mav.addObject("msg", "Error is occured...");
      Iterable<Person> everyone = repository.findAll();
      mav.addObject("people", everyone);
      res = mav;
    }
    return res;
  }

  @GetMapping("/edit/{id}")
  public ModelAndView edit(@ModelAttribute Person person,
                           @PathVariable int id, 
                           ModelAndView mav){
    mav.setViewName("person/edit");
    mav.addObject("msg", "Edit");
    Optional<Person> editPerson = repository.findById((long)id);
    mav.addObject("person", editPerson.get());
    return mav;
  }

  @PostMapping("/update")
  @Transactional(readOnly = false)
  public ModelAndView update(@ModelAttribute("person") Person person,
                             ModelAndView mav){
    repository.saveAndFlush(person);
    return new ModelAndView("redirect:/");
  }

  @PostMapping("/delete")
  @Transactional(readOnly = false)
  public ModelAndView remove(@RequestParam long id, ModelAndView mav){
    repository.deleteById(id);
    return new ModelAndView("redirect:/");
  }


  //初期データの投入
  @PostConstruct
  public void dataInit(){
    Person suzuki = new Person();
    suzuki.setName("Suzuki");
    suzuki.setAge(23);
    suzuki.setMail("suzuki@email.com");
    repository.saveAndFlush(suzuki);

    Person sato = new Person();
    sato.setName("Sato");
    sato.setAge(33);
    sato.setMail("sato@email.com");
    repository.saveAndFlush(sato);
  }
}
