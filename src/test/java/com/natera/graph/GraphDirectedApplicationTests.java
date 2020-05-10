package com.natera.graph;

import java.util.LinkedList;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.event.annotation.AfterTestClass;
import org.springframework.test.context.event.annotation.BeforeTestClass;

import com.natera.graph.common.Graph;
import com.natera.graph.common.IGraphBuilder;
import com.natera.graph.exception.NotUniqueVertexException;
import com.natera.graph.model.Edge;
import com.natera.graph.model.Vertex;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class GraphDirectedApplicationTests {

    @Autowired
    @Qualifier(value = "DirectedGraph")
    private Graph directedGraph;

    @BeforeTestClass
    public void testStart(){
        System.out.println("TESTS ARE STARTED: " + getClass().getName());
    }

    @AfterTestClass
    public void testEnd(){
        System.out.println("TESTS ARE COMPLETED: " + getClass().getName());
    }

    @Test
    public void testNotUniqueVertexException() {
        IGraphBuilder builder = directedGraph.builder();

        Assertions.assertThrows(NotUniqueVertexException.class, () -> {
            Vertex vertex_1 = builder.addVertex("1");
            Vertex vertex_2 = builder.addVertex("1");
        });
    }

    /**
     * 1->2->3->4->5
     * Find: 1->5
     */
    @Test
    public void testdirectedGraph_1() {
        IGraphBuilder builder = directedGraph.builder();
        Vertex vertex_1 = builder.addVertex("1");
        Vertex vertex_2 = builder.addVertex("2");
        Vertex vertex_3 = builder.addVertex("3");
        Vertex vertex_4 = builder.addVertex("4");
        Vertex vertex_5 = builder.addVertex("5");

        builder.addEdge(vertex_1, vertex_2);
        builder.addEdge(vertex_2, vertex_3);
        builder.addEdge(vertex_3, vertex_4);
        builder.addEdge(vertex_4, vertex_5);

        //builder.setCustomSearchFunction();
        final LinkedList<Edge> path = builder.getPath(vertex_1, vertex_5);

        assertEquals("1->2->3->4->5", printNonEmptyPath(path));
//        System.out.println(printNonEmptyPath(path));
    }

     /**
     * 1->2->3->4->5
     * Find: 4->1 (Broken)
     */
    @Test
    public void testdirectedGraph_1_1() {
        IGraphBuilder builder = directedGraph.builder();
        Vertex vertex_1 = builder.addVertex("1");
        Vertex vertex_2 = builder.addVertex("2");
        Vertex vertex_3 = builder.addVertex("3");
        Vertex vertex_4 = builder.addVertex("4");
        Vertex vertex_5 = builder.addVertex("5");

        builder.addEdge(vertex_1, vertex_2);
        builder.addEdge(vertex_2, vertex_3);
        builder.addEdge(vertex_3, vertex_4);
        builder.addEdge(vertex_4, vertex_5);

        //builder.setCustomSearchFunction();
        final LinkedList<Edge> path = builder.getPath(vertex_4, vertex_1);

        assertEquals(null, path);
//        System.out.println(printNonEmptyPath(path));
    }

     /**
     * 1->2->3->4->5
     * Find: 1->4 (OK)
     */
    @Test
    public void testdirectedGraph_1_2() {
        IGraphBuilder builder = directedGraph.builder();
        Vertex vertex_1 = builder.addVertex("1");
        Vertex vertex_2 = builder.addVertex("2");
        Vertex vertex_3 = builder.addVertex("3");
        Vertex vertex_4 = builder.addVertex("4");
        Vertex vertex_5 = builder.addVertex("5");

        builder.addEdge(vertex_1, vertex_2);
        builder.addEdge(vertex_2, vertex_3);
        builder.addEdge(vertex_3, vertex_4);
        builder.addEdge(vertex_4, vertex_5);

        //builder.setCustomSearchFunction();
        final LinkedList<Edge> path = builder.getPath(vertex_1, vertex_4);

        assertEquals("1->2->3->4", printNonEmptyPath(path));
//        System.out.println(printNonEmptyPath(path));
    }


    /**
     *      3
     *    /  \
     * 1->    4 -> 5
     *    \  /
     *     7
     *
     *    Find: 7 -> 5
     */
    @Test
    public void testdirectedGraph_2() {
        IGraphBuilder builder = directedGraph.builder();
        Vertex vertex_1 = builder.addVertex("1");
        Vertex vertex_3 = builder.addVertex("3");
        Vertex vertex_7 = builder.addVertex("7");
        Vertex vertex_4 = builder.addVertex("4");
        Vertex vertex_5 = builder.addVertex("5");

        // direction is important
        builder.addEdge(vertex_1, vertex_3);
        builder.addEdge(vertex_1, vertex_7);
        builder.addEdge(vertex_3, vertex_4);
        builder.addEdge(vertex_7, vertex_4);
        builder.addEdge(vertex_4, vertex_5);

        //builder.setCustomSearchFunction();
        final LinkedList<Edge> path = builder.getPath(vertex_7, vertex_5);

        assertEquals("7->4->5", printNonEmptyPath(path));
//        System.out.println(printNonEmptyPath(path));
    }


    /**
     *      3
     *    /  \
     * 1->    4 -> 5
     *    \
     *     7
     *
     *    Find: 7 -> 5
     */
    @Test
    public void testdirectedGraph_2_1() {
        IGraphBuilder builder = directedGraph.builder();
        Vertex vertex_1 = builder.addVertex("1");
        Vertex vertex_3 = builder.addVertex("3");
        Vertex vertex_7 = builder.addVertex("7");
        Vertex vertex_4 = builder.addVertex("4");
        Vertex vertex_5 = builder.addVertex("5");

        // direction is important
        builder.addEdge(vertex_1, vertex_3);
        builder.addEdge(vertex_1, vertex_7);
        builder.addEdge(vertex_3, vertex_4);
//        builder.addEdge(vertex_7, vertex_4); // Comment out
        builder.addEdge(vertex_4, vertex_5);

        //builder.setCustomSearchFunction();
        final LinkedList<Edge> path = builder.getPath(vertex_7, vertex_5);

        assertEquals(null, path); // Broken because directed 1->7
//        System.out.println(printNonEmptyPath(path));
//        System.out.println(printNonEmptyPath(path));
    }

    /**
     *      3
     *    /  \
     * 1->    4 -> 5
     *    \
     *     7
     *
     *    Find: 7 -> 5
     */
    @Test
    public void testdirectedGraph_2_3() {
        IGraphBuilder builder = directedGraph.builder();
        Vertex vertex_1 = builder.addVertex("1");
        Vertex vertex_3 = builder.addVertex("3");
        Vertex vertex_7 = builder.addVertex("7");
        Vertex vertex_4 = builder.addVertex("4");
        Vertex vertex_5 = builder.addVertex("5");

        // direction is important
        builder.addEdge(vertex_1, vertex_3);
//        builder.addEdge(vertex_1, vertex_7); // recover
        builder.addEdge(vertex_7, vertex_1);
        builder.addEdge(vertex_3, vertex_4);
//        builder.addEdge(vertex_7, vertex_4); // Comment out
        builder.addEdge(vertex_4, vertex_5);

        //builder.setCustomSearchFunction();
        final LinkedList<Edge> path = builder.getPath(vertex_7, vertex_5);

        assertEquals("7->1->3->4->5", printNonEmptyPath(path)); // Broken because directed 1->7
//        System.out.println(printNonEmptyPath(path));
    }


    private String printNonEmptyPath(final LinkedList<Edge> path) {
        String s = path.stream().map(this::getArrowStr).reduce((a, b) -> a + b)
                .orElseThrow(() -> new RuntimeException("Path not found"));

        return s + getTipVertex(path.getLast());
    }

    private String printNonEmptyPathEdge(final LinkedList<Edge> path) {
        String s = path.stream().map(this::getArrowlessStr).reduce((a, b) -> a + "-" + b)
                .orElseThrow(() -> new RuntimeException("Path not found"));

        return s;
    }

    public String getArrowStr(Edge edge) {
        return edge.vertexA.getName() + "->";
    }


    public String getArrowlessStr(Edge edge) {
        return edge.vertexA.getName()  + edge.vertexB.getName();
    }

    public String getTipVertex(Edge edge) {
        return edge.vertexB.getName();
    }
}
