package new_butter.new_butter.controller;

import new_butter.new_butter.domain.Comment;
import new_butter.new_butter.payload.DTO.CommentDTO;
import new_butter.new_butter.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
@RestController
@RequestMapping("/api/comments")
public class CommentController {

    @Autowired
    private CommentService commentService;

    // API để thêm bình luận
    @PostMapping
    public ResponseEntity<Comment> addComment(@RequestBody CommentDTO commentDTO) {
        Comment newComment = commentService.saveComment(commentDTO);
        return ResponseEntity.ok(newComment);
    }

    // API để lấy danh sách bình luận theo postId
    @GetMapping("/post/{postId}")
    public ResponseEntity<List<CommentDTO>> getCommentsByPostId(@PathVariable Long postId) {
        List<CommentDTO> comments = commentService.getCommentsByPostId(postId);
        if (comments.isEmpty()) {
            return ResponseEntity.noContent().build(); // Trả về 204 No Content nếu không có bình luận nào
        }
        return ResponseEntity.ok(comments); // Trả về 200 OK với danh sách bình luận
    }


    // API để xóa bình luận theo cmt ID
    @DeleteMapping("/{commentId}")
    public ResponseEntity<Map<String, String>> deleteComment(@PathVariable Long commentId) {
        boolean isDeleted = commentService.deleteComment(commentId);
        if (isDeleted) {
            Map<String, String> response = new HashMap<>();
            response.put("message", "Comment deleted successfully");
            return ResponseEntity.ok(response);
        } else {
            Map<String, String> response = new HashMap<>();
            response.put("error", "Comment not found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

}