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
import com.natera.graph.searchfunction.UnidirectedPathSearchFunction;

public class UndirectedGraph extends Graph {

    private static final TYPE type = TYPE.UNDIRECTED;

    public UndirectedGraph() {
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

    private void addVertex(final Vertex vertex, final Edge edge) {
        vertex.addEdge(edge);
        vertexMap.put(vertex, vertex.getEdges());  // create new set of edges for a vertex (direction is not important)
    }

    /**
     * Register edges by putting them into both tip and tail vertices in case of undirected graph only
     *
     * @param edge
     */
    void addEdge(final Edge edge) {
        addVertex(edge.getVertexA(), edge); // in undirected graph add a new edge to Vertex A
        addVertex(edge.getVertexB(), edge); // in undirected graph add a new edge to Vertex B (as well)
    }

    private static class GraphBuilder implements IGraphBuilder {

        private UndirectedGraph undirectedGraph;

        private BiFunction<Vertex, Vertex, LinkedList<Edge>> pathSearchFunction = UnidirectedPathSearchFunction::getPath;

        public GraphBuilder(final UndirectedGraph undirectedGraph) {
            this.undirectedGraph = undirectedGraph;
        }

        /**
         * How to create and persist a new vertex
         *
         * @param name - unique vertex name
         * @return new vertex
         * throws (@IllegalStateException) if found
         */
        @Override public Vertex addVertex(final String name) {
            if (undirectedGraph.findVertexByName(name)) {
                throw new NotUniqueVertexException("Found existing vertex. Vertex should have unique name: " + name);
            }

            final Vertex vertex = Vertex.of(name);
            undirectedGraph.addVertex(vertex);
            return vertex;
        }

        /**
         * How to create edge
         *
         * @param vertexA vertex one
         * @param vertexB vertex two
         */
        @Override public void addEdge(final Vertex vertexA, final Vertex vertexB) {
            if (!undirectedGraph.findVertexByName(vertexA.getName())) {
                throw new NotVertexFoundException("No vertex found while adding edge:" + vertexA);
            }
            if (!undirectedGraph.findVertexByName(vertexB.getName())) {
                throw new NotVertexFoundException("No vertex found while adding edge:" + vertexB);
            }
            undirectedGraph.addEdge(Edge.of(type, vertexA, vertexB));
        }

        @Override public LinkedList<Edge> getPath(final Vertex from, final Vertex to) {
            return pathSearchFunction.apply(from, to);
        }

        @Override public void setCustomSearchFunction(BiFunction<Vertex, Vertex, LinkedList<Edge>> pathSearchFunction) {
            this.pathSearchFunction = pathSearchFunction;
        }
    }
}
