public class ChatRequest {
    private final String message;
    private final String aiSystem;
    private final String model;

    public ChatRequest(String message, String aiSystem, String model) {
        this.message = message;
        this.aiSystem = aiSystem;
        this.model = model;
    }

    public String getMessage(){
        return  this.message;
    }

    public  String getAiSystem(){
        return  this.aiSystem;
    }

    public String getModel(){
        return  this.model;
    }

    public String toString(){
        return  "Sending message to " + aiSystem + "("+ model + "): " + message;
    }

}
