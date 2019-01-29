package provider;

import org.apache.http.HttpStatus;
import helper.HttpResponseStatusException;
import model.Animal;
import org.json.JSONArray;
import org.json.JSONObject;
import repository.AnimalRepository;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;

public class AnimalProvider {
    public static JSONObject createNewAnimal(HttpServletRequest request, MultivaluedMap<String,String> form){
        JSONObject result = new JSONObject();
        AnimalRepository repository = new AnimalRepository();
        boolean isCreated = repository.isCreatedAnimal(request,form);
        if (!isCreated){
            throw new HttpResponseStatusException(HttpStatus.SC_UNAUTHORIZED, "Internal Server Error", Response.Status.Family.CLIENT_ERROR);
        }
        result.put("status","OK");
        return new JSONObject();
    }

    public static JSONObject getAnimalList(HttpServletRequest request, MultivaluedMap<String, String> form) {
        JSONObject result = new JSONObject();
        AnimalRepository repository = new AnimalRepository();
        for (Animal animal: repository.getAnimalList(request,form)) {
            JSONArray animalArray = new JSONArray();
            JSONObject animalObject = new JSONObject();

            animalObject.put("name",animal.getName());
            animalObject.put("age",animal.getAge());
            animalObject.put("description",animal.getDescription());
            animalObject.put("sex",animal.getSex());
            animalObject.put("type",animal.getType());
            animalObject.put("owner_name",animal.getOwner().getFullName());
            animalObject.put("owner_address",animal.getOwner().getContactInfo());

            animalArray.put(animalObject);
            result.put("date",animalArray);
        }
        return result;
    }

    public static JSONObject getSingleAnimal(HttpServletRequest request, MultivaluedMap<String, String> form, Integer id) {
        JSONObject result = new JSONObject();
        AnimalRepository repository = new AnimalRepository();
        Animal animal = repository.getASingleAnimal(request,form,id);
        result.put("name",animal.getName());
        result.put("age",animal.getAge());
        result.put("description",animal.getDescription());
        result.put("sex",animal.getSex());
        result.put("type",animal.getType());
        result.put("owner_name",animal.getOwner().getFullName());
        result.put("owner_address",animal.getOwner().getContactInfo());
        result.put("owner_phone_number",animal.getOwner().getPhoneNumber());
        return new JSONObject();
    }

    public static JSONObject updateAnimal(HttpServletRequest request, MultivaluedMap<String, String> form, Integer id) {
        JSONObject result = new JSONObject();
        AnimalRepository repository = new AnimalRepository();
        boolean isUpdated = repository.isUpdatedAnimal(request,form,id);
        if (!isUpdated){
            throw new HttpResponseStatusException(HttpStatus.SC_UNAUTHORIZED, "Internal Server Error", Response.Status.Family.CLIENT_ERROR);
        }
        result.put("status","OK");
        return result;
    }

    public static JSONObject deleteAnimal(Integer id) {
        JSONObject result = new JSONObject();
        AnimalRepository repository = new AnimalRepository();
        boolean isDeleted = repository.isDeletedAnimal(id);
        if (!isDeleted){
            throw new HttpResponseStatusException(HttpStatus.SC_UNAUTHORIZED, "Internal Server Error", Response.Status.Family.CLIENT_ERROR);
        }
        result.put("status","OK");
        return result;
    }
}
