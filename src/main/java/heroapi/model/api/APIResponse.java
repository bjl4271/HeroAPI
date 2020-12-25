package heroapi.model.api;

public class APIResponse<T> {

    public T data;
    public int status_code;
    public String message;

    public APIResponse() {
    }

    public APIResponse(T data, int status_code, String message) {
        this.data = data;
        this.status_code = status_code;
        this.message = message;
    }
}
