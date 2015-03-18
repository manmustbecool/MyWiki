package search;

public class BinarySearch {
	public static void main(String[] args) {
		int[] arr = { 2, 4, 6, 8, 10, 12, 14, 16 };
		System.out.println("Key 14's position: " + binarySearch(arr, 14));
		System.out.println("Key 14's position: "
				+ recursiveBinarySearch(arr, 0, arr.length, 14));
	}

	// divide and conquer technique
	static int binarySearch(int[] sortedArray, int key) {
		int start = 0;
		int end = sortedArray.length - 1;
		while (start <= end) {
			int mid = (start + end) / 2;
			if (key == sortedArray[mid]) {
				return mid;
			} else if (key < sortedArray[mid]) {
				end = mid - 1;
			} else {
				start = mid + 1;
			}
		}
		return -1;
	}

	// recursive algorithm
	static int recursiveBinarySearch(int[] sortedArray, int start, int end,
			int key) {
		if (start < end) {
			int mid = start + (end - start) / 2;
			if (key == sortedArray[mid]) {
				return mid;
			} else if (key < sortedArray[mid]) {
				return recursiveBinarySearch(sortedArray, start, mid, key);
			} else {
				return recursiveBinarySearch(sortedArray, mid + 1, end, key);
			}
		}
		return -1;
	}
}