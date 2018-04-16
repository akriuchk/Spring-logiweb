package com.akriuchk.application.domain;


import org.jgrapht.Graph;
import org.jgrapht.alg.interfaces.ShortestPathAlgorithm;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

public class StateMap {
    public static SimpleWeightedGraph<String, Road> getGraph() {
        Graph<String, DefaultEdge> g = new DefaultDirectedGraph<>(DefaultEdge.class);
        String Helsinki = ("Helsinki");
        String SPb = ("Saint-Peterburg");
        String Moscow = ("Moscow");
        String Vladimir = ("Vladimir");
        String Pekin = ("Pekin");



//        DijkstraShortestPath<String, DefaultEdge> dijkstraAlg = new DijkstraShortestPath<>(g);
//
//        ShortestPathAlgorithm.SingleSourcePaths<String, DefaultEdge> iPaths = dijkstraAlg.getPaths("Saint-Peterburg");
//        System.out.println(iPaths.getPath("Pekin") + "\n");


        SimpleWeightedGraph<String, Road> g1 = new SimpleWeightedGraph<>(Road.class);

        g1.addVertex(Helsinki);
        g1.addVertex(SPb);
        g1.addVertex(Moscow);
        g1.addVertex(Vladimir);
        g1.addVertex(Pekin);

        Road E01 = new Road("E01", 50);
        System.out.println(g1.addEdge(Helsinki, SPb, E01));
        g1.setEdgeWeight(E01, E01.getDistance());

        Road E02 = new Road("E02", 320);
        System.out.println(g1.addEdge(Helsinki, Moscow, E02));
        g1.setEdgeWeight(E02, E02.getDistance());


        Road E03 = new Road("E03", 370);
        System.out.println(g1.addEdge(SPb, Vladimir, E03));
        g1.setEdgeWeight(E03, E03.getDistance());


        Road E04 = new Road("E04", 300);
        System.out.println(g1.addEdge(SPb, Moscow, E04));
        g1.setEdgeWeight(E04, E04.getDistance());


        Road E05 = new Road("E05", 40);
        System.out.println(g1.addEdge(Vladimir, Moscow, E05));
        g1.setEdgeWeight(E05, E05.getDistance());


        Road E06 = new Road("E06", 350);
        System.out.println(g1.addEdge(Vladimir, Pekin, E06));
        g1.setEdgeWeight(E06, E06.getDistance());


        Road E07 = new Road("E07", 800);
        System.out.println(g1.addEdge(Moscow, Pekin, E07));
        g1.setEdgeWeight(E07, E07.getDistance());

        return g1;
    }

    public static void itinerary(SimpleWeightedGraph<String, Road> map, String departure, String destination) {
        DijkstraShortestPath<String, Road> p = new DijkstraShortestPath<String, Road>(map);

        ShortestPathAlgorithm.SingleSourcePaths<String, Road> iPaths = p.getPaths(departure);
        System.out.println("iPath: " +  iPaths.getWeight(destination) + "\n");
//        System.out.println(iPaths.getPath("Pekin") + "\n");

        System.out.println("Path: " + iPaths.getPath(destination));
//        System.out.println("Cost of shortest (i.e cheapest) path = £" + p.getPathLength() );
    }

    public static void main(String[] args) {
        itinerary(getGraph(), "Helsinki", "Pekin");
    }

}
