package com.himanshu.springboot.responses;

import java.util.List;

/**
 * @author Himanshu Varshney on 12/16/2020
 */

public class ResponseGetResult {
    private List<Integer> result;

    public ResponseGetResult(List<Integer> result) {
        this.result = result;
    }

    public List<Integer> getResult() {
        return result;
    }

    public void setResult(List<Integer> result) {
        this.result = result;
    }
}
