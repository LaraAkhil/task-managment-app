package com.akhil.taskmanagment.service;

import java.util.Optional;

import com.akhil.taskmanagment.model.Task;

public interface TaskService {

	Optional<Task> findById(Long id);

	Task save(Task task);

}
