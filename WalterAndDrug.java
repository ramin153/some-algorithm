import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class WalterAndDrug {
    public static class Cell
    {
        int turn;
        String name;
        List<Cell> connection;

        Cell(String name)
        {
            this.name = name;
            connection = new ArrayList<>();
        }

        public static Cell find(String name,List<Cell> list)
        {

            for (Cell cell : list)
                if (name.equals(cell.name))
                    return cell;
            return null;
        }

        public static void setConnection(Cell cell1,Cell cell2)
        {
            cell1.connection.add(cell2);
            cell2.connection.add(cell1);

        }
        public boolean bfs()
        {
            if (this.turn == 0)
                return bfs(1);
            return true;
        }
        public  boolean bfs (int turn)
        {
            if (this.turn != 0)
            {
                return this.turn == turn;
            }


            this.turn = turn;
            int nextTurn = (turn%2)+1;
            for (Cell cell : connection)
            {
                if (!cell.bfs(nextTurn))
                    return false;
            }

            return true;

        }

        public static String itemTurn(int turn,List<Cell> items)
        {
            StringBuilder stringBuilder = new StringBuilder();
            for (Cell cell : items)
                if (cell.turn == turn)
                {
                    stringBuilder.append(cell.name+" ");
                }
            return stringBuilder.toString();
        }

    }


    public static void main(String[] args) throws Exception{
        Scanner scanner = new Scanner(System.in);
        int line = Integer.parseInt(scanner.nextLine());
        List<Cell> items = new ArrayList<>();
        for (int i = 0 ;i < line;i++)
            items.add(new Cell(scanner.nextLine()));


        int connection = Integer.parseInt(scanner.nextLine());

        for (int i = 0 ;i < connection;i++)
        {
            String[] names = scanner.nextLine().split(" ");
            Cell cell1 = Cell.find(names[0],items);
            Cell cell2 = Cell.find(names[1],items);

            if (cell1 == null || cell2 == null)
                throw new Exception("wrong input");

            Cell.setConnection(cell1,cell2);


        }
        for (Cell cell:items)
            if (!cell.bfs())
            {
                System.out.println("impossible");
                return;
            }


        System.out.println(Cell.itemTurn(1,items));
        System.out.println(Cell.itemTurn(2,items));

    }
}
