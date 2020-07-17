import java.lang.reflect.Array;

//Create two subarrays down to single item units
//First subarray is arr[l..m], second is arr[m+1..r]
//order: 1st done splits left end to single then recombines
//2nd done is two next left-most units to one, recombine
//does this all the way across, then when two units size of original split now sorted, recombine
//Merge/Sort occur at same time, after all units in the section divided to one unit array

public class MergeSort {
	
	void merge(int arr[], int l, int m, int r) {
		
		//adapt size to two subarrays to merge
		int n1 = m - l + 1;
		int n2 = r - m;
		
		//two temp arrays made to hold newly sorted elements
		int L[] = new int[n1];
		int R[] = new int[n2];
		
		//copy data to temp arrays
		for (int i = 0; i < n1; ++i) {
			L[i] = arr[l + i];
		}//end i loop
		for (int j = 0; j < n2; ++j) {
			R[j] = arr[m + 1 + j];		
		}//end j for loop
		
		/*Merge temp arrays together*/
		//intial index for both temp arrays initialized
		int i = 0, j = 0;
		//initialized index of merged subarray using LOW
		int k = l;
		//while index points aren't above array length
		while( i < n1 && j < n2) {
			//if L smaller save in new array at k
			if (L[i] <= R[j]) {
				arr[k] = L[i];
				i++;
			}//end if
			else {
				arr[k] = R[j];
				j++;
			}//else
		k++;
		}//end while
		
		/*Copy remaining eles of L[] if any*/
		while(i < n1) {
			arr[k] = L[i];
			i++;
			k++;
		}//end while
		
		/*Copy remaining eles of R[] if any*/
		while(i < n1) {
			arr[k] = R[j];
			j++;
			k++;
		}//end while		
	}//end merge
	
	//Main sort function
	void sort(int arr[], int l, int r) {
		if(l < r) {
			int m = (l + r)/ 2;
			
			//sort 1st and 2nd halves
			sort(arr, l, m);
			sort(arr, m + 1, r);
			
			//merge sorted halves
			merge(arr, l, m, r);
		}//end if
	}//end sort
	
	//print utility
	static void printArray(int arr[]) {
		int n = arr.length;
		for(int i = 0; i < n; ++i)
			System.out.print(arr[i] + " ");
		System.out.println();
	}//end printArray
	
	//driver method
	public static void main(String args[]) {
		int arr[] = {12, 17, 2, 3, 99, 54, 2, 89, 91, 99};
		
		System.out.println("Given array: ");
		printArray(arr);
		
		MergeSort ob = new MergeSort();
		ob.sort(arr,  0,  arr.length - 1);
		
		System.out.println("\nSorted Array: ");
		printArray(arr);
	}//end main
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
