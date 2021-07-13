package maze;

import java.util.ArrayList;

public class MazeResolution {
	public static String[][] mainMatrix;
	public static ArrayList<String> indexFound = new ArrayList<>(); 

	public static void readGentrackMaze() {
		int mazeDimention[] = Maze.mazeDimention;
		ArrayList<String> gentrackMaze = Maze.gentrackMaze; 
		
		//Create a matrix for gentrackMaze
		ArrayList<String> newGentrackMaze = new ArrayList<String>();
		int sizeI = mazeDimention[0];
		int sizeJ = mazeDimention[1];
		String[][] gentrackMazeMatrix = new String[sizeI][sizeJ]; 
		
		//Separate gentrackMaze values and save on newGentrackMaze
		for(int j = 0; j< gentrackMaze.size(); j++) {
			String[] split = gentrackMaze.get(j).split(" ");
			for(int i = 0; i< split.length; i++) {
				newGentrackMaze.add(split[i]);
			}
		}

	
		//Create a gentrack maze matrix
		
		int k = 0;
		for(int i = 0 ; i<sizeI; i++ ) {
			for(int j = 0; j< sizeJ; j++) {
				if(k < newGentrackMaze.size()) {
					gentrackMazeMatrix[i][j] = newGentrackMaze.get(k);
					k++;
				}
			}
		}
	
		mainMatrix = gentrackMazeMatrix;
		System.out.println("INPUT");
		System.out.println();
		printMatrix(gentrackMazeMatrix, sizeI, sizeJ);
		
		//Verify if the dimension write on file it's compatible with the matrix on the file
		int sizeIgentrackMazeMatrix = gentrackMazeMatrix.length;
		int sizeJgentrackMazeMatrix = gentrackMazeMatrix[0].length;
		
		if(sizeIgentrackMazeMatrix != sizeI || sizeJgentrackMazeMatrix != sizeJ) {
			System.out.println("===============");
			System.out.println("Inconsistency with file data! ");
			System.out.println("The <WIDTH> and <HEIGHT> from the .txt file is diferent from the entry MATRIX! ");
			System.out.println("===============");
		}
		
	}//readGentrackMaze

	
	//Show Gentrack Maze
	public static void printMatrix(String[][] matrix, int vI, int vJ) {
		for(int i =0 ; i<vI; i++) {
			for(int j = 0; j<vJ; j++) {
				System.out.printf("%s ", matrix[i][j]);
			}
			System.out.println();
		}
	}
	
	
	//Main Logic
	public static void findPath() {
		int mazeDimention[] = Maze.mazeDimention;
		int iMaze, jMaze;
		iMaze = mazeDimention[0];
		jMaze = mazeDimention[1];
		
		int x0, y0; //initial position
		int x1, y1; //end position
		x0 = Maze.mazeStartPosition[0];
		y0 = Maze.mazeStartPosition[1];
		x1 = Maze.mazeEndPosition[0];
		y1 = Maze.mazeEndPosition[1];
		
		//Change start, and and walls
		mainMatrix[x0][y0] = "S";
		mainMatrix[x1][y1] = "E";
		
		for(int i = 0 ; i<iMaze; i++) {
			for(int j = 0; j<jMaze; j++) {
				if(mainMatrix[i][j].contains("1")) {
					mainMatrix[i][j] = "#";		
				}
			}
		}
		
		int i = x0;
		int j = y0;
		int cont = 0;
		//indexFound.add(i+","+j);
		while(cont != 1) {
			//System.out.println();
			//printMatrix(mainMatrix, iMaze, jMaze);
			
			//get next element
			String next1 = nextValue(mainMatrix, i+1, j, iMaze, jMaze);
			String next2 = nextValue(mainMatrix, i-1, j, iMaze, jMaze);
			String next3 = nextValue(mainMatrix, i, j+1, iMaze, jMaze);
			String next4 = nextValue(mainMatrix, i, j-1, iMaze, jMaze);
			
			if(next1.equals("0")) {
				indexFound.add(i+","+j);
				
				if(i+1 >= iMaze) {
					i = 0;
				}else {
					i = i+1;
				}
				mainMatrix[i][j] = "X";
			
			}else if(next2.equals("0")) {
				indexFound.add(i+","+j);
				if(i-1 <0) {
					i = iMaze-1;
				}else {
					i = i-1;	
				}
				mainMatrix[i][j] = "X";
				
				
			}else if(next3.equals("0")) {
				indexFound.add(i+","+j);
				if(j+1 >= jMaze) {
					j = 0;
				}else {
					j = j+1;	
				}
				mainMatrix[i][j] = "X";
				
			}else if(next4.equals("0")) {
				indexFound.add(i+","+j);
				if(j-1 < 0) {
					j = jMaze-1;
				}else {
					j = j-1;
					
				}
				mainMatrix[i][j] = "X";
				
			}else if((!next1.equals("0") && !next1.equals("E")) && 
					 (!next2.equals("0") && !next2.equals("E")) && 
					 (!next3.equals("0") && !next3.equals("E")) &&
					 (!next4.equals("0") && !next4.equals("E"))) {
				//patch no conclusive
				//return to previous index to try to find another patch;
				int sizeIndexFound = (indexFound.size())-1;
				
				//Verify if it is soluble
				if(sizeIndexFound <0) {
					System.out.println();
					System.out.println("Sorry, maze not solveable! ");
					break;
				
				}else {
					String[] split = indexFound.get(sizeIndexFound).split(",");
					mainMatrix[i][j] = " ";
					
					i = Integer.parseInt(split[0]);
					j = Integer.parseInt(split[1]);	
					
					indexFound.remove(sizeIndexFound);
				}
			}		
			//Verify if found the end line
			if(next1.equals("E") || next2.equals("E") || next3.equals("E") || next4.equals("E")) {
				cont = 1;
				i = x1;
				j = y1;
			}
		}
		
		//Verify if there is passages after find the way
		for(i = 0 ; i<iMaze; i++) {
			for(j = 0; j<jMaze; j++) {
				if(mainMatrix[i][j].equals("0")) {
					mainMatrix[i][j] = " ";		
				}
			}
		}
		
		System.out.println();
		System.out.println("OUTPUT");
		printMatrix(mainMatrix, iMaze, jMaze);
		
	}//find path
	
	public static String nextValue(String[][] matrix, int index, int jindex, int sizeI, int sizeJ) {
		String value = null;

		if(jindex < 0) {
			jindex = sizeJ - 1;
		
		}else if (jindex >= sizeJ) {
			jindex = 0;
		
		}else if(index < 0) {
			index = sizeI -1;
		
		}else if(index >= sizeI) {
			index = 0;
		}
		value = matrix[index][jindex];
		
		return value;
	}
	
	

	
}