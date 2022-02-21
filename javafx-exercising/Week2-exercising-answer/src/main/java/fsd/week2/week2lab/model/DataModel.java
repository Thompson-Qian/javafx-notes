package fsd.week2.week2lab.model;

public class DataModel {

    private String name;
    private String request;
    private String message;

   public DataModel(String name, String request, String message) {

        this.name = name;
        this.request = request;
        this.message = message;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRequest() {
        return request;
    }

    public void setRequest(String request) {
        this.request = request;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
