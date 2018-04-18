package com.akriuchk.application.domain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.List;

import static org.testng.AssertJUnit.*;


public class StateMapTest {
    private final Logger log = LoggerFactory.getLogger(StateMapTest.class);
    private StateMap stateMap;

    private String Orenburg = "Orenburg";
    private String Novosibirsk = "Novosibirsk";
    private String Krasnoyarsk = "Krasnoyarsk";
    private String Ekaterinburg = "Ekaterinburg";
    private String Chelyabinsk = "Chelyabinsk";
    private String SaintPetersburg = "Saint-Petersburg";
    private String Krasnodar = "Krasnodar";
    private String Kazan = "Kazan";
    private String Moscow = "Moscow";
    private String Tumen = "Tumen";

    private String Syktyvkar = "Syktyvkar";

    private List<String> cityList = Arrays.asList(Orenburg, Novosibirsk, Krasnoyarsk, Ekaterinburg, Chelyabinsk,
            SaintPetersburg, Krasnodar, Kazan, Moscow, Tumen);

    @BeforeClass
    public void setUp() throws Exception {
        Road r1 = new Road("E01", SaintPetersburg, Moscow, 729);
        Road r2 = new Road("E02", SaintPetersburg, Krasnodar, 2069);
        Road r3 = new Road("E03", Moscow, Krasnodar, 1348);
        Road r4 = new Road("E04", Moscow, Kazan, 824);
        Road r5 = new Road("E05", Kazan, Orenburg, 710);
        Road r6 = new Road("E06", Orenburg, Ekaterinburg, 902);
        Road r7 = new Road("E07", Orenburg, Chelyabinsk, 779);
        Road r8 = new Road("E08", Ekaterinburg, Tumen, 326);
        Road r9 = new Road("E09", Chelyabinsk, Tumen, 417);
        Road r10 = new Road("E10", Tumen, Novosibirsk, 1281);
        Road r11 = new Road("E11", Novosibirsk, Krasnoyarsk, 789);
        Road r12 = new Road("E12", Tumen, Krasnoyarsk, 2066);
        Road r13 = new Road("E13", SaintPetersburg, Ekaterinburg, 2327);
        List<Road> roads = Arrays.asList(r1, r2, r3, r4, r5, r6, r7, r8, r9, r10, r11, r12, r13);


        stateMap = new StateMap("Russia", cityList, roads);
    }

    @Test
    public void testAddCity() throws Exception {
        int cityCount = stateMap.getAllCities().size();
        boolean isAdded = stateMap.addCity(Syktyvkar);
        int newCityCount = stateMap.getAllCities().size();

        assertTrue(isAdded);
        assertEquals(cityCount + 1, newCityCount);
    }

    @Test
    public void testAddRoad() throws Exception {
        boolean isAdded = stateMap.addCity(Syktyvkar);
        stateMap.addRoad(new Road("E14", Kazan, Syktyvkar, 841));
        int distance = stateMap.getRoadPath(Syktyvkar, Kazan).get(0).getDistance();
        assertEquals(distance, 841);


    }

    @Test
    public void testGetRoadPath() throws Exception {
        List<Road> roadPath = stateMap.getRoadPath(SaintPetersburg, Novosibirsk);
        int length = roadPath.stream().mapToInt(Road::getDistance).sum();
        log.info("Road path: {}", roadPath);
        log.info("Road length: {} km", length);

        assertEquals(roadPath.size(), 3);
    }

    @Test
    public void testGetCityList() throws Exception {
        List<String> cityList = stateMap.getCityList(SaintPetersburg, Novosibirsk);
        log.info("City path: {}", cityList);

        String[] citiesCheck = {SaintPetersburg, Ekaterinburg, Tumen, Novosibirsk};

        assertArrayEquals(cityList.toArray(), citiesCheck);

    }
}