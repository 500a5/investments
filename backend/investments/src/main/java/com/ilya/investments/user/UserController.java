package com.ilya.investments.user;

import com.ilya.investments.repo.UserRepositiry;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;




@RestController
@RequestMapping("/api/user")
public class UserController {

        UserRepositiry userRepositiry;

    @GetMapping
    public List<User> list()
    {
        return userRepositiry.findAll();
    }

    @GetMapping("/{id}")
    public User user(@PathVariable int id)
    {
        return userRepositiry.findById(id).orElse(null);
    }

        @GetMapping("/login")
        private ResponseEntity<String> login(@RequestParam String name,
                                             @RequestParam String password) {
            User user = userRepositiry.findByName(name);
            if (user != null)
            {
                if (!password.equals(user.getPassword()))
                {
                    return new ResponseEntity<>("Invalid password", HttpStatus.BAD_REQUEST);
                }
            }
            else
            {
                return new ResponseEntity<>("User doesn't exists", HttpStatus.BAD_REQUEST);
            }

            return new ResponseEntity<>("Success", HttpStatus.OK);
        }

        @PostMapping("/register")
        private Boolean register(@RequestParam String name,
                                 @RequestParam String password)
        {
            User user = new User(name,password);
            userRepositiry.save(user);
            return true;
        }


}
