package com.misha.application.utils.json;

/**
 * Created by misha on 18.11.16.
 */
public class JsonResponse {

    private Object response;

    public JsonResponse() {
    }

    public JsonResponse(Object response) {
        this.response = response;
    }

    public Object getResponse() {
        return response;
    }

    public void setResponse(Object response) {
        this.response = response;
    }


}
