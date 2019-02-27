package Maze;

import java.util.ArrayList;

public class DFS {
	int size;

	public DFS(int s) {
		size = s;
	}

	public boolean solve(int maze[][], int x, int y, ArrayList<Integer> path) {
		if ((x < 0 || x >= size) || (y < 0 || y >= size)) {
			return false;
		}
		if (maze[x][y] == 3) {
			path.add(x);
			path.add(y);
			return true;
		}

		if (maze[x][y] == 0 || maze[x][y] == 2) {
			maze[x][y] = 4;

			int dx = -1;
			int dy = 0;
			if (solve(maze, x + dx, y + dy, path)) {
				path.add(x);
				path.add(y);
				return true;
			}

			dx = 1;
			dy = 0;
			if (solve(maze, x + dx, y + dy, path)) {
				path.add(x);
				path.add(y);
				return true;
			}

			dx = 0;
			dy = -1;
			if (solve(maze, x + dx, y + dy, path)) {
				path.add(x);
				path.add(y);
				return true;
			}

			dx = 0;
			dy = 1;
			if (solve(maze, x + dx, y + dy, path)) {
				path.add(x);
				path.add(y);
				return true;
			}
		}
		return false;
	}
}
