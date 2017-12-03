package thread4;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created on Dec 3, 2017 2:37:55 AM
 * Created by Sicong Chen
 * TODO
 */

// multiple locks and synchronized blocks


public class Worker {

	private Random random = new Random();

	
	/*
	 * -- Operation 5: create separated locks and synchronize on two locks respectively
	 * we first create
	 */
	private Object lock1 = new Object();
	private Object lock2 = new Object();
	
	
	
	
	private List<Integer> list1 = new ArrayList<>();
	private List<Integer> list2 = new ArrayList<>();

	/*
	 * -- Operation 1~3
	 * stageOne do sth with list1
	 */
//	public void stageOne() {
//
//		// use sleep(1) to simulate some pinging machine procedure
//		try {
//			Thread.sleep(1);
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		list1.add(random.nextInt(100));
//	}

	/*
	 * -- Operation 1~3
	 * stageTwo do sth with list2
	 */
//	public void stageTwo() {
//
//		// use sleep(1) to simulate some pinging machine procedure
//		try {
//			Thread.sleep(1);
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		list2.add(random.nextInt(100));
//	}

	/*
	 * -- Operation 4:
	 * modify the stageOne() and stageTwo() to be synchronized
	 */
//	public synchronized void stageOne() {
//
//		// use sleep(1) to simulate some pinging machine procedure
//		try {
//			Thread.sleep(1);
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		list1.add(random.nextInt(100));
//	}
//	
//	public synchronized void stageTwo() {
//
//		// use sleep(1) to simulate some pinging machine procedure
//		try {
//			Thread.sleep(1);
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		list2.add(random.nextInt(100));
//	}
	
	/*
	 * -- Operation 5: 
	 * create synchronized blocks.
	 * 
	 * Two Threads can run these two methods at same time, 
	 * because we have two Object lock1 and lock2, 
	 * so One Thread acquire intrinsic lock of lock1, Another can acquire intrinsic lock of lock2, at same time
	 * 
	 * But when Two Threads enter one synchronized block at same time, 
	 * when First Thread is executing, the Second Thread has to wait.
	 * 
	 * By doing so, we bring the time cost back to around 2000 (2590 millis)
	 */
	
	public void stageOne() {
		
		synchronized (lock1) {
			
			// use sleep(1) to simulate some pinging machine procedure
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			list1.add(random.nextInt(100));
			
		}

	}
	
	public void stageTwo() {

		synchronized (lock2) {
			
			// use sleep(1) to simulate some pinging machine procedure
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			list2.add(random.nextInt(100));
			
		}

	}
	
	
	

	public void process() {
		for (int i = 0; i < 1000; i++) {
			stageOne();
			stageTwo();
		}

	}


	public void main() {
		System.out.println("Starting...");

		long start = System.currentTimeMillis();

		
		
		
		/*
		 * -- Operation 1 - Normal Operation
		 * call process(), which iterate 1000 times, each loop we do stageOne and stageTwo
		 * which write to list1 and list2 respectively
		 * it takes 2518 millis, since we got sleep(1) in each stage
		 */
//		process();
		

		/*
		 * -- Operation 2 - create multiple Threads (wrong)
		 */
//		new Thread(new Runnable() {
//			@Override
//			public void run() {
//				// TODO Auto-generated method stub
//
//				process();
//			}
//		}).start();

		/*
		 *  -- Operation 3 - create multiple Threads, 
		 *  but need to call Thread.join(), in order to wait.
		 *  Can not be anonymous
		 *  the time cost is 2510 millis
		 */
//		Thread t1 = new Thread(new Runnable() {
//			@Override
//			public void run() {
//				// TODO Auto-generated method stub
//				process();
//			}
//		});
//		t1.start();
//		
//		try {
//			t1.join();
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
		/*
		 * -- Operation 4 - run two Threads at same time
		 * still takes around 2000 millis (2519) at first few tries,
		 * but will get 
		 * Exception in thread "Thread-1" java.lang.ArrayIndexOutOfBoundsException
		 * this is expected because we have multiple Threads executing List or Lists
		 * 
		 * To fix this, we use synchronized method stageOne() and synchronized method stageTwo()
		 * to ensure that with multiple Threads executing stageOne() or stageTwo()
		 * only one Thread can acquire the monitor lock of stageOne() or stageTwo() method
		 * 
		 * by doing this, we have list1 and list2 with the correct size, 
		 * but, the time cost is around double (5044 millis)
		 * the reason is: the synchronized method acquire intrinsic lock of the Worker Object
		 * and there is only one intrinsic lock in Worker Object
		 * 
		 * In this case, 
		 * synchronized method stageOne() write list1; synchronized method stageTwo() write list2
		 * no two Threads can write stageOne() at same time; no two Threads can write stageTwo() at same time
		 * but it is definitely reasonable that 
		 * One Thread run synchronized method stageOne(), while
		 * Another Thread run synchronized method stageTwo(), at the very same time,
		 * because they are dealing with different variables.
		 * 
		 * However, we have only one Object Worker, so that at every moment, 
		 * only One Thread can acquire intrinsic lock to run, either on stageOne(), or stageTwo()
		 * but can not execute on stageOne() and stageTwo at same time  
		 * 
		 * To do this, we create separated locks and synchronize on two locks respectively
		 */
		Thread t1 = new Thread(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				process();
			}
		});
		
		Thread t2 = new Thread(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				process();
			}
		});
		
		t1.start();
		t2.start();
		
		try {
			t1.join();
			t2.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		
		


		long end = System.currentTimeMillis();

		System.out.println("Time cost is: " + (end - start));
		System.out.println("List1 size: " + list1.size() + "; List2 size: " + list2.size());
	}


}
