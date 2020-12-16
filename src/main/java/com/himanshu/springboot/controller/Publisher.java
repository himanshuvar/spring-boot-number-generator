package com.himanshu.springboot.controller;

import com.himanshu.springboot.model.Task;
import com.himanshu.springboot.repository.TaskRepository;
import com.himanshu.springboot.requests.BulkRequest;
import com.himanshu.springboot.responses.ResponseBulkGetResult;
import com.himanshu.springboot.responses.ResponseGetResult;
import com.himanshu.springboot.responses.ResponseGetStatus;
import com.himanshu.springboot.responses.ResponsePOST;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Binding;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * @author Himanshu Varshney on 12/16/2020
 */

// For creating the REST controllers.
@RestController
// Used to map incoming web requests onto the handler methods in the controller.
@RequestMapping(value = "/api")
public class Publisher {

	private static final Logger LOGGER = LoggerFactory.getLogger(Publisher.class);

	@Autowired
	private AmqpTemplate amqpTemplate;

	@Autowired
	Binding binding;

	@Autowired
	TaskRepository taskRepository;

	// HTTP GET url - http://localhost:9091/tasks
	@GetMapping("/tasks")
	public ResponseEntity<List<Task>> getTasks(@RequestParam(name = "status", required = false) String status,
											   @RequestParam(name = "name", required = false) String name) {
		try {
			List<Task> tasks = new ArrayList<>();

			if (status != null)
				taskRepository.findByStatus(status).forEach(tasks::add);
			else if (name != null)
				taskRepository.findByName(name).forEach(tasks::add);
			else
				taskRepository.findAll().forEach(tasks::add);

			if (tasks.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}

			return new ResponseEntity<>(tasks, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	// HTTP GET url - http://localhost:9091/tasks/{id}
	@GetMapping("/tasks/{id}")
	public ResponseEntity<Object> getTaskById(@PathVariable("id") String id,
											  @RequestParam(name = "action", required = false) String action) {
		Optional<Task> taskData = taskRepository.findById(id);

		if (action == null) {
			if (taskData.isPresent()) {
				return new ResponseEntity<>(taskData.get(), HttpStatus.OK);
			} else {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
		}
		else if (action.equals("get_numlist")){
			if (taskData.isPresent()) {
				if(taskData.get().isBulkTask())
					return ResponseEntity.ok().body(new ResponseBulkGetResult(taskData.get().getResults()));
				else
					return ResponseEntity.ok().body(new ResponseGetResult(taskData.get().getResult()));
			} else {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
		}
		else {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}

	// HTTP GET url - http://localhost:9091/tasks/{id}/status
	@GetMapping("/tasks/{id}/status")
	public ResponseEntity<Object> getTaskStatus(@PathVariable("id") String id) {
		Optional<Task> taskData = taskRepository.findById(id);

		if (taskData.isPresent()) {
			return ResponseEntity.ok().body(new ResponseGetStatus(taskData.get().getStatus()));
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	// HTTP POST url - http://localhost:9091/api/generate
	@PostMapping(path= "/generate", consumes = "application/json", produces = "application/json")
	// @ResponseStatus annotation marks the method with the status-code and the reason message that should be returned.
	@ResponseStatus(code = HttpStatus.OK)
	public ResponseEntity<Object> send(@RequestBody Task task) {
		try {
			Task _task = taskRepository.save(new Task("NumberGenerator", task.getGoal(), task.getStep()));
			LOGGER.info("Sending message to the queue.");
			amqpTemplate.convertAndSend(binding.getExchange(), binding.getRoutingKey(), _task);
			System.out.println("Send msg = " + _task);
			LOGGER.info("Message sent successfully to the queue, sending back the response to the user.");
			return ResponseEntity.accepted().body(new ResponsePOST(_task.getId()));
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	// HTTP POST url - http://localhost:9091/api/bulkGenerate
	@PostMapping(path= "/bulkGenerate", consumes = "application/json", produces = "application/json")
	// @ResponseStatus annotation marks the method with the status-code and the reason message that should be returned.
	@ResponseStatus(code = HttpStatus.OK)
	public ResponseEntity<Object> returnSolution(@RequestBody BulkRequest tasks) {
		try {
			LOGGER.info("Tasks received from user are : " + tasks);
			Map<Integer, Integer> goalStepsMap = new LinkedHashMap<>();
			for(Task task : tasks.getTasks())
				goalStepsMap.put(task.getGoal(), task.getStep());
 			Task _task = taskRepository.save(new Task("BulkNumberGenerator", true, goalStepsMap));
			LOGGER.info("Sending message to the queue.");
			amqpTemplate.convertAndSend(binding.getExchange(), binding.getRoutingKey(), _task);
			System.out.println("Send msg = " + _task);
			LOGGER.info("Message sent successfully to the queue, sending back the response to the user.");
			return ResponseEntity.accepted().body(new ResponsePOST(_task.getId()));
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	// HTTP PUT url - http://localhost:9091/tasks/{id}
	@PutMapping("/tasks/{id}")
	public ResponseEntity<Object> updateTask(@PathVariable("id") String id, @RequestBody Task task) {
		Optional<Task> taskData = taskRepository.findById(id);

		if (taskData.isPresent()) {
			Task _task = taskData.get();
			_task.setGoal(task.getGoal());
			_task.setStep(task.getStep());
			taskRepository.save(_task);
			LOGGER.info("Sending message to the queue.");
			amqpTemplate.convertAndSend(binding.getExchange(), binding.getRoutingKey(), _task);
			System.out.println("Send msg = " + _task);
			LOGGER.info("Message sent successfully to the queue, sending back the response to the user.");
			return ResponseEntity.accepted().body(new ResponsePOST(_task.getId()));

		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	// HTTP DELETE url - http://localhost:9091/tasks/{id}
	@DeleteMapping("/tasks/{id}")
	public ResponseEntity<HttpStatus> deleteTask(@PathVariable("id") String id) {
		try {
			taskRepository.deleteById(id);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	// HTTP DELETE url - http://localhost:9091/tasks
	@DeleteMapping("/tasks")
	public ResponseEntity<HttpStatus> deleteAllTasks() {
		try {
			taskRepository.deleteAll();
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
