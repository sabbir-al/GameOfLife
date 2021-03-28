/*Project Done BY:
 * Joel D'Souza 	b00079296
 * Sabbir Alam  	b00079438
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Scanner;

public class GOLModel {
	public boolean[][] gameMatrix = new boolean[320][160];
	private int columns = 320;	//Maximum Columns in our Boolean Matrix
	private int rows = 160;		//Maximum rows in our Boolean Matrix
	private int size = 10;		//Default Grid Size.    NOTE Size Values: BIG -> 20, MEDIUM -> 10, SMALL -> 5 
	private int speed = 100;	//Default speed         NOTE Speed Values: SLOW->500,  NORMAL->100,  FAST->75
	private int startingPointi = 80, startingPointj = 40;		//Top Left Cell number in the boolean matrix for the DRAWN component.
	private int initialX,initialY,finalX,finalY;		//USED to calculate shifts in cells for the dragging of matrix  USES XY coordinates
	private int initialI,initialJ;	//USED to calculate shifts in the BOOLEAN MATRIX according to shift in component IJ stands for matrix column and row
	private int finalI = 80;	//Same as initial but provides a final location of the topLEFT cell number in the boolean matrix after the shift
	private int finalJ = 40;
	private int generationCount = 0;	
	private boolean edit = false;	//Is the edit checkbox of the panel selected or not
	private int speedSelectionIndex= 1, sizeSelectionIndex = 1;		//The speed and size combo box of the panel are set to 1 as default.
	private int prefSpeedSelectionIndex = 1, prefSizeSelectionIndex = 1;  //The speed and size combo box of the preference Panel are set to 1 as default.
	private boolean prefEdit=false;	//Is the edit checkbox of the preference panel selected or not
	
	public GOLModel() {
		try {
			Scanner fin = new Scanner(new File(new File("Default Settings.txt").getAbsolutePath().toString()));	
			prefSizeSelectionIndex=fin.nextInt();
			sizeSelectionIndex =prefSizeSelectionIndex;
			prefSpeedSelectionIndex =fin.nextInt();
			speedSelectionIndex=prefSpeedSelectionIndex;
			prefEdit=fin.nextBoolean();
			edit=prefEdit;
			
			/*Importing the Default settings set by the preference panel IF it exists
			 * It sets all the index of the combo boxes for the panel as well as the preference panel 
			 * to what was selected in the preference panel before the program was closed.
			 * The main setting of the boxes are done by the controller.*/
			
		} catch (FileNotFoundException e) {
			//DO Nothing
		}
		
		clear(); //Function that sets all the cells in the boolean matrix to false
	}
	
	public void clear() {
		for(boolean[] barray : gameMatrix)
			Arrays.fill(barray, false);	
	}
	
	
	public void nextGeneration() { //One simulation Step
		this.generationCount++;
		
		boolean[][] tempMatrix = new boolean[320][160]; //Temporary matrix that has the values of the current matrix
		for(int i = 0; i < this.rows; i++)
			for(int j = 0; j < this.columns; j++) {
				tempMatrix[j][i] = this.gameMatrix[j][i];
			}
		
		for(int i = 0; i < this.rows; i++)
			for(int j = 0; j < this.columns; j++)
			{
				boolean top = false;
				boolean bottom = false;
				boolean left = false;
				boolean right = false;
				int count = 0;
				
				if(i == 0)
					top = true;
				if(i == this.rows - 1)
					bottom = true;
				if(j == 0)
					left = true;
				if(j == this.columns - 1)
					right = true;
				
				if(!left && tempMatrix[j-1][i]) //left cell
					count++;
				if(!left && !top && tempMatrix[j-1][i-1]) //top left
					count++;
				if(!top && tempMatrix[j][i-1]) //top
					count++;
				if(!right && !top && tempMatrix[j+1][i-1]) //top right
					count++;
				if(!right && tempMatrix[j+1][i]) //right
					count++;
				if(!bottom && !right && tempMatrix[j+1][i+1]) //bottom right
					count++;
				if(!bottom && tempMatrix[j][i+1]) //bottom
					count++;
				if(!bottom && !left && tempMatrix[j-1][i+1]) //bottom left
					count++;
				
				if(tempMatrix[j][i]) {
					if(count <= 1 || count>=4)
						this.gameMatrix[j][i] = false;
				}
				
				else {
					if(count == 3)
						this.gameMatrix[j][i] = true;
				}
			}
	}
	
	public void addShape(String name) {		//Adds the initial shapes to the center of the visible grid on screen.
		int displacei = 160, displacej = 80;
		
		if(size == 10) {
			displacei = 80;
			displacej = 40;
		}
		
		else if(size == 20) {
			displacei = 40;
			displacej = 20;
		}
				
		GOLShapes golShapes; 
		if(name.equals("Glider"))
			golShapes = new Glider(this.finalI + displacei,this.finalJ + displacej,gameMatrix);
		
		else if(name.equals("Clear"))
			clear();
		
		else if(name.equals("R-Pentomino"))
			golShapes = new RPentomino(this.finalI + displacei,this.finalJ + displacej,gameMatrix);
		
		else if(name.equals("Tumbler"))
			golShapes = new Tumbler(this.finalI + displacei,this.finalJ + displacej,gameMatrix);
		
		else if(name.equals("Small Exploder"))
			golShapes = new SmallExploder(this.finalI + displacei,this.finalJ + displacej,gameMatrix);
		
		else if(name.equals("Small Spaceship"))
			golShapes = new SmallSpaceship(this.finalI + displacei,this.finalJ + displacej,gameMatrix);
		
			
	}
	
	public void mousePressed(int initialX, int initialY, int initialI, int initialJ) {	//Saves all the initial locations on the grid when the mouse is pressed
		this.initialX = initialX;
		this.initialY = initialY;
		this.initialI = initialI;
		this.initialJ = initialJ;
		
	}
	
	public void mouseDragged(int finalx, int finaly) {	//Calculates the shifts in the grid when the mosue is dragged
		this.finalX = initialX-finalx;
		this.finalY = initialY-finaly;
		if(size!=5) {
			finalI = initialI+(finalX/size);
			finalJ = initialJ+(finalY/size);
			if(finalI<0) {
				finalI=0;
			}
			if(finalJ<0) {
				finalJ=0;
			}
			if(size==10) { //Calculations for default grid size
				if(finalI>160) {
					finalI=160;
				}
				if(finalJ>80) {
					finalJ=80;
				}
			}
			if(size==20) {	//Calculations for Big grid size
				if(finalI>240) {
					finalI=240;
				}
				if(finalJ>120) {
					finalJ=120;
				}
			}
		}
	}
	
	public void mouseClicked(int x, int y) {	//Marks the cell in the matrix to true if clicked
		int i = x/this.size;
		int j = y/this.size;
		this.gameMatrix[this.finalI + i][this.finalJ + j] = !this.gameMatrix[this.finalI + i][this.finalJ + j];
	}
	
	public void setSmall() {
		this.size = 5;
		this.startingPointi = 0;
		this.startingPointj = 0;
		this.finalI = 0;
		this.finalJ = 0;
		
		this.sizeSelectionIndex = 0;
	}
	
	public void setMedium() {
		this.sizeSelectionIndex=1;
		this.size = 10;
		this.startingPointi = 80;
		this.startingPointj = 40;
		this.finalI = 80;
		this.finalJ = 40;
	}
	
	public void setBig() {
		this.size = 20;
		this.startingPointi = 120;
		this.startingPointj = 60;
		this.finalI = 120;
		this.finalJ = 60;
		
		this.sizeSelectionIndex = 2;
	}
	
	
	
	public void setSpeed(String s) {
		if(s.equals("Slow")) { 
			this.speed=500;
			this.speedSelectionIndex=0;
		}
		
		else if(s.equals("Normal")) {
			this.speed=100;
			this.speedSelectionIndex=1;
		}
		
		else {
			this.speed=75;
			this.speedSelectionIndex=2;
		}
	}
	
	public void setEdit(boolean b) {
		this.edit=b;
	}
	public void setPrefEdit(boolean b) {
		this.prefEdit=b;
	}
	public void setPrefSizeIndex(int i) {
		this.prefSizeSelectionIndex=i;
	}
	public void setPrefSpeedIndex(int i) {
		this.prefSpeedSelectionIndex=i;
	}

	public int getSize() {
		return this.size;
	}
	
	public int getStartingPointi() {
		return this.startingPointi;
	}
	
	public int getStartingPointj() {
		return this.startingPointj;
	}
	public int getColumn() {
		return this.columns;
	}
	public int getRow() {
		return this.rows;
	}
	
	public int getFinalI() {
		return this.finalI;
	}
	
	public int getFinalJ() {
		return this.finalJ;
	}
	
	public int getGenCount() {
		return this.generationCount;
	}
	public int getSpeed() {
		return this.speed;
	}
	public boolean getEdit() {
		return this.edit;
	}
	
	public int getSizeSelectionIndex() {
		return this.sizeSelectionIndex;
	}
	
	public int getSpeedSelectionIndex() {
		return this.speedSelectionIndex;
	}
	
	public void saveState(String f, int sizeSelectionIndex, int speedSelectionIndex) throws FileNotFoundException {
		/*
		 * Saves the complete grid configuration to the specified location
		 */
		
		File newFile = new File(f + ".txt");
		PrintWriter fout = new PrintWriter(newFile);
	
		fout.println("GAME OF LIFE");
		fout.println(this.size);
		fout.println(this.speed);
		fout.println(this.startingPointi);
		fout.println(this.startingPointj);
		fout.println(this.finalI);
		fout.println(this.finalJ);
		fout.println(this.generationCount);
		fout.println(this.edit);
		fout.println(sizeSelectionIndex);
		fout.println(speedSelectionIndex);
		
		for(int i = 0; i<this.rows; i++)
			for(int j = 0; j<this.columns; j++) {
				fout.println(gameMatrix[j][i]);
			}
		fout.close();
	}
	
	public int returnSaveState(File f) throws FileNotFoundException {
		/*
		 * Opens the saved file and sets all the values in the model according to the previous state
		 */
		
		Scanner fin = new Scanner(f);
		String name = "GAME OF LIFE";
		
		if(fin.hasNext() && name.equals(fin.nextLine()) ){
			this.size = fin.nextInt();
			this.speed = fin.nextInt();
			this.startingPointi = fin.nextInt();
			this.startingPointj = fin.nextInt();
			this.finalI = fin.nextInt();
			this.finalJ = fin.nextInt();
			this.generationCount = fin.nextInt();
			this.edit = fin.nextBoolean();
			this.sizeSelectionIndex = fin.nextInt();
			this.speedSelectionIndex = fin.nextInt();
			
			for(int i = 0; i<this.rows; i++)
				for(int j = 0; j<this.columns; j++) {
					gameMatrix[j][i] = fin.nextBoolean();
				}
			
			return 0;
		}
		
		else
			return -1;
	}
	
	
	public void savePreference() throws FileNotFoundException {
		/*
		 * Saves the preferences from the preference panel into the default game directory
		 */
		
		PrintWriter fout = new PrintWriter(new File(new File("Default Settings.txt").getAbsolutePath().toString()));
		fout.println(this.prefSizeSelectionIndex);
		fout.println(this.prefSpeedSelectionIndex);
		fout.println(this.prefEdit);
		
		fout.close();
	}
}
