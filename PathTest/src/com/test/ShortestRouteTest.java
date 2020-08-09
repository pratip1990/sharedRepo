package com.test;

import com.service.PathCalculationService;

public class ShortestRouteTest {

	/**
	 * To run the test case please uncomment the main method 
	 */
	public static void main(String[] args) {

		/*new ShortestRouteTest().testCase1(null,false,"A","B");
		new ShortestRouteTest().testCase2(null,false,"A","C");
		new ShortestRouteTest().testCase3(null,false,"A","D");
		new ShortestRouteTest().testCase4(null,false,"A","J");
		new ShortestRouteTest().testCase5(null,false,"G","J");
		*/
	}

	void testCase5(String [] args, boolean runFlg, String startPointLocal, String endPointLocal) {
		System.out.println("Test Case 5 --------------");
		new PathCalculationService().processApp(args, runFlg, startPointLocal, endPointLocal);
		
	}

	void testCase4(String [] args, boolean runFlg, String startPointLocal, String endPointLocal) {
		System.out.println("Test Case 4 --------------");
		new PathCalculationService().processApp(args, runFlg, startPointLocal, endPointLocal);
	}

	void testCase3(String [] args, boolean runFlg, String startPointLocal, String endPointLocal){
		System.out.println("Test Case 3 --------------");
		new PathCalculationService().processApp(args, runFlg, startPointLocal, endPointLocal);
	}

	void testCase2(String [] args, boolean runFlg, String startPointLocal, String endPointLocal) {
		System.out.println("Test Case 2 --------------");
		new PathCalculationService().processApp(args, runFlg, startPointLocal, endPointLocal);
	}

	void testCase1(String [] args, boolean runFlg, String startPointLocal, String endPointLocal) {
		System.out.println("Test Case 1 --------------");
		new PathCalculationService().processApp(args, runFlg, startPointLocal, endPointLocal);
	}

}
