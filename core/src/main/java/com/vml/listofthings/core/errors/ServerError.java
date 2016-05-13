package com.vml.listofthings.core.errors;

/**
 * Created by tway on 9/29/15.
 */
public class ServerError extends RuntimeException {

    public ServerError(String friendlyMessage, String errorCode) {
        this.friendlyMessage = friendlyMessage;
        this.errorCode = errorCode;
    }

    public String dateTime;
    public String httpStatusCode;
    public String errorCode;
    public String errorMessage;
    public String friendlyMessage;
    public String path;

    public String getFriendlyMessage() {
        return isUnknown() ? "Unknown Server Error" : friendlyMessage;
    }

    public boolean isUnknown() {
        return friendlyMessage == null || friendlyMessage.isEmpty();
    }
}
