package smart_canteen_backend.demo.service;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import smart_canteen_backend.demo.dto.CreateOrderRequest;
import smart_canteen_backend.demo.dto.OrderDto;
import smart_canteen_backend.demo.dto.OrderItemDto;
import smart_canteen_backend.demo.dto.OrderItemRequest;
import smart_canteen_backend.demo.entity.MenuItem;
import smart_canteen_backend.demo.entity.Order;
import smart_canteen_backend.demo.entity.OrderItem;
import smart_canteen_backend.demo.entity.OrderStatus;
import smart_canteen_backend.demo.entity.User;
import smart_canteen_backend.demo.repository.MenuItemRepository;
import smart_canteen_backend.demo.repository.OrderRepository;
import smart_canteen_backend.demo.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final ModelMapper modelMapper;
    private final MenuItemRepository menuItemRepository;
    private final UserRepository userRepository;


    //  CREATE ORDER
    @Override
    public OrderDto createOrder(CreateOrderRequest dto) {

        // fetch user
        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Order order = new Order();
        order.setUser(user);
        order.setStatus(OrderStatus.PLACED);
        order.setCreatedAt(LocalDateTime.now());

        Integer nextToken = orderRepository.getNextToken();
        order.setTokenNumber(nextToken);

        List<OrderItem> orderItems = new ArrayList<>();
        int totalPrice = 0;

        for (OrderItemRequest itemReq : dto.getItems()) {

            MenuItem menuItem = menuItemRepository.findById(itemReq.getMenuItemId())
                    .orElseThrow(() -> new RuntimeException("Menu item not found"));

            OrderItem item = new OrderItem();
            item.setMenuItemId(menuItem.getId());
            item.setMenuItemName(menuItem.getName());
            item.setQuantity(itemReq.getQuantity());
            item.setPrice(menuItem.getPrice());
            item.setOrder(order);

            totalPrice += menuItem.getPrice() * itemReq.getQuantity();

            orderItems.add(item);
        }

        order.setItems(orderItems);
        order.setTotalPrice(totalPrice);

        Order savedOrder = orderRepository.save(order);

        OrderDto orderDto = modelMapper.map(savedOrder, OrderDto.class);
        orderDto.setUserId(savedOrder.getUser().getId());

        // 🔥 map items into DTO (IMPORTANT)
        List<OrderItemDto> items = new ArrayList<>();
        for (OrderItem it : savedOrder.getItems()) {
            OrderItemDto dtoItem = new OrderItemDto();
            dtoItem.setMenuItemName(it.getMenuItemName());
            dtoItem.setQuantity(it.getQuantity());
            items.add(dtoItem);
        }
        orderDto.setItems(items);

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

                    // 🔥 map items
                    List<OrderItemDto> items = new ArrayList<>();
                    for (OrderItem it : order.getItems()) {
                        OrderItemDto dtoItem = new OrderItemDto();
                        dtoItem.setMenuItemName(it.getMenuItemName());
                        dtoItem.setQuantity(it.getQuantity());
                        items.add(dtoItem);
                    }
                    dto.setItems(items);

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
       /* if (!"ADMIN".equals(user.getRole())){
            throw new RuntimeException("Only admin can update order status");
        }*/

        // order fetch
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        order.setStatus(OrderStatus.valueOf(status));

        Order updatedOrder = orderRepository.save(order);

        OrderDto dto = modelMapper.map(updatedOrder, OrderDto.class);
        dto.setUserId(updatedOrder.getUser().getId());

        List<OrderItemDto> items = new ArrayList<>();
        for (OrderItem it : updatedOrder.getItems()) {
            OrderItemDto dtoItem = new OrderItemDto();
            dtoItem.setMenuItemName(it.getMenuItemName());
            dtoItem.setQuantity(it.getQuantity());
            items.add(dtoItem);
        }
        dto.setItems(items);

        return dto;
    }

    // GET BY ID
    @Override
    public OrderDto getOrderById(Long id) {

        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        OrderDto dto = modelMapper.map(order, OrderDto.class);
        dto.setUserId(order.getUser().getId());

        List<OrderItemDto> items = new ArrayList<>();
        for (OrderItem it : order.getItems()) {
            OrderItemDto dtoItem = new OrderItemDto();
            dtoItem.setMenuItemName(it.getMenuItemName());
            dtoItem.setQuantity(it.getQuantity());
            items.add(dtoItem);
        }
        dto.setItems(items);

        return dto;
    }
    @Override
    public Map<String, Object> getAdminStats() {

        LocalDateTime start = LocalDateTime.now().toLocalDate().atStartOfDay();
        LocalDateTime end = start.plusDays(1);

        Map<String, Object> stats = new HashMap<>();

        Long totalOrders = orderRepository.countTodayOrders(start, end);
        Long revenue = orderRepository.sumTodayRevenue(start, end);
        Long pending = orderRepository.countTodayPending(start, end);

        stats.put("totalOrders", totalOrders != null ? totalOrders : 0);
        stats.put("revenue", revenue != null ? revenue : 0);
        stats.put("pending", pending != null ? pending : 0);

        return stats;
    }
    @Override
    public List<OrderDto> getOrdersByUser(Long userId) {

        List<Order> orders = orderRepository.findByUserId(userId);

        return orders.stream().map(order -> {
            OrderDto dto = modelMapper.map(order, OrderDto.class);
            dto.setUserId(order.getUser().getId());

            List<OrderItemDto> items = new ArrayList<>();
            for (OrderItem it : order.getItems()) {
                OrderItemDto dtoItem = new OrderItemDto();
                dtoItem.setMenuItemName(it.getMenuItemName());
                dtoItem.setQuantity(it.getQuantity());
                items.add(dtoItem);
            }
            dto.setItems(items);

            return dto;
        }).toList();
    }
}