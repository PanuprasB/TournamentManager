package com.SBTM.TournamentManager.Service;

import com.SBTM.TournamentManager.Model.User;
import com.SBTM.TournamentManager.Repos.UserRepos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepos userRepos;

    public void registerUser(User user){
        if(userRepos.existsByUsername(user.getUsername())){
            throw new RuntimeException("Username already exists");
        }
        userRepos.save(user);
    }


    public List<User> getAllUsers() {
        return userRepos.findAll();
    }

    public User getUserById(Long id) {
        return userRepos.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
    }
    public User getUserByUsername(String username) {
        return userRepos.findByUsername(username).orElse(null);
    }
}