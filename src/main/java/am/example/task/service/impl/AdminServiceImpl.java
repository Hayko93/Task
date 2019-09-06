package am.example.task.service.impl;

import am.example.task.entity.BoardEntity;
import am.example.task.entity.BoardMemberEntity;
import am.example.task.entity.UserEntity;
import am.example.task.repository.BoardMemberRepository;
import am.example.task.repository.BoardRepository;
import am.example.task.repository.RoleRepository;
import am.example.task.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminServiceImpl implements AdminService {
    private BoardRepository boardRepository;
    private BoardMemberRepository boardMemberRepository;
    private RoleRepository roleRepository;

    @Autowired
    public AdminServiceImpl(BoardRepository boardRepository, BoardMemberRepository boardMemberRepository, RoleRepository roleRepository) {
        this.boardRepository = boardRepository;
        this.boardMemberRepository = boardMemberRepository;
        this.roleRepository = roleRepository;
    }

    @Override
    public BoardEntity addBoard(BoardEntity boardEntity) {
        UserEntity admin = boardEntity.getAdmin();
        BoardMemberEntity boardMember = new BoardMemberEntity(admin, roleRepository.findByRole("ADMIN"),boardEntity);
        admin.setBoardMembersEntities(boardMemberRepository.save(boardMember));
        admin.setRoles(roleRepository.findByRole("ADMIN"));
        return boardRepository.save(boardEntity);
    }

    @Override
    public BoardMemberEntity addBoardMember(BoardMemberEntity boardMemberEntity) {
        boardMemberEntity.setRoleEntity(roleRepository.findByRole("USER"));
        return boardMemberEntity;
    }

    @Override
    public void deleteBoardMember(Integer id) {
        boardMemberRepository.deleteById(id);
    }
}
