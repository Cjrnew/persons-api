package com.cjrnew.personsapi.model.dto.convert;

import java.text.ParseException;

public interface ConvertEntityDTO<T,A> {

    public T convertTo(A a) throws ParseException;

    public A convertBack(T t);
}
