package files;

import exceptions.DataExportException;
import exceptions.DataImportException;
import model.Contact;
import repository.ContactRepository;

import java.io.*;
import java.util.List;

public class SerialFileManager implements FileManager {

    public static final String FILENAME = "phoneBook.txt";

    @Override
    public void save(ContactRepository repository) {

        try(var oos = new ObjectOutputStream(new FileOutputStream(FILENAME)))
        {
            oos.writeObject(repository);
        }catch (FileNotFoundException e){
            throw new DataExportException("Brak pliku o nazwie " + FILENAME);
        }catch (IOException e){
            throw  new DataExportException("Błąd zapisu danych do pliku " + FILENAME);
        }
    }

    @Override
    public ContactRepository load() {
        try(var ois = new ObjectInputStream(new FileInputStream(FILENAME)))
        {
            return  (ContactRepository) ois.readObject();
        }catch (FileNotFoundException e){
            throw new DataImportException("Brak pliku o nazwie " + FILENAME);
        }catch (IOException e){
            throw new DataImportException("Błąd wczytywania danych z pliku " + FILENAME);
        } catch (ClassNotFoundException e) {
            throw new DataImportException("Nie odnaleziono pliku " + FILENAME);
        }
    }
}
