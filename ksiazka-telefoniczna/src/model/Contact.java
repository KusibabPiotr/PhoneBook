package model;

import java.io.Serializable;
import java.util.Objects;

public class Contact implements Comparable<Contact>, Serializable {

    private String name;
    private String number;

    public Contact(String name, String number) {
        this.name = name;
        this.number = number;
    }
    public String toCsv(){
        return name+";"+number;
    }
    public static Contact fromCsv(String csv){
        String[] split = csv.split(";");
        String name = split[0];
        String number = split[1];
        return new Contact(name,number);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    @Override
    public String toString() {
        return "Kontakt: " + getName() + ", " + getNumber();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Contact contact = (Contact) o;
        return Objects.equals(name, contact.name) &&
                Objects.equals(number, contact.number);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, number);
    }

    @Override
    public int compareTo(Contact o) {
        return o.getName().compareTo(getName());
    }
}
