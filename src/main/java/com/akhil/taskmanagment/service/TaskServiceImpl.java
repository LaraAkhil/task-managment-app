package com.akhil.taskmanagment.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.akhil.taskmanagment.model.Task;
import com.akhil.taskmanagment.repository.TaskRepository;

@Service
public class TaskServiceImpl implements TaskService {
	
	private TaskRepository taskRepo;

	public TaskServiceImpl(TaskRepository taskRepo) {
		super();
		this.taskRepo = taskRepo;
	}

	@Override
	public Optional<Task> findById(Long id) {
		return taskRepo.findById(id);
	}

	@Override
	public Task save(Task task) {
		return taskRepo.save(task);
	}

}
