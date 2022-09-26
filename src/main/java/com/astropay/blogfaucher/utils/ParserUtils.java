package com.astropay.blogfaucher.utils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

public class ParserUtils {

    public static String toJsonString(Map<String, Object> map) {
        Gson gson = new Gson();
        Type gsonType = new TypeToken<HashMap>(){}.getType();
        return gson.toJson(map, gsonType);
    }
}
