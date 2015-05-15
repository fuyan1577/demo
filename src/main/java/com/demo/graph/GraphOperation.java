package com.demo.graph;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ivan on 2015/5/15.
 */
public class GraphOperation {
    private Map<String, GraphNode> graphNodeMap = new HashMap<String, GraphNode>();

    public void initGraphNodeData() throws  Exception {
        String dataFile = "graph/sampleRel.txt";
        List<String> nodeRelations = FileUtils.readLines(new File(dataFile));
        for (String nodeRelation: nodeRelations) {
            String nodes[] = StringUtils.splitByWholeSeparator(nodeRelation, ">");

            String nodeValue = StringUtils.trim(nodes[0]);
            GraphNode graphNode = graphNodeMap.get(nodeValue);
            if(graphNode == null) {
                graphNode = new GraphNode();
                graphNode.setValue(nodeValue);

                graphNodeMap.put(graphNode.getValue(), graphNode);
            }

            String dependedNodeValue = StringUtils.trim(nodes[1]);
            GraphNode dependedNode = graphNodeMap.get(dependedNodeValue);
            if(dependedNode == null) {
                dependedNode = new GraphNode();

                dependedNode.setValue(dependedNodeValue);
                graphNodeMap.put(dependedNode.getValue(), dependedNode);
            }

            graphNode.getNextNodeList().add(dependedNode);
        }

        System.out.println("graph data is:");
        for(String nodeValue: graphNodeMap.keySet()) {
            System.out.println(graphNodeMap.get(nodeValue));
        }
    }

    public void findAllNodePaths() {
        Map<String, Boolean> hasVisitedMap = new HashMap<String, Boolean>();
        for(GraphNode node: graphNodeMap.values()) {
            List<GraphNode> dependedNodes = node.getNextNodeList();

            hasVisitedMap.put(node.getValue(), Boolean.TRUE);
            if (hasVisitedMap.get(node))

        }
    }

    public static void main(String args[]) throws Exception {
        GraphOperation graphOperation = new GraphOperation();
        graphOperation.initGraphNodeData();
    }
}
