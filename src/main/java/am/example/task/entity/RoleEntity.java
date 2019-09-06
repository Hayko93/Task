package am.example.task.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "role")
public class RoleEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;

  @Column(name = "role")
  private String role;

  @JsonIgnore
  @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.REMOVE}, mappedBy = "roleEntity")
  private Set<BoardMemberEntity> boardMemberEntities;

  public RoleEntity( String role){
    this.role = role;
  }
}
