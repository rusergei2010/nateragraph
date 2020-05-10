package com.natera.graph.common;

import java.util.LinkedList;
import java.util.Set;
import java.util.function.BiFunction;

import org.springframework.util.CollectionUtils;

import com.natera.graph.exception.NotUniqueVertexException;
import com.natera.graph.exception.NotVertexFoundException;
import com.natera.graph.model.Edge;
import com.natera.graph.model.TYPE;
import com.natera.graph.model.Vertex;
import com.natera.graph.searchfunction.DirectedPathSearchFunction;

public class DirectedGraph extends Graph {

    private static final TYPE type = TYPE.DIRECTED;

    public DirectedGraph() {
    }

    @Override public IGraphBuilder builder() {
        return new GraphBuilder(this);
    }

    /**
     * Register new record of Vertex without Edges
     *
     * @param vertex
     */
    void addVertex(final Vertex vertex) {
        final Set<Edge> edges = vertexMap.get(vertex); // get existing edges for given vertex

        if (CollectionUtils.isEmpty(edges)) { // no edges for given vertex yet
            vertexMap.put(vertex, vertex.getEdges());  // Empty Set
        } else {
            throw new IllegalStateException("Trying to add Vertex after Edges are added. Wrong workflow.");
        }
    }

    private void addFromVertex(final Vertex vertex, final Edge edge) {
        vertex.addEdge(edge);
        vertexMap.put(vertex, vertex.getEdges());  // create new set of edges for a vertex (direction is important)
    }

    /**
     * Register edges by putting them into both FROM vertex in case of directed graph
     *
     * @param edge
     */
     void addEdge(final Edge edge) {
        addFromVertex(edge.getVertexA(), edge); // get Vertex From
//        addVertex(edge.getVertexB()); // get Vertex To
    }

    private static class GraphBuilder implements IGraphBuilder {

        private DirectedGraph directedGraph;

        private BiFunction<Vertex, Vertex, LinkedList<Edge>> pathSearchFunction = DirectedPathSearchFunction::getPath;

        public GraphBuilder(final DirectedGraph directedGraph) {
            this.directedGraph = directedGraph;
        }

        /**
         * How to create and persist a new vertex
         *
         * @param name - unique vertex name
         * @return new vertex
         * throws (@IllegalStateException) if found
         */
        @Override public Vertex addVertex(final String name) {
            if (directedGraph.findVertexByName(name)) {
                throw new NotUniqueVertexException("Found existing vertex. Vertex should have unique name: " + name);
            }
            final Vertex vertex = Vertex.of(name);
            directedGraph.addVertex(vertex);
            return vertex;
        }

        /**
         * How to create edge
         *
         * @param from vertex one (from)
         * @param to vertex two (to)
         */
        @Override public void addEdge(final Vertex from, final Vertex to) {
            if (!directedGraph.findVertexByName(from.getName())) {
                throw new NotVertexFoundException("No vertex found while adding edge:" + from);
            }
            if (!directedGraph.findVertexByName(to.getName())) {
                throw new NotVertexFoundException("No vertex found while adding edge:" + to);
            }
            directedGraph.addEdge(Edge.of(type, from, to));
        }

        @Override public LinkedList<Edge> getPath(final Vertex from, final Vertex to) {
            return pathSearchFunction.apply(from, to);
        }

        @Override public void setCustomSearchFunction(BiFunction<Vertex, Vertex, LinkedList<Edge>> pathSearchFunction) {
            this.pathSearchFunction = pathSearchFunction;
        }
    }
}
