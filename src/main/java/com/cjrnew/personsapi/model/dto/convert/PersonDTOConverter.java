package com.cjrnew.personsapi.model.dto.convert;

import com.cjrnew.personsapi.model.Person;
import com.cjrnew.personsapi.model.Phone;
import com.cjrnew.personsapi.model.dto.PersonDTO;
import com.cjrnew.personsapi.model.dto.PhoneDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PersonDTOConverter implements ConvertEntityDTO<Person, PersonDTO> {

    @Autowired
    private PhoneDTOConverter phoneDTOConverter;

    @Override
    public Person convertTo(PersonDTO a) throws ParseException {
        if (a != null){
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            List<Phone> phones = a.getPhones().stream()
                    .map(phone -> phoneDTOConverter.convertTo(phone))
                    .collect(Collectors.toList());

            return Person.builder()
                    .id(a.getId())
                    .firstName(a.getFirstName())
                    .lastName(a.getLastName())
                    .birthDate(sdf.parse(a.getBirthDate()))
                    .cpf(a.getCpf())
                    .email(a.getEmail())
                    .phones(phones)
                    .build();
        }
        return null;
    }

    @Override
    public PersonDTO convertBack(Person t) {
        if (t != null) {

            List<PhoneDTO> phones = t.getPhones().stream()
                    .map(phone -> phoneDTOConverter.convertBack(phone))
                    .collect(Collectors.toList());

            return PersonDTO.builder()
                    .id(t.getId())
                    .firstName(t.getFirstName())
                    .lastName(t.getLastName())
                    .birthDate(String.valueOf(t.getBirthDate()))
                    .cpf(t.getCpf())
                    .email(t.getEmail())
                    .phones(phones)
                    .build();
        }
        return null;
    }
}
