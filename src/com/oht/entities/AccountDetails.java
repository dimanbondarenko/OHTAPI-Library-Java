package com.oht.entities;

import com.google.gson.JsonObject;

/**
 * Basic account details and credits balance.
 * To get account details
 * use {@link com.oht.OHTAPI#getAccountDetails() getAccountDetails} method
 */
public class AccountDetails {

    public AccountDetails() {
    }

    public AccountDetails(JsonObject object) {
        this();

        this.accountId = object.get("account_id").getAsInt();
        this.accountUsername = object.get("account_username").getAsString();
        this.credits = object.get("credits").getAsFloat();
    }

    private int accountId;
    private String accountUsername;
    private float credits;

    /**
     * Unique account id in OHT
     */
    public int getAccountId() {
        return accountId;
    }

    /**
     * OHT username
     */
    public String getAccountUsername() {
        return accountUsername;
    }

    /**
     * Currently available credits balance
     */
    public float getCredits() {
        return credits;
    }
}
