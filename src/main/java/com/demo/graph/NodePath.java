package com.demo.graph;

import java.util.List;

/**
 * Created by ivan on 2015/5/15.
 */
public class NodePath {
    private  boolean isCircle = false;
    private List<GraphNode> graphNodeList;

    public boolean isCircle() {
        return isCircle;
    }

    public void setCircle(boolean isCircle) {
        this.isCircle = isCircle;
    }

    public List<GraphNode> getGraphNodeList() {
        return graphNodeList;
    }

    public void setGraphNodeList(List<GraphNode> graphNodeList) {
        this.graphNodeList = graphNodeList;
    }
}
