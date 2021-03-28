/*Project Done BY:
 * Joel D'Souza 	b00079296
 * Sabbir Alam  	b00079438
 */

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JColorChooser;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.SwingConstants;
import javax.swing.Timer;

class GOLView {
	
	 //VIEW CLASS has Access to the panel, component, Frame and the Preference Panel
	 //Gets and sets the private values in all these classes using appropriate getters and setters
	//The Controller has access only to the methods implemented in the View class and not directly to the panel, component, Frame and the Preference Panel
	 private final GamePanel panel = new GamePanel();
	 private final GameComponent comp = new GameComponent();
	 private final ProjectFrame frame = new ProjectFrame(panel,comp);
	 private final PreferencePanel prefPanel = new PreferencePanel(frame, false, false);

	public GOLView() {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				SplashDialog splashWindow = new SplashDialog(frame, true);	//OPTIONAL FEATURE SPLASHWINDOW
				splashWindow.setVisible(true);
				frame.setVisible(true);
			}
			
		});
	}
	
	public void setMatrix(boolean[][] gameMatrix) {
		comp.setMatrix(gameMatrix);
	}
	
	public void refresh() {
		comp.repaint();
	}
	
	public int fileOpenChoosePath(JFileChooser j) {		//Shows JDialog to choose the file to open
		int r = j.showOpenDialog(frame);
		return r;
	}
	public int fileSaveChoosePath(JFileChooser j) {		//Shows JDialog to choose the save location for file
		int r = j.showSaveDialog(frame);
		return r;
	}
	
	
	/*-------------GETTERS FOR THE PANEL THAT THE CONTROLLER CAN ACCESS--------------*/
	
	public JCheckBox getEdit() {
		return panel.getEdit();
	}
	
	public JButton getStart() {
		return panel.getStart();
	}
	
	public JButton getNext() {
		return panel.getNext();
	}
	
	
	/*-------------GETTERS FOR THE COMPONENT THAT THE CONTROLLER CAN ACCESS--------------*/
	public int getSize () {
		return comp.getSizee();
	}
	
	public int getStartingPointi () {
		return comp.getStartingPointi();
	}
	
	public int getStartingPointj () {
		return comp.getStartingPointj();
	}
	
	public int getSpeedSelectionIndex () {
		return panel.getSpeedComboBox().getSelectedIndex();
	}
	
	public int getSizeSelectionIndex () {
		return panel.getSizeComboBox().getSelectedIndex();
	}
	
	public int getPrefSizeSelectionIndex () {
		return prefPanel.getSizeComboBox().getSelectedIndex();
	}
	
	public int getPrefSpeedSelectionIndex () {
		return prefPanel.getSpeedComboBox().getSelectedIndex();
	}

	
	/*-------------SETTERS FOR THE COMPONENT THAT THE CONTROLLER CAN ACCESS--------------*/
	public void setSize (int size) {
		comp.setSize(size);
	}
	
	public void setStartingPointi (int pointi) {
		comp.setStartingPointi(pointi); ;
	}
	
	public void setStartingPointj (int pointj) {
		comp.setStartingPointj(pointj); 
	}
	
	public void setColumn (int column) {
		comp.setColumn(column);
	}
	
	public void setRow (int row) {
		comp.setRow(row);
	}
	
	public void setHandCursor () {
		comp.setCursor(new Cursor(Cursor.HAND_CURSOR));
	}
	
	public void setDefaultCursor () {
		comp.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
	}
	
	/*-------------SETTERS FOR THE PANEL THAT THE CONTROLLER CAN ACCESS--------------*/
	
	public void setGeneration(int i) {
		panel.getGeneration().setText("Generation: " + i);
	}
	
	public void setSpeedComboBoxIndex (int i) {
		panel.setSpeedComboBoxIndex(i);
	}
	
	public void setSizeComboBoxIndex (int i) {
		panel.setSizeComboBoxIndex(i);
	}
	
	public void setEditIndex (boolean b) {
		panel.setEditIndex(b);
	}
	
	
	/*-------------UPDATE THE VIEW FOR RESTORING OLD CONFIGURATION OR RESTORING DEFAULT SETTING SET BY PREFERENCE PANEL--------------*/
	
	public void updateView(int startingPointi, int startingPointj, boolean editSelection, int generation, int speedSelectIndex, int sizeSelectIndex) {
		//Updates the View when user Opens a file with the old grid configuration
		panel.setEditIndex(editSelection);
		panel.setGeneration(generation);
		panel.setSpeedComboBoxIndex(speedSelectIndex);
		panel.setSizeComboBoxIndex(sizeSelectIndex);
		
		comp.setStartingPointi(startingPointi);
		comp.setStartingPointj(startingPointj);

	}
	
	public void updateDefault(int speedIndex, int sizeIndex, boolean b) {
		//Updates the view when the Default values were changes through the preference panel during the last run of the game
		panel.setSpeedComboBoxIndex(speedIndex);
		panel.setSizeComboBoxIndex(sizeIndex);
		panel.setEditIndex(b);
		prefPanel.setSpeedComboBoxIndex(speedIndex);
		prefPanel.setSizeComboBoxIndex(sizeIndex);
		prefPanel.setEditIndex(b);
	}
	
	/*-----------------------LISTENERS FOR THE PANEL-------------------------*/
	public void panelMouseListener(MouseListener panelMouseListener) {
		panel.addMouseListener(panelMouseListener);
	}
	
	public void addEditListener(ActionListener editListener) {
		panel.getEdit().addActionListener(editListener);
	}
	
	public void addShapesComboBoxListener(ActionListener shapesComboBoxListener) {
		panel.getShapesComboBox().addActionListener(shapesComboBoxListener);
	}
	
	public void addNextListener(ActionListener nextListener) {
		panel.getNext().addActionListener(nextListener);
	}
	
	public void addStartListener(ActionListener startListener) {
		panel.getStart().addActionListener(startListener);
	}
	
	public void addSpeedComboBoxListener(ActionListener speedComboBoxListener) {
		panel.getSpeedComboBox().addActionListener(speedComboBoxListener);
	}
	
	public void addSizeComboBoxListener(ActionListener multiplyListener) {
		panel.getSizeComboBox().addActionListener(multiplyListener);
	}
	
	public void addGenerationListener(ActionListener generationListener) {
		panel.getShapesComboBox().addActionListener(generationListener);
	}
	
	
	/*-----------------------LISTENERS FOR THE COMPONENT-------------------------*/
	public void addCompKeyListener(KeyListener panelKeyListener) {
		comp.addKeyListener(panelKeyListener);
	}
	
	public void compMouseListener(MouseListener compMouseListener) {
		comp.addMouseListener(compMouseListener);
	}
	
	public void compMouseMotionListener(MouseMotionListener compMouseListener) {
		comp.addMouseMotionListener(compMouseListener);
	}
	
	/*-----------------------LISTENERS FOR THE POPUP MENU in the Component-------------------------*/
	public void addSaveListener(ActionListener saveListener) { 
		comp.getSave().addActionListener(saveListener);
	}
	
	public void addOpenListener(ActionListener openListener) { 
		comp.getOpen().addActionListener(openListener);
	}
	
	public void addPreferenceListener(ActionListener preferenceListener) { 
		comp.getPreference().addActionListener(preferenceListener);
	}
	
	
	/*-------Setting visibility for popup menu and preference panel-------------*/
	public void enablePopup(Point p) {  //Sets the POPUP menu to be visible
		comp.enablePopup(p);
	}
	
	public void setPrefPanelVisible() {		//Sets the Preference panel to be visible
		this.prefPanel.setVisible(true);
	}
	public void setPrefPanelInvisible() {		//Sets the Preference panel to be invisible
		this.prefPanel.setVisible(false);
	}
	
	
	/*-----------------------LISTENERS FOR THE Preference Panel-------------------------*/
	public void addPrefSpeedComboBoxListener(ActionListener speedComboBoxListener) {
		prefPanel.getSpeedComboBox().addActionListener(speedComboBoxListener);
	}
	
	public void addPrefSizeComboBoxListener(ActionListener multiplyListener) {
		prefPanel.getSizeComboBox().addActionListener(multiplyListener);
	}
	
	public void addPrefEditListener(ActionListener editListener) {
		prefPanel.getEdit().addActionListener(editListener);
	}
	public void addPrefSaveListener(ActionListener saveListener) {
		prefPanel.getSave().addActionListener(saveListener);
	}
	
	
	/*------------------Setters for the Preference panel-----------------*/
	public void setPrefSpeedComboBox(int i) {
		prefPanel.setSpeedComboBoxIndex(i);
	}
	
	public void setPrefSizeComboBox(int i) {
		prefPanel.setSizeComboBoxIndex(i);
	}
	
	public void setPrefEditBox(boolean b) {
		prefPanel.setEditIndex(b);
	}
	
	public void showWrongFileError() {	//Error Dialog for opening a invalid txt file
		JOptionPane.showMessageDialog(this.frame, 
				  "NOT A SUITABLE CONFIGURATION FILE", "ERROR", JOptionPane.ERROR_MESSAGE);
	}
	
	
	/*------------------------OPTIONAL FEATURES------------------------*/
	
	public void keyboardFocus() {	//Focuses the keyboard
		comp.setFocusable(true);
		comp.requestFocusInWindow();
	}
	
	
	/*---------------Listeners for changing the color of the view--------------*/
	public void addChangeBackgroundListener(ActionListener changeBackgroundListener) {
		comp.getBackgroundMenu().addActionListener(changeBackgroundListener);
	}
	
	public void addChangeCellListener(ActionListener changeCellListener) {
		comp.getCellMenu().addActionListener(changeCellListener);
	}
	
	public void addChangeLineListener(ActionListener changeLineListener) {
		comp.getLineMenu().addActionListener(changeLineListener);
	}
	
	/*---------------Getters to get the current color of the view--------------*/
	public Color getBackgroundColor() {
		return comp.getBackgroundColor();
	}
	
	public Color getLineColor() {
		return comp.getLineColor();
	}
	
	public Color getCellColor() {
		return comp.getCellColor();
	}
	
	/*---------------Changes the color of the view according to what the user has selected--------------*/
	public void changeBackgroundColor(String s) {
		comp.setBackground(JColorChooser.showDialog(frame, s, this.getBackgroundColor()));
		comp.repaint();
	}
	
	public void changeCellColor(String s) {
		comp.setCell(JColorChooser.showDialog(frame, s, this.getCellColor()));
		comp.repaint();
	}
	
	public void changeLineColor(String s) {
		comp.setLine(JColorChooser.showDialog(frame, s, this.getLineColor()));
		comp.repaint();
	}
}


class ProjectFrame extends JFrame {

	public ProjectFrame(GamePanel panel, GameComponent comp) {
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setTitle("Game of Life");
		this.setResizable(false);
		this.add(comp);
		this.add(panel, BorderLayout.SOUTH);
		
		this.pack();
		this.setLocationRelativeTo(null);
	
	}
	
}

class GamePanel extends JPanel {
	
	private String[] shapes = new String[] {"Clear", "Glider", "R-Pentomino", "Small Exploder", "Small Spaceship", "Tumbler"};
	private String[] speed = new String[] {"Slow","Normal","Fast"};
	private String[] size = new String[] {"Small", "Medium", "Big"};	
	private JCheckBox edit = new JCheckBox("Edit");
	private JComboBox<String> shapesComboBox = new JComboBox<>(shapes);
	private JButton next = new JButton("Next");
	private JButton start = new JButton("Start");
	private JComboBox<String> speedComboBox = new JComboBox<>(speed);
	private JComboBox<String> sizeComboBox = new JComboBox<>(size);
	private JLabel generation =  new JLabel("Generation: " + 0);
	
	public GamePanel() {
		
		this.add(edit);
		this.add(shapesComboBox);
		this.add(next);
		this.add(start);
		this.add(speedComboBox);
		speedComboBox.setSelectedIndex(1);
		this.add(sizeComboBox);
		sizeComboBox.setSelectedIndex(1);
		this.add(generation);
		
	}
	
	/*---------------GETTERS THE GOLView Class CAN ACCESS----------------*/
	
	public JComboBox<String> getShapesComboBox() {
		return shapesComboBox;
	}
	public JButton getNext() {
		return next;
	}
	public JButton getStart() {
		return start;
	}
	public JComboBox<String> getSpeedComboBox(){
		return speedComboBox;
	}
	public JComboBox<String> getSizeComboBox(){
		return sizeComboBox;
	}
	public JLabel getGeneration() {
		return generation;
	}
	public JCheckBox getEdit() {
		return edit;
	}
	
	/*---------------SETTERS THE GOLView Class CAN ACCESS----------------*/
	public void setSpeedComboBoxIndex(int i){
		 this.speedComboBox.setSelectedIndex(i);
	}
	public void setSizeComboBoxIndex(int i){
		this.sizeComboBox.setSelectedIndex(i);
	}
	public void setGeneration(int i) {
		this.generation.setText("Generation: "+ i);;
	}
	public void setEditIndex(boolean b) {
		this.edit.setSelected(b);
	}
	
}

class GameComponent extends JComponent {
	private boolean[][] gameMatrix;
	private int size; //BIG -> 20, MEDIUM -> 10, SMALL -> 5 
	private int startingPointi, startingPointj;	//The cells of the top left corner of 
	private int column, row;
	private JPopupMenu popmenu = new JPopupMenu();
	private JMenuItem save = new JMenuItem("Save");
	private JMenuItem open = new JMenuItem("Open");
	private JMenuItem prefpanel = new JMenuItem("Preference Panel");
	private JMenuItem changeBackground = new JMenuItem("Change Background Color");
	private JMenuItem changeCell = new JMenuItem("Change Cell Color");
	private JMenuItem changeLine = new JMenuItem("Change Line Color");
	private Color background = Color.GRAY, cell = Color.YELLOW, line = Color.black;
	
	public GameComponent() {
		popmenu.add(save);
		popmenu.add(open);
		popmenu.add(prefpanel);
		popmenu.add(changeBackground);
		popmenu.add(changeCell);
		popmenu.add(changeLine);
		
		popmenu.setVisible(false);
		this.add(popmenu);
		setFocusable(true);
		requestFocusInWindow();
	}
	
	

	@Override
	public void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D)g;
		g2.setColor(background);
		g2.fillRect(0, 0, this.getWidth(), this.getHeight());
		
		g2.setColor(cell);
		for(int i = startingPointi; i < column; i++)
			for(int j = startingPointj; j < row; j++)
				if(gameMatrix[i][j]) {
					g2.fillRect((i-startingPointi)*size, (j-startingPointj)*size, size, size);
				}
		
		g2.setColor(line);
		for(int i = 0; i < this.getWidth(); i+=size) {
			g2.drawLine(i, 0, i, this.getHeight());
			g2.drawLine(0, i, this.getWidth(), i);
		}
		
	}
	
	/*---------------GETTERS THE GOLView Class CAN ACCESS----------------*/
	public int getSizee() {
		return size;
	}
	
	public int getStartingPointi () {
		return startingPointi;
	}
	
	public int getStartingPointj () {
		return startingPointj;
	}
	
	public void enablePopup(Point p) {
		popmenu.show(this, p.x, p.y);
	}

	public JMenuItem getSave() {
		return save;
	}
	
	public JMenuItem getOpen() {
		return open;
	}
	
	public JMenuItem getPreference() {
		return prefpanel;
	}
	public JMenuItem getBackgroundMenu() {
		return this.changeBackground;
	}
	
	public JMenuItem getCellMenu() {
		return this.changeCell;
	}
	
	public JMenuItem getLineMenu() {
		return this.changeLine;
	}
	public Color getBackgroundColor() {
		return this.background;
	}
	public Color getCellColor() {
		return this.cell;
	}
	public Color getLineColor() {
		return this.line;
	}
	
	
	/*---------------SETTERS THE GOLView Class CAN ACCESS----------------*/
	
	
	public void setMatrix(boolean[][] matrix) {
		this.gameMatrix= matrix;
	}
	
	public void setSize(int size) {
		this.size=size;
	}
	
	public void setStartingPointi(int startingPointi) {
		this.startingPointi=startingPointi;
	}
	
	public void setStartingPointj(int startingPointj) {
		this.startingPointj=startingPointj;
	}
	
	public void setColumn(int column) {
		this.column=column;
	}
	
	public void setRow(int row) {
		this.row=row;
	}
	
	public void setBackground(Color c) {
		this.background=c;
	}
	
	public void setCell(Color c) {
		this.cell=c;
	}
	
	public void setLine(Color c) {
		this.line=c;
	}
	
	@Override
	public Dimension getPreferredSize() {
		return new Dimension(1600, 800);
	}
	
}

class SplashDialog extends JDialog {      // Splash window

	private final int waitTime = 5000;
	
	public SplashDialog(JFrame frame, boolean modal) {
	   super(frame, "Splash!", modal);
	   setUndecorated(true);              
	   add(new JLabel("<html><center><h1>Game of Life Project</h1><hr />"
	      + "<h2>CMP 256 GUI Design and Programming</h2><br />"
	      + "<img src='https://www.aus.edu/sites/default/files/AUS_Bilingualsss.png'"
	      + "alt='?' ><br /><br /> (this splash window "
	      + "will disappear in " + waitTime/1000 + " seconds)</center>"
	      		+ "<center><h3>Project Done By :<br />Joel D'Souza<br />Sabbir Alam<br />"
	      		+ "<br />With special thanks to: <br />"
	      		+ "Dr. Michel Pasquier<br />Ms Hend ElGhazaly</h3></center></html>",
	      SwingConstants.CENTER));
	   addMouseListener(new MouseAdapter() {  // click to dismiss the dialog
	      @Override public void mouseClicked(MouseEvent event) {
	         setVisible(false);
	      }});
	   (new Timer(waitTime, new ActionListener() { // or else the timer will
	      @Override public void actionPerformed(ActionEvent e) {
	         setVisible(false);
	      }})).start();
	   
	   pack();
	   setLocationRelativeTo(null);
	}
	@Override
	public Dimension getPreferredSize() {
		return new Dimension(450, 400);
	}
}

class PreferencePanel extends JDialog {	
	
	private String[] speed = new String[] {"Slow","Normal","Fast"};
	private String[] size = new String[] {"Small", "Medium", "Big"};	
	private JCheckBox edit = new JCheckBox("Edit");
	private JComboBox<String> speedComboBox = new JComboBox<>(speed);
	private JComboBox<String> sizeComboBox = new JComboBox<>(size);
	private JButton save = new JButton("Save");
	
	public PreferencePanel(ProjectFrame frame, boolean modal, boolean visibilty) {
		super(frame, "Preference Panel", modal);
		this.setLayout(new BorderLayout());
		this.setVisible(visibilty);
		this.setResizable(false);
		
		JLabel jl = new JLabel("Set Default Preferences", JLabel.CENTER);
		jl.setFont(new Font("Consolas", Font.BOLD, 24));
		this.add(jl, BorderLayout.NORTH);
		jl.setBorder(BorderFactory.createEtchedBorder());
		
		JPanel panel = new JPanel();
		this.add(panel, BorderLayout.CENTER);
		panel.setLayout(new GridLayout(0,3));
		
		panel.add(new JLabel(""));
		panel.add(new JLabel(""));
		panel.add(new JLabel(""));
		
		panel.add(new JLabel(" Choose default size: ", JLabel.LEFT));
		sizeComboBox.setSelectedIndex(1);
		panel.add(sizeComboBox);
		panel.add(new JLabel(""));
		
		panel.add(new JLabel(" Choose default speed: ", JLabel.LEFT));
		speedComboBox.setSelectedIndex(1);
		panel.add(speedComboBox);
		panel.add(new JLabel(""));
		
		panel.add(new JLabel(" Choose default edit setting: ", JLabel.LEFT));
		panel.add(edit);
		panel.add(new JLabel(""));
		
		panel.add(new JLabel(""));
		panel.add(new JLabel(""));
		panel.add(new JLabel(""));
		
		JPanel panel2 = new JPanel();
		this.add(panel2, BorderLayout.SOUTH);
		panel2.add(save);
		
		
		pack();
		this.setLocationRelativeTo(null);
	}
	
	/*---------------GETTERS THE GOLView Class CAN ACCESS----------------*/
	
	public JComboBox<String> getSpeedComboBox(){
		return speedComboBox;
	}
	public JComboBox<String> getSizeComboBox(){
		return sizeComboBox;
	}
	public JCheckBox getEdit() {
		return edit;
	}
	public JButton getSave() {
		return save;
	}
	
	/*---------------SETTERS THE GOLView Class CAN ACCESS----------------*/
	
	public void setSpeedComboBoxIndex(int i){
		 this.speedComboBox.setSelectedIndex(i);
	}
	public void setSizeComboBoxIndex(int i){
		this.sizeComboBox.setSelectedIndex(i);
	}
	public void setEditIndex(boolean b) {
		this.edit.setSelected(b);
	}
	
}
