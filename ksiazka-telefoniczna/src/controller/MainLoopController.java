package controller;

import exceptions.DataExportException;
import exceptions.DataImportException;
import files.FileManager;
import files.FileManagerBuilder;
import model.Contact;
import repository.ContactRepository;

import java.util.*;

public class MainLoopController {

    static Scanner scanner = new Scanner(System.in);
    private FileManager fileManager;
    private ContactRepository repository;


    public MainLoopController(){
        fileManager = new FileManagerBuilder().build();
        try{
            repository = fileManager.load();
            System.out.println("Zaimportowano dane");
        }catch (DataImportException e){
            System.out.println(e.getMessage());
            System.out.println("Zainicjowano nową bazę");
            repository = new ContactRepository();
        }
    }

    public  void run(){
        boolean exit = false;

        do {

            try {
                printOptions();
                int intOption = getIntOption();
                Option option = convertIntOptionToOption(intOption);
                exit = executeOption(option,repository);
            }catch (NoSuchElementException e){
                System.out.println("Podana opcja jest błędna! Spróbuj jeszcze raz!");
            }

        }while (!exit);
    }

    private boolean executeOption(Option option,ContactRepository repository) {
        switch (option){
            case EXIT:
                sayGoodBye();
                return true;
            case ADD:
                Contact contact = readContact();
                boolean bool = repository.addContact(contact);
                printConfirmation(bool);
                break;
            case REMOVEBYNAME:
                String name = readName();
                boolean b = repository.removeContactByName(name);
                printConfirmation(b);
                break;
            case EDIT:
                String name1 = readName();
                System.out.println("Nowe dane:");
                Contact contact1 = readContact();
                boolean b1 = repository.editContact(contact1, name1);
                printConfirmation(b1);
                break;
            case PRINTALL:
                List<Contact> allContacts = repository.getAllContacts();
                printAllContacts(allContacts);
                break;
            case FINDBYPIECEOFNAME:
                String pieceOfName = readName();
                List<Contact> byPartOfName = repository.findByPieceOf(pieceOfName);
                printAllContacts(byPartOfName);
                break;
            case FINDBYPIECEOFNUMBER:
                String number = readNumber();
                List<Contact> byPieceOfNumber = repository.findByPieceOf(number);
                printAllContacts(byPieceOfNumber);
                break;
            default:
                System.out.println("Brak Opcji");
        }
        return false;
    }

    private String readNumber() {
        System.out.println("Podaj numer telefonu");
        return scanner.nextLine();
    }

    private void printAllContacts(List<Contact> allContacts) {
        if (allContacts.isEmpty()) {
            System.out.println("Brak kontaktów odpowiadających wyszukaniu");
        }else
            allContacts.sort(Comparator.naturalOrder());
            allContacts.forEach(System.out::println);
    }

    private String readName() {
        System.out.println("Podaj nazwę kontaktu");
        return scanner.nextLine();
    }

    private void printConfirmation(boolean bool) {
        if (bool){
            System.out.println("Operacja powiodła się!");
        }else
            System.out.println("Operacja nie powiodła się!");
    }

    private Contact readContact() {
        String name = readName();
        String number = readNumber();
        return new Contact(name,number);
    }

    private void sayGoodBye() {
        try {
            fileManager.save(repository);
            System.out.println("Zapisano dane do pliku");
        }catch (DataExportException e){
            System.out.println(e.getMessage());
        }finally {
            System.out.println("Do widzenia!");
            scanner.close();
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
                option = scanner.nextInt();
                ok = true;
            } catch (InputMismatchException e) {
                System.out.println("Podaj liczbę! Spróbuj jeszcze raz!");
            } finally {
                scanner.nextLine();
            }
        }while (!ok);
        return option;
    }

    private void printOptions() {
        System.out.println("Dostępne opcje");
        Arrays.stream(Option.values())
                .forEach(option -> System.out.println(option.ordinal() + " - " +option.getDescription()));
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
