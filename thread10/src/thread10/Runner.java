package thread10;

import java.util.Scanner;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created on Dec 3, 2017 4:37:34 PM
 * Created by Sicong Chen
 * TODO
 */
public class Runner {

	private int count = 0;

	/*
	 * ReentrantLock provide lick interface
	 * ReentrantLock: once a Thread has acquired this lock(ReentrantLock),
	 * it can lock again, keep count how many times it locked,
	 * essentially, you have to unlock it by the same number of times
	 * 
	 * ReentrantLock as an Object has wait() and notify() as well
	 */
	private Lock lock = new ReentrantLock();		

	/*
	 * get the Condition Object from the lock you're locking on
	 */
	private Condition cond = lock.newCondition();


	private void increment() {
		for (int  i = 0; i < 10000; i++) {
			count++;
		}
	}

	public void firstThread() throws InterruptedException {


		/*
		 * an example of lock once
		 * which is equivalent to sync block 
		 * but it's not a good way, because if increment() throws an exception, you'll get no chance to unlock
		 */
		//		lock.lock();
		//		increment();
		//		lock.unlock();

		/*
		 * Optimization: try - finally block to ensure execution of unlock
		 */
//		lock.lock();
//
//		try {
//			increment();
//		} finally {
//			lock.unlock();
//		}

		/*
		 * use Condition and wait() / notify()
		 */

		/*
		 * acquire the lock
		 */
		lock.lock();

		/*
		 * await(): hand over the lock - same with wait()
		 * when cond.signal() is called in the Thread that firstThread handed over the lock control to, 
		 * this firstThread is woken up
		 */
		System.out.println("Waiting ...");
		cond.await();
		
		System.out.println("Woken up!");
		
		try {
			increment();
		} finally {
			lock.unlock();
		}
	}


	public void secondThread() throws InterruptedException {

		/*
		 * in order to let firstThread execute first
		 */
		Thread.sleep(1000);
		
		lock.lock();

		/*
		 * wait a standardized input to manually continue
		 */
		System.out.println("Press the return...");
		Scanner sc = new Scanner(System.in);
		sc.nextLine();
		System.out.println("Return key pressed.");
		
		/*
		 * Condition.signal() is equivalent to notify()
		 * it wake up the previous Thread that previously lock on the same locked Object (ReentrantLock)
		 */
		cond.signal();
		
		try {
			increment();
		} finally {
			lock.unlock();
		}	

	}

	public void finished() {


		System.out.println("Count is " + count);

	}


}
