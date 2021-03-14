package resource;

import common.Counter;

import javax.servlet.ServletContext;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import java.time.LocalDateTime;

class DateValueDTO {
    private String date;
    private Integer value;

    public DateValueDTO(String date, Integer value) {
        this.date = date;
        this.value = value;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }
}
@Path("/counter")

public class CounterResource {
    private final Counter counter;
    public CounterResource(@Context ServletContext context) {
        counter = (Counter) context.getAttribute("counter");
    }

    @GET
    @Produces("application/json")
    public Response get() {
        DateValueDTO response = new DateValueDTO(LocalDateTime.now().toString(), counter.get());
        return Response.ok(response).build();
    }

    @POST
    public Response post() {
        counter.inc();
        return Response.ok().build();
    }

    @DELETE
    public Response decrement(@HeaderParam("Subtraction-Value") Integer value) {
        if (value == null) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        counter.dec(value);
        return Response.ok().build();
    }

    @POST
    @Path(value = "/clear")
    public Response reset(@CookieParam("hh-auth") String auth) {
        if (auth == null || auth.length() < 10) {
            System.err.println("received request " + (auth == null ? "without a hh-auth cookie"
                : "with too short hh-auth cookie: " + auth));
            return Response.status(Response.Status.FORBIDDEN).build();
        } else {
            counter.reset();
            return Response.ok().build();
        }
    }

}
