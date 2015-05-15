package com.demo.graph;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ivan on 2015/5/15.
 * 图操作。循环每个节点，按照深度优先的算法遍历图。
 * 可用于计算图中是否有环
 */
public class GraphOperation {
    private static final Logger LOG = LoggerFactory.getLogger(GraphOperation.class);

    //图中所有节点
    private Map<String, GraphNode> graphNodeMap = new HashMap<String, GraphNode>();

    //图中节点相关的链路
    private Map<String, List<NodePath>> graphNodePathMap = new HashMap<String, List<NodePath>>();

    public Map<String, GraphNode> getGraphNodeMap() {
        return graphNodeMap;
    }

    public void setGraphNodeMap(Map<String, GraphNode> graphNodeMap) {
        this.graphNodeMap = graphNodeMap;
    }

    public Map<String, List<NodePath>> getGraphNodePathMap() {
        return graphNodePathMap;
    }

    public void setGraphNodePathMap(Map<String, List<NodePath>> graphNodePathMap) {
        this.graphNodePathMap = graphNodePathMap;
    }


    /**
     * @param nodeValue 节点值
     * @return 节点
     * 根据节点值得到节点，如果获取不到节点，则创建新节点
     */
    private GraphNode getNodeByValue(String nodeValue) {
        nodeValue = StringUtils.trim(nodeValue);
        GraphNode result = graphNodeMap.get(nodeValue);
        if (result == null) {
            result = new GraphNode();
            result.setValue(nodeValue);

            graphNodeMap.put(nodeValue, result);
        }

        return result;
    }

    /**
     * @throws Exception 初始化图中节点及节点关系
     */
    public void initGraphNodeData() throws Exception {
        //存放节点关系的文件，每行存储一条依赖关系，如 A > B表示A依赖B
        String dataFile = "graph/sampleRel4.txt";
        List<String> nodeRelations = FileUtils.readLines(new File(dataFile));
        for (String nodeRelation : nodeRelations) {
            String nodes[] = StringUtils.splitByWholeSeparator(nodeRelation, ">");

            GraphNode node = getNodeByValue(nodes[0]);
            node.getNextNodeList().add(getNodeByValue(nodes[1]));
        }

        for (String nodeValue : graphNodeMap.keySet()) {
            LOG.debug(this.getClass().getName() + ":" + graphNodeMap.get(nodeValue));
        }
    }


    /**
     * @throws Exception 查找节点的链路
     */
    public void findAllNodePaths() throws Exception {
        for (GraphNode node : graphNodeMap.values()) {
            NodePath nodePath = new NodePath();
            nodePath.getGraphNodeList().add(node);
            findPathForSpecificNode(node, node, nodePath);
        }
    }

    /**
     * @param source
     * @return 深拷贝source对象，返回NodePath
     */
    private NodePath deepCopyNodePath(NodePath source) {
        NodePath result = new NodePath();

        for (GraphNode graphNode : source.getGraphNodeList()) {
            GraphNode targetGraphNode = new GraphNode();
            targetGraphNode.setValue(graphNode.getValue());
            result.getGraphNodeList().add(targetGraphNode);
        }

        return result;
    }

    /**
     * @param nodePath  查询结束的node path, 一条node path结束可能有2种方式。一种是找到叶子节点，另一种是找到自身节点
     * @param startNode 开始节点
     *                  将结束的node path记录下来
     */
    private void recordNodePath(NodePath nodePath, GraphNode startNode) {
        String startNodeValue = startNode.getValue();
        List<NodePath> nodePaths = graphNodePathMap.get(startNodeValue);
        if (nodePaths == null) {
            nodePaths = new ArrayList<NodePath>();
            graphNodePathMap.put(startNodeValue, nodePaths);
        }

        nodePaths.add(nodePath);
    }


    /**
     * @param currentNode 当前节点
     * @param startNode   开始节点
     * @param nodePath    节点的链路
     * @throws Exception 用深度优先的算法寻找以{startNode}节点的链路
     */
    private void findPathForSpecificNode(GraphNode currentNode, GraphNode startNode, NodePath nodePath) throws Exception {
        //叶子节点，寻找结束。记录链路
        if (currentNode.getNextNodeList().size() == 0) {
            nodePath.setCircle(Boolean.FALSE);

            recordNodePath(nodePath, startNode);
            return;
        }

        //依次循环依赖的节点
        for (GraphNode dependedNode : currentNode.getNextNodeList()) {
            NodePath dependedNodePath = deepCopyNodePath(nodePath);
            //将依赖的节点加入到链路中
            dependedNodePath.getGraphNodeList().add(dependedNode);

            //如果又找到了自身节点，说明有环，结束寻找过程并记录链路
            if (dependedNode.getValue().equals(startNode.getValue())) {
                dependedNodePath.setCircle(Boolean.TRUE);
                recordNodePath(dependedNodePath, startNode);
                return;
            } else {
                //递归找子节点的链路
                findPathForSpecificNode(dependedNode, startNode, dependedNodePath);
            }
        }

    }

    public static void main(String args[]) throws Exception {
        GraphOperation graphOperation = new GraphOperation();
        graphOperation.initGraphNodeData();
        graphOperation.findAllNodePaths();

        for (String nodeValue : graphOperation.getGraphNodePathMap().keySet()) {
            LOG.debug(GraphOperation.class.getName() + ", Node path is from Node::" + nodeValue);
            List<NodePath> nodePaths = graphOperation.getGraphNodePathMap().get(nodeValue);
            for (NodePath nodePath : nodePaths) {
                LOG.debug(GraphOperation.class.getName() + ":  " + nodePath);
            }
        }
    }
}
