package concurrency;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class CallableExample {

	public static void main(String args[]) {

		// return first result when any task is completed, finish until all tasks are completed
		submit1();

		// wait until all tasks are completed before returns.
		submit2();
	}

	private static void submit1() {
		ExecutorService executor = Executors.newFixedThreadPool(10);

		// create a list to hold the Future object associated with Callable
		List<Future<String>> futureList = new ArrayList<Future<String>>();

		// parallel the tasks
		for (int i = 0; i < 5; i++) {
			Callable<String> callable = new CallableExample().new MyCallable(i);

			// submit Callable tasks to be executed by thread pool
			Future<String> future = executor.submit(callable);
			// add Future to the list, we can get return value using Future
			futureList.add(future);
		}

		System.out.println("task submission 1 is done");

		for (Future<String> future : futureList) {
			try {
				System.out.println(new Date() + "::" + future.get());
			} catch (InterruptedException | ExecutionException e) {
				e.printStackTrace();
			}
		}

		executor.shutdown();
	}

	private static void submit2() {

		List<Callable<String>> callableList = new ArrayList<Callable<String>>();

		for (int i = 0; i < 5; i++) {
			callableList.add(new CallableExample().new MyCallable(i));
		}

		ExecutorService executor = Executors.newFixedThreadPool(10);

		// create a list to hold the Future object associated with Callable
		List<Future<String>> futureList = new ArrayList<Future<String>>();

		System.out.println("task submission 2 is done");

		try {
			// waits until all results are computed before it returns.
			futureList = executor.invokeAll(callableList);
		} catch (InterruptedException e) {
			e.printStackTrace();
		} 
		
		executor.shutdown();
		
		for (Future<String> future : futureList) {
			try {
				System.out.println(new Date() + "::" + future.get());
			} catch (InterruptedException | ExecutionException e) {
				e.printStackTrace();
			}
		}
	}

	// Callable thread
	private class MyCallable implements Callable<String> {

		private int id;

		public MyCallable(int id) {
			this.id = id;
		}

		@Override
		public String call() throws Exception {
			int x = new Random().nextInt(2000);
			Thread.sleep(x);
			return "task " + id + " is done " + x;
		}
	}
}