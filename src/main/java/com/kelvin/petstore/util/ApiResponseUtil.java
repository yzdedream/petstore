package com.kelvin.petstore.util;

import com.kelvin.petstore.model.ApiResponse;

public class ApiResponseUtil {
    public static ApiResponse buildDefaultResponse() {
        ApiResponse response = new ApiResponse();
        response.code = 0;
        response.type = "default";
        response.message = "successful operation";
        return response;
    }
}
