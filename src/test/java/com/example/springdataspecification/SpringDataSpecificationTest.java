package com.example.springdataspecification;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static com.example.springdataspecification.EmployeeSpecifications.hasAge;
import static com.example.springdataspecification.EmployeeSpecifications.hasName;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;

/**
 * Created by mtumilowicz on 2018-10-08.
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class SpringDataSpecificationTest {

    @Autowired
    EmployeeRepository repository;

    @Test
    public void basic() {
        List<Employee> employees = repository.findAll(hasName("Tumilowicz"));
        
        assertThat(employees, hasSize(1));
    }

    @Test
    public void or() {
        List<Employee> employees = repository.findAll(hasName("Tumilowicz").or(hasName("Mrozek")));

        assertThat(employees, hasSize(2));
    }

    @Test
    public void and() {
        List<Employee> employees = repository.findAll(hasName("Tumilowicz").and(hasAge(29)));

        assertThat(employees, hasSize(1));
    }
}
