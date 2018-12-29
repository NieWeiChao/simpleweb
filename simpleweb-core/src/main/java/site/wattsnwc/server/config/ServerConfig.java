package site.wattsnwc.server.config;

/**
 * msg
 *
 * @author watts
 */
public final class ServerConfig {
    private ServerConfig() {
    }

    public static final ServerConfig INSTANCE = new ServerConfig();

    private Integer port = 8070;

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }
}
