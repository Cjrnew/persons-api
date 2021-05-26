package com.cjrnew.personsapi.model;

import com.cjrnew.personsapi.enums.PhoneType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Phone implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(generator = "tb_phone_seq", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "tb_phone_seq", sequenceName = "tb_phone_seq", initialValue = 1, allocationSize = 1)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PhoneType type;

    @Column(nullable = false, unique = true)
    private String number;
}
