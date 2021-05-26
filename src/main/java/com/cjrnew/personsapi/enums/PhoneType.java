package com.cjrnew.personsapi.enums;


public enum PhoneType {

    HOME(1, "Home"),
    MOBILE(2, "Mobile"),
    COMMERCIAL(3, "Commercial");

    private int cod;
    private String description;

    private PhoneType(int cod, String description) {
        this.cod = cod;
        this.description = description;
    }

    public int getCod() {
        return cod;
    }

    public String getDescription() {
        return description;
    }

    public static PhoneType toEnum(Integer cod) {
        if (cod == null) {
            return null;
        }
        for (PhoneType x : PhoneType.values()) {
            if (cod.equals((x.getCod()))) {
                return x;
            }
        }

        throw new IllegalArgumentException("Invalid ID: " + cod);
    }

}
