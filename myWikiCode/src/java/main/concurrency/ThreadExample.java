package concurrency;

public class ThreadExample {
	public static void main(String args[]) {

		MyThread myThread = new MyThread();
		myThread.start();

		Thread myRunnable = new Thread(new MyRunnable());
		myRunnable.start();

	}
}

// Thread Subclass implementation
class MyThread extends Thread {

	public void run() {
		System.out.println("MyThread running");
	}
}

// Runnable Interface implementation
class MyRunnable implements Runnable {

	public void run() {
		System.out.println("MyRunnable running");
	}
}