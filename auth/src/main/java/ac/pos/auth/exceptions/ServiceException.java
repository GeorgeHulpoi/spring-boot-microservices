package ac.pos.auth.exceptions;

public class ServiceException extends RuntimeException {
    private String statusCode;

    public ServiceException(String message, String statusCode) {
        super(message);
        this.statusCode = statusCode;
    }

    public ServiceException(String message, Throwable e, String statusCode) {
        super(message, e);
        this.statusCode = statusCode;
    }

    public String GetStatusCode() {
        return this.statusCode;
    }

    public void SetStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }
}
