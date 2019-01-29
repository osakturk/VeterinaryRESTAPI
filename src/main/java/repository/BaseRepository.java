package repository;

import model.Animal;
import model.AnimalOwner;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

class BaseRepository {
    Connection        connection;
    PreparedStatement preparedStatement;
    ResultSet resultSet;

    Animal animal1 = new Animal("Ralf", 1, "He is sick now but also using medicine", "Male", "Golden Retriever", setOwner(
            "Omer Akturk", "omerakturk@outlook.com.tr", "+90 532 247 83 57", "Abbasağa mahallesi Fulya Deresi sokak NO:5/0 Beşiktaş/İstanbul"));
    Animal animal2 = new Animal("Eddie", 2, "He is normal Labrador", "Male", "Labrador retriever", setOwner(
            "Mustafa Akturk", "deneme1@deneme.com.tr", "+90 *** *** ** **", "Abbasağa mahallesi Fulya Deresi sokak NO:5/1 Beşiktaş/İstanbul"));
    Animal animal3 = new Animal("Kaymak", 4, "Funny girl", "Female", "Kangal Shepherd Dog", setOwner(
            "Zeynep Akturk", "deneme2@deneme.com.tr", "+90 *** *** ** **", "Abbasağa mahallesi Fulya Deresi sokak NO:5/2 Beşiktaş/İstanbul"));
    Animal animal4 = new Animal("Joe", 6, "()", "Male", "English Cocker Spaniel", setOwner(
            "Omer Akturk", "omerakturk@outlook.com.tr", "+90 532 247 83 57", "Abbasağa mahallesi Fulya Deresi sokak NO:5/3 Beşiktaş/İstanbul"));


    AnimalOwner setOwner(String fullName, String email, String phoneNumber, String contactInfo) {
        AnimalOwner owner = new AnimalOwner();
        owner.setFullName(fullName);
        owner.setEmail(email);
        owner.setPhoneNumber(phoneNumber);
        owner.setContactInfo(contactInfo);
        return owner;
    }


    void cleanResources() {
        try {
            connection.close();
            preparedStatement.close();
            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
