package hr.winary.webshop.jwpwinary.service;

import hr.winary.webshop.jwpwinary.model.User;
import hr.winary.webshop.jwpwinary.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class MyUserDetailService implements UserDetailsService {

    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException(username);
        }

        String[] roles = new String[user.getRoles().size()];

        for(int i = 0; i < user.getRoles().size(); i++) {
            roles[i] = user.getRoles().get(i).getName();
        }

        return org.springframework.security.core.userdetails.User
                .withUsername(user.getUsername())
                .password(user.getPassword())
                .roles(roles)
                .accountExpired(false)
                .accountLocked(false)
                .credentialsExpired(false)
                .disabled(false)
                .build();
    }


    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

}
