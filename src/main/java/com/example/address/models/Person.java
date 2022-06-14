package com.example.address.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="person")
@Getter
@Setter
public class Person {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column
  @NotNull
  private long id;

  @Column(length = 100, nullable = false)
  @NotEmpty(message="名前を入力してください。")
  private String name;

  @Column(nullable = false)
  @NotNull(message="年齢を入力してください。")
  @Min(0)
  @Max(150)
  private Integer age;

  @Column(length = 250, nullable = false)
  @NotEmpty(message="メールアドレスを入力してください。")
  @Email
  private String mail;
}
