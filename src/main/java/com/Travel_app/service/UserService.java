package com.Travel_app.service;

import com.Travel_app.db.model.Tip;
import com.Travel_app.db.model.User;
import com.Travel_app.db.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public void save(User user) {
        //BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        //String encodedPassword = encoder.encode(user.getPassword());
        //user.setPassword(encodedPassword);

        //if(user.getRoles().isEmpty()){
          //  Role roleUser = roleRepo.findByName("LIMITED_USER");
            //user.addRole(roleUser);
        //}

        userRepository.save(user);
    }

    public Object findAll() {
        return this.userRepository.findAll();
    }

    public Object getById(Long id) {
        Optional<User> user=userRepository.findById(id);
        return user.isEmpty()? null:user.get();
    }

    public void updateUser(Long id, User user) {
        User u = userRepository.findById(id).get();
        u.setUsername(user.getUsername());
        u.setEmail(user.getEmail());
        u.setName(user.getName());
        u.setSurname(user.getSurname());
        u.setAbout(user.getAbout());
        this.userRepository.save(u);
    }

    public void deleteUser(Long id) {
        User user = userRepository.getById(id).get();
        this.userRepository.delete(user);
    }

    public User findByLogin(String login) {
        User user = userRepository.findByLogin(login);
        return user.get();
    }

    public Boolean findByEmail(String email){
        User user = userRepository.findByEmail(email);
        if (user != null){
            return true;
        }
        else return false;
    }

    public void updateResetPasswordToken(String token, String email){
        User user = userRepository.findByEmail(email);
        user.setResetPasswordToken(token);
        userRepository.save(user);
    }

    public User getByResetPasswordToken(String token){
        return userRepository.findByResetPasswordToken(token);
    }

    public void updatePassword(User user, String newPassword){
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String encodedPassword = encoder.encode(newPassword);
        user.setPassword(encodedPassword);
        user.setResetPasswordToken(null);
        userRepository.save(user);
    }
}
