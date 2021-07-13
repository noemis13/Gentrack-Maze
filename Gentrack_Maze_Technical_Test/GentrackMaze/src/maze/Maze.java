package maze;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;


public class Maze {
	public static int[] mazeDimention = new int[2];
	public static int[] mazeStartPosition = new int[2];
	public static int[] mazeEndPosition = new int[2];
	public static ArrayList<String> gentrackMaze = new ArrayList<>();
	private static Scanner scan;
	
	/*--------------------------------------------*/
	public static void main(String[] args) {
		//Get the input
		readInput();
		
		//Logic to solve Maze
		MazeResolution.readGentrackMaze();
		MazeResolution.findPath();
		
	}//MAIN
	/*--------------------------------------------*/
	
	public static void readInput() {
		scan = new Scanner(System.in);
		
		System.out.println();
		System.out.println("========== MAZE RESOLUTION =============");
		System.out.println("Please, enter the LOCATION with the file NAME with the extention (e.g .txt): \n    ");
		System.out.println("E.G - ' C:\\folder\\Projects\\Gentrack_Maze_Technical_Test_V4\\Samples\\input.txt ' ");
		String inputName = scan.next();
		System.out.println(inputName);
		//Verify the entry
		
		try {
			FileReader file = new FileReader(inputName);
			BufferedReader readFile = new BufferedReader(file);
			String line = readFile.readLine();
		
			int cont = 0;
			while(line != null) {
				inputManipulation(line, cont);
				line = readFile.readLine();
		    	cont++;
		    }
		   
		    file.close();
		        
		}catch (IOException e) {
			System.out.printf("Sorry, File error!", e.getMessage());
		}
		
	}//End of readInput()

	//Class to separate the file values
	public static void inputManipulation(String line, int cont) {

		String[] split = line.split(" ");
	
		if(cont == 0) {
			mazeDimention[0] = Integer.parseInt(split[1]);
			mazeDimention[1] = Integer.parseInt(split[0]);
			
		}else if(cont == 1) {
			mazeStartPosition[0] = Integer.parseInt(split[1]);
			mazeStartPosition[1] = Integer.parseInt(split[0]);
		
		}else if(cont == 2) {
			mazeEndPosition[0] = Integer.parseInt(split[1]);
			mazeEndPosition[1] = Integer.parseInt(split[0]);
		
		}else {
			gentrackMaze.add(line);
		}
		
		
	}//inputManipulation
	
	



}