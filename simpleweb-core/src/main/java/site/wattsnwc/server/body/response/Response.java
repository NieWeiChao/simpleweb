package site.wattsnwc.server.body.response;

/**
 * msg
 *
 * @author watts
 */
public interface Response {

    void setContentType(String contentType);

    String getContentType();

    void setHttpContent(String content);

    String getHttpContent();
}
