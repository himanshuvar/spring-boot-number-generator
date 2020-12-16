package com.himanshu.springboot.repository;

import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;
import com.himanshu.springboot.model.Task;

/**
 * @author Himanshu Varshney on 12/16/2020
 */

public interface TaskRepository extends MongoRepository<Task, String> {
    List<Task> findByStatus(String status);
    List<Task> findByName(String name);
}