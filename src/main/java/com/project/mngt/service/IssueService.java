package com.project.mngt.service;

import com.project.mngt.model.Issue;
import com.project.mngt.model.User;
import com.project.mngt.request.IssueRequest;

import java.util.List;

public interface IssueService {

    Issue getIssueById(Long issueId) throws Exception;

    List<Issue> getIssueByProjectId(Long projectId) throws Exception;

    Issue createIssue(IssueRequest issue, User user) throws Exception;

    //Optional<Issue> updateIssue(Long issueId, IssueRequest updateIssue, Long userId) throws Exception;

    void deleteIssue(Long issueId, Long userId) throws Exception;

    //List<Issue> getIssueByAssigneeId(Long assigneeId) throws Exception;

    //List<Issue> searchIssue(String title, String status, String priority, Long assigneeId) throws Exception;

    //List<User> getAssigneeForIssue(Long issueId) throws Exception;

    Issue addUserToIssue(Long issueId, Long userId) throws Exception;

    Issue updateIssueStatus(Long issueId, String status) throws Exception;


}
