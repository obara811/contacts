package com.example.address.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.address.models.Person;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {
  public Optional<Person> findById(long id);
}
