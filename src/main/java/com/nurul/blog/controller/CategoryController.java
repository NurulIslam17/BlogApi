package com.nurul.blog.controller;

import com.nurul.blog.entity.Category;
import com.nurul.blog.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/categories")
@CrossOrigin(origins = "http://localhost:5173")

public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @GetMapping("")
    public ResponseEntity<List<Category>> get() {
        List<Category> categories = categoryService.getAll();

        if (!categories.isEmpty()) {
            return new ResponseEntity<>(categories, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/save")
//    @PostMapping(value = "", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> save(@RequestBody Category category) {
        try {
            categoryService.save(category);
            return new ResponseEntity<>("Created Successfully", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{id}")
    public Optional<Category> getById(@PathVariable Integer id) {
        return categoryService.getById(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> update(@RequestBody Category categoryData, @PathVariable Integer id) {

        try {
            Category category = categoryService.updateData(categoryData, id);
            if (category != null) {
                return new ResponseEntity<>("Updated Successfully", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Category Not Found", HttpStatus.NOT_FOUND);
            }
        } catch (RuntimeException e) {
            return new ResponseEntity<>("Something went wrong", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteById(@PathVariable Integer id) {
        try {
            Category category = categoryService.deleteById(id);
            if (category != null) {
                return new ResponseEntity<>("Deleted Successfully", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Data Not Found", HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>("Something went wrong", HttpStatus.BAD_GATEWAY);
        }
    }

}
