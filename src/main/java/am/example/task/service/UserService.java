package am.example.task.service;

import am.example.task.entity.BoardEntity;
import am.example.task.entity.UserEntity;

import java.util.List;
import java.util.Optional;

public interface UserService {
    void add(UserEntity user);
    List<UserEntity> getAll();
    Optional<UserEntity> get(String email, String password);
    boolean existEmail(String email);
    UserEntity getUserByEmail(String email);
    boolean activateUser(String code);
    UserEntity getUser(int id);
}
