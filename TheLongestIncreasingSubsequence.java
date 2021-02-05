import jdk.swing.interop.SwingInterOpUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class TheLongestIncreasingSubsequence {

    // Complete the longestIncreasingSubsequence function below.
    static int longestIncreasingSubsequence(int[] arr) {
        int size = arr.length;
        int dp[] = new int[size];
        int max = 1;
        Arrays.fill(dp,1);
        for(int i = size-1;i>=0 ;i--)
            for(int j = i+1; j < size;j++)
            {
                if(arr[i]< arr[j] && dp[i]<dp[j]+1)
                {
                    dp[i]=dp[j]+1;
                    if (max < dp[i])
                        max = dp[i];
                }
            }
        return max;

    }

    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args)  {
        int size = scanner.nextInt();
        int[]items = new int[size];
        for (int i =0; i<size;i++)
            items[i] = scanner.nextInt();
        int result = longestIncreasingSubsequence(items);
        System.out.println(result);

    }
}
