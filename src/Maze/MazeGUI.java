package Maze;

import java.awt.*;
import java.awt.event.*;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import javax.swing.*;

public class MazeGUI extends JFrame {

	public int size = 10;
	JTextField sizeField;
	MazeCell cell[][] = new MazeCell[size][size];
	JPanel MazePanel;
	JPanel ControlPanel;
	
	Container ContentPanel;
	JButton ResizeButton;
	JButton ResetButton;
	JButton ResetPathButton;
	JButton SaveMazeButton;
	JButton GetSavedMazeButton;
	JButton Solve;
	ButtonGroup RadioGroup = new ButtonGroup();
	JRadioButton Player = new JRadioButton("Player");
	JRadioButton Target = new JRadioButton("Target");
	JRadioButton Wall = new JRadioButton("Wall");
	int starti;
	int startj;

	int endi;
	int endj;


	public MazeGUI() {
		super("Genetic Maze");

		setDefaultCloseOperation(EXIT_ON_CLOSE);

		MazePanel = new JPanel();
		ControlPanel = new JPanel();
	
		ContentPanel = getContentPane();
		ResizeButton = new JButton("Resize");
		ResetButton = new JButton("Reset");
		ResetPathButton = new JButton("Reset Path");
		SaveMazeButton = new JButton("Save Maze");
		GetSavedMazeButton = new JButton("Get Maze");
		Solve = new JButton("Solve");
		sizeField = new JTextField(10);
		
		this.setSize(800, 800);
		MazePanel.setSize(400, 400);
		
		Player.setMnemonic(KeyEvent.VK_B);
		Player.setActionCommand("Player");

		Target.setMnemonic(KeyEvent.VK_B);
		Target.setActionCommand("Target");

		Wall.setMnemonic(KeyEvent.VK_B);
		Wall.setActionCommand("Wall");
		Wall.setSelected(true);

		RadioGroup.add(Player);
		RadioGroup.add(Target);
		RadioGroup.add(Wall);

		ContentPanel.setLayout(new GridLayout(2, 2));
		ContentPanel.add(MazePanel, BorderLayout.WEST);
		ContentPanel.add(ControlPanel, BorderLayout.EAST);
		ContentPanel.add(new JPanel());
		ContentPanel.setVisible(true);

		MazePanel.setBorder(BorderFactory.createLineBorder(Color.black));
		ControlPanel.setBorder(BorderFactory.createLineBorder(Color.RED));

		MazePanel.setLayout(new GridLayout(size, size));
		MazePanel.setVisible(true);
		ControlPanel.add(ResizeButton);
		ControlPanel.add(sizeField);
		ControlPanel.add(Player);
		ControlPanel.add(Target);
		ControlPanel.add(Wall);
		ControlPanel.add(Solve);
		ControlPanel.add(ResetButton);
		ControlPanel.add(ResetPathButton);
		ControlPanel.add(SaveMazeButton);
		ControlPanel.add(GetSavedMazeButton);
		ControlPanel.setVisible(true);
		fillMaze(size);
		ResizeHandler resizeHandler = new ResizeHandler();

		ResizeButton.addActionListener(resizeHandler);
		ResetButton.addActionListener(new ResetHandler());
		ResetPathButton.addActionListener(new ResetPathHandler());

		SaveMazeButton.addActionListener(new SavingHandler());

		GetSavedMazeButton.addActionListener(new RetrieveHandler());
		Solve.addActionListener(new SolveHandler());
	}

	void fillMaze(int size) {
		cell = new MazeCell[size][size];
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				cell[i][j] = new MazeCell();
				cell[i][j].i = i;
				cell[i][j].j = j;
				cell[i][j].setBackground(Color.WHITE);
				cell[i][j].state = 0;
				cell[i][j].addActionListener(new ColoringHandler(i, j));
				MazePanel.add(cell[i][j]);
			}
		}
	}
	
	void fillMaze(MazeCell[][] newCell) {
		cell=new MazeCell[size][size];
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				cell[i][j] = newCell[i][j];
				cell[i][j].i = i;
				cell[i][j].j = j;
				cell[i][j].setBackground(newCell[i][j].getBackground());
				cell[i][j].state = newCell[i][j].state;
				cell[i][j].addActionListener(new ColoringHandler(i, j));
				MazePanel.add(cell[i][j]);
			}
		}
	}
	void resetPath()
	{
		MazePanel.removeAll();
		MazePanel.setLayout(new GridLayout(size, size));
		for (int i=0;i<size;i++)
		{
			for(int j=0;j<size;j++)
			{
				if(cell[i][j].state==4)
				{
					cell[i][j].setBackground(Color.WHITE);
					cell[i][j].state=0;
					endi=starti;
					endj=startj;
					
				}
				MazePanel.add(cell[i][j]);
			}
		}
	}
	
	private class ResetPathHandler implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent event) {
			try {
				resetPath();
				
				SwingUtilities.updateComponentTreeUI(MazePanel);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				System.out.println(e.toString());
				e.printStackTrace();
			}
		}
	}
	
	private class SavingHandler implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent event) {
			try {
				resetPath();
				String fileName=JOptionPane.showInputDialog("Write Name of the Maze"); 
				FileOutputStream fos=new FileOutputStream(fileName+".txt");
		        ObjectOutputStream oos=new ObjectOutputStream(fos);
		        
		        oos.writeObject(cell);
		        oos.close();
		        fos.close();
				SwingUtilities.updateComponentTreeUI(MazePanel);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				System.out.println(e.toString());
				e.printStackTrace();
			}
		}
	}
	
	private class RetrieveHandler implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent event) {
			try {
				JFileChooser fc=new JFileChooser();
				MazePanel.removeAll();
				
				fc.setDialogTitle("Choose Maze");
				fc.setCurrentDirectory(new java.io.File("F:\\Year2\\Java\\MazeSolver"));
				if(fc.showOpenDialog(GetSavedMazeButton)==JFileChooser.APPROVE_OPTION)
				{
					FileInputStream fis = new FileInputStream(fc.getSelectedFile().getAbsoluteFile());
			        ObjectInputStream ois = new ObjectInputStream(fis);
			        cell=(MazeCell[][]) ois.readObject();
			        size=cell[0].length;
			        fillMaze(cell);			        
			        MazePanel.setLayout(new GridLayout(size, size));
			        ois.close();
			        fis.close();
				}
				
				SwingUtilities.updateComponentTreeUI(MazePanel);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				System.out.println(e.toString());
				e.printStackTrace();
			}
		}
	}

	

	

	private class ColoringHandler implements ActionListener {
		int i, j;

		public ColoringHandler(int x, int y) {
			i = x;
			j = y;
		}

		@Override
		public void actionPerformed(ActionEvent event) {
			try {
				if (cell[i][j].getBackground() != Color.WHITE) {
					cell[i][j].setBackground(Color.WHITE);
					cell[i][j].state = 0;
				} else {
					if (Player.isSelected()) {
						cell[starti][startj].state = 0;
						cell[starti][startj].setBackground(Color.WHITE);

						cell[i][j].setBackground(Color.BLUE);
						cell[i][j].state = 2;
						starti = i;
						startj = j;
					}
					if (Wall.isSelected()) {
						cell[i][j].setBackground(Color.BLACK);
						cell[i][j].state = 1;
					}
					if (Target.isSelected()) {
						cell[endi][endj].state = 0;
						cell[endi][endj].setBackground(Color.WHITE);

						cell[i][j].setBackground(Color.RED);
						cell[i][j].state = 3;
						endi = i;
						endj = j;
					}
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				System.out.println(e.toString());
				e.printStackTrace();
			}
		}
	}

	private class ResizeHandler implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent event) {
			try {
				size = Integer.parseInt(sizeField.getText());
				MazePanel.removeAll();
				MazePanel.setLayout(new GridLayout(size, size));
				fillMaze(size);
				SwingUtilities.updateComponentTreeUI(MazePanel);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				System.out.println(e.toString());
				e.printStackTrace();
			}
		}
	}

	private class ResetHandler implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent event) {
			try {

				MazePanel.removeAll();
				MazePanel.setLayout(new GridLayout(size, size));
				fillMaze(size);
				endi=starti;
				endj=startj;
				SwingUtilities.updateComponentTreeUI(MazePanel);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				System.out.println(e.toString());
				e.printStackTrace();
			}
		}
	}
	
	
	private class SolveHandler implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent event) {
			try {
				int Mazesize = size;
			
				Maze m = new Maze(cell, Mazesize, starti, startj, endi, endj);
				DFS dfs=new DFS(size);
				ArrayList<Integer> path=new ArrayList<Integer>();
				dfs.solve(m.Maze, m.StartX, m.StartY, path);
				for(int i=0;i<path.size();i+=2)
				{
					int pathX=path.get(i);
					int pathY=path.get(i+1);
					cell[pathX][pathY].state=4;
					cell[pathX][pathY].setBackground(Color.GREEN);
				}
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				System.out.println(e.toString());
				e.printStackTrace();
			}
		}
	}

	public static void main(String[] args) {
		MazeGUI x = new MazeGUI();
		x.setVisible(true);
	}

}
