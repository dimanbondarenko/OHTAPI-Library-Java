package com.oht.entities;

import com.google.gson.JsonObject;
import com.oht.OHTException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Represents project rating
 */
public class Rating implements Comparable<Rating> {

    private static SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public Rating() {
    }

    public Rating(JsonObject json) throws OHTException {
        this();

        this.type = json.get("type").getAsString();
        this.rate = json.get("rate").getAsInt();
        this.remarks = json.get("remarks").getAsString();

        try {
            this.date = formatter.parse(json.get("date").getAsString());
        } catch (ParseException e) {
            throw new OHTException(-1, "error parsing date string", null);
        }
    }

    /**
     * Type (Customer or Service)
     */
    public String getType() {
        return type;
    }

    /**
     * Rating of project (1 - being the lowest; 10 - being the highest)
     */
    public int getRate() {
        return rate;
    }

    /**
     * Remark left with the rating
     */
    public String getRemarks() {
        return remarks;
    }

    /**
     * Date and time of last update to the rating
     */
    public Date getDate() {
        return date;
    }

    private String type;
    private int rate;
    private String remarks;
    private Date date;

    @Override
    public int compareTo(Rating o) {
        return this.date.compareTo(o.date);
    }
}
