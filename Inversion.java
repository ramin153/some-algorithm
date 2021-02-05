import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Inversion {


    public static int inversion(List<Integer> items)
    {
        if(items.size() == 1)
            return 0;
        int result = 0;

        List<Integer> firstHalf =items.subList(0,items.size()/2);
        List<Integer> secondHalf = items.subList(items.size()/2,items.size());

        //2T(n/2)

        result += inversion(items.subList(0,items.size()/2));
        result += inversion(items.subList(items.size()/2,items.size()));



        firstHalf = mergeSort(firstHalf);
        secondHalf = mergeSort(secondHalf);
        //O(nlogn)

        for(int i =0 ,j =0 ; i < firstHalf.size() ;)
        {
             if(j == secondHalf.size())
            {

                while (i < firstHalf.size()-1)
                {
                    result+=j;
                    i++;
                }
                i++;

            }else if (firstHalf.get(i) > secondHalf.get(j))
            {
                result++;
                j++;
            }else
            {

                i++;
                if(i != firstHalf.size())
                    result+=j;
            }
        }

        return result;
    }




    public static void main(String[] args) {
        List<Integer> test = Arrays.asList(2,4,1,3,5);//3
        System.out.println(inversion(test));

        test = Arrays.asList(1,2,3,4,5,6);//0
        System.out.println(inversion(test));

        test = Arrays.asList(6,5,4,3,2,1);//15
        System.out.println(inversion(test));

        test = Arrays.asList(6,1,3,4,5,2);//8
        System.out.println(inversion(test));
    }



    public static List<Integer> mergeSort(List<Integer> items)
    {
        if(items.size() == 1)
            return items;
        List<Integer> firstHalf = mergeSort(items.subList(0,items.size()/2));
        List<Integer> secondHalf = mergeSort(items.subList(items.size()/2,items.size()));

        List<Integer> result = new ArrayList<>();

        for(int i =0 ,j =0 ; i < firstHalf.size() || j < secondHalf.size();)
        {
            if(i == firstHalf.size())
            {
                while (j < secondHalf.size())
                {
                    result.add(secondHalf.get(j));
                    j++;
                }

            }else if(j == secondHalf.size())
            {
                while (i < firstHalf.size())
                {
                    result.add(firstHalf.get(i));
                    i++;
                }

            }else if (firstHalf.get(i) > secondHalf.get(j))
            {
                result.add(secondHalf.get(j));
                j++;
            }else
            {
                result.add(firstHalf.get(i));
                i++;
            }
        }

        return result;

    }

}
