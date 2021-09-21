package io.github.nhannht.vermindrive.service;

import io.github.nhannht.vermindrive.mapper.UserMapper;
import io.github.nhannht.vermindrive.model.User;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;

@Service
public class UserService {

    private final UserMapper userMapper;
    private final HashService hashService;

    public UserService(UserMapper userMapper, HashService hashService) {
        this.userMapper = userMapper;
        this.hashService = hashService;
    }

    public boolean isUsernameAvailable(String username) {
        return userMapper.getUser(username) == null;
    }

    public int createUser(User user) throws NoSuchAlgorithmException, InvalidKeySpecException {
        String encodedSalt = createSalt();
        String hashedPassword = hashService.getHashedValue(user.getPassword(), encodedSalt);

        return userMapper.insert(
                new User(
                        null,
                        user.getUserName(),
                        encodedSalt,
                        hashedPassword,
                        user.getFirstName(),
                        user.getLastName()
                )
        );
    }

    public User getUser(String username) {
        return userMapper.getUser(username);
    }

    private String createSalt() {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);

        return Base64.getEncoder().encodeToString(salt);
    }


}
