package com.natera.graph.common;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.natera.graph.model.Edge;
import com.natera.graph.model.Vertex;

public abstract class Graph {

    Map<Vertex, Set<Edge>> vertexMap; // Keep the unique Vertex names. Can be deleted from library if skip unique name validation.

    Graph() {
        this.vertexMap = new HashMap<>();
    }

    public Map<Vertex, Set<Edge>> getVertexMap() {
        return vertexMap;
    }

    public abstract IGraphBuilder builder();

    boolean findVertexByName(final String name) {
        return vertexMap.containsKey(Vertex.of(name));
    }
}
