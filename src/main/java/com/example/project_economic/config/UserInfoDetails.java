package com.example.project_economic.config;
import com.example.project_economic.entity.UserEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
public class UserInfoDetails implements UserDetails {
    private Long userId;
    private String username;
    private String password;
    private List<GrantedAuthority>authorities;
    public UserInfoDetails(UserEntity userEntity){
        this.userId=userEntity.getId();
        this.username=userEntity.getUsername();
        this.password=userEntity.getPassword();
        String roles=userEntity.getRoles();
        this.authorities= Arrays.stream(roles.trim().split(","))
                .map(role->new SimpleGrantedAuthority(role))
                .collect(Collectors.toList());
    }
    public Long getUserId(){
        return this.userId;
    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }
    @Override
    public String getPassword() {
        return this.password;
    }
    @Override
    public String getUsername() {
        return this.username;
    }
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
    @Override
    public boolean isEnabled() {
        return true;
    }
}
