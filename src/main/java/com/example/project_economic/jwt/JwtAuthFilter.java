package com.example.project_economic.jwt;

import com.example.project_economic.config.UserInfoDetailService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    @Autowired
    private JwtService jwtService;

    @Autowired
    private UserInfoDetailService userInfoDetailService;
    @Override
    protected void doFilterInternal(HttpServletRequest request
            , HttpServletResponse response
            , FilterChain filterChain
    ) throws ServletException, IOException {
        String auth=request.getHeader("Authorization");
        String token=null;
        String username=null;
        if(auth!=null&&auth.startsWith("Bearer ")){
            token=auth.substring(7);
            username=jwtService.extractUsernameFromToken(token);
            System.out.println(username);
        }

        if(username!=null&& SecurityContextHolder.getContext().getAuthentication()==null){
            UserDetails userDetails=userInfoDetailService.loadUserByUsername(username);
            if(this.jwtService.isTokenValid(token,userDetails)){
                UsernamePasswordAuthenticationToken authenticationToken=new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities()
                );
                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
        }
        filterChain.doFilter(request,response);
    }
}
