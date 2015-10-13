package com.oht.entities;

import com.google.gson.JsonObject;

/**
 * Represents OHT expertise
 * Use {@link com.oht.OHTAPI#getSupportedExpertises(String, String) getSupportedExpertises} method
 * to get all expertises supported by OHT service
 * */
public class Expertise {

    public Expertise() {
    }

    public Expertise(JsonObject json) {
        this();

        this.code = json.get("code").getAsInt();
        this.name = json.get("name").getAsString();
    }

    /**
     * Expertise code
     */
    public int getCode() {
        return code;
    }

    /**
     * Expertise name
     */
    public String getName() {
        return name;
    }

    private int code;
    private String name;
}
