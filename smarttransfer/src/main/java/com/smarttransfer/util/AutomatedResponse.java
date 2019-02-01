package com.smarttransfer.util;

import com.smarttransfer.model.Response;

/**
 * Created by jonathasalves on 29/01/2019.
 */
public class AutomatedResponse {

    public static Response generateMessage(EMessages message) {

        Response response;

        switch (message) {
            case SUCCESS:
                response = new Response(message.ordinal(), "SUCCESS");
                break;
            case NOT_ENOUGH_FUNDS:
                response = new Response(message.ordinal(), "NOT ENOUGH FOUND");
                break;
            case MAXIMUM_ATTEMPTS:
                response = new Response(message.ordinal(), "MAXIMUM ATTEMPTS OF TRANSFER REACHED. SYSTEM UNDER HEAVY LOAD");
                break;
            case INVALID_JSON:
                response = new Response(message.ordinal(), "INVALID JSON RECEIVED");
                break;
            case INVALID_VALUE:
                response = new Response(message.ordinal(), "INVALID VALUE RECEIVED");
                break;
            case ACCOUNT_NOT_FOUND:
                response = new Response(message.ordinal(), "ACCOUNT NOT FOUND");
                break;
            default:
                response = new Response(message.ordinal(), "UNKNOWN ERROR");
        }

        return response;
    }
}
