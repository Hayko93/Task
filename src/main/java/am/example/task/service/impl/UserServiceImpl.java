package am.example.task.service.impl;

import am.example.task.entity.UserEntity;
import am.example.task.repository.RoleRepository;
import am.example.task.repository.UserRepository;
import am.example.task.repository.redis.Redis;
import am.example.task.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.*;

@Service
@Slf4j
public class UserServiceImpl implements UserService {
    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private BCryptPasswordEncoder passwordEncoder;
    private MailService mailService;
    private Redis redis;


    @Autowired
    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, MailService mailService, BCryptPasswordEncoder passwordEncoder, Redis redis) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.mailService = mailService;
        this.redis = redis;
    }


    @Override
    public void add(UserEntity userEntity) {
        userEntity.setActiveCode(UUID.randomUUID().toString());
        userEntity.setPassword(passwordEncoder.encode(userEntity.getPassword()));
        userRepository.save(userEntity);
        if (!StringUtils.isEmpty(userEntity.getEmail())){
            String message = String.format(
                    "Hello %s \n . Please to next link: http://localhost:8080/activate/%s",
                    userEntity.getFirstName(),userEntity.getActiveCode());
            mailService.sendSimplMesage(userEntity.getEmail(),"Activation code", message);
            log.info("Send Email");
        }
    }

    @Override
    public boolean activateUser(String code) {
        UserEntity user = userRepository.findByActiveCode(code);
        if (user == null){
            return false;
        }
        user.setActiveCode(null);
        user.setRoles(roleRepository.findByRole("USER"));
        userRepository.save(user);
        log.info("Activate user");
        return true;
    }



    @Override
    public UserEntity getUser(int id) {
        Optional<UserEntity> user = redis.getUser(String.valueOf(id));
        if (!user.isPresent()) {
            user = redis.addUser(String.valueOf(id),userRepository.findById(id),70000);
        }
        return user.get();
    }

    @Override
    public List<UserEntity> getAll() {
        return userRepository.findAll();
    }

    @Override
    public Optional<UserEntity> get(String email, String password) {
        return userRepository.findByEmailAndPassword(email, password);
    }

    @Override
    public boolean existEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    public UserEntity getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }


}
