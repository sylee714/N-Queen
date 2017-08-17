/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package n.queen;

/**
 *
 * @author MingKie
 */
public class HillClimbing {
    private int n;
    private Node initial;
    private Node last;
    private int searchCost;
    private long runTime;
    private final int GOAL = 0;
    
    public HillClimbing(Node initial, int n) {
        this.n = n;
        this.initial = initial;
        searchCost = 0;
        runTime = 0;
    }
    
    public void solve() {
        // Initial state is the goal state
        if (!(initial.getValue() == GOAL)) {
            long startTime = System.currentTimeMillis();
            boolean end = false;
            Node current = initial;
            Node neighbor;
            while(!end) {
                //System.out.println("Current Board");
                //current.getBoard().print();
                //System.out.println("Value: " + current.getValue());
                searchCost = searchCost + current.getNeighbors().size();
                neighbor = new Node(current.getNeighbors().remove(), n);
                // Reached the goal state
                if (current.getValue() == GOAL) {
                    long endTime = System.currentTimeMillis();
                    runTime = endTime - startTime;
                    last = current;
                    end = true;
                // Selected neighbor's value is better
                } else if (neighbor.getValue() < current.getValue()) {
                    current = neighbor;
                // Did not find better neighbors
                } else {
                    long endTime = System.currentTimeMillis();
                    runTime = endTime - startTime;
                    last = current;
                    end = true;
                }
            }
        } else {
            last = initial;
        }
    }

    public int getSearchCost() {
        return searchCost;
    }

    public long getRunTime() {
        return runTime;
    }

    public Node getLast() {
        return last;
    }
    
    

}
