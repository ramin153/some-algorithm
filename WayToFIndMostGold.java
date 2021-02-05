package com.company;
import java.io.*;
import java.util.*;

public class WayToFIndMostGold {

    static final String fileNameRead = "a.in";
    static final String fileNameRead2 = "kill-greedy.in";
    static final String fileNameRead3 = "kill-max-queue.in";
    static final String fileNameRead4 = "max-queue-tle.in";
    static final String fileNameRead5 = "multiple-treasures.in";
    static final String fileNameRead6 = "no-budget.in";
    static final String fileNameRead7 = "no-mst.in";
    static final String fileNameRead8 = "no-way-back.in";
    static final String fileNameRead9 = "random.100.in";
    static final String fileNameRead10 = "random.5000.in";
    static final String fileNameRead11 = "random.10000.in";
    static final String fileNameRead12 = "sample.in";
    static final String fileNameRead13 = "treasure-in-first-cave.in";
    static final String fileNameRead14 = "tree.10000.in";
    static final String fileNameOut = "a.out";
    static int count = 0;

    public static void main(String[] args) throws IOException {


        Scanner myReader = new Scanner(System.in);



        int numberOfTest = myReader.nextInt();
        myReader.nextLine();
        for (int i = 0; i < numberOfTest; i++) {

            int numberOfCave = myReader.nextInt();
            int numberOfTunnel = myReader.nextInt();
            myReader.nextLine();
            int vertices = numberOfCave;
            //DijkstraPQ.Graph graph = new DijkstraPQ.Graph(vertices);
            DijkstraUsingMinHeap.Graph graph = new DijkstraUsingMinHeap.Graph(vertices);
            for (int c = 0; c < numberOfTunnel; c++) {

                int source = myReader.nextInt();
                int destination = myReader.nextInt();
                int weight = myReader.nextInt();
                myReader.nextLine();
                graph.addEdge(source, destination, weight);

            }
            int numberOfCaveHaveGold = myReader.nextInt();
            int[] caveHaveGold = new int[numberOfCaveHaveGold];

            myReader.nextLine();

            int number;
            boolean cave_zero_have_gold = false;
            for (int k = 0; k < numberOfCaveHaveGold; k++) {

                number = myReader.nextInt();
                if (number == 0)
                    cave_zero_have_gold = true;

                caveHaveGold[k] = number;
            }
            myReader.nextLine();

            int numberOfTable = 0;
            if (cave_zero_have_gold)
                numberOfTable = numberOfCaveHaveGold;
            else
                numberOfTable = numberOfCaveHaveGold + 1;

            int[][] tableOfDijkstra = new int[numberOfTable][numberOfTable];

            Arrays.sort(caveHaveGold);
            full_table(tableOfDijkstra, caveHaveGold, graph, numberOfCaveHaveGold);

            int ourPower = myReader.nextInt();
            myReader.nextLine();

            int x =Uniform_cost_search(tableOfDijkstra, numberOfTable, cave_zero_have_gold, ourPower);
//            if (i == 50) {
//                print_matrix(tableOfDijkstra, numberOfTable);
//                System.out.println(x);
//            }
            System.out.println(x);
            /*
            در این جا باید تابع ای که بین دیجسترا هایی که پیدا کردیم مسیر پیدا میکند صدا زده شود .

             */

        }

        myReader.close();


    }


    static class Find_Vertex_Comp implements Comparator<Find_Vertex> {

        // Overriding compare()method of Comparator
        // for descending order of cgpa
        public int compare(Find_Vertex s1, Find_Vertex s2) {
            if (s1.our_power < s2.our_power)
                return 1;
            else if (s1.our_power > s2.our_power)
                return -1;
            return 0;
        }
    }

    public static class Find_Vertex {

        public int vertex_name;
        public Set<Integer> vertex_goal_see;
        public int our_power;

        public Find_Vertex(int vertex_name, int our_power, Set<Integer> vertex_goal_see) {

            this.our_power = our_power;
            this.vertex_name = vertex_name;
            this.vertex_goal_see = vertex_goal_see;
        }


    }

    private static int Uniform_cost_search(int[][] tableOfDijkstra, int numberOfTable, boolean cave_zero_have_gold, int ourPower) {
//        print_matrix(tableOfDijkstra,numberOfTable);


        int initial_number_of_gold = 0;
        int MaxGoldSee = 0;

        Set<Integer> mySet = new HashSet<Integer>();
        if (cave_zero_have_gold) {
            initial_number_of_gold = 1;
            MaxGoldSee = 1;
            mySet.add(0);
        }
        if (ourPower == 0)
            return MaxGoldSee;

        PriorityQueue<Find_Vertex> pq = new PriorityQueue<Find_Vertex>(1000, new Find_Vertex_Comp());
        pq.add(new Find_Vertex(0, ourPower, mySet));
        while (!pq.isEmpty()) {

            Find_Vertex vertex = pq.poll();

//            if (vertex.our_power >= tableOfDijkstra[0][vertex.vertex_name])
//                if (vertex.vertex_goal_see.size() > MaxGoldSee) {
//                    MaxGoldSee = vertex.vertex_goal_see.size();
//
//                }
            if (vertex.our_power >= tableOfDijkstra[0][vertex.vertex_name]) {
                for (int i = 1; i < numberOfTable; i++) {
                    if (!vertex.vertex_goal_see.contains(i) &&
                            tableOfDijkstra[0][i] >= 0 &&
                            tableOfDijkstra[vertex.vertex_name][i]  >= 0 &&
                            tableOfDijkstra[0][i] != Integer.MAX_VALUE&&
                            tableOfDijkstra[vertex.vertex_name][i] != Integer.MAX_VALUE &&

                            (vertex.our_power - (tableOfDijkstra[0][i] + tableOfDijkstra[vertex.vertex_name][i]) >= 0)) {
                        Set<Integer> temp_set = new HashSet<Integer>();
//                        System.out.println("here");
//                        System.out.println(vertex.vertex_goal_see.size());
                        temp_set.addAll(vertex.vertex_goal_see);
                        temp_set.add(i);
//                        System.out.println(temp_set);
//                        System.out.println(vertex.our_power - tableOfDijkstra[0][i]);
//                        System.out.println(temp_set.size());
//                        System.out.println("exit");
                        if (temp_set.size() > MaxGoldSee)
                            MaxGoldSee = temp_set.size();
                        pq.add(new Find_Vertex(i, vertex.our_power - tableOfDijkstra[vertex.vertex_name][i], temp_set));
                    }
                }

            }

        }

         return MaxGoldSee;

    }


    private static void print_matrix(int[][] tableOfDijkstra, int numberOfTable) {
        //System.out.println(numberOfTable);
        System.out.println();
        for (int i = 0; i < numberOfTable; i++) {
            for (int j = 0; j < numberOfTable; j++) {
                System.out.print(tableOfDijkstra[i][j]);
                System.out.print(' ');
            }
            System.out.println();
        }

    }

    private static void full_table(int[][] tableOfDijkstra, int[] caveHaveGold, DijkstraUsingMinHeap.Graph graph, int numberOfCaveHaveGold) {
        DijkstraUsingMinHeap.HeapNode[] distances;
        if (caveHaveGold[0] != 0) {

            distances = graph.dijkstra_GetMinDistances(0);

            tableOfDijkstra[0][0] = 0;
            for (int temp = 0; temp < numberOfCaveHaveGold; temp++) {
                tableOfDijkstra[0][temp + 1] = distances[caveHaveGold[temp]].distance;
                tableOfDijkstra[temp + 1][0] = distances[caveHaveGold[temp]].distance;
            }

            for (int i = 1; i < numberOfCaveHaveGold + 1; i++) {
                DijkstraUsingMinHeap.HeapNode[] distances1 = graph.dijkstra_GetMinDistances(caveHaveGold[i - 1]);
                for (int j = i; j < numberOfCaveHaveGold + 1; j++) {
                    tableOfDijkstra[i][j] = distances1[caveHaveGold[j - 1]].distance;
                    tableOfDijkstra[j][i] = distances1[caveHaveGold[j - 1]].distance;
                }
            }
        } else {

            for (int i = 0; i < numberOfCaveHaveGold; i++) {

                distances = graph.dijkstra_GetMinDistances(caveHaveGold[i]);

                for (int j = i; j < numberOfCaveHaveGold; j++) {

                    tableOfDijkstra[i][j] = distances[caveHaveGold[j]].distance;
                    tableOfDijkstra[j][i] = distances[caveHaveGold[i]].distance;
                }

            }

        }


    }

    private static void createFile() {
        try {
            File myObj = new File(fileNameOut);
            if (myObj.createNewFile()) {

            } else {
                emptyFile();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void emptyFile() throws FileNotFoundException {
        File file = new File(fileNameOut);
        PrintWriter writer = new PrintWriter(file);
        writer.print("");
        writer.close();
    }

    /*
    public static class DijkstraPQ {
        static class Edge {
            int source;
            int destination;
            int weight;

            public Edge(int source, int destination, int weight) {
                this.source = source;
                this.destination = destination;
                this.weight = weight;
            }
        }

        static class Graph {
            int vertices;
            LinkedList<Edge>[] adjacencylist;

            Graph(int vertices) {
                this.vertices = vertices;
                adjacencylist = new LinkedList[vertices];
                //initialize adjacency lists for all the vertices
                for (int i = 0; i < vertices; i++) {
                    adjacencylist[i] = new LinkedList<>();
                }
            }

            public void addEdge(int source, int destination, int weight) {
                Edge edge = new Edge(source, destination, weight);
                adjacencylist[source].addFirst(edge);

                edge = new Edge(destination, source, weight);
                adjacencylist[destination].addFirst(edge); //for undirected graph
            }

            public int[] dijkstra_GetMinDistances(int sourceVertex) {

                boolean[] SPT = new boolean[vertices];
                //distance used to store the distance of vertex from a source
                int[] distance = new int[vertices];

                //Initialize all the distance to infinity
                for (int i = 0; i < vertices; i++) {
                    distance[i] = Integer.MAX_VALUE;
                }
                //Initialize priority queue
                //override the comparator to do the sorting based keys
                PriorityQueue<Pair<Integer, Integer>> pq = new PriorityQueue<>(vertices, new Comparator<Pair<Integer, Integer>>() {
                    @Override
                    public int compare(Pair<Integer, Integer> p1, Pair<Integer, Integer> p2) {
                        //sort using distance values
                        int key1 = p1.getKey();
                        int key2 = p2.getKey();
                        return key1 - key2;
                    }
                });
                //create the pair for for the first index, 0 distance 0 index
                distance[0] = 0;
                Pair<Integer, Integer> p0 = new Pair<>(distance[0], 0);
                //add it to pq
                pq.offer(p0);

                //while priority queue is not empty
                while (!pq.isEmpty()) {
                    //extract the min
                    Pair<Integer, Integer> extractedPair = pq.poll();

                    //extracted vertex
                    int extractedVertex = extractedPair.getValue();
                    if (SPT[extractedVertex] == false) {
                        SPT[extractedVertex] = true;

                        //iterate through all the adjacent vertices and update the keys
                        LinkedList<Edge> list = adjacencylist[extractedVertex];
                        for (int i = 0; i < list.size(); i++) {
                            Edge edge = list.get(i);
                            int destination = edge.destination;
                            //only if edge destination is not present in mst
                            if (SPT[destination] == false) {
                                ///check if distance needs an update or not
                                //means check total weight from source to vertex_V is less than
                                //the current distance value, if yes then update the distance
                                int newKey = distance[extractedVertex] + edge.weight;
                                int currentKey = distance[destination];
                                if (currentKey > newKey) {
                                    Pair<Integer, Integer> p = new Pair<>(newKey, destination);
                                    pq.offer(p);
                                    distance[destination] = newKey;
                                }
                            }
                        }
                    }
                }
                //print Shortest Path Tree
                //printDijkstra(distance, sourceVertex);
                return distance;
            }

            static class Pair<U, V>
            {
                public final U first;       // first field of a Pair
                public final V second;      // second field of a Pair

                // Constructs a new Pair with specified values
                private Pair(U first, V second)
                {
                    this.first = first;
                    this.second = second;
                }

                @Override
                // Checks specified object is "equal to" current object or not
                public boolean equals(Object o)
                {
                    if (this == o)
                        return true;

                    if (o == null || getClass() != o.getClass())
                        return false;

                    Pair<?, ?> pair = (Pair<?, ?>) o;

                    // call equals() method of the underlying objects
                    if (!first.equals(pair.first))
                        return false;
                    return second.equals(pair.second);
                }

                @Override
                // Computes hash code for an object to support hash tables
                public int hashCode()
                {
                    // use hash codes of the underlying objects
                    return 31 * first.hashCode() + second.hashCode();
                }

                @Override
                public String toString()
                {
                    return "(" + first + ", " + second + ")";
                }

                // Factory method for creating a Typed Pair immutable instance
                public static <U, V> Pair <U, V> of(U a, V b)
                {
                    // calls private constructor
                    return new Pair<>(a, b);
                }

                public V getValue() {
                    return this.second;
                }

                public U getKey() {
                    return this.first;
                }
            }

        }

    }

     */
    public static class DijkstraUsingMinHeap {
        static class Edge {
            int source;
            int destination;
            int weight;

            public Edge(int source, int destination, int weight) {
                this.source = source;
                this.destination = destination;
                this.weight = weight;
            }
        }

        static class HeapNode {
            int vertex;
            int distance;
        }

        static class Graph {
            int vertices;
            LinkedList<Edge>[] adjacencylist;

            Graph(int vertices) {
                this.vertices = vertices;
                adjacencylist = new LinkedList[vertices];
                //initialize adjacency lists for all the vertices
                for (int i = 0; i < vertices; i++) {
                    adjacencylist[i] = new LinkedList<>();
                }
            }

            public void addEdge(int source, int destination, int weight) {
                Edge edge = new Edge(source, destination, weight);
                adjacencylist[source].addFirst(edge);

                edge = new Edge(destination, source, weight);
                adjacencylist[destination].addFirst(edge); //for undirected graph
            }

            public HeapNode[] dijkstra_GetMinDistances(int sourceVertex) {
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
                        int destination = edge.destination;
                        //only if  destination vertex is not present in SPT
                        if (SPT[destination] == false) {
                            ///check if distance needs an update or not
                            //means check total weight from source to vertex_V is less than
                            //the current distance value, if yes then update the distance
                            int newKey = heapNodes[extractedVertex].distance + edge.weight;
                            int currentKey = heapNodes[destination].distance;
                            if (currentKey > newKey) {
                                decreaseKey(minHeap, newKey, destination);
                                heapNodes[destination].distance = newKey;
                            }
                        }
                    }
                }
                //print SPT
                //printDijkstra(heapNodes, sourceVertex);
                return heapNodes;
            }

            public void decreaseKey(MinHeap minHeap, int newKey, int vertex) {

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
    }
}