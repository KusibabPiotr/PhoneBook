package repository;

import exceptions.DuplicateContactException;
import model.Contact;

import java.util.*;
import java.util.stream.Collectors;

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

    public List<String> getAllContacts(){
        return new ArrayList<>(contacts.values());
    }

    public List<String> findByPieceOf(String pieceOf){
        return contacts.values().stream()
                .filter(s -> s.contains(pieceOf))
                .collect(Collectors.toList());
    }
}
