package com.project.memories.posts.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.project.memories.posts.model.Memories;

@Repository
public interface MemoriesRepository extends JpaRepository<Memories, Long> {
	@Query("SELECT c FROM Memories c WHERE c.title LIKE %?1%")
	public List<Memories> search(String searchQuery);
}