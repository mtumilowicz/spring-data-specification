package com.example.springdataspecification;

import lombok.NonNull;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 * Created by mtumilowicz on 2018-10-10.
 */
public final class EmployeeSpecifications {
    public static Specification<Employee> hasName(@NonNull String name) {
        return (Root<Employee> root, CriteriaQuery<?> query, CriteriaBuilder cb)
                -> cb.equal(root.get(Employee_.name), name);
    }

    public static Specification<Employee> hasAge(int age) {
        return (Root<Employee> root, CriteriaQuery<?> query, CriteriaBuilder cb)
                -> cb.equal(root.get(Employee_.age), age);
    }
}
