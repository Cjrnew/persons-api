package com.cjrnew.personsapi.specification;

import com.cjrnew.personsapi.model.Person;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;

public class PersonSpecification {

    public static Specification<Person> byID(Long id) {
        if (id == null) {
            return null;
        } else {
            return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.equal(root.get("id"), id);
        }
    }

    public static Specification<Person> byFirstName(String name) {
        if (name == null) {
            return null;
        } else {
            return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.like(criteriaBuilder.upper(root.get("firstName")), "%" + name.toUpperCase() + "%");
        }
    }

    public static Specification<Person> byLastName(String name) {
        if (name == null) {
            return null;
        } else {
            return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.like(criteriaBuilder.upper(root.get("lastName")), "%" + name.toUpperCase() + "%");
        }
    }

    public static Specification<Person> byCpf(String cpf) {
        if (cpf == null) {
            return null;
        } else {
            return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.like(criteriaBuilder.upper(root.get("cpf")), "%" + cpf.toUpperCase() + "%");
        }
    }

    public static Specification<Person> byEmail(String email) {
        if (email == null) {
            return null;
        } else {
            return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.like(criteriaBuilder.upper(root.get("email")), "%" + email.toUpperCase() + "%");
        }
    }

    public static Specification<Person> byDate(LocalDate firstDate, LocalDate lastDate) {
        if (firstDate == null || lastDate == null) {
            return null;
        } else {
            return (root, query, cb) -> cb.between(root.get("birthDate"), firstDate, lastDate);
        }
    }

}
