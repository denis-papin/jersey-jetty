package lu.isd.jetty;

import jakarta.ws.rs.core.UriBuilder;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.server.handler.ContextHandler;
import org.glassfish.jersey.jetty.JettyHttpContainerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class HttpServer {

    private static final Logger log = LoggerFactory.getLogger(HttpServer.class);

    private Integer basePort = 9999;

    public HttpServer(Integer basePort) {
        this.basePort = basePort;
    }


    public Server createHttp(ResourceConfig resourceConfig) throws IOException, URISyntaxException {

        final String path = "/";
        URI appUri = UriBuilder.fromUri("http://localhost/").port(basePort).build();
        Server server = JettyHttpContainerFactory.createServer(appUri, resourceConfig, false);

        log.trace("Server is [{}]", server);

        var c = server.getConnectors();
        var cc = (ServerConnector) c[0];
        cc.setAcceptQueueSize(128);

        // Wrapping handler required to scope the resources under desired context
        final ContextHandler handler = new ContextHandler();
        handler.setContextPath(path);
        handler.setHandler(server.getHandler());
        server.setHandler(handler);

        return server;
    }


}
