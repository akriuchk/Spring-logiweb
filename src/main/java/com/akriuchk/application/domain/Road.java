package com.akriuchk.application.domain;

import lombok.NoArgsConstructor;
import org.jgrapht.graph.DefaultWeightedEdge;

@NoArgsConstructor
public class Road extends DefaultWeightedEdge {
    private String roadNumber;
    private int distance;

    public Road(String roadNumber, int distance) {
        super();
        this.roadNumber = roadNumber;
        this.distance = distance;
    }

    public int getDistance() {
        return distance;
    }
}
