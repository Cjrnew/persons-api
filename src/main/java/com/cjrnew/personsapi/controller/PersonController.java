package com.cjrnew.personsapi.controller;

import com.cjrnew.personsapi.model.Person;
import com.cjrnew.personsapi.model.dto.PersonDTO;
import com.cjrnew.personsapi.service.PersonService;
import com.cjrnew.personsapi.specification.PersonSpecification;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.List;

@CrossOrigin(origins = "*", allowedHeaders = "*", exposedHeaders = "totalElements")
@Api(tags = "API REST Persons")
@RestController
@RequestMapping("/api/v1/persons")
public class PersonController {

    @Autowired
    private PersonService personService;

    @GetMapping(path = "/{id}", produces = "application/json")
    @ApiOperation(value = "Search and return a person by ID")
    @ApiResponses(value = {@ApiResponse(code = 404, message = "Resource not found by the given ID.")})
    public ResponseEntity<PersonDTO> searchById(
            @ApiParam(value = "ID for search", required = true) @PathVariable Long id) {
        PersonDTO obj = personService.findById(id);
        return ResponseEntity.ok(obj);
    }

    @PostMapping(consumes = "application/json", produces = "application/json")
    @ApiOperation(value = "Insert a new person in the database.")
    @ApiResponses(value = {@ApiResponse(code = 201, message = "Successfully inserted person."),
            @ApiResponse(code = 409, message = "Person already in the database.")})
    @ResponseStatus(code = HttpStatus.CREATED)
    public void savePerson(
            @ApiParam(value = "Person to be saved.", required = true) @RequestBody @Valid PersonDTO personDTO) throws ParseException {

        personService.insert(personDTO);
    }


    @PutMapping(path = "/{id}", consumes = "application/json", produces = "application/json")
    @ApiOperation(value = "Updates a person in the database.")
    @ApiResponses(value = {@ApiResponse(code = 204, message = "Successfully updated person."),
            @ApiResponse(code = 404, message = "Resource not found by the given ID.")})
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void updatePerson(
            @ApiParam(value = "Person to update.", required = true) @RequestBody @Valid PersonDTO personDTO,
            @ApiParam(value = "Person ID to update.", required = true) @PathVariable Long id) throws ParseException {

        personService.update(personDTO, id);

    }

    @DeleteMapping(path = "/{id}", produces = "application/json")
    @ApiOperation(value = "Deletes a person by ID")
    @ApiResponses(value = {@ApiResponse(code = 404, message = "Resource not found by the given ID."),
            @ApiResponse(code = 204, message = "Person successfully deleted.")})
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void deleteById(
            @ApiParam(value = "Person to be deleted.", required = true) @PathVariable Long id) {

        personService.delete(id);
    }

    @GetMapping(produces = "application/json")
    @ApiOperation(value = "Search and return a person with the filter sent.")
    @ApiResponses(value = {@ApiResponse(code = 204, message = "Resource not found."),
            @ApiResponse(code = 200, message = "Returns a list of people.")})
    public ResponseEntity<List<PersonDTO>> searchPersonByFilter(
            @RequestParam(name = "id", required = false) @ApiParam(value = "ID", required = false) Long id,
            @RequestParam(name = "firstName", required = false) @ApiParam(value = "Name", required = false) String firstName,
            @RequestParam(name = "lastName", required = false) @ApiParam(value = "Name", required = false) String lastName,
            @RequestParam(name = "cpf", required = false) @ApiParam(value = "CPF", required = false) String cpf,
            @RequestParam(name = "email", required = false) @ApiParam(value = "Email", required = false) String email,
            @RequestParam(name = "firstDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) @ApiParam(value = "First Date", required = false) LocalDate firstDate,
            @RequestParam(name = "lastDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) @ApiParam(value = "Last Date", required = false) LocalDate lastDate,
            @ApiParam(value = "Page number for pagination.", defaultValue = "0") @RequestParam(defaultValue = "0") Integer pageNo,
            @ApiParam(value = "Total elements per page.", defaultValue = "10") @RequestParam(defaultValue = "10") Integer pageSize,
            @ApiParam(value = "Field for sorting.", defaultValue = "id") @RequestParam(defaultValue = "id") String sortBy) {

        Specification<Person> filter = Specification.where(PersonSpecification.byID(id))
                .and(PersonSpecification.byFirstName(firstName))
                .and(PersonSpecification.byLastName(lastName))
                .and(PersonSpecification.byEmail(email))
                .and(PersonSpecification.byDate(firstDate, lastDate))
                .and(PersonSpecification.byCpf(cpf));

        Pageable pagenator = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));

        List<PersonDTO> persons = personService.listOfAllFilter(filter, pagenator);
        if (persons == null || persons.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.add("totalElements", personService.searchTotalElements(filter).toString());
        return ResponseEntity.ok().headers(responseHeaders).body(persons);
    }

}
