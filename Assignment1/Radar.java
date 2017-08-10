// Josh di Bona
// DBNJOS001
// Radar.java
// 10 August 2014
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveAction;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import java.util.*;

public class Radar {

	static long startTime = 0;
	
	private static void startTime(){ // Start timing
		startTime = System.currentTimeMillis();
	}
	private static float endTime(){ // End timing
		return (System.currentTimeMillis() - startTime) / 1000.0f; 
	}

	static final ForkJoinPool fjPool = new ForkJoinPool(); // Create fork/join pool
	static void cc(float[] rec, float[] trans, int size, float[] Cal){
	fjPool.invoke(new Correlate(size, 0, rec, trans, Cal)); // invoke the pool
	}
	
	static void mm(int high, float[] Cor, double[] max){
	fjPool.invoke(new Max(high, 0, Cor, max));  // invoke the pool
	}


	private float[] getArray(String filename)throws IOException // Read the files into arrays.
	{
		Scanner scan = new Scanner(new File(filename));
		String current = "";
		int count = 0;
		int size = Integer.parseInt(scan.next());
		float[] Temp = new float[size];
		while (scan.hasNext())
		{
			current = scan.next();
			Temp[count] = Float.parseFloat(current);
			current = "";
			count++;
		}
		return Temp;
	}

	
	public static void main(String[] args)throws IOException {
	Scanner scan = new Scanner(System.in); // Scanner object to get user input.
	String methodChoice = "";
	String receiveName = "";
	String transmitName = "";
	String choice = "";
	
	Radar radar = new Radar();
	double time;	
	double[] max = new double[1];
	float[] Receive; // array to store received signal
	float[] Transmit; // array to store transmitted signal
	int filesize = 0;
	double maxS = 0;
		
	while(true) // loop for user input
	{
		max[0] = 0;
		System.out.println("WELCOME TO THE RADAR ECHO LOCATION SYSTEM");
		System.out.println("Enter the name of the transmited file with extension (e.g transmit.txt)  'E' to Exit.");
		choice = scan.next();
		if (choice.equalsIgnoreCase("E"))
		{
			System.out.println("Goodbye!");
			break;
		}
		else
		{
			transmitName = choice;
		}
		
		System.out.println("Enter the name of the received file with extension (e.g received.txt)  'E' to Exit.");
		choice = scan.next();
		if (choice.equalsIgnoreCase("E")){
		System.out.println("Goodbye!");
		break;}
		else
		{
			receiveName = choice;
		}
		try
		{
			Scanner receiveN = new Scanner(new File(receiveName));// Opening the text files to search in		
			filesize = Integer.parseInt(receiveN.next());
			Receive = new float[filesize];
			Transmit = new float[filesize];
	
		}
		catch (IOException e)
		{
			System.out.println("File not found"); // Error to print if files aren't found
		}
		
		Receive = radar.getArray(receiveName);
		Transmit = radar.getArray(transmitName);
		float[] calculated = new float[filesize]; // shared array to store correlated array

		System.out.println("USE PARALLEL('P') OR SEQUENTIAL('S') METHOD?");
		methodChoice = scan.next();
		if (methodChoice.equalsIgnoreCase("P"))
		{
			radar.startTime();
			radar.cc(Receive, Transmit, filesize, calculated);
			time = radar.endTime();
			System.out.println("Parallel cross-correlate took " + time + "s");
			
			radar.startTime();
			radar.mm(filesize, calculated, max);
			time = radar.endTime();
			System.out.println("Maximum is: " + max[0]);
			System.out.println("Parallel find max took " + time + "s");
	

		}
		else if (methodChoice.equalsIgnoreCase("S"))
		{
			Correlate calc = new Correlate(filesize, 0, Receive, Transmit, calculated);
			radar.startTime();
			calculated = calc.Sequentialcompute();
			time = radar.endTime();
			System.out.println("Sequential cross correlate took " + time + "s");
			
			Max getmax = new Max(filesize, 0, calculated, max);
			radar.startTime();
			maxS = getmax.SequentialgetMax();
			time = radar.endTime();
			System.out.println("Maximum is: " + maxS);
			System.out.println("Sequential find max took " + time + "s");
		}
		else if (methodChoice.equalsIgnoreCase("E"))
		{
			System.out.println("Goodbye!");
			break;
		}

	
	
		}
	
				
	}


}
