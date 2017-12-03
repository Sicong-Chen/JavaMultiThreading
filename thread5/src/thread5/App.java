package thread5;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Created on Dec 3, 2017 2:08:08 PM
 * Created by Sicong Chen
 * TODO
 */

/*
 * Thread pools - way of managing lots of threads at same time
 */


class Processor extends Thread {

	private int id;
	
	public Processor(int id) {
		this.id = id;
	}

	@Override
	public void run() {
		System.out.println("Starting: " + id);
		

		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		System.out.println("Completed: " + id);
	}

}

public class App {

	// main Thread (Thread1)
	public static void main(String[] args) {
		
		/*
		 * Initialize Thread Pool
		 * Command +Shift + O to auto import
		 * 
		 * Say: 5 tasks, each of Thread (usually >> 2) to process the task, 
		 * when finish a task, start a new task.
		 * With Two Thread processing 5 tasks, 
		 * One Thread will process one task at a time,
		 * as soon as one Thread is finished and idle, that same Thread will now process a new task
		 * 
		 * Advantage: avoid overhead by recycling the Threads in Thread Pool
		 */
		ExecutorService executor = Executors.newFixedThreadPool(2);
		
		/*
		 * Simulate 5 tasks
		 * By: ExecutorService executor.submit(Task)
		 */
		for (int i = 0; i < 5; i++) {
			executor.submit(new Processor(i));
		}
		
		/* 
		 * Stop accepting new Tasks, shut down
		 */
		executor.shutdown();
		
		/*
		 * Return status of "Submitted" immediately
		 */
		System.out.println("All Tasks Submitted.");
		
		/*
		 * Return status of "Completed"
		 * Set wait time for all the tasks to be completed
		 */
		// executor.awaitTermination(10, TimeUnit.SECONDS);
		try {
			executor.awaitTermination(1, TimeUnit.DAYS);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("All Tasks Completed!");
		
	}
}






