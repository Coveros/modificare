package finder;

import java.util.*;
 
class TestNode implements Node {
    private String name;

    private List outgoing = new ArrayList();

    public TestNode(String name) {
        this.name = name;
    }

    public void sendTo(TestNode n) {
        outgoing.add(n);
    }

    @Override
    public String toString() {
        return name;
    }

    public int compareTo(Object o) {
        TestNode n = (TestNode) o;
        return name.compareTo(n.name);
    }

    public List getOutgoing() {
        return outgoing;
    }
}