package new_butter.new_butter.service;

import new_butter.new_butter.domain.FileDB;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Stream;

public interface FileStorageService {
    FileDB store(MultipartFile file, Long userId) throws IOException;
    List<FileDB> getFilesByUserId(Long userId);
    Stream<FileDB> getAllFiles();

    FileDB getLatestFileByUserId(Long userId);
}
