package com.misha.application.utils.json;


/**
 * Created by misha on 17.11.16.
 */
public class JsonUtils {

    public static JsonResponse buildJsonResponse(Object entity) {
        return new JsonResponse(entity);
    }
}
