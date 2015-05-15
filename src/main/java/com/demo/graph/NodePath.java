package com.demo.graph;


import org.apache.commons.collections.CollectionUtils;

import javax.management.NotificationEmitter;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ivan on 2015/5/15.
 * 记录节点的链路，比如A > B > C
 */
public class NodePath {
    //链路是否有环
    private boolean isCircle = false;
    //链路中的节点
    private List<GraphNode> graphNodeList;

    public NodePath() {
        graphNodeList = new ArrayList<GraphNode>();
    }

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

    public String toString() {
        String nodePath = "";

        for (GraphNode graphNode : graphNodeList) {
            nodePath += graphNode.getValue() + ", ";
        }

        if (graphNodeList.size() > 0) {
            nodePath = nodePath.substring(0, nodePath.length() - 2);
        }

        return nodePath;
    }
}
