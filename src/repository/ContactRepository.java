package repository;

import exceptions.DuplicateContactException;
import model.Contact;

import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

public class ContactRepository implements Serializable {
    private Map<String, Contact> contacts = new TreeMap<>();


    public void setContacts(Map<String, Contact> contacts) {
        this.contacts = contacts;
    }

    public boolean addContact(Contact contact) {
        String key = contact.getName();

        if (contacts.containsKey(key)){
            throw new DuplicateContactException("Istnieje kontakt o imieniu " + contact.getName());
        }
        contacts.put(key,contact);
        return true;
    }

    public boolean removeContactByName(String key){
        if (contacts.containsKey(key)){
            contacts.remove(key);
            return true;
        }
        return false;
    }

    public boolean editContact(Contact contact, String name){
        if (contacts.containsKey(name)){
            contacts.remove(name);
            contacts.put(contact.getName(), contact);
            return true;
        }
        return false;
    }

    public List<Contact> getAllContacts(){
        return new ArrayList<>(contacts.values());
    }


    public List<Contact> findByPieceOf(String pieceOf){
        return contacts.values().stream()
                .filter(contact -> contact.toString().contains(pieceOf))
                .collect(Collectors.toList());
    }
}
