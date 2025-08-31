package com.nurul.blog.service;

import com.nurul.blog.entity.Category;
import com.nurul.blog.repository.CategoryRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    public void save(Category category) {
        categoryRepository.save(category);
    }

    public List<Category> getAll() {
        return categoryRepository.findAll();
    }

    public Optional<Category> getById(Integer id) {
        return categoryRepository.findById(id);
    }

    public Category updateData(Category data, Integer id) {
        return categoryRepository.findById(id)
                .map(category -> {
                    category.setName(data.getName());
                    return categoryRepository.save(category);
                }).orElse(null);
    }

    public Category deleteById(Integer id) {
        Optional<Category> optionalCategory = categoryRepository.findById(id);
        if (optionalCategory.isPresent()) {
            categoryRepository.deleteById(id);
            return optionalCategory.get();
        } else {
            return null; // Not found
        }
    }


    @Service
    public static class JwtService {

        // Secret key for signing
        private static final String SECRET = "8mWXcjuPx9L2UIDqH5oCLMtTKqKrSqwlSfqs48eWrFem5EBmpw"; // must be at least 32 chars for HS256
        private static final long EXPIRATION_TIME = 1000 * 60 * 60; // 1 hour

        private static final Key key = Keys.hmacShaKeyFor(SECRET.getBytes());

        public String generateJwtToken(String name) {

            return Jwts.builder()
                    .subject(name)
                    .issuedAt(new Date())
                    .expiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                    .signWith(key)
                    .compact();
        }
    }
}
