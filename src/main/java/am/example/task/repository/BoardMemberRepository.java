package am.example.task.repository;

import am.example.task.entity.BoardMemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BoardMemberRepository extends JpaRepository<BoardMemberEntity, Integer> {
}
