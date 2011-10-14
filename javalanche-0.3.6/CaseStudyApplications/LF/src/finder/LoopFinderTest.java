package finder;

import java.util.ArrayList;
import java.util.List;

import junit.framework.TestSuite;
import junit.framework.TestCase;
import junit.framework.Test;

public class LoopFinderTest extends TestCase {

    public static TestSuite suite() {
        /* TestSuite suite = new TestSuite();
        suite.addTestSuite(LoopFinderTest.class);
        */
    	TestSuite suite = new TestSuite(LoopFinderTest.class);
        return suite;
    }	
    

    public void testSimpleLoop_Trivial_NoLoop() {
        TestNode n1 = new TestNode("1");

        List nodesInLoop = LoopFinder.findSimpleLoop(n1);

        assertEquals(0, nodesInLoop.size());
    }

    public void testSimpleLoop_NoLoop() {
        TestNode n1 = new TestNode("1");
        TestNode n2 = new TestNode("2");

        n1.sendTo(n2);

        List nodesInLoop = LoopFinder.findSimpleLoop(n1);

        assertEquals(0, nodesInLoop.size());
    }

    public void testSimpleLoop_TightLoop() {
        // setup
        TestNode n1 = new TestNode("1");
        TestNode n2 = new TestNode("2");

        n1.sendTo(n2);
        n2.sendTo(n1);

        // execute
        List nodesInLoop = LoopFinder.findSimpleLoop(n1);

        // assert
        assertEquals(2, nodesInLoop.size());
        assertEquals(n1, nodesInLoop.get(0));
        assertEquals(n2, nodesInLoop.get(1));
    }

    public void testSimpleLoop_TightLoop_WithTail_OnProcessedFacility() {
        // setup
        TestNode n1 = new TestNode("1");
        TestNode n2 = new TestNode("2");
        TestNode n3 = new TestNode("3");

        n1.sendTo(n2);
        n1.sendTo(n3);
        n2.sendTo(n1);

        // execute
        List nodesInLoop = LoopFinder.findSimpleLoop(n1);

        // assert
        assertEquals(2, nodesInLoop.size());
        assertEquals(n1, nodesInLoop.get(0));
        assertEquals(n2, nodesInLoop.get(1));
    }

    public void testSimpleLoop_TightLoop_WithTail_OnOtherFacility() {
        // setup
        TestNode n1 = new TestNode("1");
        TestNode n2 = new TestNode("2");
        TestNode n3 = new TestNode("3");

        n1.sendTo(n2);
        n2.sendTo(n3);

        n2.sendTo(n1);

        // execute
        List nodesInLoop = LoopFinder.findSimpleLoop(n1);

        // assert
        assertEquals(2, nodesInLoop.size());
        assertEquals(n1, nodesInLoop.get(0));
        assertEquals(n2, nodesInLoop.get(1));
    }

    public void testSimpleLoop_2TightLoops() {
        // setup
        TestNode n1 = new TestNode("1");
        TestNode n2 = new TestNode("2");
        TestNode n3 = new TestNode("3");
        new TestNode("4");

        n1.sendTo(n2);
        n2.sendTo(n1);

        n1.sendTo(n3);
        n3.sendTo(n1);

        // execute
        List nodesInLoop = LoopFinder.findSimpleLoop(n1);

        // assert
        assertEquals(2, nodesInLoop.size());
        assertEquals(n1, nodesInLoop.get(0));
        assertEquals(n2, nodesInLoop.get(1));
    }

    public void testSimpleLoop_LargerLoop() {
        // setup
        TestNode n1 = new TestNode("1");
        TestNode n2 = new TestNode("2");
        TestNode n3 = new TestNode("3");

        n1.sendTo(n2);
        n2.sendTo(n3);
        n3.sendTo(n1);

        // execute
        List nodesInLoop = LoopFinder.findSimpleLoop(n1);

        // setup
        assertEquals(3, nodesInLoop.size());
        assertEquals(n1, nodesInLoop.get(0));
        assertEquals(n2, nodesInLoop.get(1));
        assertEquals(n3, nodesInLoop.get(2));
    }

    public void test_NoLoop_ConnectedToALoopDownstream() {
        // setup
        TestNode n1 = new TestNode("1");
        TestNode n2 = new TestNode("2");
        TestNode n3 = new TestNode("3");

        n1.sendTo(n2);
        n2.sendTo(n3);
        n3.sendTo(n2);

        // execute
        List nodesInLoop = LoopFinder.findSimpleLoop(n1);

        // setup
        assertEquals(0, nodesInLoop.size());
    }

    public void test_NoLoop_ConnectedToALoopUpstream() {
        // setup
        TestNode n1 = new TestNode("1");
        TestNode n2 = new TestNode("2");
        TestNode n3 = new TestNode("3");

        n2.sendTo(n1);
        n3.sendTo(n2);
        n2.sendTo(n3);

        // execute
        List nodesInLoop = LoopFinder.findSimpleLoop(n1);

        // setup
        assertEquals(0, nodesInLoop.size());
    }

    public void test_Loop_MainLoopContainsNestedLoop() {
        // setup
        TestNode n1 = new TestNode("1");
        TestNode n2 = new TestNode("2");
        TestNode n3 = new TestNode("3");

        n1.sendTo(n3);
        n2.sendTo(n1);
        n3.sendTo(n2);
        n2.sendTo(n3);

        // execute
        List nodesInLoop = LoopFinder.findSimpleLoop(n1);

        // assert
        assertEquals(3, nodesInLoop.size());
        assertEquals(n1, nodesInLoop.get(0));
        assertEquals(n3, nodesInLoop.get(1));
        assertEquals(n2, nodesInLoop.get(2));
    }

    public void test_Loop_MainLoopContainsNestedLoop_WithExcludedNodes() {
        // setup
        TestNode n1 = new TestNode("1");
        TestNode n2 = new TestNode("2");
        TestNode n3 = new TestNode("3");
        TestNode n4 = new TestNode("4");

        n1.sendTo(n4);
        n4.sendTo(n2);
        n2.sendTo(n3);
        n2.sendTo(n1);
        n4.sendTo(n4);

        // execute
        List nodesInLoop = LoopFinder.findSimpleLoop(n1);

        // assert
        assertEquals(3, nodesInLoop.size());
        assertEquals(n1, nodesInLoop.get(0));
        assertEquals(n4, nodesInLoop.get(1));
        assertEquals(n2, nodesInLoop.get(2));
    }

    public void testTwoParallelStreams_WithOneComplicatedNonLoop_AndOneLoop() {
        // setup
        TestNode n1 = new TestNode("1");
        TestNode n2 = new TestNode("2");
        TestNode n3 = new TestNode("3");
        TestNode n4 = new TestNode("4");
        TestNode n5 = new TestNode("5");
        TestNode n6 = new TestNode("6");
        TestNode n7 = new TestNode("7");

        n1.sendTo(n2);
        n2.sendTo(n3);
        n2.sendTo(n4);
        n3.sendTo(n5);
        n5.sendTo(n3);
        n4.sendTo(n5);
        n1.sendTo(n6);
        n6.sendTo(n7);
        n7.sendTo(n1);

        // execute
        List nodesInLoop = LoopFinder.findSimpleLoop(n1);

        // assert
        assertEquals("three TestNodes", 3, nodesInLoop.size());
        assertEquals(n1, nodesInLoop.get(0));
        assertEquals(n6, nodesInLoop.get(1));
        assertEquals(n7, nodesInLoop.get(2));
    }

    public void testSimpleLoop_Pyramid_NoLoops() {
        // setup
        TestNode n1 = new TestNode("1");
        TestNode n2 = new TestNode("2");
        TestNode n3 = new TestNode("3");
        TestNode n4 = new TestNode("4");
        TestNode n5 = new TestNode("5");

        n1.sendTo(n2);
        n2.sendTo(n3);

        n1.sendTo(n4);
        n4.sendTo(n5);

        n4.sendTo(n2);
        n5.sendTo(n3);

        // execute
        List nodesInLoop = LoopFinder.findSimpleLoop(n1);

        // assert
        assertEquals(0, nodesInLoop.size());
    }

    public static void main(String[] args) 
    {
    	TestSuite suite = suite();
    	
    	if(args.length == 0)
    	{
    		for(int i = 0; i < suite.countTestCases();i++)
    		{
    			System.out.println(suite.testAt(i));
    			junit.textui.TestRunner.run(suite.testAt(i));
    	
    		}
    	}
    	else
    	{
    		for(int i = 0; i < args.length;i++)
    		{
    			System.out.println(suite.testAt(Integer.parseInt(args[i])));
    			junit.textui.TestRunner.run(suite.testAt(Integer.parseInt(args[i])));
    		}	
    	}
    	
    	System.out.println("Total Test Cases: "+ suite.countTestCases());
    }

}
