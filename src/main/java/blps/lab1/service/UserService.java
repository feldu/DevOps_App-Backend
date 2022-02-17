package blps.lab1.service;

import blps.lab1.entity.User;
import blps.lab1.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Slf4j
@Service
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    @Transactional
    public User loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            log.debug("User {} not found in DB", username);
            throw new UsernameNotFoundException("User not found in DB");
        }
        log.debug("User {} found in DB", username);
        return user;
    }

    public boolean saveUser(User user) {
        if (userRepository.findByUsername(user.getUsername()) != null) {
            log.debug("User {} already exists in DB", user.getUsername());
            return false;
        }
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        log.debug("User {} saved in DB", user.getUsername());
        return true;
    }

    public void updateUser(Long id, User newUser) {
        User user = userRepository.findById(id).orElse(null);
        if (user == null) {
            log.debug("User {} not found in DB", newUser.getUsername());
            throw new UsernameNotFoundException("User not found in DB");
        }
        user.setEmail(newUser.getEmail());
        user.setName(newUser.getName());
        user.setCity(newUser.getCity());
        user.setStartDrivingYear(newUser.getStartDrivingYear());
        user.setBirthDate(newUser.getBirthDate());
        user.setJob(newUser.getJob());
        user.setHobby(newUser.getHobby());
        userRepository.save(user);
    }
}
