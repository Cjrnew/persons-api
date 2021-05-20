package com.cjrnew.personsapi.repository;

import com.cjrnew.personsapi.entity.Person;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonRepository extends JpaRepository<Person, Long> {
}
