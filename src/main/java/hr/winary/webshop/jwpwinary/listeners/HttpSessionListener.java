package hr.winary.webshop.jwpwinary.listeners;

import hr.winary.webshop.jwpwinary.service.UserLoginService;
import jakarta.servlet.http.HttpSessionEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Component
public class HttpSessionListener implements jakarta.servlet.http.HttpSessionListener {
    @Autowired
    private UserLoginService userLoginService;

    @Override
    public void sessionCreated(HttpSessionEvent event) {
        String ipAddr = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes())
                .getRequest().getRemoteAddr();

        System.out.println("Session created");
        System.out.println(ipAddr);

        userLoginService.logUserLogin(ipAddr);
    }
    @Override
    public void sessionDestroyed(final HttpSessionEvent event) {
        System.out.println("Session destroyed");
    }
}
