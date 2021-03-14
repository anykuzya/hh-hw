import common.Counter;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;

class EmbeddedJetty {
  public static Server createServer(int port, Counter counter) {
    Server server = new Server(port);
    ServletContextHandler contextHandler = new ServletContextHandler();
    contextHandler.setAttribute("counter", counter);
    contextHandler.addServlet(ServletCounter.class, "/counter");
    contextHandler.addServlet(ServletCounterReset.class, "/counter/clear");
    server.setHandler(contextHandler);
    return server;
  }
}

public class ServletApplication {

  public static void main(String[] args) throws Exception {
    Server jetty = EmbeddedJetty.createServer(8080, new Counter());
    jetty.start();
    jetty.join();
  }
}
