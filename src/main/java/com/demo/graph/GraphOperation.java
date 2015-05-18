package com.demo.graph;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.soap.Node;
import java.io.File;
import java.util.*;

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
        String dataFile = "graph/sampleRel5.txt";
        List<String> nodeRelations = FileUtils.readLines(new File(dataFile));
        for (String nodeRelation : nodeRelations) {
            String nodes[] = StringUtils.splitByWholeSeparator(nodeRelation, ">");

            GraphNode node = getNodeByValue(nodes[0]);
            if (nodes.length == 2) {
                node.getNextNodeList().add(getNodeByValue(nodes[1]));
            }
        }

        for (String nodeValue : graphNodeMap.keySet()) {
            LOG.debug(this.getClass().getName() + ":" + graphNodeMap.get(nodeValue));
        }
    }


    /**
     * @throws Exception 查找节点的链路。为提高查找效率，如果一个节点已访问其所有子节点，则不再查找以该节点为起点的链路。因为以该节点为起点的链路已包含在其他链路中。
     */
    public void findAllNodePaths() throws Exception {
        //是否已访问节点的所有的子节点，KEY为NODE的value
        Map<String, Boolean> hasAccessAllSubNodeFlag = new HashMap<String, Boolean>();
        for (GraphNode node : graphNodeMap.values()) {
            NodePath nodePath = new NodePath();
            nodePath.addNode(node);

            //如果还没有访问节点的所有子节点，则继续查找
            if (!hasAccessAllSubNodeFlag.containsKey(node.getValue())) {
                findPathForSpecificNode(node, node, nodePath, hasAccessAllSubNodeFlag);
            }
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
            result.getGraphNodeMap().put(targetGraphNode.getValue(), targetGraphNode);
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
    private void findPathForSpecificNode(GraphNode currentNode, GraphNode startNode, NodePath nodePath, Map<String, Boolean> hasAccessAllSubNodeFlag) throws Exception {
        //叶子节点，寻找结束。记录链路
        if (currentNode.getNextNodeList().size() == 0) {
            nodePath.setCircle(Boolean.FALSE);
            hasAccessAllSubNodeFlag.put(currentNode.getValue(), Boolean.TRUE);

            recordNodePath(nodePath, startNode);
            return;
        } else {
            //依次循环依赖的节点
            for (GraphNode subNode : currentNode.getNextNodeList()) {
                NodePath nodePathForSubNode = deepCopyNodePath(nodePath);
                //将依赖的节点加入到链路中
                Boolean addNodeResult = nodePathForSubNode.addNode(subNode);
                //添加不成功，说明链路中出现了环
                if (!addNodeResult) {
                    recordNodePath(nodePathForSubNode, startNode);
                } else {
                    //递归找子节点的链路
                    findPathForSpecificNode(subNode, startNode, nodePathForSubNode, hasAccessAllSubNodeFlag);
                }
            }

            //所有子节点处理完毕，将该节点的值加到hasAccessAllSubNodeFlag中
            hasAccessAllSubNodeFlag.put(currentNode.getValue(), Boolean.TRUE);
            //已经访问所有的叶子节点，这条PATH不是一条完整的PATH，没有必要记录
            nodePath = null;
        }
    }

    public static void main(String args[]) throws Exception {
        GraphOperation graphOperation = new GraphOperation();
        graphOperation.initGraphNodeData();
        graphOperation.findAllNodePaths();

        for (String nodeValue : graphOperation.getGraphNodePathMap().keySet()) {
            LOG.debug(GraphOperation.class.getName() + ", Node path is from Node::" + nodeValue);
            List<NodePath> nodePaths = graphOperation.getGraphNodePathMap().get(nodeValue);

            List<NodePath> circleNodePathList = new ArrayList<NodePath>();
            for (NodePath nodePath : nodePaths) {
                //只包括环路节点的PATH
                NodePath circleNodePath = new NodePath();
                //只输出有环链路
                if (nodePath.isCircle()) {
                    circleNodePathList.add(circleNodePath);
                    Boolean findStartNodeFlag = Boolean.FALSE;
                    for (GraphNode graphNode : nodePath.getGraphNodeList()) {
                        //判断节点是否是环的起始节点
                        if (graphNode.getValue().equals(nodePath.getStartCircleNode().getValue())) {
                            findStartNodeFlag = Boolean.TRUE;
                        }
                        //将环路起始节点及其后的及节点加入到circleNodePath
                        if (findStartNodeFlag) {
                            circleNodePath.getGraphNodeList().add(graphNode);
                        }
                    }
                    LOG.debug(GraphOperation.class.getName() + ": Node PATH is:  " + nodePath);
                }
            }

            for (NodePath nodePath : circleNodePathList) {
                LOG.debug(GraphOperation.class.getName() + ": Circle PATH is:  " + nodePath);
            }
        }
    }
}
