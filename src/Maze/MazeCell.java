package Maze;

import java.io.Serializable;

import javax.swing.JButton;

public class MazeCell extends JButton implements Serializable {
	public int state = 0;
	//0:empty
	//1:Wall
	//2:Player
	//3:Target
	//4:Path
	public int i, j;
}
