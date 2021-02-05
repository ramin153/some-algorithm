import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class SeatingSystem {
    /*
    Your plane lands with plenty of time to spare. The final leg of your journey is a ferry that goes directly to the tropical island where you can finally start your vacation. As you reach the waiting area to board the ferry, you realize you're so early, nobody else has even arrived yet!

By modeling the process people use to choose (or abandon) their seat in the waiting area, you're pretty sure you can predict the best place to sit. You make a quick map of the seat layout (your puzzle input).

The seat layout fits neatly on a grid. Each position is either floor (.), an empty seat (L), or an occupied seat (#). For example, the initial seat layout might look like this:

L.LL.LL.LL
LLLLLLL.LL
L.L.L..L..
LLLL.LL.LL
L.LL.LL.LL
L.LLLLL.LL
..L.L.....
LLLLLLLLLL
L.LLLLLL.L
L.LLLLL.LL
Now, you just need to model the people who will be arriving shortly. Fortunately, people are entirely predictable and always follow a simple set of rules. All decisions are based on the number of occupied seats adjacent to a given seat (one of the eight positions immediately up, down, left, right, or diagonal from the seat). The following rules are applied to every seat simultaneously:

If a seat is empty (L) and there are no occupied seats adjacent to it, the seat becomes occupied.
If a seat is occupied (#) and four or more seats adjacent to it are also occupied, the seat becomes empty.
Otherwise, the seat's state does not change.
Floor (.) never changes; seats don't move, and nobody sits on the floor.

After one round of these rules, every seat in the example layout becomes occupied:

#.##.##.##
#######.##
#.#.#..#..
####.##.##
#.##.##.##
#.#####.##
..#.#.....
##########
#.######.#
#.#####.##
After a second round, the seats with four or more occupied adjacent seats become empty again:

#.LL.L#.##
#LLLLLL.L#
L.L.L..L..
#LLL.LL.L#
#.LL.LL.LL
#.LLLL#.##
..L.L.....
#LLLLLLLL#
#.LLLLLL.L
#.#LLLL.##
This process continues for three more rounds:

#.##.L#.##
#L###LL.L#
L.#.#..#..
#L##.##.L#
#.##.LL.LL
#.###L#.##
..#.#.....
#L######L#
#.LL###L.L
#.#L###.##
#.#L.L#.##
#LLL#LL.L#
L.L.L..#..
#LLL.##.L#
#.LL.LL.LL
#.LL#L#.##
..L.L.....
#L#LLLL#L#
#.LLLLLL.L
#.#L#L#.##
#.#L.L#.##
#LLL#LL.L#
L.#.L..#..
#L##.##.L#
#.#L.LL.LL
#.#L#L#.##
..L.L.....
#L#L##L#L#
#.LLLLLL.L
#.#L#L#.##
At this point, something interesting happens: the chaos stabilizes and further applications of these rules cause no seats to change state! Once people stop moving around, you count 37 occupied seats.

Simulate your seating area by applying the seating rules repeatedly until no seats change state. How many seats end up occupied?
     */
    public static void main(String[] args) throws FileNotFoundException {
        File file = new File("input11.txt");
        Scanner myReader = new Scanner(file);
        List<String> items = readFromFile(myReader);
        System.out.println(part2(items));
    }

    private static int part1(List<String> items) {
        boolean find = false;
        while (!find)
        {

            List<String> col = clone(items);
            for (int i = 0; i <items.size();i++)
                for (int j =0; j<items.get(i).length();j++)
                {
                    col.set(i,col.get(i).substring(0,j)+setItem(items,i,j)+col.get(i).substring(j+1));

                }


            if (equal(items,col))
            {
                find = true;
            }

            items = col;
        }
        return countOcc(items);
    }
    private static int part2(List<String> items) {
        boolean find = false;
        while (!find)
        {

            List<String> col = clone(items);
            for (int i = 0; i <items.size();i++)
                for (int j =0; j<items.get(i).length();j++)
                {
                    col.set(i,col.get(i).substring(0,j)+setItem2(items,i,j)+col.get(i).substring(j+1));

                }


            if (equal(items,col))
            {
                find = true;
            }

            items = col;
        }
        return countOcc(items);
    }


    private static List<String> readFromFile(Scanner myReader)
    {
        List<String> items = new ArrayList<>();
        while (myReader.hasNextLine())
            items.add(myReader.nextLine());
        return items;
    }
    private static List<String> clone ( List<String> items)
    {
        List<String> clone = new ArrayList<>();
        for (String i : items)
            clone.add(new String(i));
        return clone;
    }
    private static char setItem( List<String> items, int listLoc , int stringLoc)
    {
        if (items.get(listLoc).charAt(stringLoc) =='.')
            return '.';
        int countOcc = 0;

        for (int i = listLoc -1; i < listLoc+2;i++)
        {
            if (i < items.size() && i>=0)
            {
                for (int j = stringLoc -1; j < stringLoc+2;j++)
                {
                    if (j < items.get(i).length() && j>=0 && items.get(i).charAt(j) == '#'  )
                    {
                        if (i != listLoc || j != stringLoc)
                            countOcc++;
                    }
                }
            }
        }

        return ((countOcc == 0 && items.get(listLoc).charAt(stringLoc) == 'L')?'#':(countOcc >=4)?'L':items.get(listLoc).charAt(stringLoc));
    }
    private static char setItem2( List<String> items, int listLoc , int stringLoc)
    {
        if (items.get(listLoc).charAt(stringLoc) =='.')
            return '.';
        int countOcc = 0;

        for (int i = -1; i < 2;i++)
        {
            for (int j =  -1; j < 2;j++)
            {
                if (i == 0 && j ==0)
                    continue;
                boolean isDone = false;
                for (int k = 1; !isDone;k++)
                {
                    if (i*k + listLoc< 0 || i*k + listLoc >= items.size() || j*k + stringLoc<0 || j*k + stringLoc >= items.get(i*k + listLoc).length())
                        isDone = true;
                    else if(items.get(i*k+listLoc).charAt(j*k + stringLoc) != '.')
                    {
                        isDone = true;
                        if (items.get(i*k+listLoc).charAt(j*k + stringLoc) == '#')
                            countOcc++;
                    }
                }
            }

        }

        return ((countOcc == 0 && items.get(listLoc).charAt(stringLoc) == 'L')?'#':(countOcc >=5)?'L':items.get(listLoc).charAt(stringLoc));
    }
    private static int countOcc(List<String> items)
    {
        int count = 0;
        for (int i = 0; i <items.size();i++)
        {

            for (int j =0; j <items.get(i).length();j++)
            {
                if (items.get(i).charAt(j) == '#')
                    count++;
            }

        }
        return count;
    }


    private static boolean equal(List<String> items,List<String> col)
    {

        for (int i = 0; i <items.size();i++)
        {

            for (int j =0; j <items.get(i).length();j++)
            {
                if (items.get(i).charAt(j) != col.get(i).charAt(j))
                    return false;
            }

        }
        return true;
    }

    private static void mySOUT(List<String> itesm)
    {
        for (String i : itesm)
            System.out.println(i);
    }
    
    
}
