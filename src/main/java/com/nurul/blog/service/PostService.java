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
import java.util.UUID;

@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;

//    @Value("src/main/resources/static/images")
//    private String uploadDir;


    private final String uploadDir = "uploads/";



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

    public Optional<PostDto> getById(Integer id) {
        Optional<Post> post = postRepository.findById(id);
        return post.map(p -> {
            PostDto dto = new PostDto();
            dto.setId(p.getId());
            dto.setTitle(p.getTitle());
            dto.setDescription(p.getDescription());
            dto.setAuthor(p.getAuthor());
            dto.setFilePath(p.getFilePath());
            dto.setStatus(p.getStatus().name());
            dto.setCategoryId(p.getCategory().getId());
            dto.setCategoryName(p.getCategory().getName());
            dto.setUserId(p.getUser().getId());
            dto.setUserName(p.getUser().getName());
            return dto;
        });
    }

    public Post create(Post post, MultipartFile file) throws IOException {

        if (file != null && !file.isEmpty()) {
            String fileNme = saveImage(file);
            post.setFilePath(fileNme);
        }
        return postRepository.save(post);
    }

    private String saveImage(MultipartFile file) throws IOException {

        String fileName = System.currentTimeMillis()+"_"+file.getOriginalFilename();

        System.out.println(fileName);
        Path path = Paths.get(uploadDir+fileName);
        Files.createDirectories(path.getParent());
        Files.write(path, file.getBytes());

        return  fileName;
    }
}
