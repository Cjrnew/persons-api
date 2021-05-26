package com.cjrnew.personsapi.service;

import com.cjrnew.personsapi.model.Person;
import com.cjrnew.personsapi.model.dto.PersonDTO;
import com.cjrnew.personsapi.model.dto.convert.PersonDTOConverter;
import com.cjrnew.personsapi.repository.PersonRepository;
import com.cjrnew.personsapi.service.exception.DatabaseException;
import com.cjrnew.personsapi.service.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PersonService {

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private PersonDTOConverter personDTOConverter;

    public PersonDTO findById(Long id) throws ResourceNotFoundException {
        Person person = personRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(id));
        return personDTOConverter.convertBack(person);
    }

    @Transactional
    public void insert(PersonDTO personDTO) throws DatabaseException, ParseException {
        Person person = personDTOConverter.convertTo(personDTO);
        personRepository.save(person);
    }

    public void update(PersonDTO personDTO, Long id) throws ResourceNotFoundException, ParseException {
        if (personDTO.getId().equals(id)) {
            personRepository.findById(personDTO.getId())
                    .orElseThrow(() -> new ResourceNotFoundException(personDTO.getId()));
            Person person = personDTOConverter.convertTo(personDTO);
            personRepository.save(person);
        } else {
            throw new ResourceNotFoundException(id);
        }
    }

    public void delete(Long id) throws ResourceNotFoundException {
        personRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(id));
        try {
            personRepository.deleteById(id);
        } catch (DatabaseException e) {
            throw new DatabaseException(e.getMessage());
        }
    }

    public List<PersonDTO> listOfAllFilter(Specification<Person> filter, Pageable pageable) {
        return personRepository.findAll(filter, pageable).stream()
                .map(p -> personDTOConverter.convertBack(p)).collect(Collectors.toList());
    }

    public Integer searchTotalElements(Specification<Person> filter) {
        return Integer.parseInt(String.valueOf(personRepository.count(filter)));
    }

}
