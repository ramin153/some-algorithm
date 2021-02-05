/*
you will give some number
you must two number that their sum is 2020
return their mux

inp
1721
979
366
299
675
1456

out
514579//1721 * 299

 */

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class SumIs2020 {

    public static void main(String[] args) throws FileNotFoundException {
        boolean[] numbers = new boolean[2021];
        Arrays.fill(numbers,false);
        File file = new File("input.txt");
        Scanner myReader = new Scanner(file);


        // we read input and that number become index of our bool that show there was a number

        int num;
        while (myReader.hasNextInt()) {
            num = myReader.nextInt();
            if (num <= 2029 && num >= 0)
                numbers[num] = true;
        }



        for (int i = 0;i <=1010;i++)
            if (numbers[i] && numbers[2020-i])
            {
                System.out.println(i*(2020-i));
                break;
            }





    }
    public static void nNumber(boolean[] numbers,int n )
    {
        int col = numbers.length;
        DP[] dp = new DP[col];

        for (int i = 0; i < col;i++)
        {
            dp[i] = new DP();
            if (i == 1237)
                System.out.println(numbers[i]);
            if (numbers[i])
                dp[i].firstAdd(i);
        }

        for (int i = 1; i<n;i++)
        {
            for (int j = 0; j <col;j++)
            {
                if (numbers[j])
                {

                    for (int k =j ; k < col;k++)
                    {
                        if (dp[k-j].isSet)
                        {
                            dp[k].add(j,dp[k-j]);
                        }
                    }

                    }
                }
            }
        if (dp[2020].isSet){
            System.out.println(dp[2020].mux(n));
        }



    }

    public static void twoNumber(boolean[] numbers)
    {
        // if both of i and 2020-i were true ==> show that there was number that have sum that equals to 2020
        for (int i = 0;i <=1010;i++)
            if (numbers[i] && numbers[2020-i])
            {
                System.out.println(i*(2020-i));
                break;
            }
    }

    public static class DP
    {
        boolean isSet = false;
        public ArrayList<ArrayList<Integer>> pre = new ArrayList<>();

        void add(int n,DP item)
        {

            for (ArrayList<Integer> i : item.pre)
            {
                if (i.size() != 3)
                {
                    boolean isSame = false;
                    ArrayList<Integer> nItem = new ArrayList<>();
                    for (Integer j : i)
                    {
                        nItem.add(j);
                        if (j == n)
                            isSame = true;
                    }

                    nItem.add(n);
                    if(!isSame)
                    {
                        pre.add(nItem);
                        isSet = true;
                    }

                }
            }
        }

        void firstAdd(int n)
        {
            pre.add( new ArrayList<>(Arrays.asList(n)));
            isSet = true;
        }

        int mux(int n)
        {
            for (ArrayList<Integer> i : pre)
                if (i.size() == n)
                {
                    System.out.println(i.get(0));
                   int result = i.get(0);
                   for (int j = 1; j < n; j++)
                   {
                       System.out.println(i.get(j));
                       result*= i.get(j);
                   }

                   return result;

                }
            return -1;
        }

    }
}
