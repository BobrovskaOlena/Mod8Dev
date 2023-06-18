import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.TimeZone;

@WebFilter("/time?timezone=")
public class TimezoneValidateFilter implements Filter {
    public void init(FilterConfig config) throws ServletException {
        // Ініціалізаційний код (за потреби)
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        String timeZoneParam = httpRequest.getParameter("timezone");

        if (timeZoneParam != null && !timeZoneParam.isEmpty()) {
            if (!isValidTimeZone(timeZoneParam)) {
                httpResponse.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid timezone");
                return;
            }
        }

        chain.doFilter(request, response);
    }

    public void destroy() {
        // Код очищення (за потреби)
    }

    private boolean isValidTimeZone(String timeZoneParam) {
        try {
            TimeZone.getTimeZone(timeZoneParam);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}