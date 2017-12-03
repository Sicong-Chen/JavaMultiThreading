package thread9;

import java.util.LinkedList;
import java.util.Random;

/**
 * Created on Dec 3, 2017 4:04:23 PM
 * Created by Sicong Chen
 * TODO
 */
public class Processor {

	
	/*
	 * Shared variable between Threads
	 * 
	 */
	private LinkedList<Integer> list = new LinkedList<>();
	private final int LIMIT = 10;
	/*
	 * in this  demo, in order to emphasize that we should call wait() and notify() on same locked Object
	 */
	private Object lock = new Object();
	
	
	/*
	 * produce() Thread: add data to list
	 */
	public void produce() throws InterruptedException {
		
		int value = 0;
		
		while (true) {
			
			synchronized (lock) {
				
				/*
				 * to add into the list, only when the list.size() < LIMIT
				 * why while loop here? 
				 * Because we possibly come back from another Thread (after executing notify() in consume() )
				 */
				while (list.size() == LIMIT) {
					lock.wait();	    // again, to emphasis
				}
				
				list.add(value++);	// auto box
				
				/*
				 * once we add a item int=o the list, we call wake up the previous Thread 
				 * which previously held lock on very same locked Object
				 * 
				 * wake up the consume() Thread to continue removing
				 * 
				 * in this demo case, as long as the list size up to 10, 
				 * it'll go back to the consume Thread to conducting removing
				 */
				lock.notify();
			}
			
		}
	
	}
	
	
	/*
	 * remove data from the list
	 */
	public void consume() throws InterruptedException {
		
		while (true) {
			synchronized (lock) {
				
				/*
				 * to remove from the list, only when the list.size() > 0
				 * why while loop here? 
				 * Because we possibly come back from another Thread (after executing notify() in produce() )
				 */
				while (list.size() == 0) {
					lock.wait();
				}
				
				/*
				 * every time the lock control is hand over from produce(), the list is up to LIMIT size
				 */
				System.out.print("List size: " + list.size());
				int value = list.removeFirst();
				System.out.println("; Value is: " + value);
				
				/*
				 * once we remove a item from the list, we call wake up the previous Thread 
				 * which previously held lock on very same locked Object
				 * 
				 * wake up the produce() Thread to continue adding
				 * 
				 * in this demo case, as long as the list size down to 9, 
				 * it'll go back to the produce Thread to continuing adding
				 * --> aggressively adding, not aggressively removing
				 */
				lock.notify();
			}
			
			/*
			 * sleep: on avg 500 millis
			 */
			Thread.sleep(new Random().nextInt(1000));
			
		}
		
	}

	
	
}
