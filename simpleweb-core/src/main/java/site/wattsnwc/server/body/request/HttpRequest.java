package site.wattsnwc.server.body.request;

import io.netty.handler.codec.http.FullHttpRequest;

/**
 * msg
 *
 * @author watts
 */
public class HttpRequest implements Request {
    String method;

    String url;

    public static HttpRequest build(FullHttpRequest request) {
        HttpRequest httpRequest = new HttpRequest();
        httpRequest.method = request.method().name();
        httpRequest.url = request.uri();
        return httpRequest;
    }

    @Override public Object getParamter(String paramName) {
        return null;
    }

    @Override public String getMethod() {
        return this.method;
    }

    @Override public String getUrl() {
        return this.url;
    }


}
