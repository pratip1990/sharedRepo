package com.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.ListIterator;
import java.util.Map;
import java.util.Scanner;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import com.model.Point;
import com.util.AppConstants;


public class PathCalculationService {
	private static String startPoint = "G";
	private static String endPoint = "J";
	private static StringBuilder path = new StringBuilder();
	private static Map<String, Integer> valueMap = new LinkedHashMap<String, Integer>();   
	
	public void processApp(String[] args, boolean runFlg, String startPointLocal, String endPointLocal) {
		
		Map<String, Point> routeData = null;
		if(args!= null && args.length == 1 && runFlg) {
			routeData =	loadRouteData(args[0].trim());
		}else {
			/**please change path according to your file location*/
			routeData = loadRouteData("C:\\My Drive\\Pratip\\Application Development\\POC\\routes.csv");
		}
		Scanner scanner = new Scanner(System.in);
		while (runFlg) {
			System.out.println(AppConstants.START_POINT_INPUT_MSG);
			startPoint = scanner.next();

			System.out.println(AppConstants.END_POINT_INPUT_MSG);
			endPoint = scanner.next();
			
			if ("0".equalsIgnoreCase(startPoint.trim()) || "0".equalsIgnoreCase(endPoint.trim())) { 
				System.out.println(AppConstants.GOOD_DAY_MSG);
				System.exit(0);
			}
			else if (startPoint == null || startPoint.equals("") || startPoint.equals(" ") || startPoint.length() > 1
					|| endPoint == null || endPoint.equals("") || endPoint.equals(" ") || endPoint.length() > 1) {

				System.out.println(AppConstants.ERR_MSG);
				System.out.println(AppConstants.APP_QUIT_MSG);

			} else if (!routeData.containsKey(startPoint.trim().toUpperCase())) {
				System.out.println(AppConstants.ERR_MSG1);
				System.out.println(AppConstants.APP_QUIT_MSG);
			}
			else {
				startPoint = startPoint.trim().toUpperCase();
				endPoint = endPoint.trim().toUpperCase();
				break;
			}
		}
		scanner.close();
		
		if(!runFlg){
			System.out.println("Running through Unit Test!");
			startPoint = startPointLocal;
			endPoint = endPointLocal;
		}
		
		//System.out.println("Result =============== ");
		
		Point start = routeData.get(startPoint);
		//System.out.println(start.getPointLebel());
		
		/**Main path calculation process*/
		processRouteData(routeData,start);
		
		
		//System.out.println("path :: "+path.toString());
		
		if(path != null && path.toString().length() > 2 ) {
		
			String [] noOfPath = path.toString().split("\\,");
						
			Map<String, Integer> resultMap = new LinkedHashMap<String, Integer>();  
			
			for (int i = 0; i < noOfPath.length; i++) {
				String [] points = noOfPath[i].split("\\|");
				if(startPoint.equals(String.valueOf(points[0].charAt(0))) 
						&& endPoint.equals(String.valueOf(points[points.length-1].charAt(1)))) {
					
					
				
				
				int totalTime = 0;
				for (int j = 0; j < points.length; j++) {			
					totalTime = totalTime + valueMap.get(points[j]);
				}
				resultMap.put(noOfPath[i], totalTime);
				}
			}
			
			if(resultMap.isEmpty()) {
				System.out.println("No routes from "+startPoint +" to "+ endPoint);
			
			}else {
				resultMap = resultMap.entrySet().stream().sorted(Map.Entry.comparingByValue())
				.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
						(oldValue, newValue) -> oldValue, LinkedHashMap::new));
				
				int noOfStop = 0;
				int finalTime = 0;
				for (Entry<String, Integer> entry : resultMap.entrySet()) {
					//System.out.println("Shrotest path : " + entry);
					noOfStop = entry.getKey().split("\\|").length - 1;
					finalTime = entry.getValue();
					break;
				}
				
				StringBuilder msg = new StringBuilder().append("Your trip from ").append(startPoint)
						.append(" to ").append(endPoint).append(" includes ").append(noOfStop)
						.append(" stops and will take ").append(finalTime).append(" minutes");
				
				System.out.println(msg.toString());
			}
		
		}else {
			System.out.println("No Train Found for "+startPoint + " to "+endPoint+" !");
		}
		

	}
	
	private static void processRouteData(Map<String, Point> routeData, Point startPoint) {

		// System.out.println("method call :::: ");
		if (routeData.containsKey(startPoint.getPointLebel())) {

			if (routeData.get(startPoint.getPointLebel()).getPointLebel().equals(endPoint)) {
				path.append(startPoint.getPointLebel() + endPoint);
				return;
			} else {
				ArrayList<Point> destList = routeData.get(startPoint.getPointLebel()).getDest();
				ListIterator<Point> listItr = destList.listIterator();

				while (listItr.hasNext()) {

					Point point = (Point) listItr.next();
					//System.out.println(point.getPointLebel());
					path.append(startPoint.getPointLebel() + point.getPointLebel() + "|");

					if (point.getPointLebel().equals(endPoint)) {
						path.append(",");
						//System.out.println("return val :: " + point.getPointLebel());
						return;
					} else {
						processRouteData(routeData, point);
					}
				}
			}

			// System.out.println("loop end :::: ");
		}

	}

	public static Map<String, Point> loadRouteData(String path) {
		System.out.println("CSV Data init::");
		Map<String, Point> routeData = null;
		BufferedReader bufferedReader = null;
		try {

			File inputCsvFile = new File(path);

			if (inputCsvFile.exists()) {
				InputStream inputFS = new FileInputStream(inputCsvFile);
				bufferedReader = new BufferedReader(new InputStreamReader(inputFS));

				routeData = new LinkedHashMap<String, Point>();
				
				String line = null;
				int rowCount = 0;
				while ((line = bufferedReader.readLine()) != null) {
					String[] values = line.split(",");
					if (values != null && values.length == 3) {
						
						if(!routeData.containsKey(values[0].trim())) {
							Point point = new Point(values[0].trim());
							Point destPoint = new Point(values[1].trim());
							point.getDest().add(destPoint);
							routeData.put(values[0].trim(),point);
						
						}else {
							
							Point point =  routeData.get(values[0].trim());
							Point destPoint = new Point(values[1].trim());
							point.getDest().add(destPoint);
							
							routeData.put(point.getPointLebel(), point);
							
						}
						
						valueMap.put(values[0].trim() + values[1].trim(), Integer.valueOf(values[2].trim()));
						
						/*System.out.println(values[0].trim() + "->" + values[1].trim() + " = "
								+ Integer.valueOf(values[2].trim()));*/
					}

					rowCount += 1;
				}
				
				
				//System.out.println("==============================");
				/*for (Map.Entry<String, Point> entry: routeData.entrySet()) {
					System.out.println(entry.getKey() + " = "+ entry.getValue().toString());
				}*/
				
				System.out.println("CSV Data loaded successfully. Total no of records : " + rowCount);

			} else {
				System.out.println("No csv file found in following path : " + path);
			}

		} catch (IOException ioException) {
			System.out.println("Failed to load CSV Data ::: " + ioException);
		} finally {
			if (bufferedReader != null) {
				try {
					bufferedReader.close();
				} catch (IOException inIoException) {
					System.out.println("bufferedReader close exception ::: " + inIoException);
				}
			}
		}
		return routeData;
	}

}
