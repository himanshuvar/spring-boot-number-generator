package com.himanshu.springboot.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Himanshu Varshney on 12/16/2020
 */

@Document(collection = "tasks")
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "@id", scope = Task.class)
public class Task {

	@Id
	private String id;

	private String name;
	private int goal;
	private int step;
	private String status;
	private boolean isBulkTask;
	private Map<Integer, Integer> goalStepsMap;
	private List<Integer> result;
	private List<List<Integer>> results;

	public Task() {
	}

	public Task(String name, int goal, int step) {
		this.name = name;
		this.goal = goal;
		this.step = step;
		this.result = new ArrayList<>();
		this.status = TaskStatus.IN_PROGRESS.name();
	}

	public Task(String name, boolean isBulkTask, Map<Integer, Integer> goalStepsMap) {
		this.name = name;
		this.isBulkTask = isBulkTask;
		this.goalStepsMap = goalStepsMap;
		this.results = new ArrayList<>();
		this.status = TaskStatus.IN_PROGRESS.name();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getGoal() {
		return goal;
	}

	public void setGoal(int goal) {
		this.goal = goal;
	}

	public int getStep() {
		return step;
	}

	public void setStep(int step) {
		this.step = step;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public boolean isBulkTask() {
		return isBulkTask;
	}

	public void setBulkTask(boolean bulkTask) {
		isBulkTask = bulkTask;
	}

	public Map<Integer, Integer> getGoalStepsMap() {
		return goalStepsMap;
	}

	public void setGoalStepsMap(Map<Integer, Integer> goalStepsMap) {
		this.goalStepsMap = goalStepsMap;
	}

	public List<Integer> getResult() {
		return result;
	}

	public void setResult(List<Integer> result) {
		this.result = result;
	}

	public List<List<Integer>> getResults() {
		return results;
	}

	public void setResults(List<List<Integer>> results) {
		this.results = results;
	}

	public void addResult(List<Integer> result) {
		this.results.add(result);
	}

	@Override
	public String toString() {
		return "Task{" +
				"id='" + id + '\'' +
				", name='" + name + '\'' +
				", goal=" + goal +
				", step=" + step +
				", status='" + status + '\'' +
				", isBulkTask=" + isBulkTask +
				", result=" + result +
				", results=" + results +
				'}';
	}
}
