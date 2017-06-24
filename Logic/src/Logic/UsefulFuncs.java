package Logic;

import java.util.ArrayList;

public class UsefulFuncs {
	
	public static int arraySum(int[] arr) {
		int total = 0;
		
		for(int i=0;i<arr.length;i++) total += arr[i];
			
		return total;
	}

	public static String arrToString(int[] arr) {
		String str = "{";
		
		for(int i=0;i<arr.length;i++) {
			if(i!=0) str += ", ";
			str += arr[i];
		}
		
		return str + "}";
	}
	
	public static String arrToString(ArrayList<Integer> arr) {
		String str = "{";
		
		for(int i=0;i<arr.size();i++) {
			if(i!=0) str += ", ";
			str += arr.get(i);
		}
		
		return str + "}";
	}

	public static String arrToString(String[] arr) {
		String str = "{";
		
		for(int i=0;i<arr.length;i++) {
			if(i!=0) str += ", ";
			str += arr[i];
		}
		
		return str + "}";
	}
	
}
