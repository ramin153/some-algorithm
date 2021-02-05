import java.util.Scanner;

public class ArtGallery {
    static Scanner scanner = new Scanner(System.in);
    public static void main(String[] args) {
        int numberRow = scanner.nextInt();
        int numberClose = scanner.nextInt();
        int[][] twoDGallery = new int[numberRow][2];
        for (int i = 0;i < numberRow;i++)
        {
            twoDGallery[i][0] = scanner.nextInt();
            twoDGallery[i][1] = scanner.nextInt();
        }
        scanner.nextInt();
        scanner.nextInt();
        System.out.println(closeDoorsMax(twoDGallery,numberRow,numberClose));

    }

    private static int closeDoorsMax(int[][] twoDGallery,int numberRow,int numberClose) {
        Item[][] items = Item.create2DArray(numberRow,numberClose +1);
        //we initial first item
        // left twoDGallery[i][0] // right   twoDGallery[i][1]
        items[0][0].both = twoDGallery[0][0] + twoDGallery[0][1];
        items[0][0].left = twoDGallery[0][0];
        items[0][0].right = twoDGallery[0][1];

        if(numberClose > 0)
        {
            items[0][1].left = twoDGallery[0][0];
            items[0][1].right = twoDGallery[0][1];
        }

        //we initial for the way if there wasn't any close door
        for (int i = 1; i < numberRow ;i ++)
        {
            int max =  items[i-1][0].max();
            items[i][0].both = twoDGallery[i][0] + twoDGallery[i][1]+max;
            items[i][0].left = twoDGallery[i][0] + max;
            items[i][0].right = twoDGallery[i][1] + max;
        }

        for (int i = 1; i < numberRow ;i ++)
        {
            int minMove = Math.min(numberClose,i+1);
            for (int j = 1; j <= minMove;j++)
            {
                // we have there choice

                //if we don't to close any doors so we must chose from the same col because the number close be the same
                if (items[i-1][j].isWay())
                    items[i][j].both = items[i-1][j].max() + twoDGallery[i][0] + twoDGallery[i][1];
                // if we want close door we select  previse col because we want increase number close door
                if (items[i-1][j-1].isRightWay())
                    items[i][j].right = items[i-1][j-1].maxRight() + twoDGallery[i][1];
                if (items[i-1][j-1].isLeftWay())
                    items[i][j].left = items[i-1][j-1].maxLeft() + twoDGallery[i][0];

                // why we use isWay ==> to see if our way of select doesn't make way unReachAble
                // what is max ?? ==> because we close the in our view , we only can chose the option that doesn't cost unReachAble
            }

            // if close == row =>> so our last item both will be -1


        }

        return items[numberRow-1][numberClose].max();
    }

    static class Item
    {
        int both;
        int left;
        int right;
        Item()
        {
            both = -1;
            left = -1;
            right = -1;
        }
        public static Item[][] create2DArray(int row,int col)
        {
            Item[][] items = new Item[row][col];
            for (int i = 0 ;i < row;i++ )
                for (int j = 0; j < col;j ++)
                    items[i][j] = new Item();

            return items;
        }
        boolean isWay()
        {
            return -1 != max();
        }

        boolean isLeftWay()
        {
            return -1 != maxLeft();
        }

        boolean isRightWay()
        {
            return -1 != maxRight();
        }

        int max()
        {
            return Math.max(both,Math.max(left,right));
        }

        int maxLeft()
        {
            return Math.max(both,left);
        }

        int maxRight()
        {
            return Math.max(both,right);
        }
    }
}
