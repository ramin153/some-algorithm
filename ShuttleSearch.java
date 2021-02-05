/*
Your ferry can make it safely to a nearby port, but it won't get much further. When you call to book another ship, you discover that no ships embark from that port to your vacation island. You'll need to get from the port to the nearest airport.

Fortunately, a shuttle bus service is available to bring you from the sea port to the airport! Each bus has an ID number that also indicates how often the bus leaves for the airport.

Bus schedules are defined based on a timestamp that measures the number of minutes since some fixed reference point in the past. At timestamp 0, every bus simultaneously departed from the sea port. After that, each bus travels to the airport, then various other locations, and finally returns to the sea port to repeat its journey forever.

The time this loop takes a particular bus is also its ID number: the bus with ID 5 departs from the sea port at timestamps 0, 5, 10, 15, and so on. The bus with ID 11 departs at 0, 11, 22, 33, and so on. If you are there when the bus departs, you can ride that bus to the airport!

Your notes (your puzzle input) consist of two lines. The first line is your estimate of the earliest timestamp you could depart on a bus. The second line lists the bus IDs that are in service according to the shuttle company; entries that show x must be out of service, so you decide to ignore them.

To save time once you arrive, your goal is to figure out the earliest bus you can take to the airport. (There will be exactly one such bus.)

For example, suppose you have the following notes:

939
7,13,x,x,59,x,31,19
Here, the earliest timestamp you could depart is 939, and the bus IDs in service are 7, 13, 59, 31, and 19. Near timestamp 939, these bus IDs depart at the times marked D:
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.Scanner;

public class ShuttleSearch {
    public static void main(String[] args) throws FileNotFoundException {
        File file = new File("input13.txt");
        Scanner myReader = new Scanner(file);
        int stamp = myReader.nextInt();
        myReader.nextLine();
        String[] idBus = myReader.nextLine().split(",");

        System.out.println(part2another(stamp,idBus));
    }

    private static int part1(int stamp, String[] idBus) {
        int selectID = 0;
        int diff = Integer.MAX_VALUE;
        for (int i =0; i < idBus.length; i++)
        {
            String item = idBus[i];
            if (!item.equals("x") && (Integer.parseInt(item)-(stamp % Integer.parseInt(item))) < diff)
            {
                diff =  (Integer.parseInt(item)-(stamp % Integer.parseInt(item)));
                selectID = i;
            }
        }
        return Integer.parseInt(idBus[selectID])*diff;
    }
    private static long part2(int stamp, String[] idBus) {
        long result = 0;
        long lcm = 1;
        int[] items  = new int[idBus.length];
        for (int i = 0; i < idBus.length;i++)
        {
            String item = idBus[i];
            if (!item.equals("x")) {
                items[i] = Integer.parseInt(item);
            }else
            {
                items[i] = -1;
            }

        }
        for (int i = 0; i< items.length;i++)
        {

            if (items[i] != -1 )
            {
                long item = items[i];
                if (i!=0)
                    while ((result % item) != (item-i) )
                    {
                        result += lcm;
                    }

                System.out.println(lcm);
                lcm = lcm(lcm,item);
                System.out.println(lcm);
                System.out.println("======");
            }
        }
        return result%lcm;
    }


    static long gcd(long a, long b)
    {

        if (b == 0)
            return a;
        if (a>b)
            return gcd(b, a % b);
        return  gcd(a, b % a);
    }

    static long lcm(long a, long b)
    {
        return (a*b)/gcd(a,b);
    }


    private static String part2another(int stamp, String[] idBus) {
        BigInteger result = new BigInteger("0");
        long M = 1;
        int[] items  = new int[idBus.length];
        for (int i = 0; i < idBus.length;i++)
        {
            String item = idBus[i];
            if (!item.equals("x")) {
                items[i] = Integer.parseInt(item);
                M*=items[i];

            }else
            {
                items[i] = -1;
            }

        }
        for (int i = 0; i< items.length;i++)
        {

            if (items[i] != -1) {
                long m = M/items[i];
                long y = m % items[i];
                int j = 1;
                while ((y*j)%items[i] != 1)
                    j++;
                y = j % items[i];
                int a = i;
                if (i != 0)
                    a = ((items[i]-i));
                BigInteger add = new BigInteger(String.valueOf(a));
                add.multiply(BigInteger.valueOf(y));
                add.multiply(BigInteger.valueOf(m));
                result = new BigInteger(String.valueOf(new BigInteger(String.valueOf(a)).multiply(BigInteger.valueOf(y)).multiply(BigInteger.valueOf(m)))).add(result);

                System.out.println(result);
            }
        }
        return  result.mod(BigInteger.valueOf(M)).toString();
    }


}
