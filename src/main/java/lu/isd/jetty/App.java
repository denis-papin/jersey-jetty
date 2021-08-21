package lu.isd.jetty;

import com.fasterxml.jackson.core.util.JacksonFeature;
import lu.isd.jetty.controller.EchoControllerJson;
import lu.isd.jetty.controller.EchoControllerXml;
import org.eclipse.jetty.server.Server;
import org.glassfish.jersey.moxy.xml.MoxyXmlFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.TimeZone;

/**
 * Reservation Micro Service
 */
public class App {

    public static final String APP_NAME = "jetty-11";

    private static final Logger log = LoggerFactory.getLogger(App.class);

    final static int SERVER_PORT = 10020;

    public static void main(String[] args) {

        // Very important to handle DB dates correctly.
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));

        try {

            System.out.println("Service (Jersey + Jetty).");

            ResourceConfig resourceConfig = new ResourceConfig(
                    EchoControllerXml.class, EchoControllerJson.class
            )
                    .register(MoxyXmlFeature.class);
                    //.register(JacksonFeature.class);


            resourceConfig.register(CORSResponseFilter.class);

            log.info("Creating the http server");

            // Create a Http server on the given port
            var httpServer = new HttpServer(SERVER_PORT);
            Server server = httpServer.createHttp(resourceConfig);
            server.start();

            Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {

                final Logger log = LoggerFactory.getLogger(App.class);

                @Override
                public void run() {
                    try {
                        log.info("Shutting down...");
                        server.stop();
                    } catch (Exception e) {
                        log.error("Failed to stop the server", e);
                    }
                }
            }));

            log.info("HTTP Application started. Try out [{}][{}]\n", "http://localhost:" , SERVER_PORT);


            Thread.currentThread().join();
        } catch (Exception ex) {
            Logger log = LoggerFactory.getLogger("birdy-server");
            log.error("Failed to stop the server", ex);
        }

    }
}
