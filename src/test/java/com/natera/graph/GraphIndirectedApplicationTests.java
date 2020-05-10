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
class GraphIndirectedApplicationTests {

    @Autowired
    @Qualifier(value = "UndirectedGraph")
    private Graph undirectedGraph;

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
        IGraphBuilder builder = undirectedGraph.builder();

        Assertions.assertThrows(NotUniqueVertexException.class, () -> {
            Vertex vertex_1 = builder.addVertex("1");
            Vertex vertex_2 = builder.addVertex("1");
        });
    }

    /**
     * 1-2-3-4-5
     * Find: 1->5
     */
    @Test
    public void testUndirectedGraph_1() {
        IGraphBuilder builder = undirectedGraph.builder();
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

        assertEquals("1-2-3-4-5", printNonEmptyPath(path));
//        System.out.println(printNonEmptyPath(path));
    }


    /**
     *     3
     *   /  \
     * 1-    4 - 5
     *   \  /
     *    7
     *
     *    Find: 1->5
     */
    @Test
    public void testUndirectedGraph_2() {
        IGraphBuilder builder = undirectedGraph.builder();
        Vertex vertex_1 = builder.addVertex("1");
        Vertex vertex_3 = builder.addVertex("3");
        Vertex vertex_7 = builder.addVertex("7");
        Vertex vertex_4 = builder.addVertex("4");
        Vertex vertex_5 = builder.addVertex("5");

        builder.addEdge(vertex_1, vertex_3);
        builder.addEdge(vertex_1, vertex_7);
        builder.addEdge(vertex_3, vertex_4);
        builder.addEdge(vertex_7, vertex_4);
        builder.addEdge(vertex_4, vertex_5);

        //builder.setCustomSearchFunction();
        final LinkedList<Edge> path = builder.getPath(vertex_1, vertex_5);

        assertEquals("1-3-4-5", printNonEmptyPath(path));
//        System.out.println(printNonEmptyPath(path));
    }


    /**
     *     3
     *   /  \
     * 1-    4 - 5
     *   \  /
     *    7
     *
     *   Find: 7 -> 3
     */
    @Test
    public void testUndirectedGraph_3() {
        IGraphBuilder builder = undirectedGraph.builder();
        Vertex vertex_1 = builder.addVertex("1");
        Vertex vertex_3 = builder.addVertex("3");
        Vertex vertex_7 = builder.addVertex("7");
        Vertex vertex_4 = builder.addVertex("4");
        Vertex vertex_5 = builder.addVertex("5");

        builder.addEdge(vertex_1, vertex_3);
        builder.addEdge(vertex_1, vertex_7);
        builder.addEdge(vertex_3, vertex_4);
        builder.addEdge(vertex_7, vertex_4);
        builder.addEdge(vertex_4, vertex_5);

        //builder.setCustomSearchFunction();
        final LinkedList<Edge> path = builder.getPath(vertex_7, vertex_3);

        assertEquals("17-13", printNonEmptyPathEdge(path)); // Correct. [x-y] = [y->x] for undirected
//        System.out.println(printNonEmptyPath(path));
    }
    /**
     *     3
     *   /  \
     *  6 - 4 - 5
     *   \
     *    7
     *
     *   Find: 5 -> 7
     */
    @Test
    public void testUndirectedGraph_4() {
        IGraphBuilder builder = undirectedGraph.builder();
        Vertex vertex_6 = builder.addVertex("6");
        Vertex vertex_3 = builder.addVertex("3");
        Vertex vertex_7 = builder.addVertex("7");
        Vertex vertex_4 = builder.addVertex("4");
        Vertex vertex_5 = builder.addVertex("5");

        builder.addEdge(vertex_6, vertex_7);
        builder.addEdge(vertex_6, vertex_3);
        builder.addEdge(vertex_6, vertex_4);
        builder.addEdge(vertex_3, vertex_4);
        builder.addEdge(vertex_4, vertex_5);

        //builder.setCustomSearchFunction();
        final LinkedList<Edge> path = builder.getPath(vertex_5, vertex_7);

        assertEquals("45-34-63-67", printNonEmptyPathEdge(path)); // Correct. [x-y] = [y->x] for undirected
//        System.out.println(printNonEmptyPath(path));
    } /**
     *     3
     *      \
     *  6   4 - 5
     *   \
     *    7
     *
     *   Find: 5 -> 7 (Broken path)
     */
    @Test
    public void testUndirectedGraph_5() {
        IGraphBuilder builder = undirectedGraph.builder();
        Vertex vertex_6 = builder.addVertex("6");
        Vertex vertex_3 = builder.addVertex("3");
        Vertex vertex_7 = builder.addVertex("7");
        Vertex vertex_4 = builder.addVertex("4");
        Vertex vertex_5 = builder.addVertex("5");

        builder.addEdge(vertex_6, vertex_7);
        builder.addEdge(vertex_3, vertex_4);
        builder.addEdge(vertex_4, vertex_5);

        //builder.setCustomSearchFunction();
        final LinkedList<Edge> path = builder.getPath(vertex_5, vertex_7);

        assertEquals(null, path); // No Path Found
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
        return edge.vertexA.getName() + "-";
    }


    public String getArrowlessStr(Edge edge) {
        return edge.vertexA.getName()  + edge.vertexB.getName();
    }

    public String getTipVertex(Edge edge) {
        return edge.vertexB.getName();
    }
}
