package am.example.task.repository;

import am.example.task.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
  Optional<UserEntity> findByEmailAndPassword(String email, String password);
  boolean existsByEmail(String email);
  UserEntity findByEmail(String email);
  UserEntity findById(int userId);
  UserEntity findByActiveCode(String code);

}
