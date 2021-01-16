package files;

import exceptions.DataExportException;
import exceptions.DataImportException;
import model.Contact;
import repository.ContactRepository;

import java.io.*;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class CsvFileManager implements FileManager{

    public static final String FILENAME = "phoneBook.csv";

    @Override
    public void save(ContactRepository repository) {
        List<Contact> allContacts = repository.getAllContacts();

        try(var bw = new BufferedWriter(new FileWriter(FILENAME)))
        {
            allContacts.stream()
                    .map(Contact::toCsv)
                    .collect(Collectors.toList())
                    .forEach(contact->{
                        try {
                            bw.write(contact);
                            bw.newLine();
                        } catch (IOException e) {
                            System.out.println("Błąd");
                        }
        });
        }catch (FileNotFoundException e){
            throw new DataExportException("Brak pliku o nazwie " + FILENAME);
        }catch (IOException e){
            throw new DataExportException("Błąd zapisu danych do pliku " + FILENAME);
        }
    }

    @Override
    public ContactRepository load() {
        ContactRepository contactRepository = new ContactRepository();
        Map<String, Contact> contactMap;

        try(var br = new BufferedReader(new FileReader(FILENAME)))
        {
            contactMap = br.lines()
                    .map(Contact::fromCsv)
                    .collect(Collectors.toMap(Contact::getName, contact -> new Contact(contact.getName(), contact.getNumber())));

        }catch (FileNotFoundException e){
            throw new DataImportException("Brak pliku o nazwie "+FILENAME);
        }catch (IOException e){
            throw new DataImportException("Błąd odczytu danych z pliku "+FILENAME);
        }
        contactRepository.setContacts(contactMap);
        return contactRepository;
    }
}
