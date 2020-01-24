package com.akhil.taskmanagment.repository;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.akhil.taskmanagment.model.Project;

public interface ProjectRepository extends PagingAndSortingRepository<Project, Long> {

}
