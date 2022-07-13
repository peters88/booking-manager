package com.psayol.bookingmanager.response.builder;

import com.psayol.bookingmanager.response.ResponseConstants;
import com.psayol.bookingmanager.response.ResponseDataDTO;

import java.util.ArrayList;
import java.util.List;

public class ResponseDataBuilder {

    private String apiVersion = "";
    private String statusMessage = "";
    private int statusCode = 0;
    private List<Object> payload = new ArrayList<>();
    private ResponseDataDTO.ErrorBlock error = null;

    public ResponseDataBuilder setApiVersion(String apiVersion) {
        this.apiVersion = apiVersion;
        return this;
    }

    public ResponseDataBuilder setStatusMessage(String statusMessage) {
        this.statusMessage = statusMessage;
        return this;
    }

    public ResponseDataBuilder setStatusCode(int statusCode) {
        this.statusCode = statusCode;
        return this;
    }

    public ResponseDataBuilder setPayload(List<Object> payload) {
        this.payload = payload;
        return this;
    }

    public ResponseDataBuilder setPayloadObject(Object payload) {
        this.payload.add(payload);
        return this;
    }

    public ResponseDataBuilder setError(ResponseDataDTO.ErrorBlock error) {
        this.error = error;
        return this;
    }

    public static ResponseDataDTO buildSuccessResponse(){
        return new ResponseDataBuilder().setApiVersion("v1")
                .setStatusCode(ResponseConstants.SUCCESS.getCode())
                .setStatusMessage(ResponseConstants.SUCCESS.getMessage())
                .build();
    }

    public ResponseDataDTO build(){
        return new ResponseDataDTO(apiVersion,statusMessage,statusCode,payload,error);
    }

    public static class ErrorBuilder {
        private int code = 0;
        private String message = "";

        public ErrorBuilder setCode(int code) {
            this.code = code;
            return this;
        }

        public ErrorBuilder setMessage(String message) {
            this.message = message;
            return this;
        }

        public ResponseDataDTO.ErrorBlock build(){
            return new ResponseDataDTO.ErrorBlock(code, message);
        }
    }
}
