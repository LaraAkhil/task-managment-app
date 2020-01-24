package com.akhil.taskmanagment.service;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.akhil.taskmanagment.model.Project;
import com.akhil.taskmanagment.repository.ProjectRepository;

@Service
public class ProjectServiceImpl implements ProjectService {
	
	private ProjectRepository projectRepo;

	public ProjectServiceImpl(ProjectRepository projectRepo) {
		super();
		this.projectRepo = projectRepo;
	}

	@Override
	public Optional<Project> findById(Long id) {
		return projectRepo.findById(id);
	}

	@Override
	public Project save(Project project) {
		if (StringUtils.isEmpty(project.getId())) {
            project.setDateCreated(LocalDate.now());
        }
		return projectRepo.save(project);
	}

	@Override
	public Iterable<Project> findAll() {
		return projectRepo.findAll();
	}
	


}
