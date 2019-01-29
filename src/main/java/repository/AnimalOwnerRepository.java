package repository;

import helper.RequestObject;
import model.Animal;
import model.AnimalOwner;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.MultivaluedMap;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AnimalOwnerRepository extends BaseRepository{
    @SuppressWarnings("SqlNoDataSourceInspection")
    public List<AnimalOwner> getAnimalOwnerList(HttpServletRequest request, MultivaluedMap<String, String> form) {
        List<AnimalOwner> animalOwners = new ArrayList<>();
        List<Animal> animals = new ArrayList<>();
        try {
            String sql = "select ";
            if (RequestObject.hasKey("start", form, request)) {
                sql += "top " + RequestObject.getInt("start", form, request) + " ";
            }
            sql += "* from AnimalOwners";
            connection = DriverManager.getConnection("{DB_URL}", "{DB_USERNAME}", "{DB_PASSWORD}");
            preparedStatement = connection.prepareStatement(sql);

            resultSet = preparedStatement.executeQuery();
            try {
                while (resultSet.next()){
                    AnimalOwner animalOwner = new AnimalOwner();
                    animalOwner.setFullName(resultSet.getString("FULLNAME"));
                    animalOwner.setEmail(resultSet.getString("EMAIL"));
                    animalOwner.setPhoneNumber(resultSet.getString("PHONE_NUMBER"));
                    animalOwner.setContactInfo(resultSet.getString("CONTACTINFO"));

                    animalOwners.add(animalOwner);
                }
            }catch (SQLException e){
                e.printStackTrace();
                e.getErrorCode();
                e.getSQLState();
            }
            // WE NEED DUMMY DATA
            animalOwners.add(setOwner("Metin Tekin","mt@deneme.com.tr","+90 533 333 33 33","Fatih/Kız Taşı"));// THIS IS ANIMALS LIST FROM DUMMY DATA
            animalOwners.add(setOwner("Feyyaz Uçar","fu@deneme.com.tr","+90 544 444 44 44","Kadıköy/Moda"));
            animalOwners.add(setOwner("Ali Gültiken","ag@deneme.com.tr","+90 532 323 23 23","Beşiktaş/Yıldız"));
            animals.add(animal1);
            animals.add(animal2);
            animalOwners.get(0).setAnimalList(animals);
            animals = new ArrayList<>();
            animals.add(animal3);
            animalOwners.get(1).setAnimalList(animals);
            animals = new ArrayList<>();
            animals.add(animal4);
            animalOwners.get(2).setAnimalList(animals);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            this.cleanResources();
        }
        return animalOwners;
    }
    @SuppressWarnings("SqlNoDataSourceInspection")
    public AnimalOwner getASingleAnimalOwner(HttpServletRequest request, MultivaluedMap<String, String> form, Integer id) {
        try {
            String sql = "select ";
            if (RequestObject.hasKey("start", form, request)) {
                sql += "top " + RequestObject.getInt("start", form, request) + " ";
            }
            sql += "* from AnimalOwners where ID = ?";
            connection = DriverManager.getConnection("{DB_URL}", "{DB_USERNAME}", "{DB_PASSWORD}");
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            // WE CAN GET A SINGLE ANIMAL FROM DB BUT WE ALSO NEED DUMMY DATA IN HERE.

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            this.cleanResources();
        }
        List<Animal> animals = new ArrayList<>();
        animals.add(animal4);
        AnimalOwner owner = setOwner("Ali Gültiken","ag@deneme.com.tr","+90 555 555 55 55","Şişli/Nişantaşı");
        owner.setAnimalList(animals);
        return owner;
    }
    @SuppressWarnings("SqlNoDataSourceInspection")
    public Boolean isUpdatedAnimalOwner(HttpServletRequest request, MultivaluedMap<String, String> form, Integer id) {
        try {
            connection = DriverManager.getConnection("{DB_URL}", "{DB_USERNAME}", "{DB_PASSWORD}");
            preparedStatement = connection.prepareStatement("update AnimalOwners set FULLNAME = ?,CONTACTINFO = ?, PHONENUMBER = ?, EMAIL = ? where ID = ?");
            preparedStatement.setString(1, RequestObject.getString("full_name", form, request));
            preparedStatement.setString(2, RequestObject.getString("contact_info", form, request));
            preparedStatement.setString(3, RequestObject.getString("phone_number", form, request));
            preparedStatement.setString(4, RequestObject.getString("email", form, request));
            preparedStatement.setInt(5, id);
            int result = preparedStatement.executeUpdate();
            if (result == 1) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            this.cleanResources();
        }

        return false;
    }
    @SuppressWarnings("SqlNoDataSourceInspection")
    public Boolean isCreatedAnimalOwner(HttpServletRequest request, MultivaluedMap<String, String> form) {
        try {
            connection = DriverManager.getConnection("{DB_URL}", "{DB_USERNAME}", "{DB_PASSWORD}");
            preparedStatement = connection.prepareStatement("insert into AnimalOwners(FULLNAME, CONTACTINFO, PHONENUMBER, EMAIL) VALUES(?, ?, ?, ?)");
            preparedStatement.setString(1, RequestObject.getString("full_name", form, request));
            preparedStatement.setString(2, RequestObject.getString("contact_info", form, request));
            preparedStatement.setString(3, RequestObject.getString("phone_number", form, request));
            preparedStatement.setString(4, RequestObject.getString("email", form, request));

            int result = preparedStatement.executeUpdate();
            if (result == 1) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            this.cleanResources();
        }

        return false;
    }
    @SuppressWarnings("SqlNoDataSourceInspection")
    public Boolean isDeletedAnimalOwner(Integer id) {
        try {
            connection = DriverManager.getConnection("{DB_URL}", "{DB_USERNAME}", "{DB_PASSWORD}");
            preparedStatement = connection.prepareStatement("update AnimalOwners set STATUS = 0 where ID = ?");
            preparedStatement.setInt(1, id);
            int result = preparedStatement.executeUpdate();
            if (result == 1) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            this.cleanResources();
        }

        return false;
    }
}
