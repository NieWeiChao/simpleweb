package site.wattsnwc.server.exception;

/**
 * msg
 *
 * @author watts
 */
public class BaseException extends Exception{

    private String code;

    public BaseException(String message) {
        super(message);
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
