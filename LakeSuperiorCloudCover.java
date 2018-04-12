import java.io.FileNotFoundException;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

/**
 * Javen Zamojcin
 * CS 1131
 * 
 * Lake Superior Cloud Cover Project
 * 
 */

public class LakeSuperiorCloudCover implements CloudCoverInterface {

	public ArrayList<double[]> dataList = new ArrayList<double[]>();
	public ArrayList<Object[ ]> array = new ArrayList<Object[ ]>( );
	
	public static void main(String[] args) throws FileNotFoundException {
		
		LakeSuperiorCloudCover obj = new LakeSuperiorCloudCover();
		obj.loadData("SuperiorCloudCover1.dat");
		obj.getAverage(Month.JANUARY);
		obj.getAverage(2010);
		obj.getAverage();
		obj.getMax(Month.APRIL);
		obj.getMax(2011);
		obj.getMax();
		obj.getMin(Month.JANUARY);
		obj.getMin(2012);
		obj.getMin();
		obj.coverageToArea(50);
		obj.beginYear();
		obj.endYear();
	}

	@Override
	public void loadData(String filename) throws FileNotFoundException {
		
		File inputFile = new File(filename);		
		Scanner input = new Scanner(inputFile);
		int count = 0;
		
		while ( input.hasNextLine() ) {
			
			double[] yearData = new double[13];
			
			for (int i = 0; i < yearData.length; i++ ) {
				
				yearData[i] = input.nextDouble();
				
			}
			
			dataList.add(yearData);
			System.out.println(Arrays.toString(dataList.get(count)));
			count++;
		}
		
		input.close();
	}

	@Override
	public double getAverage(Month month) {
		
		int monthIndex = month.ordinal() + 1;
		double average = 0;
		
		for ( int i = 0; i < dataList.size(); i++ ) {
			
			average += dataList.get(i)[monthIndex];
			
		}
		
		average = average / dataList.size();
		
		if ( average == 0 ) {
			
			return -1;
			
		}
		
		System.out.println(average);
		
		return average;
	}

	@Override
	public double getAverage(int year) {
		
		int yearIndex = (int) (year - dataList.get(0)[0]);
		double average = 0;
		
		if ( yearIndex < 0 || yearIndex > dataList.size()) {
			
			return -1;
			
		}
		
		for ( int i = 1; i < dataList.get(yearIndex).length; i++) {
			
			average += dataList.get(yearIndex)[i];
			
		}
		
		average = average / 12;
		
		System.out.println(average);
		
		return average;
	}

	@Override
	public double getAverage() {
		
		double average = 0;
		
		for ( int row = 0; row < dataList.size(); row++) {
			
			for ( int col = 1; col < dataList.get(row).length; col++) {
				
				average += dataList.get(row)[col];
				
			}
		}
		
		if ( average == 0 ) {
			
			return -1;
			
		}
		
		average = average / (dataList.size()*12);
		
		System.out.println(average);
		
		return average;
	}

	@Override
	public double getMax(Month month) {
		
		int monthIndex = month.ordinal() + 1;
		
		if ( monthIndex < 1 || monthIndex > 12 ) {
			
			System.out.println("Max month value: -1");
			return -1;
			
		}
		
		double[] monthValues = new double[dataList.size()];
		
		for ( int i = 0; i < monthValues.length; i++ ) {
			
			monthValues[i] = dataList.get(i)[monthIndex];
			
		}
		
		Arrays.sort(monthValues);
		
		System.out.println("Max month value: " + monthValues[monthValues.length - 1]);
		return monthValues[monthValues.length - 1];
			
		} 
	
	@Override
	public double getMax(int year) {
		
		int yearIndex = (int) (year - dataList.get(0)[0]);
		
		if ( yearIndex < 0 || yearIndex >= dataList.size()) {
			
			System.out.println("Max year value: -1");
			return -1;
			
		}
		
		double[] yearValues = new double[dataList.get(yearIndex).length];
		
		for ( int i = 1; i < yearValues.length; i++ ) {
			
			yearValues[i - 1] = dataList.get(yearIndex)[i];
			
		}
		
		Arrays.sort(yearValues);
		
		System.out.println("Max year value: " + yearValues[yearValues.length - 1]);
		return yearValues[yearValues.length - 1];
		
	}

	@Override
	public double getMax() {
		
		if ( dataList.size() == 0 ) {
			
			return -1;
			
		}
		
		double[] allValues = new double[dataList.size() * 12];
		int count = 0;
		
		for ( int row = 0; row < dataList.size(); row++) {
			
			for ( int col = 1; col < 13; col++ ) {
				
				allValues[count] = dataList.get(row)[col];
				count++;
			}
			
		}
		
		Arrays.sort(allValues);
		
		System.out.println(allValues[allValues.length - 1]);
		return allValues[allValues.length - 1];
	}

	@Override
	public double getMin(Month month) {
		
		int monthIndex = month.ordinal() + 1;
		
		if ( monthIndex < 1 || monthIndex > 12 ) {
			
			System.out.println("Min month value: -1");
			return -1;
			
		}
		
		double[] monthValues = new double[dataList.size()];
		
		for ( int i = 0; i < monthValues.length; i++ ) {
			
			monthValues[i] = dataList.get(i)[monthIndex];
			
		}
		
		Arrays.sort(monthValues);
		
		System.out.println("Min month values: " + monthValues[0]);
		return monthValues[0];
	}

	@Override
	public double getMin(int year) {
		
		int yearIndex = (int) (year - dataList.get(0)[0]);
		
		if ( yearIndex < 0 || yearIndex >= dataList.size()) {
			
			System.out.println("Mix year value: -1");
			return -1;
			
		}
		
		double[] yearValues = new double[dataList.get(yearIndex).length];
		
		for ( int i = 1; i < yearValues.length; i++) {
			
			yearValues[i - 1] = dataList.get(yearIndex)[i];
			
		}
		
		Arrays.sort(yearValues);
		
		System.out.println("Min year values: " + yearValues[1]);
		return yearValues[1];
	}

	@Override
	public double getMin() {
		
		if ( dataList.size() == 0 ) {
			
			return -1;
			
		}
		
		double[] allValues = new double[dataList.size() * 12];
		int count = 0;
		
		for ( int row = 0; row < dataList.size(); row++) {
			
			for ( int col = 1; col < 13; col++ ) {
				
				allValues[count] = dataList.get(row)[col];
				count++;
			}
			
		}
		
		Arrays.sort(allValues);
		
		System.out.println(allValues[0]);
		return allValues[0];
	}

	@Override
	public double coverageToArea(double coverage) {
	
		coverage = coverage / 100;
		double surfaceCovered = coverage * SURFACE_AREA;
		
		System.out.println("surface covered: " + surfaceCovered);
		return surfaceCovered;
	}

	@Override
	public int beginYear() {
		
		int beginYear = (int) dataList.get(0)[0];
		
		System.out.println("The first year: " + beginYear);
		return beginYear;
	}

	@Override
	public int endYear() {
		
		int endYear = (int) dataList.get(dataList.size() - 1)[0];
		
		System.out.println("The last year: " + endYear);
		return endYear;
	}

}
