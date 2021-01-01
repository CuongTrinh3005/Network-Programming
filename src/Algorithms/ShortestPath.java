/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Algorithms;

import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author Quoc Cuong
 */
/**
 * Represents a position in the matrix.
 */
class Position {
    final private int x;
    final private int y;

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    @Override
    public String toString() {
        return "(" + x + ", " + y + ')';
    }

//    @Override
//    public boolean equals(Object o) {
//        if (this == o) {
//            return true;
//        }
//        if (o == null || getClass() != o.getClass()) {
//            return false;
//        }
//        Position position = (Position) o;
//        if (x != position.x) {
//            return false;
//        }
//        
//        return y == position.y;
//    }

    public boolean equals(Position p) {
        if(p==null)
            return false;
        if(this.getX() == p.getX() && this.getY() == p.getY())
            return true;
        return false;
    }
}

class Board {
    final int[][] board;

    public Board(int[][] board) {
        this.board = board;
    }

    final int positionValue(Position position) {
        return this.board[position.getY()][position.getX()];
    }

    final int getWidth() {
        return board[0].length; //  number of columns
    }

    final int getHeight() {
        return board.length; // number of rows
    }
}

public class ShortestPath {
    final private Board board;
    final private Position start;
    final private Position end;

    public ShortestPath(Board board, int startX, int startY, int endX, int endY) {
        this.board = board;
        this.start = new Position(startX, startY);
        this.end = new Position(endX, endY);
    }

    /**
     * Gets the shortest path from the start to end positions. This method takes
     * all of the paths, then determines which one is shortest and returns that.
     *
     * @return the shortest path from the start to end positions.
     */
    public int sumWeightOfPath(List<Position> path){
        int sum = 0;
        for (Position position : path) {
            sum += this.board.positionValue(position);
        }
        return sum;
    }
    
    public List<Position> shortestPath() {
        List<List<Position>> allPaths = this.getAllPaths();

        System.out.println("Paths found: " + allPaths.size());
        List<Position> shortestPath = null;

        for (List<Position> path : allPaths) {
            if (shortestPath == null) {
                shortestPath = path;
            } else if (sumWeightOfPath(path) < sumWeightOfPath(shortestPath)) {
                shortestPath = path;
            }
        }

        return shortestPath;
    }

    /**
     * Convenience method for starting the getAllPaths process.
     *
     * @return all of the paths from the start to end positions
     */
    private List<List<Position>> getAllPaths() {
        List<List<Position>> paths = new ArrayList<List<Position>>();
        return this.getAllPaths(paths, new ArrayList<Position>(), start);
    }

    /**
     * Gets all of the paths from the start to end position. This is done
     * recursively by visiting every position, while following the rules that
     * you can only move to a position with a value greater than the position
     * you're currently on. When reaching the end position, the path is added to
     * the list of all found paths, which is returned.
     *
     * @param paths the current list of all found paths.
     * @param path the current path
     * @param position the current position
     * @return all paths from the start to end positions
     */
    private List<List<Position>> getAllPaths(List<List<Position>> paths, List<Position> path, Position position) {
        path.add(position);
        if (position.equals(end)) {
            paths.add(path);
            return paths;
        }

        //x+ means right direction
        if (position.getX() + 1 < board.getWidth()) {
            Position xp = new Position(position.getX() + 1, position.getY());
            if (board.positionValue(position) < board.positionValue(xp)) {
                getAllPaths(paths, new ArrayList<Position>(path), xp);
            }
        }
        //x-
//        if (position.getX() - 1 >= 0) {
//            Position xm = new Position(position.getX() - 1, position.getY());
//            if (board.positionValue(position) < board.positionValue(xm)) {
//                getAllPaths(paths, new ArrayList<Position>(path), xm);
//            }
//        }
        //y+ means down direction
        if (position.getY() + 1 < board.getHeight()) {
            Position yp = new Position(position.getX(), position.getY() + 1);
            if (board.positionValue(position) < board.positionValue(yp)) {
                getAllPaths(paths, new ArrayList<Position>(path), yp);
            }
        }
        //y-
//        if (position.getY() - 1 >= 0) {
//            Position ym = new Position(position.getX(), position.getY() - 1);
//            if (board.positionValue(position) < board.positionValue(ym)) {
//                getAllPaths(paths, new ArrayList<Position>(path), ym);
//            }
//        }
        // x++ and y++ means skew direction
        if (position.getX() + 1 < board.getWidth() && position.getY() + 1 < board.getHeight()) {
            Position xm = new Position(position.getX() + 1, position.getY() + 1);
            if (board.positionValue(position) < board.positionValue(xm)) {
                getAllPaths(paths, new ArrayList<Position>(path), xm);
            }
        }
        return paths;
    }

    public static void main(String[] args) {
        int[][] array = {{4, 5, 6},
        {2, 6, 8}, {2,3,9}};

        final Board board = new Board(array);
        final Position end = new Position(board.getWidth() - 1, board.getHeight() - 1);
        final ShortestPath pathFinder = new ShortestPath(board, 0, 0, 2, 1);

        final List<Position> path = pathFinder.shortestPath();
        if(path == null){
            System.out.println("Can not find any path!");
            return;
        }
        int minCost = 0;
        System.out.println("Shortest Path: ");
        for (Position position : path) {
            int value = board.positionValue(position);
            minCost += board.positionValue(position);
            if (!position.equals(end)) {
                System.out.print(value + " -> ");
            } else {
                System.out.print(value);
            }
        }
        System.out.println();
        System.out.println("Min Cost travel: " + minCost);
    }
}