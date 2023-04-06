package com.example.like.api;

import com.example.like.app.PostService;
import com.example.like.dto.PostDTO;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;
    private final Logger logger = LoggerFactory.getLogger(PostController.class);

    @GetMapping("/posts/{id}")
    public ResponseEntity<PostDTO> findById(@PathVariable Long id, @RequestParam(defaultValue = "false") boolean useColumn) {
        return ResponseEntity.ok(postService.findById(id, useColumn));
    }

    @Cacheable(value = "posts", key = "#id")
    @GetMapping("/posts-cache/{id}")
    public ResponseEntity<PostDTO> findByIdWithCache(@PathVariable Long id, @RequestParam(defaultValue = "false") boolean useColumn) {
        return ResponseEntity.ok(postService.findById(id, useColumn));
    }

//    @GetMapping("/posts")
//    public ResponseEntity<Page<PostDTO>> findAll(Pageable pageable, @RequestParam(defaultValue = "false") boolean useColumn) {
//        return ResponseEntity.ok(postService.findAll(pageable, useColumn));
//    }
//
//    @Cacheable(value = "posts")
//    @GetMapping("/posts-cache")
//    public ResponseEntity<Page<PostDTO>> findAllWithCache(Pageable pageable, @RequestParam(defaultValue = "false") boolean useColumn) {
//        return ResponseEntity.ok(postService.findAll(pageable, useColumn));
//    }

    @CacheEvict(value = "posts", allEntries = true)
    @Scheduled(fixedRateString = "5000")
    public void removePostsCache() {
        logger.info("Post cache removed");
    }
}
