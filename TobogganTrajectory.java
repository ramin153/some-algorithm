/*

With the toboggan login problems resolved, you set off toward the airport. While travel by toboggan might be easy, it's certainly not safe: there's very minimal steering and the area is covered in trees. You'll need to see which angles will take you near the fewest trees.

Due to the local geology, trees in this area only grow on exact integer coordinates in a grid. You make a map (your puzzle input) of the open squares (.) and trees (#) you can see. For example:

..##.......
#...#...#..
.#....#..#.
..#.#...#.#
.#...##..#.
..#.##.....
.#.#.#....#
.#........#
#.##...#...
#...##....#
.#..#...#.#
These aren't the only trees, though; due to something you read about once involving arboreal genetics and biome stability, the same pattern repeats to the right many times:

..##.........##.........##.........##.........##.........##.......  --->
#...#...#..#...#...#..#...#...#..#...#...#..#...#...#..#...#...#..
.#....#..#..#....#..#..#....#..#..#....#..#..#....#..#..#....#..#.
..#.#...#.#..#.#...#.#..#.#...#.#..#.#...#.#..#.#...#.#..#.#...#.#
.#...##..#..#...##..#..#...##..#..#...##..#..#...##..#..#...##..#.
..#.##.......#.##.......#.##.......#.##.......#.##.......#.##.....  --->
.#.#.#....#.#.#.#....#.#.#.#....#.#.#.#....#.#.#.#....#.#.#.#....#
.#........#.#........#.#........#.#........#.#........#.#........#
#.##...#...#.##...#...#.##...#...#.##...#...#.##...#...#.##...#...
#...##....##...##....##...##....##...##....##...##....##...##....#
.#..#...#.#.#..#...#.#.#..#...#.#.#..#...#.#.#..#...#.#.#..#...#.#  --->
You start on the open square (.) in the top-left corner and need to reach the bottom (below the bottom-most row on your map).

The toboggan can only follow a few specific slopes (you opted for a cheaper model that prefers rational numbers); start by counting all the trees you would encounter for the slope right 3, down 1:

From your starting position at the top-left, check the position that is right 3 and down 1. Then, check the position that is right 3 and down 1 from there, and so on until you go past the bottom of the map.

The locations you'd check in the above example are marked here with O where there was an open square and X where there was a tree:

..##.........##.........##.........##.........##.........##.......  --->
#..O#...#..#...#...#..#...#...#..#...#...#..#...#...#..#...#...#..
.#....X..#..#....#..#..#....#..#..#....#..#..#....#..#..#....#..#.
..#.#...#O#..#.#...#.#..#.#...#.#..#.#...#.#..#.#...#.#..#.#...#.#
.#...##..#..X...##..#..#...##..#..#...##..#..#...##..#..#...##..#.
..#.##.......#.X#.......#.##.......#.##.......#.##.......#.##.....  --->
.#.#.#....#.#.#.#.O..#.#.#.#....#.#.#.#....#.#.#.#....#.#.#.#....#
.#........#.#........X.#........#.#........#.#........#.#........#
#.##...#...#.##...#...#.X#...#...#.##...#...#.##...#...#.##...#...
#...##....##...##....##...#X....##...##....##...##....##...##....#
.#..#...#.#.#..#...#.#.#..#...X.#.#..#...#.#.#..#...#.#.#..#...#.#  --->
In this example, traversing the map using this slope would cause you to encounter 7 trees.

Starting at the top-left corner of your map and following a slope of right 3 and down 1, how many trees would you encounter?
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class TobogganTrajectory {

    public static void main(String[] args) throws FileNotFoundException {
        File file = new File("input3.txt");
        Scanner myReader = new Scanner(file);
        ArrayList<String> map = new ArrayList<>();
        while (myReader.hasNextLine())
            map.add(myReader.nextLine());
        char[][] myMap = new char[map.size()][map.get(0).length()];
        for (int i = 0 ; i < map.size();i++)
            for (int j = 0; j < map.get(i).length();j++)
                myMap[i][j] = map.get(i).charAt(j);

        System.out.println(treeMove(myMap,1,1)*
                treeMove(myMap,1,3)*
                treeMove(myMap,1,5)*
                treeMove(myMap,1,7)*
                treeMove(myMap,2,0.5));
    }

    public static int tree3leftBottom(char[][] map)
    {
        int tree = 0;


        for (int i = 0;i<map.length ;i++)
        {
            System.out.print(String.format("%3d", i+1)+":"+map[i][(i*3)%map[i].length]+"  ");

            if (map[i][(i*3)%map[i].length] == '#')
            {
                tree++;
                map[i][(i*3)%map[i].length] = 'X';
            }else
            {
                map[i][(i*3)%map[i].length] = 'O';
            }

            for (int j = 0; j<map[i].length;j++)
            {
                if (map[i][j] == 'X')
                    System.out.print(ConsoleColor.RED_BOLD+"X"+ConsoleColor.RESET);
                else if (map[i][j] == 'O')
                    System.out.print(ConsoleColor.GREEN_BOLD+"O"+ConsoleColor.RESET);
                else
                    System.out.print(map[i][j]);

            }
            System.out.println();
        }
        return tree;
    }

    public static long treeMove(char[][] map,int down,double rightRatio)
    {
        long tree = 0;


        for (int i = 0;i<map.length ;i+=down)
        {
            if (map[i][((int)Math.floor(i*rightRatio))%map[i].length] == '#')
            {
                tree++;

            }
        }
        return tree;
    }
}
