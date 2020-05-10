package com.natera.graph.searchfunction;

import java.util.HashSet;
import java.util.LinkedList;

import com.natera.graph.model.Edge;
import com.natera.graph.model.Vertex;

public interface DirectedPathSearchFunction extends UnidirectedPathSearchFunction {

    static LinkedList<Edge> getPath(final Vertex from, final Vertex to){
        return UnidirectedPathSearchFunction.getPath(from, to, new HashSet<Vertex>(), new LinkedList<>());// the same
    }

}
