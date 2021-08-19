package lu.isd.jetty.controller;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import lu.isd.jetty.model.echo.Echo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@Path("json")
public class EchoControllerJson {

   static int counter = 0;

   private static final Logger log = LoggerFactory.getLogger(EchoControllerJson.class);

   @GET
   @Path("/{message}")
   @Produces(MediaType.APPLICATION_JSON)
   public Echo get(@PathParam("message") String message) {
      var response = new Echo();
      response.setMessage(message + " " + counter);
      counter++;
      return response;
   }


}
