package files;

import exceptions.NoSuchFileManagerException;

import java.util.Scanner;

public class FileManagerBuilder {

    static Scanner scanner = new Scanner(System.in);

    public FileManager build(){
        System.out.println("Wybierz format danych");
        FileType fileType = getFileType();

        switch (fileType){
            case CSV:
                return new CsvFileManager();
            case SERIAL:
                return new SerialFileManager();
            default:
                throw new NoSuchFileManagerException("Błędny format danych");
        }
    }

    private FileType getFileType() {
        boolean typeOk = false;
        FileType fileType = null;

        do {
            printFileTypes();
            String type = scanner.nextLine().toUpperCase();
            try {
                fileType = FileType.valueOf(type);
                typeOk = true;
            }catch (IllegalArgumentException e){
                System.out.println("Wybierz dostępny typ danych!");
            }
        }while (!typeOk);
        return fileType;
    }

    private void printFileTypes() {
        System.out.println("Wybierz opcje:");
        for (FileType value : FileType.values()) {
            System.out.println(value);
        }
    }
}
