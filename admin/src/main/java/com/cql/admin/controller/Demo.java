package com.cql.admin.controller;

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

        int [] abc = new int[nums.length];
        for (int i = 1; i <= nums.length; i++) {
            int num = 0;
            for (int j = 0; j < i; j++) {
                num += nums[j];
            }
            abc[i-1] = num;
        }
        return abc;
    }
}
