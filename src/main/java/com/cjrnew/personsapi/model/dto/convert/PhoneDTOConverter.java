package com.cjrnew.personsapi.model.dto.convert;

import com.cjrnew.personsapi.model.Phone;
import com.cjrnew.personsapi.model.dto.PhoneDTO;
import org.springframework.stereotype.Service;

@Service
public class PhoneDTOConverter implements  ConvertEntityDTO<Phone, PhoneDTO>{

    @Override
    public Phone convertTo(PhoneDTO a) {
        if (a != null) {
            return Phone.builder()
                    .id(a.getId())
                    .type(a.getType())
                    .number(a.getNumber())
                    .build();
        }
        return null;
    }

    @Override
    public PhoneDTO convertBack(Phone t) {
        if (t != null) {
            return PhoneDTO.builder()
                    .id(t.getId())
                    .type(t.getType())
                    .number(t.getNumber())
                    .build();
        }
        return null;
    }

}
