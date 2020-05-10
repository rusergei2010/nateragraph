package com.natera.graph.model;


public class Edge {
    public Vertex vertexA;
    public Vertex vertexB;
    public TYPE type;

    Edge(TYPE type, final Vertex vertexA, final Vertex vertexB) {
        this.vertexA = vertexA;
        this.vertexB = vertexB;
        this.type = type;
    }

    public Vertex getVertexA() {
        return vertexA;
    }

    public Vertex getVertexB() {
        return vertexB;
    }

    public static Edge of(TYPE type, final Vertex vertexA, final Vertex vertexB) {
        return new Edge(type, vertexA, vertexB);
    }

    public TYPE getType() {
        return type;
    }

    public Vertex getPeerOrNull(Vertex one) {
        if (type == TYPE.UNDIRECTED) {
            if (vertexA.equals(one)) {
                return vertexB;
            } else if (vertexB.equals(one)) {
                return vertexA;
            }
            return null;
        } else {
            if (vertexA.equals(one)) { // in case of directed edge then return tip only
                return vertexB;
            }
            return null;
        }
    }

    @Override public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final Edge edge = (Edge) o;

        if (type == TYPE.DIRECTED) {
            return vertexA.equals(edge.vertexA) &&
                    vertexB.equals(edge.vertexB) &&
                    type == edge.type;
        } else if (type == TYPE.UNDIRECTED) {
            return ((vertexA.equals(edge.vertexA) && vertexB.equals(edge.vertexB)) ||
                    (vertexA.equals(edge.vertexB) && vertexB.equals(edge.vertexA))) &&
                    type == edge.type;
        }
        return false;
    }

    @Override public int hashCode() {
        return vertexA.hashCode() + vertexB.hashCode();
    }
}
