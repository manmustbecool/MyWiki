package sort;

import java.util.Arrays;

public class BubbleSort {

	public static void main(String[] args) {
		int[] x = { 9, 2, 4, 7, 3, 7, 10 };
		System.out.println(Arrays.toString(x));

		bubbleSort(x);
		System.out.println(Arrays.toString(x));
	}

	static void bubbleSort(int[] arr) {

		boolean swapped = true;
		int j = 0;

		while (swapped) {
			
			swapped = false;
			j++;
			for (int i = 0; i < arr.length - j; i++) {
				if (arr[i] > arr[i + 1]) {
					
					int tmp = arr[i];
					arr[i] = arr[i + 1];
					arr[i + 1] = tmp;
					swapped = true;
					
					System.out.println(Arrays.toString(arr));
				}
			}
			
		}
	}
}