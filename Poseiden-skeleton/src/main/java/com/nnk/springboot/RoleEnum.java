package com.nnk.springboot;

public enum RoleEnum {
    USER("USER"),
    ADMIN("ADMIN");

    private final String code;

    RoleEnum(String code){
        this.code = code;
    }

    public String getCode(){return code;}
}
