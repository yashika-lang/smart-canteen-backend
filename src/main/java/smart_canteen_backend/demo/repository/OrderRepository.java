package smart_canteen_backend.demo.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import smart_canteen_backend.demo.entity.Order;
@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    @Query(value = "SELECT nextval('token_seq')", nativeQuery = true) // query is used for sequentially adding token
    Integer getNextToken();
}
