package com.bookstore.service.impl;

import com.bookstore.entity.Role;
import com.bookstore.entity.User;
import com.bookstore.repository.RoleRepository;
import com.bookstore.repository.UserRepository;
import com.bookstore.service.ISecurityService;
import com.bookstore.utils.CustomUserDetails;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@Transactional
public class UserDetailsServiceImpl implements ISecurityService, UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        User user = userRepository.findByUserName(userName);

        if(user == null) {
            throw new UsernameNotFoundException("User " + userName + " was not found in the database");
        }

        Set<Role> roles = user.getRoles();

        List<GrantedAuthority> grantList = new ArrayList<GrantedAuthority>();

        if(roles != null) {
            for(Role role: roles) {
                GrantedAuthority authority = new SimpleGrantedAuthority(role.getRoleName());
                grantList.add(authority);
            }
        }

        UserDetails userDetails = new CustomUserDetails(user.getUserName(),
                user.getEncryptedPassword(),
                grantList);

        return userDetails;
    }

    public User createUser(String username, String password, Set<Role> roles) {
        User user =  new User(username, passwordEncoder.encode(password), roles);
        return user;
    }

    @Override
    @Transactional
    public void generateUsersRoles() {
        Role roleAdmin = new Role("ROLE_ADMIN");
        Role roleUser = new Role("ROLE_USER");

        roleRepository.save(roleAdmin);
        roleRepository.save(roleUser);
        roleRepository.flush();

        Set<Role> adminRoles = new HashSet<>();
        adminRoles.add(roleAdmin);
        User admin = createUser("admin", "123", adminRoles);
        userRepository.save(admin);

        Set<Role> userRoles = new HashSet<>();
        userRoles.add(roleUser);
        User user = createUser("user", "123", userRoles);
        userRepository.save(user);

        userRepository.flush();
    }

}
