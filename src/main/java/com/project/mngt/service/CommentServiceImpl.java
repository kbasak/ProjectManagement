package com.project.mngt.service;

import com.project.mngt.model.Comment;
import com.project.mngt.model.Issue;
import com.project.mngt.model.User;
import com.project.mngt.repository.CommentRepo;
import com.project.mngt.repository.IssueRepo;
import com.project.mngt.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentRepo commentRepo;

    @Autowired
    private IssueRepo issueRepo;

    @Autowired
    private UserRepo userRepo;

    @Override
    public Comment createComment(Long issueId, Long userId, String content) throws Exception {
        Optional<Issue> issueOptional = issueRepo.findById(issueId);
        Optional<User> userOptional = userRepo.findById(userId);

        if (issueOptional.isEmpty()) {
            throw new Exception("Issue not found with id: " + issueId);
        }

        if (userOptional.isEmpty()) {
            throw new Exception("User not found with id: " + userId);
        }

        Issue issue = issueOptional.get();
        User user = userOptional.get();

        Comment comment = new Comment();

        comment.setIssue(issue);
        comment.setUser(user);
        comment.setCreatedTime(LocalDateTime.now());
        comment.setContent(content);

        Comment savedComment = commentRepo.save(comment);
        issue.getComments().add(savedComment);

        return savedComment;
    }

    @Override
    public void deleteComment(Long commentId, Long userId) throws Exception {
        Optional<Comment> commentOptional = commentRepo.findById(commentId);
        Optional<User> userOptional = userRepo.findById(userId);

        if (commentOptional.isEmpty()) {
            throw new Exception("Comment not found with id: " + commentId);
        }

        if (userOptional.isEmpty()) {
            throw new Exception("User not found with id: " + userId);
        }

        Comment comment = commentOptional.get();
        User user = userOptional.get();

        if (comment.getUser().equals(user)) {
            commentRepo.delete(comment);
        } else {
            throw new Exception("User does not have permission to delete this comment");
        }

    }

    @Override
    public List<Comment> findCommentByIssueId(Long issueId) {
        return commentRepo.findByIssueId(issueId);
    }
}
