package site.wattsnwc.server.context;

import io.netty.util.concurrent.FastThreadLocal;

/**
 * msg
 *
 * @author watts
 */
public class ContextThreadLocalHodler {
    private static final FastThreadLocal<Context> CONTEXT = new FastThreadLocal<>();

    public static void setContext(Context context){
        CONTEXT.set(context);
    }

    public static void removeContext(){
        CONTEXT.remove();
    }

    public static Context getContext(){
        return CONTEXT.get();
    }
}
