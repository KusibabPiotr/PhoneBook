package files;

import repository.ContactRepository;

import java.io.File;

public interface FileManager {
    void save(ContactRepository repository);
    ContactRepository load();
}
