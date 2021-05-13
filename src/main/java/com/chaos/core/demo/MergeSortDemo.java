package com.chaos.core.demo;

import java.util.Arrays;

/**
 * 归并排序demo
 *
 * @author huangdawei 2021/3/5
 */
public class MergeSortDemo {

    public static void main(String[] args) {
        int[] arr = {9, 8, 7, 6, 5, 4, 3, 2, 1};
        sort(arr);
        System.out.println(Arrays.toString(arr));
    }

    public static void sort(int[] arr) {
        int[] temp = new int[arr.length];
        sort(arr, 0, arr.length - 1, temp);
    }

    public static void sort(int[] arr, int left, int right, int[] temp) {
        if (left < right) {
            System.out.println("sort :" + left + "-" + right);
            int mid = (left + right) / 2;
            sort(arr, left, mid, temp);
            sort(arr, mid + 1, right, temp);
            merge(arr, left, right, temp);
        }
    }

    public static void merge(int[] arr, int left, int right, int[] temp) {

        String prefix = "merge " + left + "-" + right;
        System.out.println(prefix + ",before arr:" + Arrays.toString(arr));

        int i = left;
        int mid = (left + right) / 2;
        int j = mid + 1;

        int t = 0;
        while (i <= mid && j <= right) {
            if (arr[i] < arr[j]) {
                temp[t++] = arr[i++];
            } else {
                temp[t++] = arr[j++];
            }
        }

        while (i <= mid) {
            temp[t++] = arr[i++];
        }
        while (j <= right) {
            temp[t++] = arr[j++];
        }

        t = 0;
        while (left <= right) {
            arr[left++] = temp[t++];
        }

        System.out.println(prefix + ", after arr:" + Arrays.toString(arr));
    }
}
