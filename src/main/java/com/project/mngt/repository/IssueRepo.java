package com.project.mngt.repository;

import com.project.mngt.model.Issue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IssueRepo extends JpaRepository<Issue, Long> {
    public List<Issue> findByProjectID(Long id);
}
