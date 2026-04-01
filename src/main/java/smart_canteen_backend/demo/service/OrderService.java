package smart_canteen_backend.demo.service;

import smart_canteen_backend.demo.dto.AddOrderRequestDto;
import smart_canteen_backend.demo.dto.OrderDto;

import java.util.List;

public interface OrderService {

    OrderDto createOrder(AddOrderRequestDto addOrderRequestDto);

    List<OrderDto> getAllOrders();

    OrderDto updateOrderStatus(Long id, String status);

    OrderDto getOrderById(Long id);
}

