import java.util.concurrent.TimeUnit;

public class RunTimer {

		public static void main(String[] args)
			throws InterruptedException{
				long start = System.nanoTime();
				
				//CODE BEING TIMES GOES HERE!!
				
				//EXAMPLED REPLACE WITH CODE
				//sleep for 5 sec
				TimeUnit.SECONDS.sleep(5);
				//end code being timed above
				
				long end = System.nanoTime();
				long msElapsed = (end - start) / 1000000;
				System.out.println("Execution time in milliseconds: " + msElapsed);
		}//end main	
}//end class
