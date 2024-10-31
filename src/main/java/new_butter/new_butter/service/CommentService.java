package new_butter.new_butter.service;

import new_butter.new_butter.domain.Comment;
import new_butter.new_butter.payload.DTO.CommentDTO;

import java.util.List;

public interface CommentService {
    Comment saveComment(CommentDTO commentDTO);
    List<CommentDTO> getCommentsByPostId(Long postId);

    boolean deleteComment(Long commentId);

    public void deleteCommentsByPostId (Long postId);
}
