/* by Jared Kozar
Created on 6/11/21
Graph.java
*/

// imports various classes
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.*;
 
class Graph {

    // declares the cost, the first vertex, etc. as public variables available to all methods. The cost per edge variable is static because it's being set by the driver method and it's constant; we don't have to change it. 
    private int V;
    public int pathCount;
    public int totalCost;
    public int firstVertex;
    public int secondVertex;
    public int cost;
    public static int costPerEdge;

    // creates array of lists for calculating which edges are adjacent to one another.
    private LinkedList<Integer> adj[];
 

    @SuppressWarnings("unchecked")
    Graph (int v) {
        //creates new linked list with all edges
        V = v;
        adj = new LinkedList[v];
        for (int i = 0; i < v; ++i) {
            adj[i] = new LinkedList<>();
        }
    }
 
    //adds an edge into the graph
    void addEdge(int v, int w) {
        // Add w to v's list.
        adj[v].add(w);
    }
 
    //arecursive method to count all paths from u to d.
    int countPathsUtil(int u, int d, int firstVertex, int secondVertex, boolean[] isVisited, List<Integer> localPathList) {
 
        //try to get how many paths there are, and calculates cost 
        if (u == d) {
            pathCount++;
            cost = ((localPathList.size() - 1) * costPerEdge);
            getcost(cost);
        }

        //recur for all the vertices adjacent to this vertex
        else {
            for (Integer i : adj[u]) {
                if (!isVisited[i]) {
                    // store current node in path[]
                    localPathList.add(i);
                    countPathsUtil(i, d, firstVertex, secondVertex, isVisited, localPathList);
     
                    // remove current node in path[]
                    localPathList.remove(i);
                }
            }
        }
        return pathCount;
    }
 
    //returns number of paths from 's' to 'd'
    int countPaths(int s, int d, int firstVertex, int secondVertex) {
        //call the recursive method to count all paths
        int pathCount = 0;
        boolean[] isVisited = new boolean[V];
        ArrayList<Integer> pathList = new ArrayList<>();

        //add source to path[]
        pathList.add(s);
        pathCount = countPathsUtil(s, d, firstVertex, secondVertex, isVisited, pathList);
        return pathCount;
    }
 
    public void printCost() {
        System.out.println("the total cost is " + totalCost);
    }
 
    public void getcost(int mycost) {
       totalCost = totalCost + mycost;
    }

    public int returnUserCost(int costPerEdge) {
        return costPerEdge;
    }

    //driver method
    public static void main(String args[]) {

        Scanner keyboard = new Scanner(System.in);
        System.out.print("How many vertices do you want in your graph?: ");
        int verticies = keyboard.nextInt();

        System.out.print("How many edges do you want in your graph?: ");
        int edges = keyboard.nextInt();
        Graph g = new Graph(verticies);

        keyboard.nextLine(); 
        for(int i = 0; i < edges; i++) {

            //asks the user to enter the coordinate points, and the system takes out the space (if the user entered a space), and creates an array. The first element pf the array contains the first coordinate point, and the second number contains the second coordinate point. It then converts these numbers to integers, and calls the addEdge method to add them into the graph.

            System.out.print("Enter the coordinate points, in the form of (point1, point2): ");
            String coordinates = keyboard.nextLine(); 
            String coordsWOSpaces = coordinates.replaceAll("\\s", "");
            String[] coordarray = coordsWOSpaces.split(",");
            g.addEdge(Integer.parseInt(coordarray[0]), Integer.parseInt(coordarray[1]));
        }
 
        System.out.print("Enter the cost per edge: ");
        costPerEdge = keyboard.nextInt();

        System.out.print("Enter your starting point: ");
        int first = keyboard.nextInt();

        System.out.print("Enter your destination: ");
        int second = keyboard.nextInt();

        int s = first, d = second;
 
        System.out.println("Total paths between the two points is " + g.countPaths(s, d, first, second));
 
        g.printCost();
    }
}
