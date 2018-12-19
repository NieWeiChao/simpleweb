package site.wattsnwc.server.http;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpMethod;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpVersion;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import site.wattsnwc.server.body.request.HttpRequest;
import site.wattsnwc.server.body.response.HttpResponse;
import site.wattsnwc.server.context.Context;
import site.wattsnwc.server.scanner.ControllerScanner;

import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;

/**
 * 请求处理类
 *
 * @author watts
 */
public class HttpDispatcher extends SimpleChannelInboundHandler<FullHttpRequest> {
    private static Logger logger = LoggerFactory.getLogger(HttpDispatcher.class);

    @Override protected void channelRead0(ChannelHandlerContext ctx, FullHttpRequest request) {
        HttpMethod method = request.method();
        HttpRequest httpRequest = HttpRequest.build(request);
        HttpResponse httpResponse = HttpResponse.build();
        Context.setContext(new Context(httpRequest,httpResponse));
        try{
            //解析請求
            if(HttpMethod.POST == method){
                resolvePost(request);
            }else if(HttpMethod.GET == method){
                resolveGet(request);
            }
            //查找映射方法
            Method doMethod = ControllerScanner.getMethod(request.uri());
            if(doMethod == null){
                throw new NoSuchMethodException();
            }
            doMethod.invoke(doMethod.getDeclaringClass().newInstance());
            buildResponse(ctx);
        }catch (Exception e){
            Context.getContext().response().setHttpContent("500 error!");
        }
        finally {
            buildResponse(ctx);
            Context.removeContext();
        }

    }

    /**
     * 构建响应
     * @param ctx
     */
    private void buildResponse(ChannelHandlerContext ctx) {
        String content = Context.getContext().response().getHttpContent();
        ByteBuf buf = Unpooled.wrappedBuffer(content.getBytes(StandardCharsets.UTF_8));
        DefaultFullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK, buf);
        HttpHeaders headers = response.headers();
        headers.setInt(HttpHeaderNames.CONTENT_LENGTH, response.content().readableBytes());
        ctx.writeAndFlush(response);
    }

    private void resolveGet(FullHttpRequest request){
        String uri = request.uri();
    }
    private void resolvePost(FullHttpRequest request){
        ByteBuf content = request.content();
        byte[] reqContent = new byte[content.readableBytes()];
        content.readBytes(reqContent);
        String strContent = new String(reqContent, StandardCharsets.UTF_8);
    }

}
