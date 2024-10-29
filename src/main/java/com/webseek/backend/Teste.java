package com.webseek.backend;

public class Teste {
    public static void main(String[] args) {
        int[] a = twoSum(new int[]{2, 9, 1, 3},11);
    }
    public static int[] twoSum(int[] nums, int target) {
        int[] result = null;
        while (result == null) {
            for (int x = 0; x<nums.length; x++) {
                result = getSum(nums, target,x);
            }
        }
        return result;
    }

    private static int[] getSum(int[] nums, int target, int actual) {
        for (int x=actual+1; x<nums.length; x++) {
            if (nums[x] + nums[actual] == target) {
                return new int[] {x,actual};
            }
        }
        return null;
    }
}
