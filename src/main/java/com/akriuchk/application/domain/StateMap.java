package com.akriuchk.application.domain;

import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.graph.SimpleWeightedGraph;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Set;

public class StateMap {
    private final Logger log = LoggerFactory.getLogger(StateMap.class);

    private String stateName;
    private SimpleWeightedGraph<String, Road> roadGraph;
    private DijkstraShortestPath<String, Road> shortestPath;

    public StateMap(String stateName) {
        this.stateName = stateName;
        this.roadGraph = new SimpleWeightedGraph<>(Road.class);
        this.shortestPath = new DijkstraShortestPath<>(roadGraph);
    }

    public StateMap(String stateName, List<String> cityList, List<Road> roads) {
        this(stateName);

        for (String s : cityList) {
            addCity(s);
        }
        for (Road road : roads) {
            addRoad(road);
        }

    }

    public boolean addCity(String city) {
        boolean isSuccess = roadGraph.addVertex(city);
        if (isSuccess) {
            log.info("City '{}' added to '{}' sucessfully", city, stateName);
        } else {
            log.info("City '{}' wasn't add to '{}'", city, stateName);
        }
        return isSuccess;
    }

    public void addRoad(Road road) {
        log.info("Adding road '{}' from '{}' to '{}'. Distance: {}", road.getRoadNumber(), road.getSource(), road.getDestination(), road.getDistance());
        roadGraph.addEdge(road.getSource(), road.getDestination(), road);
        roadGraph.setEdgeWeight(road, road.getDistance());
    }

    public List<Road> getRoadPath(String source, String destination) {
        return shortestPath.getPath(source, destination).getEdgeList();
    }

    public List<String> getCityList(String source, String destination) {
        return shortestPath.getPath(source, destination).getVertexList();
    }

    public Set<String> getAllCities() {
        return roadGraph.vertexSet();
    }
}
