package site.wattsnwc.server.context;

import site.wattsnwc.server.body.request.Request;
import site.wattsnwc.server.body.response.Response;

/**
 * msg
 *
 * @author watts
 */
public final class Context {

    private Request request;

    private Response response;


    public Context(Request request, Response response) {
        this.request = request;
        this.response = response;
    }

    public Request request() {
        return Context.getContext().request;
    }

    public Response response() {
        return Context.getContext().response;
    }

    public static void removeContext() {
        ContextThreadLocalHodler.removeContext();
    }

    public static void setContext(Context context) {
        ContextThreadLocalHodler.setContext(context);
    }

    public static Context getContext() {
        return ContextThreadLocalHodler.getContext();
    }

}
