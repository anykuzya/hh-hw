package resource;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

@Path("/")
public class ExampleResource {

    @GET
    @Path(value = "/")
    public Response get() {
        return Response.ok("Hello!").build();
    }
}
