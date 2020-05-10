package com.natera.graph.model;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Vertex {
    // private UUID uuid;
    private String name; // unique name
    private Set<Edge> edges;

    Vertex(String name) {
        this(name, new HashSet<>());
    }

    Vertex(String name, final Set<Edge> edges) {
        this.name = name;
        this.edges = edges;
    }

    public void addEdge(final Edge edge) {
        if (edges.contains(edge)){
            throw new IllegalStateException("Duplicate edge is added to Vertex");
        }
        edges.add(edge);
    }

    public String getName() {
        return name;
    }

    public Set<Edge> getEdges() {
        return edges;
    }

    public static Vertex of(String name) {
        return new Vertex(name);
    }

    public static Vertex of(String name, final Set<Edge> edges) {
        return new Vertex(name, edges);
    }

    @Override public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final Vertex vertex = (Vertex) o;
        return name.equals(vertex.name);
    }

    @Override public int hashCode() {
        return Objects.hash(name);
    }
}
