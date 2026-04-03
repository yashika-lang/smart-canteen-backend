package smart_canteen_backend.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import smart_canteen_backend.demo.entity.MenuItem;

@Repository
public interface MenuItemRepository extends JpaRepository<MenuItem,Long>
{


}
