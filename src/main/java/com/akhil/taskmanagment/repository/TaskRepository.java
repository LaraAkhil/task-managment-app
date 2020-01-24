package com.akhil.taskmanagment.repository;

import org.springframework.data.repository.CrudRepository;

import com.akhil.taskmanagment.model.Task;

public interface TaskRepository extends CrudRepository<Task, Long>{

}
