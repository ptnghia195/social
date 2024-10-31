package new_butter.new_butter.repository;

import new_butter.new_butter.domain.FileDB;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FileDBRepository extends JpaRepository<FileDB, Long> {
    List<FileDB> findByUserId(Long userId);
    Optional<FileDB> findByPostId(Long postId);

}