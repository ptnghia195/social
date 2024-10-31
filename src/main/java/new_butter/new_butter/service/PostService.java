package new_butter.new_butter.service;

import new_butter.new_butter.domain.Post;
import new_butter.new_butter.payload.DTO.PostDTO;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface PostService {
//    Post createPost(PostDTO postDTO);

    List<PostDTO> getPostsByUserIdSortedByNewest(Long userId);

    boolean deletePost(Long postId);

    List<PostDTO> getAllPostsSortedByNewest();

    public Post createPost(PostDTO postDTO,  MultipartFile file) throws IOException;
}
