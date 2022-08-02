package com.psayol.bookingmanager.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Builder
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class ResponseDataDTO implements Serializable {
    private String apiVersion;
    private String statusMessage;
    private int statusCode;
    private List<Object> payload;
    private ErrorBlock error;

    public ResponseDataDTO(String apiVersion, String statusMessage, int statusCode){
        this.apiVersion = apiVersion;
        this.statusMessage = statusMessage;
        this.statusCode = statusCode;
    }

    public static final class ErrorBlock {

        private final UUID uniqueId;

        private final List<Error> errors;

        public ErrorBlock(final int code, final String errorMessage) {
            this.uniqueId = UUID.randomUUID();
            this.errors = List.of(
                    new Error(code, errorMessage)
            );
        }

        private ErrorBlock(final UUID uniqueId, final List<Error> errors) {
            this.uniqueId = uniqueId;
            this.errors = errors;
        }

        public static ErrorBlock copyWithMessage(final ErrorBlock s, final String message) {
            return new ErrorBlock(s.uniqueId, s.errors);
        }

        public UUID getUniqueId() {
            return uniqueId;
        }

        public List<Error> getErrors() {
            return errors;
        }

    }

    private static final class Error {
        private final int code;
        private final String message;


        public Error(final int code, final String message) {

            this.code = code;
            this.message = message;
        }

        public int getCode() {
            return code;
        }

        public String getMessage() {
            return message;
        }
    }

}