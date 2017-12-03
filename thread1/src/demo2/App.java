package demo2;
/**
 * Created on Dec 2, 2017 9:39:09 PM
 * Created by Sicong Chen
 * TODO
 */

// method 2.1 to start a thread --> by implementing Interface Runnable

// introducing Interface Runnable, which has only one method -->  run()
class Runner implements Runnable {

	@Override
	public void run() {
		// TODO Auto-generated method stub
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
		// declare an instance og Thread class
		// pass an instance of Runner class to the constructor of Thread
		Thread t1 = new Thread(new Runner());
		Thread t2 = new Thread(new Runner());
		
		t1.start();
		t2.start();
	}
}
