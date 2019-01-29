package repository;

import helper.RequestObject;
import model.Animal;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.MultivaluedMap;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AnimalRepository extends BaseRepository {

    @SuppressWarnings("SqlNoDataSourceInspection")
    public List<Animal> getAnimalList(HttpServletRequest request, MultivaluedMap<String, String> form) {
        List<Animal> animals = new ArrayList<>();
        try {
            String sql = "select ";
            if (RequestObject.hasKey("start", form, request)) {
                sql += "top " + RequestObject.getInt("start", form, request) + " ";
            }
            sql += "* from Animals";
            connection = DriverManager.getConnection("{DB_URL}", "{DB_USERNAME}", "{DB_PASSWORD}");
            preparedStatement = connection.prepareStatement(sql);
            // WE NEED DUMMY DATA
            animals.add(animal1);// THIS IS ANIMALS LIST FROM DUMMY DATA
            animals.add(animal2);
            animals.add(animal3);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            this.cleanResources();
        }
        return animals;
    }
    @SuppressWarnings("SqlNoDataSourceInspection")
    public Animal getASingleAnimal(HttpServletRequest request, MultivaluedMap<String, String> form, Integer id) {
        List<Animal> animals = new ArrayList<>();
        try {
            String sql = "select ";
            if (RequestObject.hasKey("start", form, request)) {
                sql += "top " + RequestObject.getInt("start", form, request) + " ";
            }
            sql += "* from Animals where ID = ?";
            connection = DriverManager.getConnection("{DB_URL}", "{DB_USERNAME}", "{DB_PASSWORD}");
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            // WE CAN GET A SINGLE ANIMAL FROM DB BUT WE ALSO NEED DUMMY DATA IN HERE.
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            this.cleanResources();
        }
        return animal1;
    }
    @SuppressWarnings("SqlNoDataSourceInspection")
    public Boolean isUpdatedAnimal(HttpServletRequest request, MultivaluedMap<String, String> form, Integer id) {
        try {
            connection = DriverManager.getConnection("{DB_URL}", "{DB_USERNAME}", "{DB_PASSWORD}");
            preparedStatement = connection.prepareStatement("update Animals set NAME = ?,AGE = ?, SEX = ?, TYPE = ?, OWNER_ID = ?, DESCRIPTION = ?  where ID = ?");
            preparedStatement.setString(1, RequestObject.getString("name", form, request));
            preparedStatement.setInt(2, RequestObject.getInt("age", form, request));
            preparedStatement.setString(3, RequestObject.getString("sex", form, request));
            preparedStatement.setString(4, RequestObject.getString("type", form, request));
            preparedStatement.setInt(5, RequestObject.getInt("owner_id", form, request));
            preparedStatement.setString(6, RequestObject.getString("description", form, request));
            preparedStatement.setInt(7, id);
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
    public Boolean isCreatedAnimal(HttpServletRequest request, MultivaluedMap<String, String> form) {
        try {
            connection = DriverManager.getConnection("{DB_URL}", "{DB_USERNAME}", "{DB_PASSWORD}");
            preparedStatement = connection.prepareStatement("insert into Animals(NAME, AGE, SEX, TYPE, OWNER_ID, DESCRIPTION) VALUES(?, ?, ?, ?, ?, ?)");
            preparedStatement.setString(1, RequestObject.getString("name", form, request));
            preparedStatement.setInt(2, RequestObject.getInt("age", form, request));
            preparedStatement.setString(3, RequestObject.getString("sex", form, request));
            preparedStatement.setString(4, RequestObject.getString("type", form, request));
            preparedStatement.setInt(5, RequestObject.getInt("owner_id", form, request));
            preparedStatement.setString(6, RequestObject.getString("description", form, request));

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
    public Boolean isDeletedAnimal(Integer id) {
        try {
            connection = DriverManager.getConnection("{DB_URL}", "{DB_USERNAME}", "{DB_PASSWORD}");
            preparedStatement = connection.prepareStatement("update Animals set STATUS = 0 where ID = ?");
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
