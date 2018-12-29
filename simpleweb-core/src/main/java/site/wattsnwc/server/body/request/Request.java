package site.wattsnwc.server.body.request;

/**
 * msg
 *
 * @author watts
 */
@SuppressWarnings("all")
public interface Request {

    /**
     * 获取请求参数
     * @param paramName
     * @return
     */
    Object getParamter(String paramName);

    /**
     * 获取请求方法
     * @return
     */
    String getMethod();

    /**
     * 获取请求Url
     * @return
     */
    String getUrl();

}
