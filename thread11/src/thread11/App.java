package thread11;

/**
 * Created on Dec 3, 2017 5:07:26 PM
 * Created by Sicong Chen
 * TODO
 */

/*
 * Deadlock
 * 
 */
public class App {

	public static void main(String[] args) throws InterruptedException {


		/*
		 * init a Processor instance
		 */
		final Runner runner = new Runner();


		/*
		 * create two Threads, executing processor.produce() and processor.consumer() respectively
		 */
		Thread t1 = new Thread(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				try {
					runner.firstThread();
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
					runner.secondThread();
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



		runner.finished();

	}
}
