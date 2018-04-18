package com.akriuchk.application.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.jgrapht.graph.DefaultWeightedEdge;

@Getter
@Setter
@NoArgsConstructor
public class Road extends DefaultWeightedEdge {

    private long id;
    private String roadNumber;
    private String source;
    private String destination;
    private int distance;

    public Road(String roadNumber, int distance) {
        super();
        this.roadNumber = roadNumber;
        this.distance = distance;
    }

    public Road(String roadNumber, String source, String destination, int distance) {
        super();
        this.roadNumber = roadNumber;
        this.source = source;
        this.destination = destination;
        this.distance = distance;
    }
}
