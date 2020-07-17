package com.infostretch.microservice.view;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Arrays;
import java.util.List;

@Data
@NoArgsConstructor
public class ErrorResponse {

    private interface ErrorCode {
        int BAD_PARAMETER = 1;
    }

    @Data
    public static class Error {
        private String errorMessage;
        private int errorCode;

        public static Error badParameter(String message) {
            Error e = new Error();
            e.errorCode = ErrorCode.BAD_PARAMETER;
            e.errorMessage = message;
            return e;
        }
    }

    private List<Error> errors;

    public static ErrorResponse badParameter(String message) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setErrors(Arrays.asList(Error.badParameter(message)));
        return errorResponse;
    }


}
