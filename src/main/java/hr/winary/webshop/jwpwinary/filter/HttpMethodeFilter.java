package hr.winary.webshop.jwpwinary.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@Slf4j
public class HttpMethodeFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        String httpMethod = ((HttpServletRequest) servletRequest).getMethod();
        log.info(httpMethod);
        filterChain.doFilter(servletRequest, servletResponse);
    }
}
