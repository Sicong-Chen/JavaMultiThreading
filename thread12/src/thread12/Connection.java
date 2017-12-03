package thread12;

import java.util.concurrent.Semaphore;

/**
 * Created on Dec 3, 2017 6:15:40 PM
 * Created by Sicong Chen
 * TODO
 */


// singleton
public class Connection {

	private static Connection instance = new Connection();

	
	/* Usage: control access to resources
	 * true: whichever Thread call acquire() first will become first one to get permit 
	 * when permit gets available
	 */
	Semaphore sem = new Semaphore(10, true);
	
	
	
	/*
	 * number of connections made at any given moment
	 */
	private int connections = 0;

	private Connection() {

	}

	public static Connection getInstance() {
		return instance;
	}

	public void connect() {
		
		/*
		 * acquire()  - decrement
		 * acquire() will wait if there is no permits available
		 * 
		 * moved from original connect (connect0) block
		 */
		try {
			sem.acquire();
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		try {
			connect0();
		} finally {
			/*
			 * release() - increment
			 * can happily release() from different Threads to where you did the acquire()
			 * (recall lock has a limitation of intrinsic lock of the same object)
			 * 
			 * moved from original connect (connect0) block
			 */
			sem.release();
		}
		
	}
	
	public void connect0() {

		
		/*
		 * acquire()  - decrement
		 * acquire() will wait if there is no permits available
		 * 
		 * moved to new connect method
		 */
//		try {
//			sem.acquire();
//		} catch (InterruptedException e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		}
		
		
		/*
		 * sync block
		 */
		synchronized (this ) {

			connections++;
			System.out.println("Current connctions " + connections);

		}
		
		/*
		 * simulate some work here between connection++ and connection--
		 * 
		 * if this sleep throws any exceptions， it will not go to sem.release();
		 * how to fix this?
		 * move the release() to a finally block whose try block is the original connect method
		 */
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		


		synchronized (this ) {

			connections--;
			System.out.println("Current connctions " + connections);

		}
		
		
		/*
		 * release() - increment
		 * can happily release() from different Threads to where you did the acquire()
		 * (recall lock has a limitation of intrinsic lock of the same object)
		 * 
		 * moved to finally block in connect()
		 */
		// sem.release();
		
	}

}





/*
 * for introducing Semaphore
 */
class Demo {
	public static void main(String[] args) throws InterruptedException {
		
		/*
		 * Object Semaphore maintains a count, 
		 * we refer to this number as the number of available permits of the semaphore
		 * 
		 * with the property of sem.acquire(),
		 * it can be used as a lock,
		 * can happily release from different Threads to where you did the acquire()
		 * (recall lock has a limitation of intrinsic lock of the same object)
		 * 
		 * Usage: control access to resources
		 * true: whichever Thread call acquire() first will become first one to get permit 
		 * when permit gets available
		 */
		Semaphore sem = new Semaphore(1, true);
		
		/*
		 * release() - increment
		 * can happily release() from different Threads to where you did the acquire()
		 * (recall lock has a limitation of intrinsic lock of the same object)
		 */
		sem.release();
		
		/*
		 * acquire()  - decrement
		 * acquire() will wait if there is no permits available
		 */
		sem.acquire();
		
		
		
		
		/*
		 * acquire the number of available permits of the semaphore
		 */
		int permits = sem.availablePermits();
		System.out.println("Available permits = " + permits);
	}
}
