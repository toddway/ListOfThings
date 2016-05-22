package com.vml.listofthings.core.environment;

/**
 * Created by tway on 8/21/15.
 */
public class SessionEntity {
    public String access_token;
    public String token_type;
    public String refresh_token;
    public long exp;

    public String getAuthString() {
        return token_type + " " + access_token;
    }
}
