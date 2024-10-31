package new_butter.new_butter.controller;

import new_butter.new_butter.domain.Post;
import new_butter.new_butter.payload.DTO.PostDTO;
import new_butter.new_butter.payload.response.MessageResponse;
import new_butter.new_butter.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
@RestController
@RequestMapping("/api/posts")
public class PostController {

    @Autowired
    private PostService postService;

    @PostMapping()
    public ResponseEntity<MessageResponse> createPost(
            @ModelAttribute PostDTO postDTO,
            @RequestParam(value = "file", required = false) MultipartFile file) {
        String message;
        try {
            Post newPost = postService.createPost(postDTO, file);  // Gọi hàm tạo bài post

            message = "Post created successfully with ID: " + newPost.getId();
            return ResponseEntity.status(HttpStatus.OK).body(new MessageResponse(message));
        } catch (IOException e) {
            message = "Error creating post or uploading file!";
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new MessageResponse(message));
        }
    }


    // GET request để lấy các bài viết theo userID
    @GetMapping("/user/{userId}")
    public List<PostDTO> getAllPostsByUserId(@PathVariable Long userId) {
        return postService.getPostsByUserIdSortedByNewest(userId);  // Gọi đến phương thức đã chuyển đổi thành PostDTO
    }

    // GET request để lấy tất cả các bài viết
    @GetMapping("/all")
    public ResponseEntity<List<PostDTO>> getAllPosts() {
        List<PostDTO> posts = postService.getAllPostsSortedByNewest();
        return new ResponseEntity<>(posts, HttpStatus.OK);  // Trả về 200 OK kèm danh sách PostDTO
    }

    // API to delete a post by postId
    @DeleteMapping("/{postId}")
    public ResponseEntity<Void> deletePost(@PathVariable Long postId) {
        postService.deletePost(postId);
        return  ResponseEntity.ok().build();
    }

}