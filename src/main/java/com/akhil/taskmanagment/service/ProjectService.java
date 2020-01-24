package com.akhil.taskmanagment.service;

import java.util.Optional;

import com.akhil.taskmanagment.model.Project;

public interface ProjectService {
	
	Optional<Project> findById(Long id);

    Project save(Project project);

    Iterable<Project> findAll();
	
	

}
