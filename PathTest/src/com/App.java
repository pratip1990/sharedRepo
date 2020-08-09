package com;

import com.service.PathCalculationService;

public class App {

	public static void main(String[] args) {
		System.out.println("Running through Main (CMD)");
		boolean runFlg = true;
		new PathCalculationService().processApp(args, runFlg, "", "");
	}

}
