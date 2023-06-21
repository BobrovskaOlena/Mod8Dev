import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter("/time")
public class TimeZoneValidateFilter extends HttpFilter {
    @Override
    protected void doFilter(HttpServletRequest req, HttpServletResponse resp, FilterChain chain) throws IOException, ServletException {
        String timeZoneParam = req.getParameter("timezone");

        if (timeZoneParam == null || timeZoneParam.isEmpty()) {
            timeZoneParam = "UTC+3";
        } else if (!timeZoneParam.matches("^UTC([01]?\\d|2[0-3])(\\+|-)[0-9]{1,2}$")) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid timezone");
            return;
        }

        req.setAttribute("timezone", timeZoneParam);
        chain.doFilter(req, resp);
    }
}







