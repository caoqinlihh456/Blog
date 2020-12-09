package com.cql.admin.controller;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Demo {
    public static void main(String[] args) {

        int[] nums = new int[4];
        nums[0] = 1;
        nums[1] = 1;
        nums[2] = 1;
        nums[3] = 1;

        int[] ints = runningSum(nums);
        for (int anInt : ints) {
            System.out.println(anInt);
        }


    }


    public static int[] runningSum(int[] nums) {

        int[] abc = new int[nums.length];
        for (int i = 1; i <= nums.length; i++) {
            int num = 0;
            for (int j = 0; j < i; j++) {
                num += nums[j];
            }
            abc[i - 1] = num;
        }
        return abc;
    }


    public static int[] singleNumber(int[] nums) {
        List<Integer> list = new ArrayList<>();
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            int i1 = nums[i];
            for (int j = i + 1; j < nums.length; i1++) {
                int i2 = nums[j];
                Integer num = map.get(i1);
                if (num == null) {
                    num = 0;
                }
                if (i1 == i2) {
                    num++;
                }
                map.put(i1, num);
            }
        }
        int[] array = new int[map.size()];
        for (int i = 0; i < list.size(); i++) {
            array[i] = list.get(i);
        }
        return array;
    }


}
