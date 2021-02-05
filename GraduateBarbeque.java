import java.util.Scanner;

public class GraduateBarbeque {
    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        int numberFood = scanner.nextInt();
        int maxCal = scanner.nextInt();
        int[] foodsCal = new int[numberFood];

        for (int i = 0 ; i < numberFood;i++)
            foodsCal[i] = scanner.nextInt();

        int minRow = Math.min(numberFood,(int)(Math.log(maxCal)/Math.log(1.5)))+1;
        System.out.println(maxCallery(maxCal,minRow,numberFood,foodsCal));
    }

    private static int maxCallery(int maxCal,int minRow,int numberFood,int[] foods) {
        int[][] calculateCal = new int[minRow][numberFood +4];
        //why plus 4=>fist save maxzCal can eat in that time ,3 next col only added to decrease number of if else statement
        // from 4 col is about the foods index
        // number row show ==>number of sequence  of eating
        // last row about maxFood we can eat in that col
        // we initial the first col with max cal in that row we can eat'
        // good point of java it initial all item array with zero
        for (int i = 0, maxCalRow = maxCal; i < minRow-1;i++,maxCalRow = maxCalRow * 2 /3)
            calculateCal[i][0] = maxCalRow;
        for (int j = 4; j < numberFood +4;j++)
        {
            // index begin at zero and last row save for best
            int minMove = Math.min(j-4,minRow-2);
            int foodIsItTime = foods[j-4];
            for (int i = minMove;i > 0;i--)
            {
                // the amount foods we can eat in that time(min between foods cal and sequence)+max value food we can eat between 2 time ago with same sequence or one time back with one less sequence
                calculateCal[i][j] = Math.min(calculateCal[i][0],foodIsItTime)+Math.max(calculateCal[i-1][j-1],calculateCal[i][j-2]);
                // if it's more than max eat in this food col ,we change it to new one(max foods we can eat until this food)
                if (calculateCal[i][j] > calculateCal[minRow-1][j])
                    calculateCal[minRow-1][j] = calculateCal[i][j];
            }
            // if we want to eat in max cal or first row there is two way, we don't eat for two sequence or we didn't eat one time in in first sequence
            calculateCal[0][j] = Math.min(calculateCal[0][0],foodIsItTime)+Math.max(calculateCal[minRow-1][j-3],calculateCal[0][j-2]);
            if (calculateCal[0][j] > calculateCal[minRow-1][j])
                calculateCal[minRow-1][j] = calculateCal[0][j];

        }

        return calculateCal[minRow-1][numberFood+3];
    }
}
