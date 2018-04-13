package com.akriuchk.application.truck;

import com.akriuchk.configuration.SpringConfiguration;
import com.akriuchk.configuration.SpringInit;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Test
@ContextHierarchy({
        @ContextConfiguration(classes = SpringInit.class),
        @ContextConfiguration(classes = SpringConfiguration.class)
})
@WebAppConfiguration
public class TruckServiceTest extends AbstractTransactionalTestNGSpringContextTests {
    private final Logger log = LoggerFactory.getLogger(TruckServiceTest.class);

    private final String restPath = "/api/trucks";


    private MockMvc mockMvc;

    @Autowired
    ITruckService truckService;

    @Autowired
    TruckConverter truckConverter;

    @BeforeMethod
    public void setUp() throws Exception {
        mockMvc = MockMvcBuilders.standaloneSetup(new TruckController(truckService, truckConverter)).build();
    }

    @AfterMethod
    public void tearDown() throws Exception {
    }

    @Test
    public void testGetAll() throws Exception {
        mockMvc.perform(get(restPath).accept(MediaType.APPLICATION_JSON))
//                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void testGetTruckByID() throws Exception {
        mockMvc.perform((get(restPath + "/1")).accept(MediaType.APPLICATION_JSON))
//                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.registrationNumber", is("7PPDUBAF")));
    }

    @Test
    public void testAddTruck() throws Exception {
        Truck t = new Truck("AB12CD34", 3, 15, "test", "in MVC");
        String redirectedUrl = mockMvc.perform(post(restPath + "/").content(asJsonString(t)).contentType(MediaType.APPLICATION_JSON))
//                .andDo(print())
//                .andExpect(redirectedUrlPattern(restPath + "/*"))
                .andExpect(status().isCreated())
                .andReturn().getResponse().getRedirectedUrl();
        Assert.notNull(redirectedUrl, "check redirectUrl for null");

        mockMvc.perform(get(redirectedUrl).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andDo(print())
                .andExpect(jsonPath("$.currentCity", is("in MVC")))
                .andExpect(jsonPath("$.registrationNumber", is("AB12CD34")));
    }

    @Test
    public void testUpdateTruck() throws Exception {
        Truck t = new Truck("AB12CD34", 3, 15, "test", "for Update");
        String redirectedUrl = mockMvc.perform(post(restPath + "/").content(asJsonString(t)).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andReturn().getResponse().getRedirectedUrl();

        Assert.notNull(redirectedUrl, "check redirectUrl for null");

        Truck newT = new Truck("AB12CD34", 3, 15, "test", "Updated");
        mockMvc.perform(put(redirectedUrl).content(asJsonString(newT)).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isAccepted());

        mockMvc.perform(get(redirectedUrl).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.currentCity", is("Updated")))
                .andExpect(jsonPath("$.registrationNumber", is("AB12CD34")));

    }

    @Test
    public void testDeleteTruck() throws Exception {
        Truck t = new Truck("AB12CD34", 3, 15, "test", "for Delete");
        String redirectedUrl = mockMvc.perform(post(restPath + "/").content(asJsonString(t)).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andReturn().getResponse().getRedirectedUrl();

        Assert.notNull(redirectedUrl, "check redirectUrl for null");

        mockMvc.perform(delete(redirectedUrl))
                .andExpect(status().isNoContent());
    }

    @Test
    public void testFindTruckByCapacity() throws Exception {
        Truck t = new Truck("AB12CD34", 3, 500, "test", "for_capacity_test");
        mockMvc.perform(post(restPath + "/").content(asJsonString(t)).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andReturn().getResponse().getRedirectedUrl();

        String truckByCapacity = "$.*[?(@.currentCity == '%s')]";

        mockMvc.perform((get(restPath + "/search?minCapacityKg=19999")).accept(MediaType.APPLICATION_JSON))
//                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()", greaterThanOrEqualTo(9)))
                .andExpect(jsonPath(truckByCapacity, "for_capacity_test").exists());
    }

    private static String asJsonString(final Object obj) {
        try {

            final ObjectMapper mapper = new ObjectMapper();
            final String jsonContent = mapper.writeValueAsString(obj);
            return jsonContent;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}