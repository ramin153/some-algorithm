import java.util.*;

public class RestaurantOrders {
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
        // we sort item from min to max
        int[] foodsPriceColon = foodsPrice.clone();
        Arrays.sort(foodsPriceColon);
        int maxOrderPrice = Arrays.stream(moneyToPayForOrder).max().getAsInt();
        Item[] items = new Item[maxOrderPrice+1];
        for(int i = 0 ; i < maxOrderPrice+1; i++)
            items[i] = new Item();

        // if they order any food so there is no food so
        items[0].isWay = true;
        // we see for every item is there a way to reach to end

        for(int i = 0; i < numberFoodPrice;i++)
        {
            // for each item we check if there is way that we can find to reach that food in this way that
            // (index - foodPrice[i]) is select item if it was true so its a way to items[index] to be found

            for(int index = foodsPriceColon[i],baseFoodPrice = foodsPriceColon[i] ;index< maxOrderPrice+1;index++)
            {

                if(items[index - baseFoodPrice].isWay)
                {
                    items[index].setItem(index - baseFoodPrice);
                }
            }
        }

        // if in the way of the answer one of way was -1 is ambiguous
        // if last item was false there is no way
        for(int i = 0; i < numberOfOrder;i++) {
            int order = moneyToPayForOrder[i];
            boolean isThereWay = true;
            ArrayList<Integer> wayToAnswer = new ArrayList<>();
            if (!items[order].isWay) {
                System.out.print("Impossible");
                isThereWay = false;

            }

            for (int j = order; j != 0 && isThereWay; ) {
                if (items[j].isAmbiguous()) {
                    isThereWay = false;
                    System.out.print("Ambiguous");
                    continue;
                }
                wayToAnswer.add(findIndex(foodsPrice, j - items[j].prevItem) + 1);
                j= items[j].prevItem;
            }
            String text = "";
            Collections.sort(wayToAnswer);
            if (isThereWay) {
                StringBuilder builder = new StringBuilder();
                for (int j = 0 ; j<  wayToAnswer.size() ; j++ ){


                    builder.append(wayToAnswer.get(j));

                    if (j != wayToAnswer.size() -1)
                        builder.append(" ");
                }
                text = builder.toString();


            }
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


}

class Item
{
    boolean isWay ;
    int prevItem ;
    Item()
    {
        isWay = false;
        prevItem = 0;
    }
    public void setItem(int x)
    {
        if(isWay)
        {
            prevItem = -1;
            return;
        }
        isWay = true;
        prevItem = x;
    }
    public boolean isAmbiguous()
    {
        return (prevItem == -1);
    }
}
