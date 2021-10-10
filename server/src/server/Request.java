package server;

import java.io.Serializable;

public class Request implements Serializable {
    private final String requestBody;

    public Request(String requestBody){
        this.requestBody = requestBody;
    }

    public String getRequestBody() {
        return requestBody;
    }
}