package com.akriuchk.application.order.order;

import com.akriuchk.configuration.SpringConfiguration;
import com.akriuchk.configuration.SpringInit;
import com.fasterxml.jackson.databind.ObjectMapper;
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

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Test
@ContextHierarchy({
        @ContextConfiguration(classes = SpringInit.class),
        @ContextConfiguration(classes = SpringConfiguration.class)
})
@WebAppConfiguration
public class OrderControllerTest extends AbstractTransactionalTestNGSpringContextTests {

    private final String restPath = "/api/orders";

    private MockMvc mockMvc;

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderConverter orderConverter;

    @BeforeMethod
    public void setUp() throws Exception {
        mockMvc = MockMvcBuilders.standaloneSetup(new OrderController(orderService, orderConverter)).build();
    }

    @Test
    public void testAddNewOrder() throws Exception {
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

    private static OrderDTO getOrder() {
        OrderDTO o = new OrderDTO();
        o.setState(Order.OrderState.INWORK.getOrderState());
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