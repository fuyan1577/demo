package com.demo.graph;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ivan on 2015/5/15.
 * ͼ�нڵ�
 */
public class GraphNode {
    //�ڵ�ֵ
    private String value;
    //��̽ڵ�
    private List<GraphNode> nextNodeList;
    //�Ըýڵ㿪ͷ��������·��2����·���޻���·����Ҷ�ӽڵ��β���л���·���������β
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
