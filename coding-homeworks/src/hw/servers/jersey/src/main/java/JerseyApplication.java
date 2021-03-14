import common.Counter;

import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.DefaultHandler;
import org.eclipse.jetty.server.handler.HandlerCollection;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.glassfish.jersey.servlet.ServletContainer;
import resource.CounterResource;

import javax.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;

class Config extends Application {
  @Override
  public Set<Class<?>> getClasses() {
    return new HashSet<>(){{ add(CounterResource.class); }};
  }
}
public class JerseyApplication extends Server {

  public JerseyApplication(int port) {
    super(port);
    Counter counter = new Counter();

    HandlerCollection handlers = new HandlerCollection();
    ServletContextHandler context = new ServletContextHandler();
    context.setAttribute("counter", counter);
    ServletHolder holder = context.addServlet(ServletContainer.class, "/*");
    holder.setInitOrder(1);
    holder.setInitParameter("javax.ws.rs.Application", "Config");
    handlers.setHandlers(new Handler[]{context, new DefaultHandler()});
    setHandler(handlers);
  }

  public static void main(String[] args) throws Exception {
    int port = 8080;
    Server server = new JerseyApplication(port);
    server.start();
    server.join();
  }
}