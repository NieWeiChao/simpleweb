package site.wattsnwc;

import site.wattsnwc.server.bootstrap.NettyBootstrap;

/**
 * msg
 *
 * @author watts
 */
public class SampleApplication {
    public static void main(String[] args) throws Exception {
        NettyBootstrap.startServer(18080);
    }
}
