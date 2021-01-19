package dao;

import model.Contact;

import java.sql.*;

public class ContactDao {
    private final Connection connection;

    public ContactDao(){
        try{
            this.connection = DriverManager.getConnection
                    ("jdbc:mysql://localhost:3306/test?serverTimezone=UTC", "root", "Igloopol1");
        }catch (SQLException throwables) {
            throw new RuntimeException();
        }
    }

    void close(){
        try{
            connection.close();
        } catch (SQLException throwables) {
            throw new RuntimeException();
        }
    }
    boolean save(Contact contact){
        boolean saved = false;
       final String sql = String.format("INSERT INTO contacts (name, number) VALUES ('%s','%s')",contact.getName(),contact.getNumber());
       try(Statement statement = connection.createStatement()) {
           statement.executeUpdate(sql, Statement.RETURN_GENERATED_KEYS);
           ResultSet result = statement.getGeneratedKeys();
           if (result.next()){
               contact.setId(result.getInt("id"));
           }
           saved = true;
       } catch (SQLException throwables) {
           throwables.printStackTrace();
       }
       return saved;
    }
    List
}
