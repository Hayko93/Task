package am.example.task.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.UniqueElements;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.Set;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user")
public class UserEntity implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;

  @NotEmpty(message = "*Please provide your first name")
  @NotEmpty(message = "*Please provide your first name")
  @Column(name = "first_name")
  private String firstName;

  @NotEmpty(message = "*Please provide your last name")
  @Column(name = "last_name")
  @NotEmpty(message = "*Please provide your last name")
  private String lastName;

  @Email(message = "*Please provide a valid Email")@NotEmpty(message = "*Please provide an email")
  @Email(message = "*Please provide a valid Email")
  @NotEmpty(message = "*Please provide an email")
  @Column(name = "email")
  private String email;

  @Length(min = 5, message = "*Your password must have at least 5 characters")@NotEmpty(message = "*Please provide your password")
  @JsonIgnore
  @Length(min = 5, message = "*Your password must have at least 5 characters")
  @NotEmpty(message = "*Please provide your password")
  @Column(name = "password")
  private String password;

  @Column(name = "active", columnDefinition = "TINYINT")
  private int active;

  @Column(name = "active_code")
  private String activeCode;



  @JsonIgnore
  @UniqueElements
  @ManyToMany(cascade = CascadeType.ALL)
  @JoinTable(name = "user_role",
          joinColumns = @JoinColumn(name = "user_id"),
          inverseJoinColumns = @JoinColumn(name = "role_id"))
  private Set<RoleEntity> roles;


  @JsonIgnore
  @OneToMany(cascade = {CascadeType.REMOVE, CascadeType.PERSIST},mappedBy = "admin")
  private Set<BoardEntity> board;

  @JsonIgnore
  @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.REMOVE}, mappedBy = "userEntity")
  private Set<BoardMemberEntity> boardMembersEntities;

  public UserEntity(String firstName, String lastName, String email, String password, int active){
    this.firstName = firstName;
    this.lastName = lastName;
    this.email = email;
    this.password = password;
    this.active = active;
  }

  public void setRoles( RoleEntity roles) {
    this.roles.add(roles);
  }

  public void setBoard(BoardEntity board){
    this.board.add(board);
  }

  public void setBoardMembersEntities(BoardMemberEntity boardMemberEntity){
    this.boardMembersEntities.add(boardMemberEntity);
  }


}
