package ru.hogwarts.school.service;

import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;
import ru.hogwarts.school.model.Avatar;

import java.io.IOException;
import java.util.Collection;

public interface AvatarService {
    void uploadAvatar(Long studentId, MultipartFile file) throws IOException;
    Avatar findAvatar(long studentId);
 Collection<Avatar> getAllAvatars(Integer pageNumber, Integer pageSize);

}
