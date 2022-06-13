package site.wattsnwc.server.http;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import site.wattsnwc.server.annotation.RequestParam;
import site.wattsnwc.server.body.request.HttpRequest;
import site.wattsnwc.server.body.response.HttpResponse;
import site.wattsnwc.server.context.Context;
import site.wattsnwc.server.exception.NotFoundException;
import site.wattsnwc.server.scanner.ControllerScanner;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

/**
 * 请求处理类
 *
 * @author watts
 */
public class HttpDispatcher extends SimpleChannelInboundHandler<FullHttpRequest> {

    private static Logger logger = LoggerFactory.getLogger(HttpDispatcher.class);

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, FullHttpRequest request) {
        HttpMethod method = request.method();
        HttpRequest httpRequest = HttpRequest.build(request);
        HttpResponse httpResponse = HttpResponse.build();
        Context.setContext(new Context(httpRequest, httpResponse));
        try {
            Method doMethod = ControllerScanner.getMethod(request.uri().split("\\?")[0]);
            if (doMethod == null) {
                throw new NotFoundException("404", "not found !");
            }
            List<Object> args = new ArrayList<>(4);
            if (HttpMethod.POST == method) {
                resolvePost(request);
            } else if (HttpMethod.GET == method) {
                resolveGet(request, args, doMethod);
            }
            doMethod.invoke(doMethod.getDeclaringClass().newInstance(), args.toArray(new Object[0]));
        } catch (Exception e) {
            e.printStackTrace();
            Context.getContext().response().setHttpContent(e.getMessage());
        } finally {
            buildResponse(ctx);
            Context.removeContext();
        }
    }

    /**
     * 构建响应
     *
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

    private void resolveGet(FullHttpRequest request, List<Object> args, Method doMethod) {
        String uri = request.uri();
        String[] paramsUri = uri.split("\\?");
        if (paramsUri.length > 1) {
            Stream<Parameter> paramsStream = Stream.of(doMethod.getParameters());
            String params = paramsUri[1];
            String[] param = params.split("=");
            String paramName = param[0];
            String paramValue = param[1];
            paramsStream.forEach(parameter -> {
                RequestParam requestParam = parameter.getAnnotation(RequestParam.class);
                if (requestParam.value().equals(paramName)) {
                    args.add(paramValue);
                }
                if (parameter.getType() == Context.class) {
                    args.add(Context.getContext());
                }
            });
        }

    }

    private void resolvePost(FullHttpRequest request) {
        ByteBuf content = request.content();
        byte[] reqContent = new byte[content.readableBytes()];
        content.readBytes(reqContent);
        String strContent = new String(reqContent, StandardCharsets.UTF_8);
    }

}
