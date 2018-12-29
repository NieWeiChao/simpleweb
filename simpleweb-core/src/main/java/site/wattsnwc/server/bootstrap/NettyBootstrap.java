package site.wattsnwc.server.bootstrap;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import site.wattsnwc.server.config.ServerConfig;
import site.wattsnwc.server.http.HttpChannelHandler;
import site.wattsnwc.server.scanner.ControllerScanner;

/**
 * netty服务
 *
 * @author watts
 */
public class NettyBootstrap {

    private NettyBootstrap() {
    }

    private static Logger logger = LoggerFactory.getLogger(NettyBootstrap.class);
    private static ChannelFuture channelFuture = null;
    private static EventLoopGroup bossGroup = new NioEventLoopGroup();
    private static EventLoopGroup workerGroup = new NioEventLoopGroup();

    public static void startServer() throws Exception {
        ControllerScanner.scanner();
        start();
        join();
        shutDownHook();
    }

    private static void start() throws InterruptedException {
        ServerBootstrap bootstrap = new ServerBootstrap()
                .group(bossGroup, workerGroup)
                .channel(NioServerSocketChannel.class)
                .childHandler(new HttpChannelHandler());
        channelFuture = bootstrap.bind(ServerConfig.INSTANCE.getPort()).sync();
        if (channelFuture.isSuccess()) {
            logger.info("server is start !");
        }
    }

    private static void join() throws InterruptedException {
        channelFuture.channel().closeFuture().sync();
    }

    private static void shutDownHook() {
        NettyShutDownHook shutDownHook = new NettyShutDownHook();
        shutDownHook.setName("SHUT_DOWN_THREAD");
        Runtime.getRuntime().addShutdownHook(shutDownHook);
    }

    static class NettyShutDownHook extends Thread {
        @Override public void run() {
            logger.info("server is starting stop...");

            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();

            logger.info("server is has been successfully stopped.");
        }
    }
}
