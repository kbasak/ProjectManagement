package com.project.mngt.service;

import com.project.mngt.model.Chat;
import com.project.mngt.model.Project;
import com.project.mngt.model.User;

import java.util.List;

public interface ProjectService {
    Project createProject(Project project, User user) throws Exception;

    List<Project> getProjectByTeam(User user, String category, String tag) throws Exception;

    Project getProjectById(Long projectId) throws Exception;

    void deleteProject(Long projectID, Long userID) throws Exception;

    Project updateProject(Project updatedProject, Long id) throws Exception;

    void addUserToProject(Long projectID, Long userID) throws Exception;

    void removeUserFromProject(Long projectID, Long userID) throws Exception;

    Chat getChatByProjectId(Long projectID) throws Exception;

    List<Project> searchProjects(String keyword, User user) throws Exception;
}
