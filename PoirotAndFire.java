package com.company;

import java.io.*;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class PoirotAndFire {
    static final String fileNameRead = "a.in";
    static final String fileNameOut = "a.out";

    public static void main(String[] args) throws IOException {

        Scanner myReader = new Scanner(System.in);
        createFile();

        while (true)
        {
            Queue<Cell> bfs = new LinkedList<>();
            Cell[][] map = Cell.initial(myReader,bfs);
            if (map == null)
                break;

            while (bfs.size() != 0)
            {

                Cell select = bfs.poll();

                select.burn(map,bfs);
            }

            int[] pos = Cell.latest(map);
            System.out.println(pos[0]+" "+ pos[1]);


        }

        myReader.close();


    }

    protected static void createFile()
    {
        try {
            File myObj = new File(fileNameOut);
            if (myObj.createNewFile()) {

            }else
            {
                emptyFile();
            }
        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    static void emptyFile() throws FileNotFoundException {
        File file = new File(fileNameOut);
        PrintWriter writer = new PrintWriter(file);
        writer.print("");
        writer.close();
    }

    public static class Cell
    {
        int timeReachFire;
        boolean isBurned;
        int row;
        int col;

        public Cell(int i,int j) {
            row = i;
            col = j;
            timeReachFire = 0;
            isBurned = false;
        }

        public void Lightning()
        {
            timeReachFire =0;
            isBurned = true;

        }

        boolean doBurning(int time)
        {
            if (isBurned)
                return false;
            isBurned = true;
            timeReachFire = time;
            return true;
        }

        void burn(Cell[][] map, Queue<Cell> bfs)
        {

            //we check 8 place around item
            for (int i = row-1; i<=row+1;i++)
            {
                if (i >= 0 && i < map.length)
                {
                    for (int j = col -1; j <=col+1;j++)
                    {
                        if (j >= 0 && j < map[0].length)
                        {
                            if(map[i][j].doBurning(timeReachFire+1))
                            {
                                bfs.add(map[i][j]);
                            }
                        }
                    }
                }
            }
        }

        public static Cell[][] createMap(int row,int col)
        {
            Cell[][] map = new Cell[row][col];
            for (int i = 0;i < row;i++)
                for (int j =0;j<col;j++)
                    map[i][j] = new Cell(i,j);
            return map;
        }

        public static Cell[][] initial(Scanner scanner,Queue<Cell> bfs)
        {
            int row = scanner.nextInt();
            int col = scanner.nextInt();
            scanner.nextInt();
            scanner.nextLine();
            if (row == 0 || col == 0)
                return null;
            Cell[][] map = new Cell[row][col];
            for (int i = 0 ; i < row;i++)
            {
                String line = scanner.nextLine();
                for (int j = 0 ; j < col;j++)
                {
                    map[i][j] = new Cell(i,j);
                    if (line.charAt(j) == 'f')
                    {
                        map[i][j].Lightning();
                        bfs.add(map[i][j]);
                    }
                }
            }
            return map;
        }

        public static int[] latest(Cell[][] map)
        {
            int[] pos = {0,0};
            int time = map[0][0].timeReachFire;
            for (int i = 0;i < map.length;i++)
                for (int j =0;j< map[i].length;j++)
                {
                    if (map[i][j].timeReachFire > time ||(map[i][j].timeReachFire == time &&  (i<  (pos[0]) ||(i== (pos[0]) && j<  (pos[1])))))
                    {
                        pos[0] = i;
                        pos[1] = j;
                        time = map[i][j].timeReachFire;
                    }
                }
            

            return pos;
        }
    }
}
