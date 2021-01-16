package files;

import exceptions.NoSuchFileManagerException;
import io.ConsolePrinter;
import io.DataReader;

import java.util.Scanner;

public class FileManagerBuilder {

    DataReader reader = new DataReader();
    ConsolePrinter printer = new ConsolePrinter();

    public FileManager build(){
        printer.printLine("Wybierz format danych");
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
            String type = reader.readLine().toUpperCase();
            try {
                fileType = FileType.valueOf(type);
                typeOk = true;
            }catch (IllegalArgumentException e){
                printer.printLine("Wybierz dostępny typ danych!");
            }
        }while (!typeOk);
        return fileType;
    }

    private void printFileTypes() {
        printer.printLine("Wybierz opcje:");
        for (FileType value : FileType.values()) {
            printer.printLine(value.toString());
        }
    }
}
