package provider;

import helper.HttpResponseStatusException;
import model.AnimalOwner;
import org.apache.http.HttpStatus;
import org.json.JSONArray;
import org.json.JSONObject;
import repository.AnimalOwnerRepository;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;

public class AnimalOwnerProvider {
    public static JSONObject createNewAnimalOwner(HttpServletRequest request, MultivaluedMap<String, String> form) {
        JSONObject            result     = new JSONObject();
        AnimalOwnerRepository repository = new AnimalOwnerRepository();
        boolean               isCreated  = repository.isCreatedAnimalOwner(request, form);
        if (!isCreated) {
            throw new HttpResponseStatusException(HttpStatus.SC_UNAUTHORIZED, "Internal Server Error", Response.Status.Family.CLIENT_ERROR);
        }
        result.put("status", "OK");
        return new JSONObject();
    }

    public static JSONObject getAnimalOwnersList(HttpServletRequest request, MultivaluedMap<String, String> form) {
        JSONObject            result     = new JSONObject();
        AnimalOwnerRepository repository = new AnimalOwnerRepository();
        for (AnimalOwner animalOwner : repository.getAnimalOwnerList(request, form)) {
            JSONArray  animalArray  = new JSONArray();
            JSONObject animalObject = new JSONObject();

            animalObject.put("name", animalOwner.getFullName());
            animalObject.put("age", animalOwner.getPhoneNumber());
            animalObject.put("description", animalOwner.getEmail());
            animalObject.put("sex", animalOwner.getContactInfo());

            animalArray.put(animalObject);
            result.put("date", animalArray);
        }
        return result;
    }

    public static JSONObject getSingleAnimalOwner(HttpServletRequest request, MultivaluedMap<String, String> form, Integer id) {
        JSONObject            result      = new JSONObject();
        AnimalOwnerRepository repository  = new AnimalOwnerRepository();
        AnimalOwner           animalOwner = repository.getASingleAnimalOwner(request, form, id);

        result.put("full_name", animalOwner.getFullName());
        result.put("email", animalOwner.getEmail());
        result.put("address", animalOwner.getContactInfo());
        result.put("phone_number", animalOwner.getPhoneNumber());

        return new JSONObject();
    }

    public static JSONObject updateAnimalOwner(HttpServletRequest request, MultivaluedMap<String, String> form, Integer id) {
        JSONObject            result     = new JSONObject();
        AnimalOwnerRepository repository = new AnimalOwnerRepository();
        boolean               isUpdated  = repository.isUpdatedAnimalOwner(request, form, id);
        if (!isUpdated) {
            throw new HttpResponseStatusException(HttpStatus.SC_UNAUTHORIZED, "Internal Server Error", Response.Status.Family.CLIENT_ERROR);
        }
        result.put("status", "OK");
        return result;
    }

    public static JSONObject deleteAnimalOwner(Integer id) {
        JSONObject            result     = new JSONObject();
        AnimalOwnerRepository repository = new AnimalOwnerRepository();
        boolean               isDeleted  = repository.isDeletedAnimalOwner(id);
        if (!isDeleted) {
            throw new HttpResponseStatusException(HttpStatus.SC_UNAUTHORIZED, "Internal Server Error", Response.Status.Family.CLIENT_ERROR);
        }
        result.put("status", "OK");
        return result;
    }
}
