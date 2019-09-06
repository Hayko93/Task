package am.example.task.service;

import am.example.task.entity.BoardEntity;
import am.example.task.entity.BoardMemberEntity;

public interface AdminService {
    BoardEntity addBoard(BoardEntity boardEntity);
    BoardMemberEntity addBoardMember(BoardMemberEntity boardMemberEntity);
    void deleteBoardMember(Integer id);
}

