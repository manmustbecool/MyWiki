package concurrency;

public class ThreadExample {
	public static void main(String args[]) {

		MyThread myThread = new MyThread();
		myThread.start();

		Thread thread = new Thread(new MyRunnable());
		thread.start();

	}
}

// Thread Subclass
class MyThread extends Thread {

	public void run() {
		System.out.println("MyThread running");
	}

}

// Runnable Interface Implemention
class MyRunnable implements Runnable {

	public void run() {
		System.out.println("MyRunnable running");
	}
}