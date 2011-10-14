package finder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;


public class LoopFinder {

    public static List findSimpleLoop(Node startingNode) {
        Set visitedNodes = new HashSet();
        List loop = LoopFinder.findSimpleLoop(startingNode, startingNode, visitedNodes, true);
        if (loop == null) {
            return new ArrayList();
        }

        return loop;
    }

    private static List findSimpleLoop(Node startingNode, Node currentNode, Set visitedNodes, boolean firstCall) {
        if (LoopFinder.returnedToStartingNode(startingNode, currentNode, firstCall)) {
            return new LinkedList();
        }

        if (LoopFinder.alreadyVisitedHere(visitedNodes, currentNode)) {
            return null;
        }
        visitedNodes.add(currentNode);

        List outgoing = currentNode.getOutgoing();
        Collections.sort(outgoing);
        for (Iterator iter = outgoing.iterator(); iter.hasNext();) {
            Node outgoingNode = (Node) iter.next();
            List loop = LoopFinder.findSimpleLoop(startingNode, outgoingNode, visitedNodes, false);
            if (loop != null) {
                loop.add(0, currentNode);
                return loop;
            }
        }

        return null;
    }

    private static boolean alreadyVisitedHere(Set visitedNodes, Node currentNode) {
        return visitedNodes.contains(currentNode);
    }

    private static boolean returnedToStartingNode(Node startingNode, Node currentNode, boolean first) {
        return !first && startingNode.equals(currentNode);
    }
}
