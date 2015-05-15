package com.demo.graph;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ivan on 2015/5/15.
 */
public class GraphNode {
    private String value;
    private List<GraphNode> nextNodeList;

    private List<NodePath> nodePathList;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }


    public List<GraphNode> getNextNodeList() {
        return nextNodeList;
    }

    public void setNextNodeList(List<GraphNode> nextNodeList) {
        this.nextNodeList = nextNodeList;
    }

    public GraphNode() {
        nextNodeList = new ArrayList<GraphNode>();
        nodePathList = new ArrayList<NodePath>();
    }

    @Override
    public String toString() {
        String result = value + " > ";
        for (GraphNode node : nextNodeList) {
            result += node.getValue() + ", ";
        }

        if (nextNodeList.size() > 0) {
            result = result.substring(0, result.length() - 2);
        }

        return result;
    }
}
