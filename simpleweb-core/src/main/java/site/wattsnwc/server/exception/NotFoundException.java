package site.wattsnwc.server.exception;

/**
 * msg
 *
 * @author watts
 */
public class NotFoundException extends BaseException {

    public NotFoundException(String code, String message) {
        super(message);
        this.setCode(code);
    }

}
