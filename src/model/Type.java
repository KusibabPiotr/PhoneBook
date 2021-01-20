package model;

import java.util.Arrays;

public enum Type{
    PRIVATE,BUSINESS;

    public static Type getType(int option){
        return Arrays.stream(Type.values())
                .filter(value->value.ordinal()==option)
                .findFirst()
                .orElseThrow();
    }
}
