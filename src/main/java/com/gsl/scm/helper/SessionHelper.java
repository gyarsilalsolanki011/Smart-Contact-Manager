package com.gsl.scm.helper;

import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Component
public class SessionHelper {

    private static final Logger log = LoggerFactory.getLogger(SessionHelper.class);

    public static void removeMessage() {
        try {
            log.info("removing message from session");
            HttpSession session = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest()
                    .getSession();
            session.removeAttribute("message");
        } catch (Exception e) {
            log.error("Error removing message from session", e);
        }
    }
}
