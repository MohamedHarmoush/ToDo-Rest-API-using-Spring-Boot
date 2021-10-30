package com.harmoush.utils;

import com.harmoush.security.AppUser;
import com.harmoush.security.UserService;
import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class FirstTimeInitializer implements CommandLineRunner {

    private final Log logger = LogFactory.getLog(FirstTimeInitializer.class);

    @Autowired
    private UserService userService;

    @Override
    public void run(String... args) throws Exception {
        if (userService.getAllUsers().isEmpty()) {
            logger.info("No users accounts found. Creating some users.");
            userService.saveUser(new AppUser("Harmoush", "mkotb.harmoush@gmail.com", "12346"));
        }
    }
}
