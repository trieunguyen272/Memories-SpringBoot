package com.project.memories.posts.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.memories.posts.exception.ResourceNotFoundException;
import com.project.memories.posts.model.Memories;
import com.project.memories.posts.repository.MemoriesRepository;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/v1")
public class MemoriesController {

	@Autowired
	private MemoriesRepository memoriesRepository;

	// Chỉ cần sử dụng @Autowire, spring sẽ đảm nhận việc tạo bean
	// nếu bạn đã cung cấp @Repository trên giao diện JPA.

	// get all memories
	@GetMapping("/memories")
	public List<Memories> getAllMemories() {
		return memoriesRepository.findAll();
	}

	// search memories rest api
	@GetMapping("/memories/search")
	public List<Memories> searchMemories(String searchQuery) {
		return memoriesRepository.search(searchQuery);

	}

	// create memories rest api
	@PostMapping("/memories")
	public Memories createMemories(@RequestBody Memories memories) {
		return memoriesRepository.save(memories);
	}

	// update memories rest api
	@PatchMapping("/memories/{id}")
	public ResponseEntity<Memories> updateMemories(@PathVariable Long id, @RequestBody Memories memoriesDetails) {
		Memories memories = memoriesRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Memories not exist with id: " + id));

		memories.setName(memoriesDetails.getName());
		memories.setCreator(memoriesDetails.getCreator());
		memories.setImage(memoriesDetails.getImage());
		memories.setTitle(memoriesDetails.getTitle());

		Memories updateMemories = memoriesRepository.save(memories);

		return ResponseEntity.ok(updateMemories);
	}

	// delete memories rest api
	@DeleteMapping("/memories/{id}")
	public ResponseEntity<Map<String, Boolean>> deleteMemories(@PathVariable Long id) {
		Memories memories = memoriesRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Memories not exist with id: " + id));

		memoriesRepository.delete(memories);
		Map<String, Boolean> respon = new HashMap<>();
		respon.put("deleted", Boolean.TRUE);
		return ResponseEntity.ok(respon);
	}
}
