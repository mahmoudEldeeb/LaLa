package com.programs.lala.lala.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by melde on 3/15/2017.
 */

public class ServerResponseUploadImage {
    boolean success;
    @SerializedName("message")
    String message;

    public String getMessage() {
        return message;
    }

    public boolean getSuccess() {
        return success;
    }
}
