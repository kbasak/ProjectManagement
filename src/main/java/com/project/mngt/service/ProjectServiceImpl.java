package com.project.mngt.service;

import com.project.mngt.model.Chat;
import com.project.mngt.model.Project;
import com.project.mngt.model.User;
import com.project.mngt.repository.ProjectRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProjectServiceImpl implements ProjectService {

    @Autowired
    private ProjectRepo projectRepo;

    @Autowired
    private UserService userService;

    @Autowired
    private ChatService chatService;

    @Override
    public Project createProject(Project project, User user) throws Exception {
        Project createdProject = new Project();
        createdProject.setOwner(user);
        createdProject.setTags(project.getTags());
        createdProject.setName(project.getName());
        createdProject.setCategory(project.getCategory());
        createdProject.setDescription(project.getDescription());
        createdProject.getTeam().add(user);

        Project savedProject = projectRepo.save(createdProject);

        Chat chat = new Chat();
        chat.setProject(savedProject);

        Chat projectChat = chatService.createChat(chat);
        savedProject.setChat(projectChat);

        return savedProject;
    }

    @Override
    public List<Project> getProjectByTeam(User user, String category, String tag) throws Exception {
        List<Project> projects = projectRepo.findByTeamContainingOrOwner(user, user);

        if (category != null) {
            projects = projects.stream().filter(project -> project.getCategory().equals(category))
                    .collect(Collectors.toList());
        }
        if (tag != null) {
            projects = projects.stream().filter(project -> project.getTags().contains(tag))
                    .collect(Collectors.toList());
        }
        return projects;
    }

    @Override
    public Project getProjectById(Long projectId) throws Exception {
        Optional<Project> optionalProject = projectRepo.findById(projectId);
        if (optionalProject.isEmpty()) {
            throw new Exception("Project not found with id: " + projectId);
        }
        return optionalProject.get();
    }

    @Override
    public void deleteProject(Long projectID, Long userID) throws Exception {
        getProjectById(projectID);
        projectRepo.deleteById(projectID);
    }

    @Override
    public Project updateProject(Project updatedProject, Long id) throws Exception {
        Project project = getProjectById(id);

        project.setTags(updatedProject.getTags());
        project.setName(updatedProject.getName());
        project.setDescription(updatedProject.getDescription());

        return projectRepo.save(project);
    }

    @Override
    public void addUserToProject(Long projectID, Long userID) throws Exception {
        User user = userService.findUserById(userID);
        Project project = getProjectById(projectID);
        if (!project.getTeam().contains(user)) {
            project.getChat().getUsers().add(user);
            project.getTeam().add(user);
        }
        projectRepo.save(project);
    }

    @Override
    public void removeUserFromProject(Long projectID, Long userID) throws Exception {
        User user = userService.findUserById(userID);
        Project project = getProjectById(projectID);
        if (project.getTeam().contains(user)) {
            project.getChat().getUsers().remove(user);
            project.getTeam().remove(user);
        }
        projectRepo.save(project);
    }

    @Override
    public Chat getChatByProjectId(Long projectID) throws Exception {
        Project project = getProjectById(projectID);

        return project.getChat();
    }

    @Override
    public List<Project> searchProjects(String keyword, User user) throws Exception {
        return projectRepo.findByNameContainingAndTeamContains(keyword, user);
    }


}
