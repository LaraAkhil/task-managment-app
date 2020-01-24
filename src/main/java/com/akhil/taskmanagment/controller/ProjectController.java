package com.akhil.taskmanagment.controller;

import java.util.ArrayList;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ResponseStatusException;

import com.akhil.taskmanagment.dto.ProjectDto;
import com.akhil.taskmanagment.dto.TaskDto;
import com.akhil.taskmanagment.events.ProjectCreatedEvent;
import com.akhil.taskmanagment.model.Project;
import com.akhil.taskmanagment.model.Task;
import com.akhil.taskmanagment.service.ProjectService;

@Controller
@RequestMapping("/projects")
public class ProjectController {
	
	@Autowired
    private ApplicationEventPublisher publisher;

	private ProjectService projectService;

	public ProjectController(ProjectService projectService) {
		super();
		this.projectService = projectService;
	}

	@GetMapping
	public String getProjects(Model model) {
		Iterable<Project> projects = projectService.findAll();
		List<ProjectDto> projectDtos = new ArrayList<ProjectDto>();
		projects.forEach(p -> projectDtos.add(convertProjectToDto(p)));
		model.addAttribute("projects", projectDtos);
		return "projects";
	}

	@GetMapping("/new")
	public String newProject(Model model) {
		model.addAttribute("project", new ProjectDto());
		return "new-project";
	}

	@PostMapping
	public String addProject(@Valid @ModelAttribute("project") ProjectDto project, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return "new-project";
		}
		Project newProject = projectService.save(convertProjectToEntity(project));
		publisher.publishEvent(new ProjectCreatedEvent(newProject.getId()));
		return "redirect:/projects";
	}

	@GetMapping(value = "/{id}")
	public String findOne(@PathVariable Long id, Model model) {
		Project project = projectService.findById(id)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"project not found"));
		model.addAttribute("project", convertProjectToDto(project));
		return "project-id";

	}

	protected ProjectDto convertProjectToDto(Project entity) {
		ProjectDto dto = new ProjectDto(entity.getId(), entity.getName(), entity.getDateCreated());
		dto.setTasks(entity.getTasks().stream().map(t -> convertTaskToDto(t)).collect(Collectors.toSet()));
		return dto;
	}

	protected Project convertProjectToEntity(ProjectDto dto) {
		Project project = new Project(dto.getName(), dto.getDateCreated());
		if (!StringUtils.isEmpty(dto.getId())) {
			project.setId(dto.getId());
		}
		return project;
	}

	protected TaskDto convertTaskToDto(Task entity) {
		TaskDto dto = new TaskDto(entity.getId(), entity.getName(), entity.getDescription(), entity.getDateCreated(),
				entity.getDueDate(), entity.getStatus());
		return dto;
	}

	protected Task convertTaskToEntity(TaskDto dto) {
		Task task = new Task(dto.getName(), dto.getDescription(), dto.getDateCreated(), dto.getDueDate(),
				dto.getStatus());
		if (!StringUtils.isEmpty(dto.getId())) {
			task.setId(dto.getId());
		}
		return task;
	}

}
