package new_butter.new_butter.payload.DTO;

import java.time.LocalDateTime;

public class CommentDTO {
    private String content;
    private Long postId; // ID của bài viết
    private Long userId; // ID của người dùng bình luận

    private String author;

    private LocalDateTime createdAt;


    // Getters và Setters
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getPostId() {
        return postId;
    }

    public void setPostId(Long postId) {
        this.postId = postId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public CommentDTO(String content, Long postId, Long userId, String author,LocalDateTime createdAt) {
        this.content = content;
        this.postId = postId;
        this.userId = userId;
        this.author = author;
        this.createdAt = createdAt;
    }
}