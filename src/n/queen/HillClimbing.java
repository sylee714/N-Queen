package n.queen;

/**
 * This class represents the steepest hill-climbing algorithm.
 */
public class HillClimbing {
    private int n; // The number of queens
    private Node initial;
    private Node last;
    private int searchCost;
    private long runTime;
    private final int GOAL = 0; // The number of attacking pairs
    
    public HillClimbing(Node initial, int n) {
        this.n = n;
        this.initial = initial;
        searchCost = 0;
        runTime = 0;
    }
    
    public void solve() {
        // Check if initial state is the goal state
        if (!(initial.getValue() == GOAL)) {
            long startTime = System.currentTimeMillis();
            boolean end = false;
            Node current = initial;
            Node neighbor;
            while(!end) {
                searchCost = searchCost + current.getNeighbors().size();
                // Get the neighbor with the best value
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
