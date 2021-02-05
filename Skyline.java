import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Skyline {

    static class Location
    {
        double height =0;
        double left_x =0;
        double right_x =0;

        public Location( double left_x, double right_x,double height) {
            this.height = height;
            this.left_x = left_x;
            this.right_x = right_x;
        }

        @Override
        public String toString() {
            return "{" +
                     height +
                    "," + left_x +
                    "," + right_x +
                    '}';
        }
    }

    public static double skyLine(List<Location> items)
    {
        List<Double> shape = createShape(items);
        double volume = calculateShape(shape);
        return volume;
    }

    private static List<Double> createShape(List<Location> items)
    {
        if(items.size() == 1)
        {
            Location save = items.get(0);
            return Arrays.asList(save.left_x,save.height,save.right_x-save.left_x,-1*save.height);

        }

        List<Double> firstHalf = createShape(items.subList(0,items.size()/2));
        List<Double> secondHalf = createShape(items.subList(items.size()/2,items.size()));

        List<Double> result = new ArrayList<>();

        double height =0,xCoordinate =0,
               addHeight =0,addXCoordinate =0,
               heightFirst =0,xCoordinateFirst =0,
               heightSecond =0,xCoordinateSecond =0;

        for (int i = 0, j =0;i <firstHalf.size() || j < secondHalf.size();)
        {
            if(i ==0 && j == 0)
            {
                if(firstHalf.get(0) > secondHalf.get(0))
                {
                    xCoordinateSecond += secondHalf.get(j);
                    heightSecond += secondHalf.get(j+1);
                    addHeight= heightSecond;
                    addXCoordinate=xCoordinateSecond;
                    j+=2;


                }else
                {
                    xCoordinateFirst = firstHalf.get(i);
                    heightFirst = firstHalf.get(i+1);
                    addHeight= heightFirst;
                    addXCoordinate=xCoordinateFirst;
                    i+=2;

                }
                height =addHeight;
                xCoordinate = addXCoordinate;


            }
            else if (i >=firstHalf.size() || j >= secondHalf.size())
            {

                double saveH,saveX;
                int index;
                List<Double> itemss ;
                if(i >=firstHalf.size())
                {
                    saveH = heightSecond;
                    saveX = xCoordinateSecond;
                    index = j;
                    itemss = secondHalf;
                }
                else
                {
                    saveH = heightFirst;
                    saveX = xCoordinateFirst;
                    index = i;
                    itemss = firstHalf;
                }
                System.out.println(j+" "+secondHalf);
                while (index <itemss.size() )
                {
                    if(xCoordinate != saveX)
                    {

                        addXCoordinate = saveX +itemss.get(index) - xCoordinate;
                        xCoordinate+= addXCoordinate;
                        saveX += itemss.get(index);
                        saveH += itemss.get(index+1);
                        addHeight =saveH-height;
                        height=saveH;

                    }
                    else
                    {
                        addXCoordinate =itemss.get(index);
                        addHeight =itemss.get(index+1);

                        xCoordinate += addXCoordinate ;
                        saveX = xCoordinate;
                        height += addHeight;
                        saveH= height;

                    }

                    index+=2;
                    result.add(addXCoordinate);
                    result.add(addHeight);

                }
                break;
            }
            else {

                if(xCoordinateFirst+firstHalf.get(i) > xCoordinateSecond+secondHalf.get(j))
                {
                    addXCoordinate = (xCoordinateSecond+secondHalf.get(j)) - xCoordinate;
                    xCoordinate += addXCoordinate;

                    xCoordinateSecond += secondHalf.get(j);
                    heightSecond += secondHalf.get(j+1);
                    j+=2;

                }
                else
                {

                    addXCoordinate = (xCoordinateFirst+firstHalf.get(i)) - xCoordinate;
                    xCoordinate += addXCoordinate;

                    xCoordinateFirst += firstHalf.get(i);
                    heightFirst += firstHalf.get(i+1);

                    i+=2;


                }

                if(heightSecond > heightFirst)
                {
                    addHeight = heightSecond-height;
                    height += addHeight;
                }else
                {
                    addHeight = heightFirst-height;
                    height += addHeight;
                }
            }
            result.add(addXCoordinate);
            result.add(addHeight);

        }
        return result;
    }
    private static double calculateShape(List<Double> shape)
    {
        double size = 0;
        double height = 0 ;
        System.out.println(shape);
        for(int i = 0; i < shape.size();i+=2)
        {
            size += height*shape.get(i);
            height+= shape.get(i+1);
        }
        return size;
    }

    public static void main(String[] args) {
        List<Location> items = new ArrayList<>();
        items.add(new Location(6,12,8));
        items.add(new Location(2,11,5));

        items.add(new Location(4,10,7));
        items.add(new Location(14,20,6));
        items.add(new Location(15, 17, 9));
        items.add(new Location(22, 25, 6.5));
        System.out.println(skyLine(items));

    }
}
