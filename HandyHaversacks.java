/*
--- Day 7: Handy Haversacks ---
You land at the regional airport in time for your next flight. In fact, it looks like you'll even have time to grab some food: all flights are currently delayed due to issues in luggage processing.

Due to recent aviation regulations, many rules (your puzzle input) are being enforced about bags and their contents; bags must be color-coded and must contain specific quantities of other color-coded bags. Apparently, nobody responsible for these regulations considered how long they would take to enforce!

For example, consider the following rules:

light red bags contain 1 bright white bag, 2 muted yellow bags.
dark orange bags contain 3 bright white bags, 4 muted yellow bags.
bright white bags contain 1 shiny gold bag.
muted yellow bags contain 2 shiny gold bags, 9 faded blue bags.
shiny gold bags contain 1 dark olive bag, 2 vibrant plum bags.
dark olive bags contain 3 faded blue bags, 4 dotted black bags.
vibrant plum bags contain 5 faded blue bags, 6 dotted black bags.
faded blue bags contain no other bags.
dotted black bags contain no other bags.
These rules specify the required contents for 9 bag types. In this example, every faded blue bag is empty, every vibrant plum bag contains 11 bags (5 faded blue and 6 dotted black), and so on.

You have a shiny gold bag. If you wanted to carry it in at least one other bag, how many different bag colors would be valid for the outermost bag? (In other words: how many colors can, eventually, contain at least one shiny gold bag?)

In the above rules, the following options would be available to you:

A bright white bag, which can hold your shiny gold bag directly.
A muted yellow bag, which can hold your shiny gold bag directly, plus some other bags.
A dark orange bag, which can hold bright white and muted yellow bags, either of which could then hold your shiny gold bag.
A light red bag, which can hold bright white and muted yellow bags, either of which could then hold your shiny gold bag.
So, in this example, the number of bag colors that can eventually contain at least one shiny gold bag is 4.

How many bag colors can eventually contain at least one shiny gold bag? (The list of rules is quite long; make sure you get all of it.)

 */

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class HandyHaversacks {
    public static  class  Cell
    {
        String name = "";
        List<String> contain;
        List<Integer> numbers;
        boolean isCheck;

        public Cell(String name, List<String> contain,List<Integer> number) {
            this.name = name;
            this.contain = contain;
            this.numbers = number;
            this.isCheck = false;
        }

        int getNumberIndex(int i)
        {
            return  numbers.get(i);
        }

        @Override
        public String toString() {
            return "Cell{" +
                    "name='" + name + '\'' +
                    ", contain=" + contain +
                    ", number=" + numbers +
                    ", isCheck=" + isCheck +
                    '}';
        }

        boolean isContain(String name)
        {
            for (String i : contain)
                if (i.equals(name))
                    return true;
            return false;
        }
        void itChecked()
        {
            isCheck = true;
        }

        public String getName() {
            return name;
        }

        public boolean isCheck() {
            return isCheck;
        }

    }

    public static void main(String[] args) throws FileNotFoundException {
        File file = new File("input7.txt");
        Scanner myReader = new Scanner(file);
        HashMap<String,Cell> items = readFileHash(myReader);
        System.out.println(part2(items,"shiny gold"));
        myReader.close();
    }

    private static int part2(HashMap<String, Cell> items, String name) {
        if (items.get(name).contain.size() == 0)
            return 0;
        int result = 0;
        Cell item = items.get(name);

        for (int i =0;i < item.contain.size();i++)
        {
            result += item.getNumberIndex(i)*(1+part2(items, item.contain.get(i)));
        }
        return result;
    }

    private static int part1(List<Cell> items,String name) {
        List<String> haveIt = new ArrayList<>();
        for (Cell i : items)
        {
            if (i.isContain(name) && !i.isCheck())
            {
                haveIt.add(i.name);
                i.itChecked();
            }

        }
        for (int j = 0; j < haveIt.size();j++)
        {
            for (Cell i : items)
            {
                if (i.isContain(haveIt.get(j)) && !i.isCheck())
                {
                    haveIt.add(i.name);
                    i.itChecked();
                }

            }
        }

        return haveIt.size();
    }

    private static List<Cell> readFileList(Scanner myReader) {
        List<Cell> items = new ArrayList<>();
        while (myReader.hasNextLine())
        {
            String line = myReader.nextLine();
            String[] words = line.split("\\s+");
            String name = words[0] + " "+words[1];
            List<String> contain = new ArrayList<>();
            List<Integer> numbers = new ArrayList<>();
            for (int i = 4 ;!words[4].equals("no") && i < words.length;i+=4)
            {
                contain.add(words[i+1]+" "+words[i+2]) ;
                numbers.add(Integer.parseInt(words[i])) ;
            }
            items.add(new Cell(name,contain,numbers));


        }
        return items;
    }
    private static HashMap<String,Cell> readFileHash(Scanner myReader) {
        HashMap<String,Cell> items = new HashMap<>();
        while (myReader.hasNextLine())
        {
            String line = myReader.nextLine();
            String[] words = line.split("\\s+");
            String name = words[0] + " "+words[1];
            List<String> contain = new ArrayList<>();
            List<Integer> numbers = new ArrayList<>();
            for (int i = 4 ;!words[4].equals("no") && i < words.length;i+=4)
            {
                contain.add(words[i+1]+" "+words[i+2]) ;
                numbers.add(Integer.parseInt(words[i])) ;
            }
            items.put(name,new Cell(name,contain,numbers));


        }
        return items;
    }

}
