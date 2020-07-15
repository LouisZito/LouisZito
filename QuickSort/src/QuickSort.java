/* Sort around Pivot, working from front to back, pushing higher values up the chain
 * start by picking Median Value as pivot: sort 1, middle, last - use middle value first pivot
 * ends with two groups of values around pivot either LOWER or HIGHER than pivot
 * recursively pick new pivots in each group and keep reapplying
 * DRAGGING LARGER ITEMS FORWARD is the process
 */

//this particular method starts at the back automatically, no Median is calculated
public class QuickSort {
	
	int partition(int arr[], int low, int high) {
		//this method starts with high being median obtained pivot value already found
		int pivot = arr[high];
		int i = (low - 1); //index of lowest element, need to start below in order to keep motion while checking ele 1
		
			//end for loop before pivot at the end in HIGH
			for (int j = low; j<high; j++) {
				
				//if current ele smaller than pivot want it to end on the left
				if(arr[j] < pivot) {
					i++; //move i up to drag larger element up with it
					
					//swap of lower j with larger i thats one ahead
					int temp = arr[i];
					arr[i] = arr[j];//moving larger up
					arr[j] = temp;//lower is now on left in j, behind i
				}//end if
			}//end for
		
			//completed move through array, assign (i + 1) as new HIGH and return
			//j will reset in the repeated for loop as LOW
			int temp = arr[i + 1];
			arr[i + 1] = arr[high];
			arr[high] = temp;
			
	return i+1;
	}//end partition
	
	//main function the implements the Sort via partition
	//arr is array to sort, low is starting index, high is ending index
	void sort(int arr[], int low, int high) {
		
		//while low != to high index
		if (low < high) {
			
			//set loop for partition index after first round
			int pi = partition(arr, low, high);
			
			//recursive call to sort before pi and after pi until low = high, base-case
			sort(arr, low, pi-1);
			sort(arr, pi+1, high);			
		}//end if	
	}//end sort
	
	//function to view the array of size n
	static void printArray(int arr[]) {
		int n = arr.length;
		for(int i = 0; i < n; i++) {
			System.out.print(arr[i] + " ");
		System.out.println();
		}//end for
	}//end printArray
	
	//driver program
	public static void main(String args[]){
		
		int arr[] = {10, 7, 8, 9, 1, 5, 12, 98, 34};
		int n = arr.length;
		
		QuickSort ob = new QuickSort();
		ob.sort(arr, 0, n-1);
		
		System.out.println("Sorted Array: ");
		printArray(arr);
	}//main method
}//end class
