package lu.isd.jetty.controller;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import lu.isd.jetty.model.echo.EchoXml;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@Path("xml")
public class EchoControllerXml {

   static int counter = 0;

   private static final Logger log = LoggerFactory.getLogger(EchoControllerXml.class);


   @GET
   @Path("/{message}")
   @Produces(MediaType.APPLICATION_XML)
   public EchoXml getXML(@PathParam("message") String message) {
      log.info("Start the xml echo...");
      var response = new EchoXml();
      response.setMessage(message + " " + counter);
      counter++;
      return response;
   }

}
