package model;

import java.util.Arrays;

public class Category {
    private Type type;

    public Category(Type type) {
        this.type = type;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }
}
