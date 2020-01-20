package application.exception;

public class AppException extends RuntimeException {
    private int statusCode;
    public AppException(int statusCode, String message){
        super(message);
        this.statusCode = statusCode;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }
}
