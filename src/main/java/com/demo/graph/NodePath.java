package com.demo.graph;


import org.apache.commons.collections.CollectionUtils;

import javax.management.NotificationEmitter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ivan on 2015/5/15.
 * 记录节点的链路，比如A > B > C
 */
public class NodePath {
    //链路是否有环
    private Boolean isCircle = Boolean.FALSE;
    //链路中的节点
    private List<GraphNode> graphNodeList;

    //链路中环的开始节点，如D > A > B > C > A，则节点A是环的开始节点
    private GraphNode startCircleNode;

    //记录链路中的节点，其中KEY为Node Value，便于判断链路中是否有环
    Map<String, GraphNode> graphNodeMap;

    public NodePath() {
        graphNodeList = new ArrayList<GraphNode>();
        graphNodeMap = new HashMap<String, GraphNode>();
    }

    public Boolean isCircle() {
        return isCircle;
    }

    public void setCircle(Boolean isCircle) {
        this.isCircle = isCircle;
    }

    public GraphNode getStartCircleNode() {
        return startCircleNode;
    }

    public void setStartCircleNode(GraphNode startCircleNode) {
        this.startCircleNode = startCircleNode;
    }

    public List<GraphNode> getGraphNodeList() {
        return graphNodeList;
    }

    public void setGraphNodeList(List<GraphNode> graphNodeList) {
        this.graphNodeList = graphNodeList;
    }

    public Map<String, GraphNode> getGraphNodeMap() {
        return graphNodeMap;
    }

    public void setGraphNodeMap(Map<String, GraphNode> graphNodeMap) {
        this.graphNodeMap = graphNodeMap;
    }

    //往链路里添加节点
    public Boolean addNode(GraphNode node) {
        Boolean result = Boolean.TRUE;
        this.getGraphNodeList().add(node);
        //如果链路里已经包含了该节点，则说明出现了环
        if(graphNodeMap.containsKey(node.getValue())) {
            startCircleNode = node;
            isCircle = Boolean.TRUE;
            result = Boolean.FALSE;
        } else {
            graphNodeMap.put(node.getValue(), node);
        }
        return result;
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
