package new_butter.new_butter.service.impl;

import new_butter.new_butter.domain.FileDB;
import new_butter.new_butter.repository.FileDBRepository;
import new_butter.new_butter.service.FileStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;

@Service
public class FileStorageServiceImpl implements FileStorageService {

    @Autowired
    private FileDBRepository fileDBRepository;

    public FileDB store(MultipartFile file, Long userId) throws IOException {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        FileDB FileDB = new FileDB(fileName, file.getContentType(), file.getBytes(), userId);

        return fileDBRepository.save(FileDB);
    }
    @Transactional(readOnly = true)
    public List<FileDB> getFilesByUserId(Long userId) {
        return fileDBRepository.findByUserId(userId); // Gọi phương thức tìm kiếm theo userId
    }

//    Get file theo userID, Lấy hình ảnh mới nhất.
    public FileDB getLatestFileByUserId(Long userId) {
        List<FileDB> files = getFilesByUserId(userId);

        if (files.isEmpty()) {
            throw new RuntimeException("No files found for userId: " + userId);
        }

        return files.size() == 1
                ? files.get(0) // Nếu chỉ có một tệp, lấy tệp đó
                : files.stream()
                .max(Comparator.comparing(FileDB::getCreatedAt)) // Nếu có nhiều tệp, lấy tệp mới nhất
                .orElseThrow(() -> new RuntimeException("No files found for userId: " + userId));
    }

    public Stream<FileDB> getAllFiles() {
        return fileDBRepository.findAll().stream();
    }

//    Lấy hình ảnh theo postId

}