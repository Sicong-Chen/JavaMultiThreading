package thread3;

import java.util.Scanner;

/**
 * Created on Dec 3, 2017 1:54:26 AM
 * Created by Sicong Chen
 * TODO
 */
// modify a variable from another Thread --> Method 2, by Thread Synchronization
// Thread Synchronization:
// every Object in Java has a monitor lock
// in this case we call a synchronized method increment() on the Object App
// we have to acquire the intrinsic lock before calling
// the magic is only one Thread can acquire the intrinsic lock at a time
// While one Thread has acquired the intrinsic lock and running the synchronized method increment(),
// the other Thread has to wait when they try to call this synchronized method at the same time, 
// until the intrinsic lock is released by the synchronized method finishing executing

// synchronized COVER the function of "volatile" --> it automatically guarantee that multiple threads can access to the shared variable
// but, volatile key word can NOT issue Synchronization problems
public class App {

	
	private int count = 0;
	
	
	// main Thread (Thread1)
	public static void main(String[] args) {
		
		App app = new App();
		app.doWork();


	}
	
	public void doWork() {
		Thread t1 = new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				for (int i = 0; i < 10000; i++) {
					// count++;
					increment();		// execute count++ in a synchronized way
					
				}
			}
			
		});
		
		Thread t2 = new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				for (int i = 0; i < 10000; i++) {
					// count++;
					increment();		// execute count++ in a synchronized way
				}
			}
			
		});
		
		t1.start();		// generate a new Thread (Thread2)
		t2.start();		// generate a new Thread (Thread3)
		
		
		// two Threads (Thread2 and Thread3) will do count++ at the same time, modifying the same variable
		// while System.out.println is executed in main Thread (Thread1)
		// if directly print, it will print Count is: 0
		
		// to fix this, we need to wait until Thread2 and Thread3 execution finished
		// Thread.join() will return when this Thread is finished
		// need put inside try/catch
		try {
			t1.join();
			t2.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// still, the result is NOT always 20000
		// Because the count++ operation is actually: count = count + 1
		// there are three steps: 1, get current value, 2. plus 1, 3. store back 
		// NOTE: two Thread is fetching a very same variable count concurrently
		// so that some increment might be skipped
		// to address this, (volatile will not help) we could: Thread Synchronization
		// --> see increment() method below
		
		System.out.println("Count is: " + count);
		
	}
	
	
	public synchronized void increment() {
		count++;
	}
	
	
}





