package new_butter.new_butter.service.impl;

import new_butter.new_butter.domain.Comment;
import new_butter.new_butter.domain.Post;
import new_butter.new_butter.payload.DTO.CommentDTO;
import new_butter.new_butter.repository.CommentRepository;
import new_butter.new_butter.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService  {
    @Autowired
    private CommentRepository commentRepository;

    // Lưu bình luận mới
    public Comment saveComment(CommentDTO commentDTO) {
        Comment comment = new Comment(); // Tạo đối tượng Comment mới
        comment.setContent(commentDTO.getContent());
        comment.setUserId(commentDTO.getUserId()); // Thiết lập userId từ commentDTO
        comment.setAuthor(commentDTO.getAuthor());
        // Tạo đối tượng Post dựa trên postId và thiết lập cho Comment
        Post post = new Post();
        post.setId(commentDTO.getPostId()); // Thiết lập ID của bài viết
        comment.setPost(post); // Thiết lập bài viết cho bình luận

        return commentRepository.save(comment); // Lưu bình luận vào repository
    }
    // Lấy danh sách bình luận theo postId
    public List<CommentDTO> getCommentsByPostId(Long postId) {
        List<Comment> comments = commentRepository.findByPostIdOrderByCreatedAtDesc(postId);
        return comments.stream().map(comment ->
                new CommentDTO(
                        comment.getContent(),
                        comment.getPost().getId(),
                        comment.getUserId(),
                        comment.getAuthor(),
                        comment.getCreatedAt()
                )
        ).collect(Collectors.toList());
    }

    //Xóa bình luận theo commentId
    public boolean deleteComment (Long commentId) {
        Optional<Comment> comment = commentRepository.findById(commentId);
        if(comment.isPresent()) {
            commentRepository.deleteById(commentId);
            return true;

        } else {
            return false;
        }
    }

    //Xóa bình luận theo postId
    public void deleteCommentsByPostId (Long postId) {
        List<Comment> comments = commentRepository.findAll().stream()
                .filter(comment -> comment.getPost().getId().equals(postId))
                .collect(Collectors.toList());
        if (!comments.isEmpty()) {
            commentRepository.deleteAll(comments);
        }
    }


}
