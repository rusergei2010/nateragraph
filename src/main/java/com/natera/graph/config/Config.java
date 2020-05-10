package com.natera.graph.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import com.natera.graph.common.DirectedGraph;
import com.natera.graph.common.Graph;
import com.natera.graph.common.UndirectedGraph;

@Configuration
public class Config {

    @Bean
    @Scope("prototype")
    @Qualifier(value = "DirectedGraph")
    public Graph getDirectedGraph() {
        return new DirectedGraph();
    }

    @Bean
    @Scope("prototype")
    @Qualifier(value = "UndirectedGraph")
    public Graph getUndirectedGraph() {
        return new UndirectedGraph();
    }
}
