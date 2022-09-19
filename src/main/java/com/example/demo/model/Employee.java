package com.example.demo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "employees")
@ToString
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long document;
    @Column(name ="first_name", nullable = false)
    private String firstName;
    @Column(name ="last_name", nullable = false)
    private String lastName;
    @Column(name ="email_address", nullable = false)
    private String emailId;
}
