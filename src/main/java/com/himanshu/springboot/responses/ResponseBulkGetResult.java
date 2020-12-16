package com.himanshu.springboot.responses;

import java.util.List;

/**
 * @author Himanshu Varshney on 12/16/2020
 */

public class ResponseBulkGetResult {
    private List<List<Integer>> results;

    public ResponseBulkGetResult(List<List<Integer>> results) {
        this.results = results;
    }

    public List<List<Integer>> getResults() {
        return results;
    }

    public void setResults(List<List<Integer>> results) {
        this.results = results;
    }
}