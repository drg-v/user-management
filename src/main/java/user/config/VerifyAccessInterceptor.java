package user.config;

import java.util.Arrays;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import user.entities.User;
import user.repositories.UserRepository;

@Component
public class VerifyAccessInterceptor implements HandlerInterceptor {

    @Autowired
    UserRepository repository;
    
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = repository.findByName(auth.getName());
        String status = user != null ? user.getStatus() : "deleted";
        Authentication reloadedAuth = new UsernamePasswordAuthenticationToken(user.getName(),
                user.getPassword(), Arrays.asList(new SimpleGrantedAuthority(status)));
        SecurityContextHolder.getContext().setAuthentication(reloadedAuth);
        return true;
    }

}
