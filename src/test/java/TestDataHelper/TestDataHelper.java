package TestDataHelper;

import pojo.AddPlace;
import pojo.Location;

import java.util.ArrayList;
import java.util.List;

public class TestDataHelper {


    public AddPlace addPlacePayLoad(String name, String language, String address) {

        AddPlace addPlace = new AddPlace();
        addPlace.setAccuracy(50);
        addPlace.setAddress(address);
        addPlace.setName(name);

        addPlace.setPhone_number("32534534");
        addPlace.setWebsite("https://rahulshettyacademy.com/");
        addPlace.setLanguage(language);

        Location location = new Location();
        location.setLat(-34);
        location.setLng(45);

        addPlace.setLocation(location);
        List<String> myTypes = new ArrayList<String>();
        myTypes.add("shoepark");
        myTypes.add("shop");

        addPlace.setTypes(myTypes);

        return addPlace;
    }


    public String deletePlacePayLoad(String placeId){
        return "{\"place_id\":\""+placeId+"\"}";
    }
}
