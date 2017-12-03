package demo3;

/**
 * Created on Dec 2, 2017 9:44:33 PM
 * Created by Sicong Chen
 * TODO
 */

//method 2.2 to start a thread --> Anonymous class of Interface Runnable
public class App {
	public static void main(String[] args) {
		Thread t1 = new Thread(new Runnable() {
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
			
		});
		
		Thread t2 = new Thread(new Runnable() {
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
			
		});
		
		
		
		t1.start();
		t2.start();
	}
}
