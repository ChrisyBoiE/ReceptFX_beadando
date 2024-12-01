package com.recept.recept;

import com.oanda.v20.account.AccountID;

public class Config {
    private Config() {}

    // Az API alap URL-je
    public static final String URL = "https://api-fxpractice.oanda.com";

    // Az API token
    public static final String TOKEN = "78b8a8f4d9f1d83bab60a5641b3af8b0-908d6d4dd7e435a59083f3215ad320a9";

    // A számla azonosító
    public static final AccountID ACCOUNTID = new AccountID("101-004-30473886-001");

    // Metódus az URL eléréséhez
    public static String getApiUrl() {
        return URL;
    }

    // Metódus az API token eléréséhez
    public static String getApiKey() {
        return TOKEN;
    }
}
