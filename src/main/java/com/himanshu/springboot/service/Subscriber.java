package com.himanshu.springboot.service;

import com.himanshu.springboot.model.Task;
import com.himanshu.springboot.model.TaskStatus;
import com.himanshu.springboot.repository.TaskRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author Himanshu Varshney on 12/16/2020
 */


@Component
public class Subscriber {

	private static final Logger LOGGER = LoggerFactory.getLogger(Subscriber.class);

	@Autowired
	Queue queue;

	@Autowired
	TaskRepository taskRepository;

	// Dynamically reading the queue name using SpEL from the "queue" object.
	@RabbitListener(queues = "#{queue.getName()}")
	public void receive(final Task task) {

		LOGGER.info("Listening messages from the queue!!");
		LOGGER.info("Received the following message from the queue= " + task);
		LOGGER.info("Message received successfully from the queue.");

		Optional<Task> taskData = taskRepository.findById(task.getId());

		if (!taskData.isPresent())
			return;

		Task _task = taskData.get();

		if(_task.isBulkTask()) {
			Map<Integer, Integer> goalsStepMap = _task.getGoalStepsMap();
			ExecutorService executor = Executors.newFixedThreadPool(goalsStepMap.size());

			for(Map.Entry<Integer, Integer> entry : goalsStepMap.entrySet()) {
				NumberGenerator worker = new NumberGenerator(entry.getKey(), entry.getValue());
				executor.execute(worker);
				_task.addResult(worker.getResult());
			}

			executor.shutdown();
			// Wait until all threads are finish
			while (!executor.isTerminated()) {

			}
			System.out.println("Finished all task threads");
		}
		else {
			NumberGenerator ng = new NumberGenerator(_task.getGoal(), _task.getStep());
			ng.generateNumbers();
			_task.setResult(ng.getResult());
		}

		_task.setStatus(TaskStatus.SUCCESS.name());
		taskRepository.save(_task);

		LOGGER.info("Updated the task = " + _task);
	}
}
