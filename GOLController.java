/*Project Done BY:
 * Joel D'Souza 	b00079296
 * Sabbir Alam  	b00079438
 */

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.io.File;
import java.io.FileNotFoundException;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.Timer;
import javax.swing.filechooser.FileNameExtensionFilter;

public class GOLController {
	private final GOLModel golModel;
	private final GOLView golView; 
	private Timer t;

	public GOLController(GOLModel golModel, GOLView golView) {	//Controller has access to the model and view
		this.golModel = golModel;
		this.golView = golView;
		this.golView.setSize(this.golModel.getSize());
		this.golView.setColumn(this.golModel.getColumn());
		this.golView.setRow(this.golModel.getRow());
		this.golView.setStartingPointi(this.golModel.getStartingPointi());
		this.golView.setStartingPointj(this.golModel.getStartingPointj());
		this.golView.setMatrix(this.golModel.gameMatrix);
	
		
		/*-----------------Listeners for the Mouse events------------------*/
		
		
		this.golView.panelMouseListener(new MouseAdapter() { //If clicked anywhere in the panel, the keyboard will be set in focus once again so it can take inputs
			@Override
			public void mouseClicked(MouseEvent e) {
				golView.keyboardFocus(); //Focuses the keyboard so its input can be taken
			}
		});
		
		this.golView.compMouseListener(new MouseAdapter() {	//MouseListeners to get the points for making a cell live or dead, as well as the points when dragging the grid around
			@Override
			public void mousePressed(MouseEvent e) {
				golModel.mousePressed(e.getX(), e.getY(), golView.getStartingPointi(),
				golView.getStartingPointj());	
			}
				
			@Override
			public void mouseReleased(MouseEvent e) {
				golView.setDefaultCursor();
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				if(e.getButton() == 1 ) {
					if(golModel.getEdit()) {	//left click to get the points to make the cell live or dead
						golModel.mouseClicked(e.getX(), e.getY());		
						golView.refresh();
					}
				}
				
				else if(e.getButton() == 3) { 	//right click to make the popup visible. 
					golView.enablePopup(e.getPoint());
				}
				
				golView.keyboardFocus();//Focuses the keyboard so its input can be taken
			}
		});
		
		this.golView.compMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseDragged(MouseEvent e) {
					golView.setHandCursor();
					golModel.mouseDragged(e.getX(), e.getY());
					golView.setStartingPointi(golModel.getFinalI());
					golView.setStartingPointj(golModel.getFinalJ());
					golView.refresh();
			}
		});
		
		/*-----------------Listeners for the Panel Buttons and combo Boxes------------------*/
		
		
		this.golView.addSizeComboBoxListener(new ActionListener() {  //Listener for the Size combo box

			@Override
			public void actionPerformed(ActionEvent e) {
				JComboBox cb = (JComboBox)e.getSource();
		        String newSize = (String)cb.getSelectedItem();
		        
		        if(newSize.equals("Small"))
		        	golModel.setSmall();        
		        else if (newSize.equals("Medium"))
		        	golModel.setMedium();
		        else
		        	golModel.setBig();
		   
		        golView.setSize(golModel.getSize());
	        	golView.setStartingPointi(golModel.getStartingPointi()); //Sets the point of the top left cell in the view according to the matrix.
	        	golView.setStartingPointj(golModel.getStartingPointj());
		        golView.refresh();
		        
		        golView.keyboardFocus(); //Focuses the keyboard so its input can be taken
			}	
		});
		
		this.golView.addEditListener(new ActionListener() { //Listener for the edit box 
			@Override
			public void actionPerformed(ActionEvent e) {
				JCheckBox cb = (JCheckBox)e.getSource();
				if(cb.isSelected())
					golModel.setEdit(true);
				else
					golModel.setEdit(false);
				
				golView.keyboardFocus();//Focuses the keyboard so its input can be taken
			}			
		});
		
		this.golView.addShapesComboBoxListener(new ActionListener() { //Listens for changes in the Shapes Combo Box
			@Override
			public void actionPerformed(ActionEvent e) {
				JComboBox cb = (JComboBox)e.getSource();
		        String name = (String)cb.getSelectedItem();
		        golModel.addShape(name);
				golView.refresh();
				
				golView.keyboardFocus();//Focuses the keyboard so its input can be taken
			}
			
		});
		
		this.golView.addNextListener(new ActionListener() {	//Listener for the Next Button

			@Override
			public void actionPerformed(ActionEvent e) {
				golModel.nextGeneration();
				golView.setGeneration(golModel.getGenCount());
				golView.refresh();
				
				golView.keyboardFocus();
			}
			
		});
		
		this.golView.addStartListener(new ActionListener() {	//Listener for the Start Button
			@Override
			public void actionPerformed(ActionEvent e) {
				JButton b = (JButton)e.getSource();
				startSimulation(b);
				
				golView.keyboardFocus();//Focuses the keyboard so its input can be taken
			}
		});
		
		this.golView.addSpeedComboBoxListener(new ActionListener() {	//Listens for changes in the Speed Combo Box

			@Override
			public void actionPerformed(ActionEvent e) {
				JComboBox cb = (JComboBox)e.getSource();
		        String name = (String)cb.getSelectedItem();
				
		        golModel.setSpeed(name);
		        
		        if(t != null && t.isRunning()) {
		        	 t.stop();
				        t = new Timer(golModel.getSpeed(), new ActionListener() {
							@Override
							public void actionPerformed(ActionEvent arg0) {
								golModel.nextGeneration();
								golView.setGeneration(golModel.getGenCount());
								golView.refresh();
							}
						});
					t.start();
		        }
		        golView.keyboardFocus();//Focuses the keyboard so its input can be taken
			}
			
		});
		
		/*-----------------Listeners for the POPUP Menu------------------*/
		
		this.golView.addOpenListener(new ActionListener() {	//Listener for the Open Menu Item in the popup

			@Override
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser fileChoose = new JFileChooser();
				fileChoose.setCurrentDirectory(new File("."));
				fileChoose.setFileFilter(new FileNameExtensionFilter("Only text files", "txt"));
				
				int r = golView.fileOpenChoosePath(fileChoose);
			    if (r != JFileChooser.APPROVE_OPTION) return;
			    
			    File openFile = fileChoose.getSelectedFile();
			    try {
					int s = golModel.returnSaveState(openFile);		//If the File exists then restore the values
					if(s<0) {
						golView.showWrongFileError();	//If the file does now exist, then show error dialog
					}
					else {
						golView.updateView(golModel.getFinalI(), golModel.getFinalJ(), 
								golModel.getEdit(), golModel.getGenCount(), golModel.getSpeedSelectionIndex(), golModel.getSizeSelectionIndex());
						//If the file exists update the view according to the restored values in the model
						
						golView.refresh();
					}
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
			    
			    golView.keyboardFocus();//Focuses the keyboard so its input can be taken
			}
			
		});
		
		this.golView.addSaveListener(new ActionListener() {	//Listener for the Save Menu Item in the popup

			@Override
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser fileChoose = new JFileChooser();
				fileChoose.setCurrentDirectory(new File("."));
				fileChoose.setFileFilter(new FileNameExtensionFilter("Only text files", "txt"));
				
				int r = golView.fileSaveChoosePath(fileChoose);
			    if (r == JFileChooser.APPROVE_OPTION) {
						try {
							golModel.saveState(fileChoose.getSelectedFile().getAbsolutePath(), golView.getSizeSelectionIndex(), golView.getSpeedSelectionIndex());
						} catch (FileNotFoundException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					
			    };
			    golView.keyboardFocus();//Focuses the keyboard so its input can be taken
			}
		});
		
		this.golView.addPreferenceListener(new ActionListener() {//Listener for the Preference Menu Item in the popup

			@Override
			public void actionPerformed(ActionEvent arg0) {
				golView.setPrefPanelVisible();
			}
			
		});
	
		
		/*-----------------Listeners for the Preference JDialog Menu------------------*/
		
		this.golView.addPrefEditListener(new ActionListener() {	//Listens for changes to the Edit checkbox in the preference menu 

			@Override
			public void actionPerformed(ActionEvent e) {
				JCheckBox cb = (JCheckBox)e.getSource();
				if(cb.isSelected()) {
					golView.setEditIndex(true);
					golModel.setEdit(true);
					golModel.setPrefEdit(true);
					
				}
				else {
					golView.setEditIndex(false);
					golModel.setEdit(false);
					golModel.setPrefEdit(false);
				}
				
			}
		});
		
		this.golView.addPrefSpeedComboBoxListener(new ActionListener() { //Listens for changes to the Speed Combo Box in the preference menu 

			@Override
			public void actionPerformed(ActionEvent e) {
		        golView.setSpeedComboBoxIndex(golView.getPrefSpeedSelectionIndex());	//Changes the Value in the speed combo box for the panel
		        golModel.setPrefSpeedIndex(golView.getPrefSpeedSelectionIndex());	//Sets the preference in the Model for saving
			}
		});	
		
		this.golView.addPrefSizeComboBoxListener(new ActionListener() { //Listens for changes to the Size Combo Box in the preference menu 

			@Override
			public void actionPerformed(ActionEvent e) {
				golView.setSizeComboBoxIndex(golView.getPrefSizeSelectionIndex()); //Changes the Value in the Size combo box for the panel
				golModel.setPrefSizeIndex(golView.getPrefSizeSelectionIndex());	//Sets the preference in the Model for saving
			}
			
		});
		
		this.golView.addPrefSaveListener(new ActionListener() { //Listens for a press of the save button in the preference Panel
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					golModel.savePreference();	//Save all the values in the default location through the model
				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				golView.setPrefPanelInvisible(); //Closes the preference panel
				
				golView.keyboardFocus();//Focuses the keyboard so its input can be taken
			}
		});
		
		/*--------------------------------OPTIONAL FEAUTRES--------------------------------*/
		
		/*-----------------Listener for the Keyboard Buttons--------------------*/
	
		this.golView.addCompKeyListener(new KeyAdapter() {  

			@Override
			public void keyPressed(KeyEvent e) {
				switch (e.getKeyCode()) {
				case KeyEvent.VK_N:			//N button Signifies NEXT
					golModel.nextGeneration();
					golView.setGeneration(golModel.getGenCount());
					golView.refresh();
					break;
					
				case KeyEvent.VK_S:		//S button STARTS and STOPS the simulation
					startSimulation(golView.getStart());
					break;
					
				default:
					break;
				}
			}
		});
		
		/*-----------------Listeners To change the colors of the view through the PopUp------------------*/
		
		
		this.golView.addChangeBackgroundListener(new ActionListener() {		//Changes the Background Color

			@Override
			public void actionPerformed(ActionEvent arg0) {
				golView.changeBackgroundColor("Set Background Color");
			}
		});
		
		this.golView.addChangeCellListener(new ActionListener() {	//Changes the Cell Color for live cells

			@Override
			public void actionPerformed(ActionEvent arg0) {
				golView.changeCellColor("Set Cell Color");
			}
		});
		
		this.golView.addChangeLineListener(new ActionListener() {		//Changes the Line Color

			@Override
			public void actionPerformed(ActionEvent arg0) {
				golView.changeLineColor("Set Line Color");
			}
		});
		
		this.golView.updateDefault(golModel.getSpeedSelectionIndex(), golModel.getSizeSelectionIndex(), golModel.getEdit());
	}
	
	void startSimulation(JButton b) {			//For Start Button and S keyboard Key
		if(b.getText().equals("Start")) {
			golModel.setEdit(false);
			golView.getEdit().setSelected(false);
			golView.getEdit().setEnabled(false);
			b.setText(new String("Stop"));
			golView.getNext().setEnabled(false);
			t = new Timer(golModel.getSpeed(), new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0) {
					golModel.nextGeneration();
					golView.setGeneration(golModel.getGenCount());
					golView.refresh();
				}
			});
			t.start();
		}
		
		else {
			t.stop();
			golView.getEdit().setEnabled(true);
			b.setText(new String("Start"));
			golView.getNext().setEnabled(true);
		}
	}

}