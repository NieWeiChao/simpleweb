package site.wattsnwc.server.http;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;
import io.netty.handler.stream.ChunkedWriteHandler;

/**
 * http channel
 * @author watts
 */
public class HttpChannelHandler extends ChannelInitializer<Channel> {
    @Override protected void initChannel(Channel ch) throws Exception {
        ch.pipeline()
                .addLast(new HttpRequestDecoder())
                .addLast("http-aggregator", new HttpObjectAggregator(65535))
                .addLast(new HttpResponseEncoder())
                .addLast(new ChunkedWriteHandler())
                .addLast(new HttpDispatcher());
    }
}
