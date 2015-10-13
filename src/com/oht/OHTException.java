package com.oht;

import com.google.gson.JsonArray;

/**
 * Created with IntelliJ IDEA.
 * User: diman
 * Date: 08.10.15
 * Time: 15:42
 * To change this template use File | Settings | File Templates.
 */
public class OHTException extends Exception
{
    public OHTException(int statusCode, String statusMessage, JsonArray errors)
    {
        super(String.format("Error %d: %s", statusCode, statusMessage));
        this.statusCode = statusCode;
        this.statusMessage = statusMessage;
        this.errors = errors;
    }

    public OHTException(Throwable cause)
    {
        super("internal error", cause);
    }

    private int statusCode;
    private String statusMessage;
    private JsonArray errors;

    public int getStatusCode() {
        return statusCode;
    }

    public String getStatusMessage() {
        return statusMessage;
    }

    public JsonArray getErrors() {
        return errors;
    }
}
