package repository;

import exceptions.DuplicateContactException;
import model.Contact;

import java.util.*;

public class ContactRepository {
    private Map<String, String> contacts = new TreeMap<>();

    public boolean addContact(Contact contact) {
        String key = contact.getName();
        String value = contact.toString();

        if (contacts.containsKey(key)){
            throw new DuplicateContactException("Istnieje kontakt o imieniu " + contact.getName());
        }
        contacts.put(key,value);
        return true;
    }

    public boolean removeContactByName(String key){
        if (contacts.containsKey(key)){
            contacts.remove(key);
            return true;
        }
        return false;
    }

    public boolean editContact(Contact contact){
        String value = contact.toString();

        if (contacts.containsKey(contact.getName())){
            contacts.replace(contact.getName(),value);
            return true;
        }
        return false;
    }

    public Map<String,String> getAllContacts(){
        return contacts;
    }

    public List<Contact> findByPartName(String pieceOfName){
        Set<String> strings = contacts.keySet();
        strings.stream().filter(n->n.contains(pieceOfName))
                .
    }
}
