import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class AirportAndWay {

    public static class Edge
    {
        int side1;
        int side2;
        int value;

        Edge(int a,int b,int value)
        {
            side1 = a;
            side2 = b;
            this.value = value;
        }

        public static List<Edge> merge(List<Edge> items)
        {

            if (items.size() == 1)
            {
                return items;
            }
            int size = items.size();
            List<Edge> result = new ArrayList<>();
            List<Edge> left = merge(items.subList(0,size/2));
            List<Edge> right = merge(items.subList(size/2,size));
            for (int i = 0 , j =0; i < left.size() || j < right.size();)
            {
                if (i < left.size() && j < right.size())
                {
                    if (left.get(i).value > right.get(j).value)
                    {
                        result.add(right.get(j));
                        j++;
                    }else
                    {
                        result.add(left.get(i));
                        i++;
                    }
                }else if(i < left.size())
                {
                    result.add(left.get(i));
                    i++;
                }else
                {
                    result.add(right.get(j));
                    j++;
                }
            }
            return result;
        }

    }

    public static class Cell
    {

        int name;
        Cell father;
        int cost;

        Cell(int name)
        {
            this.name = name;
            father = null;
            cost = 0;
        }

        Cell findRoot()
        {
            if (father == null)
                return this;
            return father.findRoot();
        }

        static Cell find(List<Cell> cells,int name)
        {
            for (Cell c:cells)
                if (c.name == name)
                    return c;
            return null;
        }

        static void connect(List<Cell> items,Cell cell1,Cell cell2,int cost)
        {
            Cell cell1Father = cell1.findRoot();
            Cell cell2Father = cell2.findRoot();
            if (cell1Father.equal(cell2Father))
            {
                return ;
            }

            cell2Father.father = cell1Father;
            cell1Father.cost += (cost +cell2Father.cost);
            for (int i = 0 ;i <items.size();i++)
                if (items.get(i).equal(cell2Father)) {
                    items.remove(i);
                    break;
                }

        }


        public boolean equal(Cell cell) {
            return cell.name == name;
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int testCase = Integer.parseInt(scanner.nextLine());

        for (int test =0;test<testCase;test++)
        {
            String[] line = scanner.nextLine().split(" ");
            int loc = Integer.parseInt(line[0]);
            int ways = Integer.parseInt(line[1]);
            int airportCost = Integer.parseInt(line[2]);

            List<Cell> items = new ArrayList<>();
            List<Cell> kros = new ArrayList<>();

            for (int i = 0 ;i < loc;i++)
            {
                Cell cell = new Cell(i+1);
                items.add(cell);
                kros.add(cell);
            }

            List<Edge> edges = new ArrayList<>();
            for (int i = 0 ;i < ways;i++)
            {
                String[] vert = scanner.nextLine().split(" ");
                int from = Integer.parseInt(vert[0]);
                int to = Integer.parseInt(vert[1]);
                int cost = Integer.parseInt(vert[2]);

                if (cost < airportCost)
                {
                    edges.add(new Edge(from,to,cost));
                }
            }
            edges = Edge.merge(edges);


            for (Edge edge:edges)
            {
                Cell cell1 = Cell.find(items, edge.side1);
                Cell cell2 = Cell.find(items, edge.side2);
                Cell.connect(kros,cell1,cell2, edge.value);

            }

            int cost = 0;
            for (Cell cell:kros)
                cost+= (airportCost+ cell.cost);
            System.out.println(cost+" "+kros.size());

        }


    }

}
