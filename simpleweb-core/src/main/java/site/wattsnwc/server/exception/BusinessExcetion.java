package site.wattsnwc.server.exception;

/**
 * msg
 *
 * @author watts
 */
public class BusinessExcetion extends BaseException {

    public BusinessExcetion(String code,String message) {
        super(message);
        this.setCode(code);
    }

}
