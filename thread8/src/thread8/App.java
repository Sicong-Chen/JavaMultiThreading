package thread8;
/**
 * Created on Dec 3, 2017 3:15:45 PM
 * Created by Sicong Chen
 * TODO
 */

/*
 * Wait and Notify - some low level thread synchronization techniques
 * 
 */
public class App {
	
	public static void main(String[] args) throws InterruptedException {
		
		/*
		 * init a Processor instance
		 */
		final Processor processor = new Processor();
		
		
		/*
		 * create two Threads, executing processor.produce() and processor.consumer() respectively
		 */
		Thread t1 = new Thread(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				try {
					processor.produce();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		
		Thread t2 = new Thread(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				try {
					processor.consume();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		
		/*
		 * start and wait
		 */
		t1.start();
		t2.start();
		
		t1.join();
		t2.join();
		
		
	}
}
