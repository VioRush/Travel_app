package com.Travel_app.db.model;

import com.Travel_app.db.model.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

public class CustomUserDetails implements UserDetails {

    private User user;

    public CustomUserDetails(User user) {
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<SimpleGrantedAuthority> authorityList = new ArrayList<>();
        if (user.getLogin().equals("admin")){
            System.out.println("admin");
            authorityList.add(new SimpleGrantedAuthority("ADMIN"));
        }
        else{
            System.out.println("user");
            authorityList.add(new SimpleGrantedAuthority("USER"));
        }
        return authorityList;
    }

    @Override
    public String getPassword() {
        System.out.println("Passw w CUD:" + user.getPassword());
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        System.out.println("Login w CUD:" + user.getLogin());
        return user.getLogin();
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

    public String getFullName() {
        return user.getUsername();
    }

}