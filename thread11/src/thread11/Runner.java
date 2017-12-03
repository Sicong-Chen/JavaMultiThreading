package thread11;

import java.util.Random;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created on Dec 3, 2017 5:13:32 PM
 * Created by Sicong Chen
 * TODO
 */
public class Runner {

	private Account acc1 = new Account();
	private Account acc2 = new Account();


	/*
	 * Operation 2 - Introduce two ReentrantLock locks
	 */
	private Lock lock1 = new ReentrantLock();	
	private Lock lock2 = new ReentrantLock();	



	/*
	 * Operation 4  -  to avoid Deadlock (promised acquire both locks safely, in a no order sense):
	 * use a acquireLocks to get firstLock and secondLock locked
	 * if error happened that we can not get both locks locked, we unlock the locked one and try again
	 */
	private void acquireLocks(Lock firstLock, Lock secondLock) throws InterruptedException {

		while (true) {

			// try to Acquire the locks

			boolean getFirstLock = false;
			boolean getSecondLock = false;

			try {
				/*
				 * return true if got locked --> unlock it and go around the loop again
				 */
				getFirstLock = firstLock.tryLock();
				getSecondLock = secondLock.tryLock();
			} finally {

				/* 
				 * if firstLock and secondLock both got locked, return
				 */
				if (getFirstLock && getSecondLock) {
					return;
				}

				/*
				 * if only one Lock got locked, check each one and unlock the locked one and loop again
				 */
				if (getFirstLock) {
					firstLock.unlock();
				}

				if (getSecondLock) {
					secondLock.unlock();
				}
			}


			// Locks Not Acquired
			Thread.sleep(1);

		}
	}


	/*
	 * Operation 1 - Simulate transactions, but the total balance will not be 20000, which it should be
	 * because multithreading issue has not been issued
	 * To fix, use ReentrantLock
	 * 
	 */
	/*
	 * firstThread: transfer money from acc1 to acc2
	 */
	//	public void firstThread() throws InterruptedException {
	//
	//		Random random = new Random();
	//		for ( int i = 0; i < 10000; i++) {
	//			Account.transfer(acc1, acc2, random.nextInt(100));
	//		}
	//		
	//	}
	//	/*
	//	 * secondThread: transfer money from acc2 to acc1
	//	 */
	//	public void secondThread() throws InterruptedException {
	//
	//		Random random = new Random();
	//		for ( int i = 0; i < 10000; i++) {
	//			Account.transfer(acc2, acc1, random.nextInt(100));
	//		}
	//
	//	}




	/*
	 * Operation 2 - Simulate transactions, with ReentrantLock lock1 and lock2
	 * Drawbacks: in huge system, we do not know which acc to transfer from and towards
	 * 
	 */
	/*
	 * firstThread: transfer money from acc1 to acc2
	 */
	//	public void firstThread() throws InterruptedException {
	//
	//		Random random = new Random();
	//		for ( int i = 0; i < 10000; i++) {
	//			
	//			/*
	//			 * get lock1 and lock2 locked, corresponding to acc1 and acc2 respectively
	//			 * always put unlock in finally block
	//			 */
	//			lock1.lock();
	//			lock2.lock();
	//			
	//			try {
	//				Account.transfer(acc1, acc2, random.nextInt(100));
	//			} finally {
	//				lock1.unlock();
	//				lock2.unlock();
	//			}
	//			
	//		}
	//		
	//	}
	//	/*
	//	 * secondThread: transfer money from acc2 to acc1
	//	 */
	//	public void secondThread() throws InterruptedException {
	//
	//		Random random = new Random();
	//		for ( int i = 0; i < 10000; i++) {
	//			/*
	//			 * get lock1 and lock2 locked, corresponding to acc1 and acc2 respectively
	//			 * always put unlock in finally block
	//			 */
	//			lock1.lock();
	//			lock2.lock();
	//			
	//			try {
	//				Account.transfer(acc2, acc1, random.nextInt(100));
	//			} finally {
	//				lock1.unlock();
	//				lock2.unlock();
	//			}		}
	//
	//	}





	/*
	 * Operation 3 - Deadlock 
	 * in secondThread, if we lock lock2 first then lock lock1 
	 * 
	 * Reason: firstThread need to lock2 before unlock, while secondThread need to lock1 before unlock
	 * so both Threads get one lock but neither can get another lock, this is deadlock,
	 * which is occurred if you lock in different orders
	 * 
	 * It happens not only with ReentrantLock but also nested synchronized block
	 * 
	 */
	/*
	 * firstThread: transfer money from acc1 to acc2
	 */
	//	public void firstThread() throws InterruptedException {
	//
	//		Random random = new Random();
	//		for ( int i = 0; i < 10000; i++) {
	//
	//			lock1.lock();
	//			lock2.lock();
	//
	//			try {
	//				Account.transfer(acc1, acc2, random.nextInt(100));
	//			} finally {
	//				lock1.unlock();
	//				lock2.unlock();
	//			}
	//
	//		}
	//
	//	}
	//	/*
	//	 * secondThread: transfer money from acc2 to acc1
	//	 */
	//	public void secondThread() throws InterruptedException {
	//
	//		Random random = new Random();
	//		for ( int i = 0; i < 10000; i++) {
	//			/*
	//			 * in secondThread, if we lock lock2 first then lock lock1
	//			 * leading to we did lock in different orders in firstThread and secondThread
	//			 * --> deadlock (frozen)
	//			 * 
	//			 *  
	//			 */
	//			lock2.lock();
	//			lock1.lock();
	//
	//			try {
	//				Account.transfer(acc2, acc1, random.nextInt(100));
	//			} finally {
	//				lock1.unlock();
	//				lock2.unlock();
	//			}		
	//		}
	//
	//	}




	/*
	 * Operation 4  -  use a acquireLocks method to avoid Deadlock
	 */
	public void firstThread() throws InterruptedException {

		Random random = new Random();
		for ( int i = 0; i < 10000; i++) {

			acquireLocks(lock1, lock2);


			try {
				Account.transfer(acc1, acc2, random.nextInt(100));
			} finally {
				lock1.unlock();
				lock2.unlock();
			}

		}

	}
	
	public void secondThread() throws InterruptedException {

		Random random = new Random();
		for ( int i = 0; i < 10000; i++) {

			acquireLocks(lock2, lock1);


			try {
				Account.transfer(acc2, acc1, random.nextInt(100));
			} finally {
				lock1.unlock();
				lock2.unlock();
			}		
		}


	}



	public void finished() {

		System.out.println("Account 1 balance: " + acc1.getBalance());
		System.out.println("Account 2 balance: " + acc2.getBalance());
		System.out.println("Total balance: " + (acc1.getBalance() + acc2.getBalance()));

	}
}
