import java.util.*;

public class MainLoop {
    static Scanner scanner = new Scanner(System.in);

    static void loop(){
        boolean exit = false;

        do {

            try {
                printOptions();
                int intOption = getIntOption();
                Option option = convertIntOptionToOption(intOption);
                exit = executeOption(option);
            }catch (InputMismatchException e){
                System.out.println("Podaj liczbę! Spróbuj jeszcze raz!");
            }catch (NoSuchElementException e){
                System.out.println("Podana opcja jest błędna! Spróbuj jeszcze raz!");
            }finally {
                scanner.nextLine();
            }

        }while (!exit);
    }

    private static boolean executeOption(Option option) {
        switch (option){
            case EXIT:
                System.out.println("Exit");
                return true;
            case ADD:
                System.out.println("Add");
                break;
            case REMOVE:
                System.out.println("Remove");
                break;
            case EDIT:
                System.out.println("Edit");
                break;
            case PRINTALL:
                System.out.println("PrintAll");
                break;
            default:
                System.out.println("Brak Opcji");
        }
        return false;
    }

    private static Option convertIntOptionToOption(int intOption) throws NoSuchElementException{
        return Option.getOption(intOption);
    }

    private static int getIntOption() throws InputMismatchException {
        return scanner.nextInt();
    }

    private static void printOptions() {
        Arrays.stream(Option.values())
                .forEach(option -> System.out.println(option.ordinal() + " - " +option.getDescription()));
    }

    public enum Option {
        EXIT("Wyjście"),
        ADD("Dodaj kontakt"),
        REMOVE("Usuń kontakt"),
        EDIT("Edytuj kontakt"),
        PRINTALL("Wyświetl kontakty");

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
