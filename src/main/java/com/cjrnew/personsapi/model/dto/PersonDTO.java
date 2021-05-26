package com.cjrnew.personsapi.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.tomcat.jni.Local;
import org.hibernate.validator.constraints.br.CPF;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PersonDTO {

    private Long id;

    @NotEmpty(message="Required field.")
    @Size(min = 2, max = 20)
    private String firstName;

    @NotEmpty(message="Required field.")
    @Size(min = 2, max = 70)
    private String lastName;

    @NotEmpty(message="Required field.")
    @CPF
    private String cpf;

    @NotNull
    private String birthDate;

    @NotEmpty(message="Required field.")
    @Email
    private String email;

    @Valid
    @NotEmpty
    private List<PhoneDTO> phones = new ArrayList<>();
}
