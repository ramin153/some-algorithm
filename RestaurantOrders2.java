import java.util.Arrays;
import java.util.Scanner;
// it's another way but i didn't metion it in hw
public class RestaurantOrders2 {

    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        int numberFoodPrice;
        int[] foodsPrice;
        int numberOfOrder;
        int[] moneyToPayForOrder;

        numberFoodPrice = scanner.nextInt();
        foodsPrice = new int[numberFoodPrice];
        for(int i = 0 ; i < numberFoodPrice;i++)
            foodsPrice[i] = scanner.nextInt();

        numberOfOrder = scanner.nextInt();
        moneyToPayForOrder = new int[numberOfOrder];
        for(int i = 0 ; i < numberOfOrder;i++)
            moneyToPayForOrder[i] = scanner.nextInt();

        restaurantOrders(numberFoodPrice,foodsPrice,numberOfOrder,moneyToPayForOrder);
    }

    public static void restaurantOrders(int numberFoodPrice,int[] foodsPrice,int numberOfOrder,int[] moneyToPayForOrder)
    {
        int maxOrderPrice = Arrays.stream(moneyToPayForOrder).max().getAsInt();
        Item[] items = new Item[maxOrderPrice+1];
        for(int i = 0 ; i < maxOrderPrice+1; i++)
            items[i] = new Item(numberFoodPrice);

        for(int i = 0 ; i < maxOrderPrice+1;i++)
        {
            // why we compare to item[0] ?? because always all of its value is zero so mean there wasn't any way until now

            for(int foodP:foodsPrice)
            {

                if(foodP > i)
                    continue;

                if(items[i - foodP].isAmbiguse )
                {

                    items[i].isAmbiguse = true;
                    continue;
                }
                // fist time to come
                if (foodP == i)
                {
                    if(items[i].isSame(items[0].untilNow))
                    {
                        //there was no way unitl now

                            items[i].untilNow[findIndex(foodsPrice,foodP)] += 1;




                    }else
                    {
                        //there was a way before so its ambiguse
                        items[i].isAmbiguse = true;

                    }
                }
                // is there way until now

                else if (!items[i - foodP].isSame(items[0].untilNow))
                {
                    try {
                        int[] colonWay = items[i - foodP].untilNow.clone();
                        colonWay[findIndex(foodsPrice,foodP)]+= 1;
                        // it check the way we create was new or not
                        if(!items[i].isSame(colonWay) && !items[i].isSame(items[0].untilNow))
                        {
                            //there is two way until here
                            items[i].isAmbiguse = true;
                        }else
                        {
                            items[i].untilNow = colonWay;
                        }
                    }catch (Exception e)
                    {

                    }



                }


            }
        }

        for(int order : moneyToPayForOrder)
        {
            Item result = items[order];
            String text = "";
            StringBuilder builder = new StringBuilder();
            if(result.isAmbiguse )
            {
                builder.append("Ambiguous");
            }else if (result.isSame(items[0].untilNow))
            {
                builder.append("Impossible");
            }else
            {
                for(int i = 0; i < numberFoodPrice;i++)
                {
                    for (int j = 0; j < result.untilNow[i];j++)
                        builder.append(i+1+" ");
                }
            }
            text = builder.toString();
            System.out.println(text);
        }



    }

    public static int findIndex(int arr[], int t)
    {
        for(int i = 0; i < arr.length;i++)
            if(t == arr[i])
                return i;

        return -1;
    }

    static class Item
    {
        int[] untilNow;
        boolean isAmbiguse;
        Item(int i)
        {
            untilNow = new int[i];
            Arrays.fill(untilNow,0);
            isAmbiguse = false;
        }

        public  boolean isSame(int[] arr)
        {
            boolean result = true;
            if(arr.length != untilNow.length)
                result = false;

            for (int i = 0;i < arr.length&&result;i++)
                result = result & (arr[i] == untilNow[i]);
            return result;
        }

    }



}


