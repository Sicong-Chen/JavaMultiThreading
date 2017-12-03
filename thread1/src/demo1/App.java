package demo1;
/**
 * Created on Dec 2, 2017 9:19:58 PM
 * Created by Sicong Chen
 * TODO
 */

// thread can run concurrently with other threads

// method 1 to start a thread:
class Runner extends Thread {

	// override the method run()
	@Override
	public void run() {
		// TODO Auto-generated method stub
		super.run();
		for (int i = 0; i < 10; i++) {
			// sysout + ctrl + space
			System.out.println("Hello! " + i);
			
			
			try {
				Thread.sleep(100);     // unit: mili sec
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
			
		}
	}
	
	
}
public class App {
	public static void main(String[] args) {
		Runner runner1 = new Runner();
		// start menu, not call run
		// if call run() --> run the code in the main thread of the application
		// start(): tell the Thread class to go look for a run() method
		runner1.start();
		
		// runner1 and runner2 can run at the same time
		Runner runner2 = new Runner();
		runner2.start();
		
//		Runner runner3 = new Runner();
//		runner3.run();
	}
}









