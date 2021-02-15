import common.Counter;
//import org.slf4j.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(urlPatterns = "/counter")
public class ServletCounter extends HttpServlet {
    private Counter counter;
    public void init() {
        counter = (Counter) getServletContext().getAttribute("counter");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter writer = resp.getWriter();
        writer.print(counter.get());
        writer.flush();
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String decValHeader = "Subtraction-Value";
        String decVal = req.getHeader(decValHeader);
        if (decVal == null) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            System.err.println("received request without Subtraction-Value header");
        } else {
            try {
                counter.dec(req.getIntHeader(decValHeader));
            } catch (NumberFormatException e) {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                System.err.println("received request with not a number in the Subtraction-Value header");
            }
        }

    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        counter.inc();
    }

}
