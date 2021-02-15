import common.Counter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Optional;

@WebServlet(urlPatterns = "/")
public class ServletCounterReset extends HttpServlet {
    private Counter counter;
    public void init() {
        counter = (Counter) getServletContext().getAttribute("counter");
    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Cookie[] cookies = req.getCookies();
        Optional<Cookie> auth = cookies == null ? Optional.empty() :
            Arrays.stream(cookies)
                .filter(cookie -> cookie.getName().equals("hh-auth"))
                .findFirst();
        if (auth.isEmpty() || auth.get().getValue().length() < 10) {
            PrintWriter writer = resp.getWriter();
            writer.print("You should pass hh-auth cookie more then 10 symbols");
            writer.flush();
            resp.setStatus(HttpServletResponse.SC_FORBIDDEN);
            System.err.println("received request " + (auth.isEmpty() ? "without a hh-auth cookie"
                : "with too short hh-auth cookie: " + auth.get().getValue()));
        } else {
            counter.reset();
        }
    }

}
