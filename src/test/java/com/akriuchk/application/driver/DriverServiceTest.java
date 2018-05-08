package com.akriuchk.application.driver;

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
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.hamcrest.Matchers.*;
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
public class DriverServiceTest extends AbstractTransactionalTestNGSpringContextTests {
    private final Logger log = LoggerFactory.getLogger(DriverServiceTest.class);


    private final String restPath = "/api/drivers";


    private MockMvc mockMvc;

    @Autowired
    private DriverService driverService;

    @Autowired
    private DriverConverter driverConverter;

    @BeforeMethod
    public void setUp() throws Exception {
        mockMvc = MockMvcBuilders.standaloneSetup(new DriverController(driverService, driverConverter)).build();
    }

    @Test
    public void testAddDriver() throws Exception {
        String redirectedUrl = mockMvc.perform(post(restPath + "/").content(asJsonString(getDriver())).contentType(MediaType.APPLICATION_JSON))
//                .andDo(print())
//                .andExpect(redirectedUrlPattern(restPath + "/*"))
                .andExpect(status().isCreated())
                .andReturn().getResponse().getRedirectedUrl();
        Assert.notNull(redirectedUrl, "check redirectUrl for null");

        mockMvc.perform(get(redirectedUrl).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.currentCity", is("in MVC")))
                .andExpect(jsonPath("$.registrationNumber", is(12345678)));
    }

    @Test
    public void testGetAllPaged() throws Exception {
        mockMvc.perform(post(restPath + "/").content(asJsonString(getDriver())).contentType(MediaType.APPLICATION_JSON))
//                .andDo(print())
//                .andExpect(redirectedUrlPattern(restPath + "/*"))
                .andExpect(status().isCreated())
//                .andReturn().getResponse().getRedirectedUrl()
        ;

        mockMvc.perform((get(restPath + "?offset=0&size=1")).accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()", equalTo(1)))
                .andExpect(jsonPath("$[0].id", greaterThan(15)));

        mockMvc.perform((get(restPath + "?offset=1&size=1")).accept(MediaType.APPLICATION_JSON))
//                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()", equalTo(1)))
                .andExpect(jsonPath("$[0].id", greaterThan(15)));

        mockMvc.perform((get(restPath + "?offset=1&size=2")).accept(MediaType.APPLICATION_JSON))
//                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()", equalTo(2)));

        //check default parameter
        mockMvc.perform((get(restPath + "?offset=0")).accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()", lessThanOrEqualTo(50)));

    }

    @Test
    public void testUpdateDriver() throws Exception {
        String redirectedUrl = mockMvc.perform(post(restPath + "/").content(asJsonString(getDriver())).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andReturn().getResponse().getRedirectedUrl();

        Assert.notNull(redirectedUrl, "check redirectUrl for null");


        Driver newD = new Driver("Adam", "Biglow", 12345678,
                10, "resting", "Updated", "198264");
        mockMvc.perform(put(redirectedUrl).content(asJsonString(newD)).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isAccepted());

        mockMvc.perform(get(redirectedUrl).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.currentCity", is("Updated")))
                .andExpect(jsonPath("$.registrationNumber", is(12345678)));
    }

    @Test
    public void testDeleteDriver() throws Exception {
        String redirectedUrl = mockMvc.perform(post(restPath + "/").content(asJsonString(getDriver())).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andReturn().getResponse().getRedirectedUrl();

        Assert.notNull(redirectedUrl, "check redirectUrl for null");

        mockMvc.perform(delete(redirectedUrl))
                .andExpect(status().isNoContent());
    }

//    @Test
//    public void testFindByNumber() throws Exception {
//
//    }
//
//    @Test
//    public void testFindByWorkingHours() throws Exception {
//    }

    private static Driver getDriver() {
        return  new Driver("Adam", "Biglow", 12345678,
                10, "resting", "in MVC", "198264");
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