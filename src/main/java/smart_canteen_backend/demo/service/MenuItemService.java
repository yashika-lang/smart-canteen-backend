package smart_canteen_backend.demo.service;

import smart_canteen_backend.demo.dto.MenuDto;
import smart_canteen_backend.demo.entity.MenuItem;

import java.util.List;

public interface MenuItemService {

    List<MenuDto> getMenuItems();
}
