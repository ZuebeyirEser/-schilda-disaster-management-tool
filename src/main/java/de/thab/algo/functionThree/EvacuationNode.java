package de.thab.algo.functionThree;
public class EvacuationNode {
    private int dest;
    private int weight;
    private boolean isBlocked;

    public EvacuationNode(int dest, int weight) {
        this.dest = dest;
        this.weight = weight;
        this.isBlocked = false;
    }

    public int getDest() {
        return dest;
    }

    public int getWeight() {
        return weight;
    }

    public boolean isBlocked() {
        return isBlocked;
    }

    public void setBlocked(boolean blocked) {
        isBlocked = blocked;
    }
}
