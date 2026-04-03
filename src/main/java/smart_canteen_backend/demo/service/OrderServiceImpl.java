package smart_canteen_backend.demo.service;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import smart_canteen_backend.demo.dto.AddOrderRequestDto;
import smart_canteen_backend.demo.dto.OrderDto;
import smart_canteen_backend.demo.entity.MenuItem;
import smart_canteen_backend.demo.entity.Order;
import smart_canteen_backend.demo.entity.OrderStatus;
import smart_canteen_backend.demo.entity.User;
import smart_canteen_backend.demo.repository.MenuItemRepository;
import smart_canteen_backend.demo.repository.OrderRepository;
import smart_canteen_backend.demo.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final ModelMapper modelMapper;
    private final MenuItemRepository menuItemRepository;
    private final UserRepository userRepository;

    //  CREATE ORDER
    @Override
    public OrderDto createOrder(AddOrderRequestDto dto) {

        //  fetch menu item
        MenuItem menuItem = menuItemRepository.findById(dto.getMenuItemId())
                .orElseThrow(() -> new RuntimeException("Menu item not found"));

        //  fetch user
        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        // create order
        Order order = new Order();

        order.setMenuItemId(dto.getMenuItemId());
        order.setQuantity(dto.getQuantity());

        //
        order.setUser(user);

        //  price
        int totalPrice = menuItem.getPrice() * dto.getQuantity();
        order.setTotalPrice(totalPrice);

        //  status + time
        order.setStatus(OrderStatus.PLACED);
        order.setCreatedAt(LocalDateTime.now());

        //  token
        Integer nextToken = orderRepository.getNextToken();
        order.setTokenNumber(nextToken);

        //  save
        Order savedOrder = orderRepository.save(order);

        //
        OrderDto orderDto = modelMapper.map(savedOrder, OrderDto.class);
        orderDto.setUserId(savedOrder.getUser().getId());

        return orderDto;
    }

    //  GET ALL ORDERS
    @Override
    public List<OrderDto> getAllOrders() {
        List<Order> orders = orderRepository.findAll();

        return orders.stream()
                .map(order -> {
                    OrderDto dto = modelMapper.map(order, OrderDto.class);
                    dto.setUserId(order.getUser().getId());
                    return dto;
                })
                .toList();
    }


    // UPDATE STATUS
    @Override
    public OrderDto updateOrderStatus(Long id, String status, Long userId) {

        // user fetch
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // ADMIN CHECK
        if (!"ADMIN".equals(user.getRole())){
            throw new RuntimeException("Only admin can update order status");
        }

        // order fetch
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        order.setStatus(OrderStatus.valueOf(status));

        Order updatedOrder = orderRepository.save(order);

        OrderDto dto = modelMapper.map(updatedOrder, OrderDto.class);
        dto.setUserId(updatedOrder.getUser().getId());

        return dto;
    }

    // GET BY ID
    @Override
    public OrderDto getOrderById(Long id) {

        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        OrderDto dto = modelMapper.map(order, OrderDto.class);
        dto.setUserId(order.getUser().getId());

        return dto;
    }
}