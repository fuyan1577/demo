package com.demo.graph;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ivan on 2015/5/15.
 * 图中节点
 */
public class GraphNode {
    //节点值
    private String value;
    //后继节点
    private List<GraphNode> nextNodeList;
    //以该节点开头的所有链路。2种链路：无环链路，以叶子节点结尾；有环链路，以自身结尾
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

    public List<NodePath> getNodePathList() {
        return nodePathList;
    }

    public void setNodePathList(List<NodePath> nodePathList) {
        this.nodePathList = nodePathList;
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
