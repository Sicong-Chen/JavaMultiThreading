package thread8;

import java.util.Scanner;

/**
 * Created on Dec 3, 2017 3:15:52 PM
 * Created by Sicong Chen
 * TODO
 */
public class Processor {
	
	/*
	 * produce() and consume() are executed in two different Threads, in App.java
	 */
	public void produce() throws InterruptedException {

		/*
		 * synchronized block using intrinsic lock of Processor Object
		 */
		synchronized (this) {
			System.out.println("Producer Thread Running ...");
			
			/*
			 * can only call wait() in synchronized block,
			 * it's resource efficient,
			 * it hands over the control of the intrinsic lock which current sync block is locked on. 
			 * so that the current sync block will lose control of the lock.
			 * This Thread will NOT resume until two things happen:
			 * 1. this Thread regain the control of the lock 
			 * 2. from another Thread which is locked on the same Object, call a method notify()
			 * 
			 * while waiting here, consume() will be able to run and acquire the lock 
			 */
			wait();
			
			/*
			 * after notify() is executed in consume()
			 * go back here and resume
			 */
			System.out.println("Resumed.");
			
		}
		
	}

	
	public void consume() throws InterruptedException {

		/*
		 * new a Scanner
		 */
		Scanner sc = new Scanner(System.in);
		
		/*
		 * let consume() sleep, making sure produce() will be executed first, in a time order sense
		 */
		Thread.sleep(2000);
		
		
		/*
		 * make sure this sync block is locked on the SAME OBJECT
		 */
		synchronized (this) {
			
			/*
			 * expect a standardized input to continue 
			 */
			System.out.println("Waiting for return key...");
			sc.nextLine();
			System.out.println("Return key pressed.");
			
			/*
			 * can only call wait() in synchronized block,
			 * notify() will notify one other Thread, which is the first Thread that locked on the current lock Object
			 * if that Thread is waiting, it can wake up.
			 * 
			 * Note: 
			 * 1. it will notify ALL Threads that are waiting and previously locked on the same lock Object
			 * 2. notify() does NOT relinquish the control of the lock, we need to relinquish 
			 * 		
			 */
			notify();
			
			/*
			 * to prove that notify() will not relinquish the control of the lock:
			 * you'll have to wait this sync block finishes (that is, after another 5000 millis)
			 * and then can go back to produce() and print"resume"
			 */
			Thread.sleep(5000);
			
		}
		sc.close();
	}
}



