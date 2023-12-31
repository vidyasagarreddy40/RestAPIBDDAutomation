package enums;

public enum APIResources {

    AddPlaceAPI("/maps/api/place/add/json"),
    getPlaceAPI("/maps/api/place/get/json"),
    deletePlaceAPI("/maps/api/place/delete/json");


    String resource;

    APIResources(String resource){
        this.resource=resource;
    }

    public String getResources(){
        return resource;
    }
}
