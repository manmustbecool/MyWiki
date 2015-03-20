package sort;

import java.util.Arrays;

public class BubbleSort {

	public static void main(String[] args) {
		int[] x = { 9, 2, 4, 7, 3, 7, 10 };
		System.out.println(Arrays.toString(x));

		bubbleSort(x);
		System.out.println(Arrays.toString(x));

	}

	private static void bubbleSort(int[] arr) {

		int n = arr.length;

		for (int i = 0; i < n; i++) {
			for (int j = 1; j < (n - i); j++) {

				if (arr[j - 1] > arr[j]) {
					// swap the elements!
					int temp = arr[j - 1];
					arr[j - 1] = arr[j];
					arr[j] = temp;
				}

			}
			System.out.println(Arrays.toString(arr));
		}

	}
}