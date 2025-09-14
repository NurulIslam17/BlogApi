package com.nurul.blog.service;

import com.nurul.blog.DTO.PostDto;
import com.nurul.blog.entity.Post;
import com.nurul.blog.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;

    @Value("src/main/resources/static/images")
    private String uploadDir;

    public List<PostDto> getAlPosts()
    {
        return postRepository.findAll().stream().map(post -> {
            PostDto dto = new PostDto();
            dto.setId(post.getId());
            dto.setTitle(post.getTitle());
            dto.setDescription(post.getDescription());
            dto.setAuthor(post.getAuthor());
            dto.setFilePath(post.getFilePath());
            dto.setStatus(post.getStatus().name());
            dto.setCategoryId(post.getCategory().getId());
            dto.setCategoryName(post.getCategory().getName());
            dto.setUserId(post.getUser().getId());
            dto.setUserName(post.getUser().getName());
            return dto;
        }).toList();
    }

    public Post storePost(Post post, MultipartFile file) throws IOException {

        String fileName = "PO_"+System.currentTimeMillis() + "_" + file.getOriginalFilename();
        Path filePath = Paths.get(uploadDir, fileName);

        Files.createDirectories(filePath.getParent());
        Files.write(filePath,file.getBytes());

        post.setFilePath(filePath.toString());
        return postRepository.save(post);
    }

    public Optional<Post> getById(Integer id) {
        return postRepository.findById(id);
    }
}
