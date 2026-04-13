package smart_canteen_backend.demo.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import smart_canteen_backend.demo.entity.Order;
import java.util.List;
import smart_canteen_backend.demo.entity.OrderStatus;
@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    @Query(value = "SELECT nextval('token_seq')", nativeQuery = true) // query is used for sequentially adding token
    Integer getNextToken();
    @Query("SELECT COUNT(o) FROM Order o")
    long getTotalOrders();

    @Query("SELECT SUM(o.totalPrice) FROM Order o")
    Long getTotalRevenue();

    @Query("SELECT COUNT(o) FROM Order o WHERE o.createdAt BETWEEN :start AND :end")
    Long countTodayOrders(java.time.LocalDateTime start, java.time.LocalDateTime end);

    @Query("SELECT SUM(o.totalPrice) FROM Order o WHERE o.createdAt BETWEEN :start AND :end")
    Long sumTodayRevenue(java.time.LocalDateTime start, java.time.LocalDateTime end);

    @Query("SELECT COUNT(o) FROM Order o WHERE o.createdAt BETWEEN :start AND :end AND o.status NOT IN ('COMPLETED')")
    Long countTodayPending(java.time.LocalDateTime start, java.time.LocalDateTime end);

    long countByStatusNotIn(List<OrderStatus> statuses);
    List<Order> findByUserId(Long userId);
}
