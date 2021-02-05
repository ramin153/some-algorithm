package com.company;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Scanner;

public class PoirotAndWall {

    static class Edge {
        int source;
        long destination;
        long weight;

        public Edge(int source, int destination, long weight) {
            this.source = source;
            this.destination = (long) destination;
            this.weight = weight;
        }
    }

    static class HeapNode {
        int vertex;
        long distance;

        static long[] getSize(HeapNode[] items) {
            long[] dis = new long[items.length];
            for (int i = 0; i < items.length; i++)
                dis[i] = items[i].distance;
            return dis;
        }
    }

    static class Graph {
        int vertices;
        public LinkedList<Edge>[] adjacencylist;

        Graph(int vertices) {
            this.vertices = vertices;
            adjacencylist = new LinkedList[vertices];
            //initialize adjacency lists for all the vertices
            for (int i = 0; i < vertices; i++) {
                adjacencylist[i] = new LinkedList<>();
            }
        }

        public void addEdge(int source, int destination, long weight) {
            Edge edge = new Edge(source, destination, weight);
            adjacencylist[source].addFirst(edge);


        }

        public long[] dijkstra_GetMinDistances(int sourceVertex) {
            int INFINITY = Integer.MAX_VALUE;
            boolean[] SPT = new boolean[vertices];

//          //create heapNode for all the vertices
            HeapNode[] heapNodes = new HeapNode[vertices];
            for (int i = 0; i < vertices; i++) {
                heapNodes[i] = new HeapNode();
                heapNodes[i].vertex = i;
                heapNodes[i].distance = INFINITY;
            }

            //decrease the distance for the first index
            heapNodes[sourceVertex].distance = 0;

            //add all the vertices to the MinHeap
            MinHeap minHeap = new MinHeap(vertices);
            for (int i = 0; i < vertices; i++) {
                minHeap.insert(heapNodes[i]);
            }
            //while minHeap is not empty
            while (!minHeap.isEmpty()) {
                //extract the min
                HeapNode extractedNode = minHeap.extractMin();

                //extracted vertex
                int extractedVertex = extractedNode.vertex;
                SPT[extractedVertex] = true;

                //iterate through all the adjacent vertices
                LinkedList<Edge> list = adjacencylist[extractedVertex];
                for (int i = 0; i < list.size(); i++) {
                    Edge edge = list.get(i);
                    int destination = (int) edge.destination;
                    //only if  destination vertex is not present in SPT
                    if (SPT[destination] == false) {
                        ///check if distance needs an update or not
                        //means check total weight from source to vertex_V is less than
                        //the current distance value, if yes then update the distance
                        long newKey = heapNodes[extractedVertex].distance + edge.weight;
                        long currentKey = heapNodes[destination].distance;
                        if (currentKey > newKey) {
                            decreaseKey(minHeap, newKey, destination);
                            heapNodes[destination].distance = newKey;
                        }
                    }
                }
            }
            //print SPT
            return HeapNode.getSize(heapNodes);
        }

        public void decreaseKey(MinHeap minHeap, long newKey, int vertex) {

            //get the index which distance's needs a decrease;
            int index = minHeap.indexes[vertex];

            //get the node and update its value
            HeapNode node = minHeap.mH[index];
            node.distance = newKey;
            minHeap.bubbleUp(index);
        }

        public void printDijkstra(HeapNode[] resultSet, int sourceVertex) {
            System.out.println("Dijkstra Algorithm: (Adjacency List + Min Heap)");
            for (int i = 0; i < vertices; i++) {
                System.out.println("Source Vertex: " + sourceVertex + " to vertex " + +i +
                        " distance: " + resultSet[i].distance);
            }
        }
    }

    static class MinHeap {
        int capacity;
        int currentSize;
        HeapNode[] mH;
        int[] indexes; //will be used to decrease the distance


        public MinHeap(int capacity) {
            this.capacity = capacity;
            mH = new HeapNode[capacity + 1];
            indexes = new int[capacity];
            mH[0] = new HeapNode();
            mH[0].distance = Integer.MIN_VALUE;
            mH[0].vertex = -1;
            currentSize = 0;
        }

        public void display() {
            for (int i = 0; i <= currentSize; i++) {
                System.out.println(" " + mH[i].vertex + "   distance   " + mH[i].distance);
            }
            System.out.println("________________________");
        }

        public void insert(HeapNode x) {
            currentSize++;
            int idx = currentSize;
            mH[idx] = x;
            indexes[x.vertex] = idx;
            bubbleUp(idx);
        }

        public void bubbleUp(int pos) {
            int parentIdx = pos / 2;
            int currentIdx = pos;
            while (currentIdx > 0 && mH[parentIdx].distance > mH[currentIdx].distance) {
                HeapNode currentNode = mH[currentIdx];
                HeapNode parentNode = mH[parentIdx];

                //swap the positions
                indexes[currentNode.vertex] = parentIdx;
                indexes[parentNode.vertex] = currentIdx;
                swap(currentIdx, parentIdx);
                currentIdx = parentIdx;
                parentIdx = parentIdx / 2;
            }
        }

        public HeapNode extractMin() {
            HeapNode min = mH[1];
            HeapNode lastNode = mH[currentSize];
//            update the indexes[] and move the last node to the top
            indexes[lastNode.vertex] = 1;
            mH[1] = lastNode;
            mH[currentSize] = null;
            sinkDown(1);
            currentSize--;
            return min;
        }

        public void sinkDown(int k) {
            int smallest = k;
            int leftChildIdx = 2 * k;
            int rightChildIdx = 2 * k + 1;
            if (leftChildIdx < heapSize() && mH[smallest].distance > mH[leftChildIdx].distance) {
                smallest = leftChildIdx;
            }
            if (rightChildIdx < heapSize() && mH[smallest].distance > mH[rightChildIdx].distance) {
                smallest = rightChildIdx;
            }
            if (smallest != k) {

                HeapNode smallestNode = mH[smallest];
                HeapNode kNode = mH[k];

                //swap the positions
                indexes[smallestNode.vertex] = k;
                indexes[kNode.vertex] = smallest;
                swap(k, smallest);
                sinkDown(smallest);
            }
        }

        public void swap(int a, int b) {
            HeapNode temp = mH[a];
            mH[a] = mH[b];
            mH[b] = temp;
        }

        public boolean isEmpty() {
            return currentSize == 0;
        }

        public int heapSize() {
            return currentSize;
        }
    }

    public static void main(String[] args) {



        Scanner scanner = new Scanner(System.in);
        try {
            System.out.println(createWall(scanner));
        } catch (Exception e) {
            e.printStackTrace();
        }


        scanner.close();
    }

    protected static void createFile(String fileNameOut) {
        try {
            File myObj = new File(fileNameOut);
            if (myObj.createNewFile()) {

            } else {
                emptyFile(fileNameOut);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static void emptyFile(String fileNameOut) throws FileNotFoundException {
        File file = new File(fileNameOut);
        PrintWriter writer = new PrintWriter(file);
        writer.print("");
        writer.close();
    }

    static int[][] readArray(Scanner scanner, int row, int col) {
        int[][] map = new int[row][col];
        for (int i = 0; i < row; i++)
            for (int j = 0; j < col; j++) {
                map[i][j] = scanner.nextInt();

            }

        return map;

    }

    static long createWall(Scanner myReader) throws FileNotFoundException {

        int row = myReader.nextInt();
        int col = myReader.nextInt();
        int[][] map = readArray(myReader, row, col);
        int[] src = new int[2];
        src[0] = myReader.nextInt();
        src[1] = myReader.nextInt();
        myReader.close();


        return cutGraph(map,src);
    }

    static long cutGraph(int[][] map, int[] src) {
        Graph helpGraph = new Graph(src[0]+ map.length+ map[0].length-2);

        topLeft(map,src,helpGraph);
        bottomLeft(map,src,helpGraph);
        bottomRight(map,src,helpGraph);
        topRight(map,src,helpGraph);


        return finalResult(helpGraph,src[0],map.length+ map[0].length-2);
    }

    static long finalResult(Graph graph,int size,int endBegin)
    {
        long min = Integer.MAX_VALUE;
        long[] save;
        int maxSize = size +endBegin;
        for (int i = 0; i < size;i++)
        {
         save = graph.dijkstra_GetMinDistances(i);

         for (int j = endBegin+i-1; j <= endBegin+i+1;j++ )
         {

             if (j >= endBegin && j<maxSize&& save[j]<min)
             {

                 min = save[j];
             }
         }
        }

        return min;

    }

    static void topLeft(int[][] map, int[] src,Graph hgraph) {
        int endRow = (src[0]);

        int[][] helpMap = new int[endRow][src[1]+2];
        copyMap(map,helpMap, helpMap[0].length-1, 0, helpMap.length, 0);

        for (int i = 0; i < endRow;i++)
        {
            helpMap[i][helpMap[0].length-1] = Integer.MAX_VALUE;
        }

        Graph graph = new Graph(helpMap.length * helpMap[0].length);

        for (int i = 0 ; i < helpMap.length;i++)
        {
            for (int j = 0; j < helpMap[0].length-1;j++)
            {
                addItemToGraph(helpMap,j,i,graph,true,true);
            }
        }


        long[][] twoD ;





        for (int i = 0 ; i < endRow;i++)
        {

            twoD = convert1dTo2d(graph.dijkstra_GetMinDistances(i*helpMap[0].length + (helpMap[0].length-1)), helpMap.length, helpMap[0].length);

            for (int j = 0; j < helpMap[0].length-1;j++)
            {

                helpAdd(hgraph,endRow,src[0]+src[1],twoD[helpMap.length-1][j],i,endRow+j);

            }
        }

    }

    static void helpAdd(Graph graph,int min,int max ,long val,int src,int loc)
    {
        for (int i = loc -1;i <=loc+1;i++)
            if (i>= min && i < max ) {
                graph.addEdge(src, i, val);
            }
    }

    static void bottomLeft(int[][] map, int[] src,Graph hgraph) {
        int endRow = (map.length);
        int beginRow = src[0] -1;

        int endCol = src[1];
        int beginCol = 0;

        int[][] helpMap = new int[endRow - beginRow][endCol];
        copyMapBL(map,helpMap, endCol, beginCol,endRow , beginRow);

        for (int j = 0; j < endCol;j++)
        {
            helpMap[0][j] = Integer.MAX_VALUE;
        }

        Graph graph = new Graph(helpMap.length * helpMap[0].length);

        for (int i = 1 ; i < helpMap.length;i++)
        {
            for (int j = 0; j < helpMap[0].length;j++)
            {
                addItemToGraph(helpMap,j,i,graph,false,true);
            }
        }


        long[][]twoD;
        for (int j = 0 ; j < endCol;j++)
        {
            twoD = convert1dTo2d(graph.dijkstra_GetMinDistances(j), helpMap.length, helpMap[0].length);

            for (int i = 1; i < helpMap.length;i++)
            {
                helpAdd(hgraph,src[0]+src[1],src[1]+ map.length-1,twoD[i][helpMap[0].length-1],j+src[0],src[0]+src[1]+i-1);

            }
        }

    }

    static void bottomRight(int[][] map, int[] src,Graph hgraph) {
        int endRow = (map.length);
        int beginRow = src[0]+1;

        int endCol = map[0].length;
        int beginCol = src[1]-1;

        int[][] helpMap = new int[endRow - beginRow][endCol-beginCol];
        copyMapBR(map,helpMap, endCol, beginCol,endRow , beginRow);
        for (int i = 0; i < endRow-beginRow;i++)
        {
            helpMap[i][0] = Integer.MAX_VALUE;
        }

        Graph graph = new Graph(helpMap.length * helpMap[0].length);

        for (int i = 0 ; i < helpMap.length;i++)
        {
            for (int j = 1; j < helpMap[0].length;j++)
            {
                addItemToGraph(helpMap,j,i,graph,false,false);
            }
        }

        long[][]twoD;
        for (int i = 0 ; i < endRow-beginRow;i++)
        {
            twoD = convert1dTo2d(graph.dijkstra_GetMinDistances(i* helpMap[0].length), helpMap.length, helpMap[0].length);

            for (int j = 1; j < helpMap[0].length;j++)
            {

                helpAdd(hgraph,src[1]+ map.length-1, map[0].length+ map.length-2,twoD[0][j],src[0]+src[1]+i,src[1]+ map.length+j-2);


            }
        }

    }

    static void topRight(int[][] map, int[] src,Graph hgraph) {
        int endRow = src[0]+2;
        int beginRow =0;

        int endCol = map[0].length;
        int beginCol = src[1]+1;

        int[][] helpMap = new int[endRow - beginRow][endCol-beginCol];
        copyMapTR(map,helpMap, endCol, beginCol,endRow , beginRow);

        for (int j = 0; j < endCol-beginCol;j++)
        {
            helpMap[endRow-1][j] = Integer.MAX_VALUE;
        }

        Graph graph = new Graph(helpMap.length * helpMap[0].length);

        for (int i = 0 ; i < helpMap.length -1;i++)
        {
            for (int j = 0; j < helpMap[0].length;j++)
            {
                addItemToGraph(helpMap,j,i,graph,true,false);
            }
        }



        long[][]twoD;

        for (int j = 0 ; j < endCol-beginCol;j++) {
            twoD = convert1dTo2d(graph.dijkstra_GetMinDistances(j + (helpMap[0].length*(helpMap.length-1))), helpMap.length, helpMap[0].length);


            for (int i = 0; i <src[0]+1; i++) {

                helpAdd(hgraph,map[0].length+ map.length-2, map[0].length+ map.length+src[0]-2,twoD[i][0],src[1]+ map.length+j-1,map[0].length + map.length +i -2);


            }
        }



    }


    static void addItemToGraph(int[][] map, int col,int row,Graph graph,boolean top,boolean left)
    {

        for (int i = row-1;i<=row+1;i++)
            for (int j = col-1;j<=col+1;j++)
            {


                if (i>=0 && i< map.length && j>=0 && j< map[0].length  )
                {
                    if (i == row && j == col)
                        continue;
                    if (top && left && (col == map[0].length-1 && col != i -1 && row != j))
                    {
                        continue;
                    }else if(!top && left &&(row == 0 && col != i && 1 != j))
                    {
                        continue;
                    }else if(!top && !left &&(col == 0 && i != 1 && row != j))
                    {
                        continue;
                    }else if(top && !left &&(row == map.length -1 && col != i && row != map.length -2))
                    {
                        continue;
                    }

                    graph.addEdge(i* map[0].length+j,row*map[0].length+col, map[row][col]);
                }
            }
    }

    static void copyMap(int[][] map,int[][] help, int maxCol, int minCol, int maxRow, int minRow)
    {

        for (int i = minRow; i <maxRow;i++)
            for (int j = minCol; j <maxCol;j++)
            {
                help[i-minRow][j-minCol] = map[i][j];
            }

    }

    static void copyMapBL(int[][] map,int[][] help, int maxCol, int minCol, int maxRow, int minRow)
    {

        for (int i = minRow+1; i <maxRow;i++)
            for (int j = minCol; j <maxCol;j++)
            {
                help[i-minRow][j-minCol] = map[i][j];
            }

    }
    static void copyMapBR(int[][] map,int[][] help, int maxCol, int minCol, int maxRow, int minRow)
    {

        for (int i = minRow; i <maxRow;i++)
            for (int j = minCol+1; j <maxCol;j++)
            {
                help[i-minRow][j-minCol] = map[i][j];
            }

    }

    static void copyMapTR(int[][] map,int[][] help, int maxCol, int minCol, int maxRow, int minRow)
    {

        for (int i = minRow; i <maxRow-1;i++)
            for (int j = minCol; j <maxCol;j++)
            {
                help[i-minRow][j-minCol] = map[i][j];
            }

    }

    static long[][] convert1dTo2d(long[] item,int row , int col)
    {
        long[][] result = new long[row][col];

        for (int i = 0; i < row;i++)
            for (int j = 0; j < col;j++)
                result[i][j] = item[i*col+j];

        return result;
    }

    static void print2d(long[][] item)
    {
        int row = item.length;
        for (int i = 0; i < row;i++)
                System.out.println(Arrays.toString(item[i]));

        System.out.println("++++++++++++++");
    }

    static void print2d(int[][] item)
    {
        int row = item.length;
        for (int i = 0; i < row;i++)
            System.out.println(Arrays.toString(item[i]));
    }


}
