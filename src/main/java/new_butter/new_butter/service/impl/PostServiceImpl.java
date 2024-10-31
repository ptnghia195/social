package new_butter.new_butter.service.impl;

import new_butter.new_butter.domain.FileDB;
import new_butter.new_butter.domain.Post;
import new_butter.new_butter.domain.User;
import new_butter.new_butter.payload.DTO.PostDTO;
import new_butter.new_butter.repository.FileDBRepository;
import new_butter.new_butter.repository.PostRepository;
import new_butter.new_butter.repository.UserRepository;
import new_butter.new_butter.service.CommentService;
import new_butter.new_butter.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CommentService commentService;

    @Autowired
    private FileDBRepository fileDBRepository;
    @Transactional
    public Post createPost(PostDTO postDTO,  MultipartFile file) throws IOException {
        User user = userRepository.findById(postDTO.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Post post = new Post();
        post.setTitle(postDTO.getTitle());
        post.setContent(postDTO.getContent());
        post.setAuthor(postDTO.getAuthor());
        post.setUser(user);  // Gán User cho Post
        Post savedPost = postRepository.save(post);

        if (file != null && !file.isEmpty()) {
            String fileName = StringUtils.cleanPath(file.getOriginalFilename()); // Lấy tên tệp gốc
            FileDB fileDB = new FileDB();
            fileDB.setName(fileName);
            fileDB.setType(file.getContentType());
            fileDB.setData(file.getBytes());
            fileDB.setUserId(user.getId());
            fileDB.setPostId(savedPost.getId());
            fileDBRepository.save(fileDB);
        }
        return savedPost;



    }

    public List<PostDTO> getPostsByUserIdSortedByNewest(Long userId) {
        List<Post> posts = postRepository.findByUserIdOrderByCreatedAtDesc(userId);
        return posts.stream().map(post ->
                new PostDTO(
                        post.getId(),
                        post.getTitle(),
                        post.getContent(),
                        post.getAuthor(),
                        post.getCreatedAt(),
                        post.getUser().getId()
                )
        ).collect(Collectors.toList());
    }


    //Phương thức Xóa bài Post ( Khi bài post bị xóa thì tất cả cmt đều bị xóa)
    public boolean deletePost(Long postId) {
        Optional<Post> post = postRepository.findById(postId);
        if (post.isPresent()) {
            // Nếu có, xóa tất cả các bình luận liên quan
            commentService.deleteCommentsByPostId(postId);
            // Sau đó xóa bài viết
            postRepository.delete(post.get());
            return true; // Trả về true nếu bài viết được xóa
        }
        return false; // Trả về false nếu không tìm thấy bài viết
    }

    //Phương thức getAll tất cả các bài POSTS
    @Transactional
    public List<PostDTO> getAllPostsSortedByNewest() {
        List<Post> posts = postRepository.findAllByOrderByCreatedAtDesc();

        return posts.stream().map(post -> {
            // Tìm file liên quan đến post
            Optional<FileDB> fileDB = fileDBRepository.findByPostId(post.getId());

            // Nếu có file, lấy thông tin file
            byte[] fileData = fileDB.map(FileDB::getData).orElse(null);

            return new PostDTO(
                    post.getId(),
                    post.getTitle(),
                    post.getContent(),
                    post.getAuthor(),
                    post.getCreatedAt(),
                    post.getUser().getId(),
                    fileData
            );
        }).collect(Collectors.toList());
    }






}
