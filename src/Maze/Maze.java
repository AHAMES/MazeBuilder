package Maze;

public class Maze {
	int Maze[][];

	int CurrentX;
	int CurrentY;
	int StartX;
	int StartY;
	int finishPointX;
	int finishPointY;
	int size;

	public Maze(MazeCell m[][], int Size, int Sx, int Sy, int Ex, int Ey) {
		
		size = Size;
		Maze =new int[size][size];
		for(int i=0;i<Size;i++)
		{
			for(int j=0;j<Size;j++)
			Maze[i][j]=m[i][j].state;
		}
		StartX = Sx;
		StartY = Sy;
		CurrentX = Sx;
		CurrentY = Sy;
		finishPointX = Ex;
		finishPointY = Ey;
	}

	void changePosition(int futureX, int futureY) {

		CurrentX = futureX;
		CurrentY = futureY;

	}

	void moveUP() {
		int futureY = CurrentY + 1;
		changePosition(CurrentX, futureY);
	}

	void moveDown() {
		int futureY = CurrentY - 1;
		changePosition(CurrentX, futureY);
	}

	void moveLeft() {
		int futureX = CurrentX - 1;
		changePosition(futureX, CurrentY);
	}

	void moveRight() {
		int futureX = CurrentX + 1;
		changePosition(futureX, CurrentY);
	}

	void resetPosition() {
		CurrentX = StartX;
		CurrentY = StartY;
	}
}
