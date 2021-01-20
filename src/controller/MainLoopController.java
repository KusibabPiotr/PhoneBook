package controller;

import exceptions.DataExportException;
import exceptions.DataImportException;
import exceptions.DuplicateContactException;
import files.FileManager;
import files.FileManagerBuilder;
import io.ConsolePrinter;
import io.DataReader;
import model.Category;
import model.Contact;
import model.Type;
import repository.ContactRepository;

import java.util.*;

public class MainLoopController {

    ConsolePrinter printer = new ConsolePrinter();
    DataReader reader = new DataReader();

    private FileManager fileManager;
    private ContactRepository repository;


    public MainLoopController(){
        fileManager = new FileManagerBuilder().build();
        try{
            repository = fileManager.load();
            printer.printLine("Zaimportowano dane");
        }catch (DataImportException e){
            printer.printLine(e.getMessage());
            printer.printLine("Zainicjowano nową bazę");
            repository = new ContactRepository();
        }
    }

    public  void run(){
        boolean exit = false;

        do {

            printOptions();
            int intOption = getIntOption();

            try {
                Option option = convertIntOptionToOption(intOption);
                exit = executeOption(option);
            }catch (NoSuchElementException e){
                printer.printLine("Podana opcja jest błędna! Spróbuj jeszcze raz!");
            }catch (DuplicateContactException e){
                printer.printLine(e.getMessage());
            }

        }while (!exit);
    }

    private boolean executeOption(Option option)
            throws DuplicateContactException {
        switch (option){
            case EXIT:
                sayGoodBye();
                return true;
            case ADD:
                add();
                break;
            case REMOVEBYNAME:
                removeByName();
                break;
            case EDIT:
                edit();
                break;
            case PRINTALL:
                printAll();
                break;
            case FINDBYPIECEOFNAME:
                findByPieceOfName();
                break;
            case FINDBYPIECEOFNUMBER:
                findByPieceOfNumber();
                break;
            default:
                printer.printLine("Brak Opcji");
        }
        return false;
    }

    private void findByPieceOfNumber() {
        String number = readNumber();
        List<Contact> byPieceOfNumber = repository.findByPieceOf(number);
        printAllContacts(byPieceOfNumber);
    }

    private void findByPieceOfName() {
        String pieceOfName = readName();
        List<Contact> byPartOfName = repository.findByPieceOf(pieceOfName);
        printAllContacts(byPartOfName);
    }

    private void printAll() {
        List<Contact> allContacts = repository.getAllContacts();
        printAllContacts(allContacts);
    }

    private void edit() {
        String name1 = readName();
        printer.printLine("Nowe dane:");
        Contact contact1 = readContact();
        boolean b1 = repository.editContact(contact1, name1);
        printConfirmation(b1);
    }

    private void removeByName() {
        String name = readName();
        boolean b = repository.removeContactByName(name);
        printConfirmation(b);
    }

    private void add() {
        Contact contact = readContact();
        boolean bool = repository.addContact(contact);
        printConfirmation(bool);
    }

    private String readNumber() {
        printer.printLine("Podaj numer telefonu");
        return reader.readLine();
    }

    private void printAllContacts(List<Contact> allContacts) {
        if (allContacts.isEmpty()) {
            printer.printLine("Brak kontaktów odpowiadających wyszukaniu");
        }else
            allContacts.sort(Comparator.naturalOrder());
            allContacts.forEach(System.out::println);
    }

    private String readName() {
        printer.printLine("Podaj nazwę kontaktu");
        return reader.readLine();
    }

    private void printConfirmation(boolean bool) {
        if (bool){
            printer.printLine("Operacja powiodła się!");
        }else
            printer.printLine("Operacja nie powiodła się!");
    }

    private Contact readContact() {
        String name = readName();
        String number = readNumber();
        Category category = readCategory();
        return new Contact(name,number,category);
    }

    private Category readCategory() {
        printer.printLine("kontakt prywatny/biznesowy");
        printer.printLine("Wybierz 1 lub 2");

        boolean allOk = false;
        Type type = null;

        do {
            try {
                int opt = reader.readInt();
                type = Type.getType(opt);
                allOk = true;
            } catch (InputMismatchException e) {
                printer.printLine("Wpisz 1 lub 2");
            }catch (NoSuchElementException e){
                printer.printLine("Brak opcji ");
            }
        }while (!allOk);
        return new Category(type);
    }

    private void sayGoodBye() {
        try {
            fileManager.save(repository);
            printer.printLine("Zapisano dane do pliku");
        }catch (DataExportException e){
            printer.printLine(e.getMessage());
        }finally {
            printer.printLine("Do widzenia!");
            reader.close();
        }
    }

    private Option convertIntOptionToOption(int intOption) throws NoSuchElementException{
        return Option.getOption(intOption);
    }

    private int getIntOption() throws InputMismatchException {
        int option = -1;
        boolean ok = false;
        do {
            try {
                option = reader.readInt();
                ok = true;
            } catch (InputMismatchException e) {
                printer.printLine("Podaj liczbę! Spróbuj jeszcze raz!");
            } finally {
                reader.readLine();
            }
        }while (!ok);
        return option;
    }

    private void printOptions() {
        printer.printLine("Dostępne opcje");
        Arrays.stream(Option.values())
                .forEach(option -> printer.printLine(option.ordinal() + " - " +option.getDescription()));
    }

    public enum Option {
        EXIT("Wyjście"),
        ADD("Dodaj kontakt"),
        REMOVEBYNAME("Usuń kontakt"),
        EDIT("Edytuj kontakt"),
        PRINTALL("Wyświetl kontakty"),
        FINDBYPIECEOFNAME("Znajdź kontakt po części imienia"),
        FINDBYPIECEOFNUMBER("Znajdź kontakt po części numberu telefonu");

        private String description;

        Option(String description) {
            this.description = description;
        }

        public String getDescription() {
            return description;
        }

        static Option getOption(int intOption){
            return Arrays.stream(Option.values())
                    .filter(value -> intOption == value.ordinal())
                    .findFirst()
                    .orElseThrow();
        }
    }
}
