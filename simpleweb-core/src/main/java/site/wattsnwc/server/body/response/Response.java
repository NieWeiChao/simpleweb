package site.wattsnwc.server.body.response;

/**
 * msg
 *
 * @author watts
 */
@SuppressWarnings("all")
public interface Response {
    /**
     * 设置响应类型
     * @param contentType
     */
    void setContentType(String contentType);

    /**
     * 获取响应类型
     * @return
     */
    String getContentType();

    /**
     * 设置响应内容
     * @param content
     */
    void setHttpContent(String content);

    /**
     * 获取响应内容
     * @return
     */
    String getHttpContent();
}
