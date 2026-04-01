package smart_canteen_backend.demo.service;


import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import smart_canteen_backend.demo.dto.AddOrderRequestDto;
import smart_canteen_backend.demo.dto.OrderDto;
import smart_canteen_backend.demo.entity.MenuItem;
import smart_canteen_backend.demo.entity.Order;
import smart_canteen_backend.demo.repository.MenuItemRepository;
import smart_canteen_backend.demo.repository.OrderRepository;

import java.time.LocalDateTime;
import java.util.List;



@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final ModelMapper modelMapper;
    private final MenuItemRepository menuItemRepository;


    // POST THE ORDER
    @Override
    public OrderDto createOrder(AddOrderRequestDto addOrderRequestDto) {
        // menu item fetch
        MenuItem menuItem = menuItemRepository.findById(addOrderRequestDto.getMenuItemId())
                .orElseThrow(() -> new RuntimeException("Menu item not found"));

        //make a new container
        Order order = new Order();

        //basic fields adding
        order.setMenuItemId(addOrderRequestDto.getMenuItemId());
        order.setQuantity(addOrderRequestDto.getQuantity());

        //total price calculation
        int totalPrice = menuItem.getPrice() * addOrderRequestDto.getQuantity();
        order.setTotalPrice(totalPrice);

        //status
        order.setStatus("PLACED");
        order.setCreatedAt(LocalDateTime.now());

        //Token Generator logic
        Integer nextToken = orderRepository.getNextToken();
        order.setTokenNumber(nextToken);

        //saving the order bro
        Order savedOrder = orderRepository.save(order);

        //converting entity to dto
        return modelMapper.map(savedOrder, OrderDto.class);


    }

    @Override
    public List<OrderDto> getAllOrders() {
        List<Order> orders = orderRepository.findAll();

        return orders.stream()
                .map(order -> modelMapper.map(order, OrderDto.class))
                .toList();

    }

    @Override
    public OrderDto updateOrderStatus(Long id, String status) {

        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        order.setStatus(status);

        Order updatedOrder = orderRepository.save(order);

        return modelMapper.map(updatedOrder, OrderDto.class);
    }

    @Override
    public OrderDto getOrderById(Long id) {

        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        return modelMapper.map(order, OrderDto.class);
    }
}
