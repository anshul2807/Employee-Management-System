package com.anshul.EmployeeManagementSystem.LogManager;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class RequestLoggingInterceptor implements HandlerInterceptor {

    private static final Logger AUDIT_LOGGER =
            LoggerFactory.getLogger("REQUEST_AUDIT_LOG");

    @Override
    public boolean preHandle(
            HttpServletRequest request,
            HttpServletResponse response,
            Object handler) throws Exception {

        String remoteAddr = request.getRemoteAddr();
        String method = request.getMethod();
        String requestUri = request.getRequestURI();

        // --- AUTHENTICATION LOGIC ---
        String username = "ANONYMOUS";
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.isAuthenticated()) {
            // Check if the principal is a String (e.g., in a simple token setup)
            // or an instance of UserDetails (common in Spring Security)
            Object principal = authentication.getPrincipal();
            username = authentication.getName();
        }

        // Log the key request details including the username
        AUDIT_LOGGER.info("USER: {}; IP: {}; Method: {}; URI: {}",
                username,
                remoteAddr,
                method,
                requestUri);

        return true; // Continue processing the request
    }
}