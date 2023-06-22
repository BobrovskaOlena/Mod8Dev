import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.TimeZone;

@WebFilter("/time")
public class TimeZoneValidateFilter extends HttpFilter {
    @Override
    protected void doFilter(HttpServletRequest req, HttpServletResponse resp, FilterChain chain)
            throws IOException, ServletException {
        String timeZoneParam = req.getParameter("timezone");

        if (timeZoneParam == null || timeZoneParam.isEmpty()) {
            timeZoneParam = TimeZone.getDefault().getID();
        } else if (!timeZoneParam.matches("^UTC([+-])(0?[0-9]|1[0-9]|2[0-3])$")) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid timezone");
            return;
        }

        req.setAttribute("timezone", timeZoneParam);
        chain.doFilter(req, resp);
    }
}










