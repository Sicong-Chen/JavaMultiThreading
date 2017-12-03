package thread2;

import java.util.Scanner;

/**
 * Created on Dec 3, 2017 1:24:50 AM
 * Created by Sicong Chen
 * TODO
 */

// modify a variable from another Thread --> Method 1, by keyword "volatile"

class Processor extends Thread {

	// use running, shutdown() and Scanner listening to standardized input (renter) to shut down the print
	// not necessary GUARATEE in every system
	// private boolean running = true;

	// in Thread2
	// Thread2 will look into running and say Thread2 itself did not modify running
	// so java might optimize in some system that it'll NOT bother check running for each while loop
	// to GUARATEE other Thread (Thread1) can interrupt Thread2, use keyword "volatile"
	
	// using volatile, Thread1 can GUARATEE interrupt Thread2 
	// the shutdown() in main thread IS MADE SURE to work 
	// volatile: prevent Thread from caching variables when they're not modified within that Thread
	// that is, if we want to modify a variable from another Thread, we use volatile
	// Or, we use Thread Synchronization
	private volatile boolean running = true;


	@Override
	public void run() {

		while (running) {
			System.out.println("Hello");

			try {
				Thread.sleep(100);    // in while loop, it'll do print every 100 millis
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	// in Thread1 (main Thread)
	public void shutdown() {
		running = false;

	}
}

public class App {

	// main Thread (Thread1)
	public static void main(String[] args) {
		Processor p1 = new Processor();

		// when start() is called, another Thread (Thread2) is running
		// Thread2 is reading "running" to check if it's true
		p1.start();

		System.out.println("Press return to stop...");
		Scanner scanner = new Scanner(System.in);
		scanner.nextLine();

		// Thread1 is writing "running"
		p1.shutdown();


	}
}
