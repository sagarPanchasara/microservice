package com.infostretch.microservice.view;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

@Data
@NoArgsConstructor
public class ErrorResponse {

    private enum ErrorCode {
        BAD_PARAMETER(1, 400),
        UNAUTHORIZED(2, 403);

        private final int errorCode, httpStatus;

        ErrorCode(int errorCode, int httpStatus) {
            this.errorCode = errorCode;
            this.httpStatus = httpStatus;
        }

    }

    @Data
    public static class Error {
        private String errorMessage;
        private int errorCode;
        @JsonIgnore
        private int httpStatus;

        public static Error badParameter(String message) {
            Error e = new Error();
            e.errorCode = ErrorCode.BAD_PARAMETER.errorCode;
            e.errorMessage = message;
            e.httpStatus = ErrorCode.BAD_PARAMETER.httpStatus;
            return e;
        }

        public static Error unauthorized() {
            Error e = new Error();
            e.errorCode = ErrorCode.UNAUTHORIZED.errorCode;
            e.errorMessage = "Unauthorized";
            e.httpStatus = ErrorCode.UNAUTHORIZED.httpStatus;
            return e;
        }
    }

    private List<Error> errors;

    public static ErrorResponse badParameter(String message) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setErrors(Arrays.asList(Error.badParameter(message)));
        return errorResponse;
    }

    public static ErrorResponse unauthorized() {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setErrors(Arrays.asList(Error.unauthorized()));
        return errorResponse;
    }

    public ResponseEntity<?> toResponseEntity() {
        return ResponseEntity.status(this.errors.get(0).httpStatus).body(this);
    }

}
