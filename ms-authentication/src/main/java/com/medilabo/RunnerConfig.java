package com.medilabo;

import com.medilabo.persistence.UserCredential;
import com.medilabo.persistence.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

@Configuration
@Slf4j
@RequiredArgsConstructor
public class RunnerConfig implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder encoder;

    @Override
    public void run(String... args) {
        log.info("init in memory users");
        UserCredential omar = new UserCredential("omar", encoder.encode("passer@123"), true);
        UserCredential robin = new UserCredential("robin", encoder.encode("passer"), true);
        UserCredential alain = new UserCredential("alain", encoder.encode("passer123"), false);
        userRepository.saveAll(List.of(omar, robin, alain));
        log.info("users saved in DB");
    }
}
