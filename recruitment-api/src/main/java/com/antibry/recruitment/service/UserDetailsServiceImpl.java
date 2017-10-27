package com.antibry.recruitment.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.antibry.recruitment.domain.acl.UserAccount;
import com.antibry.recruitment.repository.UserAccountRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    
	private UserAccountRepository userAccountRepo;

    public UserDetailsServiceImpl(UserAccountRepository userAccountRepository) {
        this.userAccountRepo = userAccountRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserAccount userAccount = userAccountRepo.findByUsername(username);
        
        if (userAccount == null) throw new UsernameNotFoundException(username);

        List<GrantedAuthority> authorities = new ArrayList<>();
        
        userAccount.getRoles().forEach(role -> {
        		authorities.add(new SimpleGrantedAuthority(role.getAuthority()));
        });
        
        return new User(userAccount.getUsername(), userAccount.getPassword(), authorities);
    }
}