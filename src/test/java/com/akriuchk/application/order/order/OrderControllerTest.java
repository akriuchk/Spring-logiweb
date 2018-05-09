package com.akriuchk.application.order.order;

import com.akriuchk.application.order.cargo.Cargo;
import com.akriuchk.application.order.cargo.CargoDTO;
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

import java.util.UUID;

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


    private static String Chelyabinsk = "Chelyabinsk";
    private static String SaintPetersburg = "Saint-Petersburg";

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
        OrderDTO testOrder = getOrder(getCargo().getPublicId());
        String redirectedUrl = mockMvc.perform(post(restPath + "/").content(asJsonString(testOrder)).contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
//                .andExpect(redirectedUrlPattern(restPath + "/*"))
                .andExpect(status().isCreated())
                .andReturn().getResponse().getRedirectedUrl();
        Assert.notNull(redirectedUrl, "check redirectUrl for null");

        mockMvc.perform(get(redirectedUrl).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.publicId", is(testOrder.getPublicId())));

    }

    private static OrderDTO getOrder(String uuid) {
        OrderDTO o = new OrderDTO(UUID.randomUUID().toString(),
                Order.OrderState.PROCESSING.getOrderState(),
                uuid,
                "someTruckNumber",
                Chelyabinsk,
                SaintPetersburg
                );
        return o;

    }

    private static CargoDTO getCargo() {
        CargoDTO c = new CargoDTO();
        c.setPublicId(UUID.randomUUID().toString());
        c.setDescription("Test stuff");
        c.setWeightKg(20);
        c.setStatus(Cargo.Status.PREPARED.getStatus());

        return c;
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