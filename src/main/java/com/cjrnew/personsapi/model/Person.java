package com.cjrnew.personsapi.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Person implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(generator = "tb_person_seq", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "tb_person_seq", sequenceName = "tb_person_seq", initialValue = 1, allocationSize = 1)
    private Long id;

    @Column(nullable = false, length = 20)
    private String firstName;

    @Column(nullable = false, length = 70)
    private String lastName;

    @Column(nullable = false, unique = true)
    private String cpf;

    @JsonFormat(pattern = "dd/MM/yyyy")
    private Date birthDate;

    @Column(unique = true)
    private String email;

    @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.ALL})
    private List<Phone> phones = new ArrayList<>();
}
