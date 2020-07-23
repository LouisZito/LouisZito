import java.util.Random;

public class HomeWork4SortTest {
	
	public static void main(String[] args)
			throws InterruptedException{
	BinaryHeap<Float> h = new BinaryHeap<>( );
	Float N = 10000000.00f;
	Random rand = new Random();
	long start = System.nanoTime();
	Float insertNo;
	int count = 0;
		
//count for input average
	while(count <= 4) {
		//Section utilizing "INSERT" method
				//inserting random number through range N
		        for( int i = 0; i <= N; i++) {
		        	insertNo = (rand.nextFloat() * N);
		        	//System.out.println(insertNo);
		            h.insert(insertNo);
		        }//end for
       
		        //forward sort
		        for( int i = 1; i <= 10000000.0000; i++) {
		        	//System.out.println(N);
		            h.insert( (float) N );
		            N = (float) (N + 1.00);
		        }//end for
		        
		        //reversed sorted order
		        for( int i = 10000000; i >= 0; i--) {
		        	//System.out.println(i);
		            h.insert( (float) N );
		            N = (float) (N - 1.00);
	        }//end for
		//end INSERT method

		//Section utilizing "BUILDHEAP" method
		        for( int j = 0; j <= N; j++) {

		        	//System.out.println(i);
		        	Float currentSize = (float) ((rand.nextFloat() * N));
		        	//System.out.println(currentSize);
		            h.buildHeap();
	        }//end for

				//forward sort
				Float currentSize = (float) 0.0;
		        for( int i = 1; i <= N; i++) {
		        	//System.out.println(i);
		        	currentSize = (float) (N + 1.0);
		            h.buildHeap();
		        }//end for
	        
		        //reversed sorted order
		        for( Float i = N; i >= 0; i--) {
		        	//System.out.println(i);
		        	currentSize = (float) (N - 1.0);
		            h.buildHeap( );
		        }//end for

		//end BUILDHEAP method

				//end code being timed above

				long end = System.nanoTime();
				long msElapsed = (end - start) / 1000000;
				System.out.println("N ints: Execution time in milliseconds: " + msElapsed);
			count++;
			start = System.nanoTime();
	}//end while
	}//end main method
}//main class
