package smart_canteen_backend.demo.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import smart_canteen_backend.demo.dto.AddOrderRequestDto;
import smart_canteen_backend.demo.dto.OrderDto;
import smart_canteen_backend.demo.service.OrderService;

import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RequestMapping("/order")
@RestController

public class OrderController {

    private final OrderService orderService;
    

    @PostMapping
    public ResponseEntity<OrderDto> createOrder(@RequestBody AddOrderRequestDto addOrderRequestDto) {

        return ResponseEntity.status(HttpStatus.CREATED).body(orderService.createOrder(addOrderRequestDto));
    }
    @GetMapping
    public ResponseEntity<List<OrderDto>> getAllOrders(){
        return ResponseEntity.ok(orderService.getAllOrders());
    }

     @PatchMapping("/{id}/status")
    public ResponseEntity<OrderDto> updateStatus(
             @PathVariable Long id,
             @RequestBody Map<String, String> request) {
         String status = request.get("status");
         return ResponseEntity.ok(orderService.updateOrderStatus(id, status));
     }

     @GetMapping("/{id}")
     public ResponseEntity<OrderDto> getOrderById(@PathVariable Long id) {
         return ResponseEntity.ok(orderService.getOrderById(id));
     }

     }
