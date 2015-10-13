# OneHourTranslation API Library for Java #

One Hour Translation&trade; provides translation, proofreading and transcription services worldwide.
The following API library allows customers to submit and monitor jobs automatically and remotely.

## Preconditions and Dependencies  

#### Authentication 
1. Register as a customer on [One Hour Translation](http://www.onehourtranslation.com/auth/register).
2. Request your API Keys [here](http://www.onehourtranslation.com/profile/apiKeys).

#### Dependencies
1. JDK 1.7 or higher
2. Apache HttpComponents Client library (http://hc.apache.org/)
3. Google Gson library (https://github.com/google/gson)

#### Installation:
TODO:

## Starters' Guide ##

#### Configuration ####

The API Library must be instantiated and configured before calling any API method.

One method of configuration is done on object instantiation:

    OHTAPI api = new OHTAPI("<secret key here>", "<public key here>", false); // or <true> for using OHT Sandbox environment

#### Running Methods ####

Once configure and initialized, you are ready to call API methods.
For example, requesting account details:

    AccountDetails acc = api.getAccountDetails();

#### Complete Example ####  

    OHTAPI api = new OHTAPI("<secret key here>", "<public key here>", true);
    try {
        // get account details
        AccountDetails acc = api.getAccountDetails();
        System.out.printf("Account id=%d, name=%s\n", acc.getAccountId(), acc.getAccountUsername());
    } catch (OHTException ex) {
        ex.printStackTrace();
    }
