package thread7;

import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * Created on Dec 3, 2017 2:52:14 PM
 * Created by Sicong Chen
 * TODO
 */

/*
 * Producer - Consumer Pattern
 * Blocking Queue (Thread Safe)
 */


public class App {

	/*
	 * the class BlockingQueue in java.util.concurrent is Thread safe
	 * so that we do not need synchronized keywords 
	 */
	private static BlockingQueue<Integer> queue = new ArrayBlockingQueue<>(10);
	
	public static void main(String[] args) throws InterruptedException {
		
		/*
		 * simulate two Threads
		 * which execute producer() and consumer() respectively 
		 */
		Thread t1 = new Thread(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				try {
					producer();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		
		Thread t2 = new Thread(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				try {
					consumer();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		
		/*
		 * start and wait
		 */
		t1.start();
		t2.start();
		
		t1.join();
		t2.join();
		
	}
	
	/*
	 * idea of Producer - Consumer Pattern is:
	 * One / Several Thread producing things (adding to the BlockingQueue)
	 * while Other Thread(s) remove things from the BlockingQueue and Process it
	 * 
	 * no need to add synchronized keyword for  method producer() and consumer()
	 * because BlockQueue is a Thread safe class and queue.put() and queue.take() are thread safe
	 * 
	 * If the queue.size has been up to the init size of the BlockingQueue,
	 * queue.put() will patiently wait, until sth is removed from the queue
	 * 
	 * in this demo case, producer() will be adding things aggressively, as fast as it can,
	 * while consumer will be executed every 10 
	 */
	private static void producer() throws InterruptedException {
		
		Random random = new Random();
		
		while (true) {
			queue.put(random.nextInt(100));
		} 
	}
	
	/*
	 * randomly take things out of BlockingQueue and simulate processing time
	 * BlockQueue is a Thread safe class.
	 * If there is NOTHING in the queue, queue.take() will patiently wait, until sth is added into the queue
	 */
	private static void consumer() throws InterruptedException {
		
		Random random = new Random();
		
		while (true) {
			Thread.sleep(100);
		
			if (random.nextInt(10) == 0) {
				Integer value = queue.take();
				System.out.println("Taken value: " + value + "; Queue size is: " + queue.size());
			}
			
		} 
	}
	
}
