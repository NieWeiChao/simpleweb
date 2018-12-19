package site.wattsnwc.server.body.request;

/**
 * msg
 *
 * @author watts
 */
public interface Request {

    Object getParamter(String paramName);

    String getMethod();

    String getUrl() ;

}
