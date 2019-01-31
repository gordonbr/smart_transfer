package com.smarttransfer.util;

import com.smarttransfer.model.ResponseModel;

/**
 * Created by jonathasalves on 29/01/2019.
 */
public class AutomatedResponse {

    public static ResponseModel generateMessage(EMessages message) {

        ResponseModel responseModel;

        switch (message) {
            case SUCCESS:
                responseModel = new ResponseModel(message.ordinal(), "SUCCESS");
                break;
            case NOT_ENOUGH_FUNDS:
                responseModel = new ResponseModel(message.ordinal(), "NOT ENOUGH FOUND");
                break;
            case MAXIMUM_ATTEMPTS:
                responseModel = new ResponseModel(message.ordinal(), "MAXIMUM ATTEMPTS OF TRANSFER REACHED. SYSTEM UNDER HEAVY LOAD");
                break;
            case INVALID_JSON:
                responseModel = new ResponseModel(message.ordinal(), "INVALID JSON RECEIVED");
                break;
            case INVALID_VALUE:
                responseModel = new ResponseModel(message.ordinal(), "INVALID VALUE RECEIVED");
                break;
            case ACCOUNT_NOT_FOUND:
                responseModel = new ResponseModel(message.ordinal(), "ACCOUNT NOT FOUND");
                break;
            default:
                responseModel = new ResponseModel(message.ordinal(), "UNKNOWN ERROR");
        }

        return responseModel;
    }
}
