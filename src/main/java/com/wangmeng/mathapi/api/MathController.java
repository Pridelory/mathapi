package com.wangmeng.mathapi.api;

import com.wangmeng.mathapi.common.response.ServerResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.PriorityQueue;
import java.util.stream.Collectors;

/**
 * @ClassName MathController
 * @Description Math operations
 * @Author wangmeng
 * @Date 2022/10/3
 */
@RestController
public class MathController {

    /**
     * Calculate the minimum quantifier (how many) numbers give a list
     *
     * @param nums
     * @param quantifier
     * @return
     */
    @GetMapping("/min")
    public ServerResponse min(@RequestParam(value = "nums") List<Long> nums, @RequestParam("quantifier") Long quantifier) {
        PriorityQueue<Long> minHeap = new PriorityQueue<>(nums);
        int size = minHeap.size();
        List<Long> res = new ArrayList<>();
        for (int i = 0; i < quantifier; i++) {
            if (i == size) break;
            res.add(minHeap.poll());
        }

        return ServerResponse.success(res);
    }

    /**
     * Calculate the maximum quantifier (how many) numbers give a list
     *
     * @param nums
     * @param quantifier
     * @return
     */
    @GetMapping("/max")
    public ServerResponse max(@RequestParam(value = "nums") List<Long> nums, @RequestParam("quantifier") Long quantifier) {
        PriorityQueue<Long> minHeap = new PriorityQueue<>((o1, o2) -> (int) (o2 - o1));
        minHeap.addAll(nums);
        int size = minHeap.size();
        List<Long> res = new ArrayList<>();
        for (int i = 0; i < quantifier; i++) {
            if (i == size) break;
            res.add(minHeap.poll());
        }

        return ServerResponse.success(res);
    }

    /**
     * Calculate the average given a list
     *
     * @param nums
     * @return
     */
    @GetMapping("/avg")
    public ServerResponse avg(@RequestParam(value = "nums") List<Long> nums) {
        Double avg = nums.stream().collect(Collectors.averagingLong(Long::longValue));
        return ServerResponse.success(avg);
    }

    /**
     * Calculate the average given a list
     *
     * @param nums
     * @return
     */
    @GetMapping("/median")
    public ServerResponse median(@RequestParam(value = "nums") List<Long> nums) {
        Collections.sort(nums);
        int size = nums.size();
        Double median;
        if (size % 2 != 0) {
            median = Double.valueOf(nums.get(nums.size() / 2));
        } else {
            median = (nums.get(nums.size() / 2 - 1) + nums.get(nums.size() / 2)) / 2.0;
        }
        return ServerResponse.success(median);
    }

    /**
     * Calculate the qth percentile of the list elements
     *
     * @param nums
     * @param q
     * @return
     */
    @GetMapping("/percentile")
    public ServerResponse percentile(@RequestParam(value = "nums") List<Long> nums, @RequestParam("q") Double q) {
        Collections.sort(nums);
        int index = (int) Math.round(nums.size() * q + .5) - 1;
        return ServerResponse.success(nums.get(index));
    }
}
