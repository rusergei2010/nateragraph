package com.natera.graph.searchfunction;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;
import java.util.function.BiFunction;

import org.springframework.util.CollectionUtils;

import com.natera.graph.model.Edge;
import com.natera.graph.model.Vertex;

public interface UnidirectedPathSearchFunction extends BiFunction<Vertex, Vertex, LinkedList<Edge>> {

    static LinkedList<Edge> getPath(final Vertex from, final Vertex to){
        return getPath(from, to, new HashSet<Vertex>(), new LinkedList<>());
    }

    static LinkedList<Edge> getPath(final Vertex from, final Vertex target, Set<Vertex> encounteredVertices, LinkedList<Edge> path) {

        encounteredVertices.add(from);

        // iterate through all vertex's edges
        for (Edge edge : from.getEdges()) {
            final Vertex to = edge.getPeerOrNull(from); // get another end of edge or null if vertex is not found in edge

            if (to == null){
                continue;
            }

            if (encounteredVertices.contains(to)) {
                continue; // this Vertex is already encountered
            }

            // ### FOUND TARGET ###
            if (to != null && to.equals(target)) { // ARRIVED TO TARGET !!!
                path.addLast(edge);
                return path;
            }

            // continue iteration till target is reached
            // recursion - go though a new Vertex
            final LinkedList<Edge> foundPath = getPath(to, target, encounteredVertices, path);

            // ### ADD CURRENT EDGE BEFORE RETURN THE HEAD WITH TARGET ###
            if (!CollectionUtils.isEmpty(foundPath)) { // found > 1 edge then reached the target
                path.addFirst(edge); // add current edge beforehand in recursion (build path in backward)
                return path;
            }
        }

        return null;
    }
}
