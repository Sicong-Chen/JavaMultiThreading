package thread12;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Created on Dec 3, 2017 6:07:15 PM
 * Created by Sicong Chen
 * TODO
 */

/*
 * Semaphore
 */
public class App {
	public static void main(String[] args) throws InterruptedException {
		
		
		/*
		 * using ThreadPool to create 200 Threads
		 */
		ExecutorService executor = Executors.newCachedThreadPool();
		
		for (int i = 0; i < 200; i++) {
			executor.submit(new Runnable() {

				@Override
				public void run() {
					// TODO Auto-generated method stub
					Connection.getInstance().connect();
				}
			});
		}
		
		/*
		 * terminate all Threads afterwards (see previous)
		 */
		executor.shutdown();
		
		executor.awaitTermination(1, TimeUnit.DAYS);
		
		
		
		
	}
	
	
}
