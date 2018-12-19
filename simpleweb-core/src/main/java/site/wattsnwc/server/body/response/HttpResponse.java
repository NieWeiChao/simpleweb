package site.wattsnwc.server.body.response;

/**
 * msg
 *
 * @author watts
 */
public class HttpResponse implements Response {

    private String contentType;

    private String httpContent = "";


    private HttpResponse() {
    }

    public static HttpResponse build() {
        HttpResponse httpResponse = new HttpResponse();
        httpResponse.contentType = "text/plain; charset=UTF-8;";
        return httpResponse;
    }

    @Override public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    @Override public String getContentType() {
        return this.contentType;
    }

    @Override public void setHttpContent(String content) {
        this.httpContent = content;
    }

    @Override public String getHttpContent() {
        return this.httpContent;
    }
}
