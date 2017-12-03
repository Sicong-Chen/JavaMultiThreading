package thread6;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created on Dec 3, 2017 2:32:08 PM
 * Created by Sicong Chen
 * TODO
 */

/*
 * CountDown Latch
 * 
 */

class Processor implements Runnable {

	
	/*
	 * we do NOT use volatile key word, nor synchronized methods key word,
	 * because CountDownLatch is a Thread safe class
	 */
	private CountDownLatch latch;
	
	public Processor(CountDownLatch latch) {
		this.latch = latch;
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		System.out.println("Started.");
		
		/*
		 * simulate running or pinging machine, by sleep(millis)
		 */
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		/*
		 * call countDown() after sleep()
		 * each time countDown() is called, latch is down by 1
		 */
		latch.countDown();
		
	}
	
	
	
	
	
	
}


public class App {
	public static void main(String[] args) {
		
		/*
		 * issue the Thread Synchronization problems when Multiple Threads access to same variable 
		 * CountDownLatch is Thread Safe
		 * CountDownLatch let you count down from the number you specify,
		 * it enables one or more Thread(s) to wait, until the countDown number down to zero
 		 */
		CountDownLatch latch = new CountDownLatch(3);  
		
		/*
		 * Thread Pool of size 3
		 * Create 3 Threads
		 */
		ExecutorService executor = Executors.newFixedThreadPool(3);
		
		/*
		 * Submit 3 Processors as work tasks
		 * pass reference latch into instance of Processor
		 * each Thread will get one Processor at a time, where latch.countDown() are called
		 * 
		 */
		for (int i = 0; i < 3; i++) {
			executor.submit(new Processor(latch));
		}
		
		/*
		 * wait until CountDowmLatch has countDown to zero
		 * 
		 */
		try {
			latch.await();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println("Completed!");
		
		
	}
}
