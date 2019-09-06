package am.example.task.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "board_member")
public class BoardMemberEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @ManyToOne(cascade = {CascadeType.PERSIST,CascadeType.REMOVE,CascadeType.REFRESH})
    @JoinColumn(name = "user_id")
    private UserEntity userEntity;

    @ManyToOne(cascade = {CascadeType.PERSIST,CascadeType.REMOVE,CascadeType.REFRESH})
    @JoinColumn(name = "role_id")
    private RoleEntity roleEntity;


    @ManyToOne(cascade = {CascadeType.PERSIST,CascadeType.REMOVE,CascadeType.REFRESH})
    @JoinColumn(name = "board_id")
    private BoardEntity boardEntity;

    public BoardMemberEntity(UserEntity userEntity, BoardEntity boardEntity){
        this.userEntity = userEntity;
        this.boardEntity = boardEntity;

    }
    public BoardMemberEntity(UserEntity userEntity,RoleEntity roleEntity, BoardEntity boardEntity){
        this.userEntity = userEntity;
        this.boardEntity = boardEntity;
        this.roleEntity = roleEntity;

    }

}
