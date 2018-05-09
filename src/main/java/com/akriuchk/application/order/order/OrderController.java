package com.akriuchk.application.order.order;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {
    private final Logger log = LoggerFactory.getLogger(OrderController.class);

    private OrderService orderService;
    private OrderConverter orderConverter;

    @Autowired
    public OrderController(OrderService orderService, OrderConverter orderConverter) {
        this.orderService = orderService;
        this.orderConverter = orderConverter;
    }


    @RequestMapping(value = "/{orderId}", method = RequestMethod.GET)
    ResponseEntity<OrderDTO> getOrderById(@PathVariable Long orderId) {
        Order order = orderService.getOrderByID(orderId);
        if (null != order) {
            OrderDTO newOrderDto = (OrderDTO) orderConverter.convert(order, OrderDTO.class);
            return ResponseEntity.ok(newOrderDto);
        } else {
            return ResponseEntity.badRequest().body(new OrderDTO());
//            throw new NotFoundException("Order id[" + orderId + "] not found");
        }
    }

    @RequestMapping(method = RequestMethod.GET)
    ResponseEntity<List<OrderDTO>> getOrdersPaged(@RequestParam(defaultValue = "0") int offset,
                                                   @RequestParam(defaultValue = "20") int size) {
        List<Order> resultList = orderService.getAllPaged(offset, size);

        return ResponseEntity.ok().body(orderConverter.convertList(resultList, OrderDTO.class));
    }


    @RequestMapping(value = "/", method = RequestMethod.POST)
    ResponseEntity<OrderDTO> addNewOrder(@RequestBody OrderDTO inputOrderDTO) {
        Long orderId;

        Order newOrder = (Order) orderConverter.convert(inputOrderDTO, Order.class);
        orderId = orderService.addNewOrder(newOrder);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(orderId).toUri();
        log.info("Generated URI for new order id[{}]: {}", orderId, location);
        return ResponseEntity.created(location).build();
    }
}
