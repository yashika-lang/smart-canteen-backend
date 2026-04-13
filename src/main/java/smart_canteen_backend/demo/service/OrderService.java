package smart_canteen_backend.demo.service;

import smart_canteen_backend.demo.dto.AddOrderRequestDto;
import smart_canteen_backend.demo.dto.CreateOrderRequest;
import smart_canteen_backend.demo.dto.OrderDto;

import java.util.List;
import java.util.Map;

public interface OrderService {

    OrderDto createOrder(CreateOrderRequest dto);

    List<OrderDto> getAllOrders();

    OrderDto updateOrderStatus(Long id, String status, Long userId);

    OrderDto getOrderById(Long id);

    Map<String, Object> getAdminStats();

    List<OrderDto> getOrdersByUser(Long userId);
}

