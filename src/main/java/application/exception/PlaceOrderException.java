package application.exception;

import org.springframework.http.HttpStatus;

public class PlaceOrderException extends RuntimeException {
    private HttpStatus httpStatus;
    private PlaceOrderErrorResponse placeOrderErrorResponse;

    public PlaceOrderException(HttpStatus httpStatus, PlaceOrderErrorResponse placeOrderErrorResponse){
        this.httpStatus = httpStatus;
        this.placeOrderErrorResponse = placeOrderErrorResponse;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public void setHttpStatus(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }

    public PlaceOrderErrorResponse getPlaceOrderErrorResponse() {
        return placeOrderErrorResponse;
    }

    public void setPlaceOrderErrorResponse(PlaceOrderErrorResponse placeOrderErrorResponse) {
        this.placeOrderErrorResponse = placeOrderErrorResponse;
    }
}
