package Maze;


public class Maze {
	MazeCell Maze[][];

	int CurrentX;
	int CurrentY;
	int StartX;
	int StartY;
	int finishPointX;
	int finishPointY;
	int size;
	int penalties = 0;

	public Maze(MazeCell m[][], int Size, int Sx, int Sy, int Ex, int Ey) {
		Maze = m;
		size = Size;
		StartX = Sx;
		StartY = Sy;
		CurrentX = Sx;
		CurrentY = Sy;
		finishPointX = Ex;
		finishPointY = Ey;
	}

	void addPenalty(int futureX, int futureY) {
		if (futureX < 0 || futureX >= size || futureY < 0 || futureY >= size) {
			penalties++;
			return;
		} else if (Maze[futureX][futureY].state == 1) {
			penalties++;
		} else {
			CurrentX = futureX;
			CurrentY = futureY;
		}

	}

	void moveUP() {
		int futureY = CurrentY + 1;
		addPenalty(CurrentX, futureY);
	}

	void moveDown() {
		int futureY = CurrentY - 1;
		addPenalty(CurrentX, futureY);
	}

	void moveLeft() {
		int futureX = CurrentX - 1;
		addPenalty(futureX, CurrentY);
	}

	void moveRight() {
		int futureX = CurrentX + 1;
		addPenalty(futureX, CurrentY);
	}

	void resetPosition() {
		CurrentX = StartX;
		CurrentY = StartY;
	}
}
