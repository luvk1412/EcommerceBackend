package application.interceptor;

import application.exception.AppException;
import application.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static application.model.Constants.*;

@Component
public class AuthInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    UserService userService;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if(request.getHeader(HEADER_USER_ID) != null && request.getHeader(HEADER_PASSWORD) != null)
            if(userService.authenticateUser(Integer.parseInt(request.getHeader(HEADER_USER_ID)), request.getHeader(HEADER_PASSWORD)))
                return super.preHandle(request, response, handler);
        throw new AppException(HttpStatus.UNAUTHORIZED, MESSAGE_UNAUTHORISED);
    }

}
