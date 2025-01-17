package de.thab.algo.functionThree;
public class InfrastructureNode {
    private String name;
    private NodeType type;
    private boolean isPriority;

    public InfrastructureNode(String name) {
        this.name = name;
        this.type = NodeType.STANDARD_NODE;
        this.isPriority = false;
    }

    public String getName() {
        return name;
    }

    public NodeType getType() {
        return type;
    }

    public void setType(NodeType type) {
        this.type = type;
    }

    public boolean isPriority() {
        return isPriority;
    }

    public void setPriority(boolean priority) {
        isPriority = priority;
    }
}
