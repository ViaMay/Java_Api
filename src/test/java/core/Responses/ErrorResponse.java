package core.Responses;
public class ErrorResponse
{
    private int code;
    private String type;
    private String message;

    public ErrorResponse(){}

    public ErrorResponse(int code, String type, String message) {
        this.code = code;
        this.type = type;
        this.message = message;
    }

    @Override
    public String toString() {
        return "ErrorResponse{" +
                "code='" + code + '\'' +
                ", type='" + type + '\'' +
                ", message='" + message + '\'' +
                '}';
    }

    public int getCode() {
        return code;
    }

    public String getType() {
        return type;
    }

    public String getMessage() {
        return message;
    }
}