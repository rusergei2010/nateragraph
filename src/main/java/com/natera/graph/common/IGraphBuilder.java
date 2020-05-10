package com.natera.graph.common;

import java.util.LinkedList;
import java.util.function.BiFunction;

import com.natera.graph.model.Edge;
import com.natera.graph.model.Vertex;

public interface IGraphBuilder {

    Vertex addVertex(String vertex);

    void addEdge(final Vertex vertexA, final Vertex vertexB);

    public LinkedList<Edge> getPath(final Vertex from, final Vertex to);

    void setCustomSearchFunction(final BiFunction<Vertex, Vertex, LinkedList<Edge>> pathSearchFunction);
}
