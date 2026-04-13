package smart_canteen_backend.demo.service;

import smart_canteen_backend.demo.dto.AddMenuRequestDto;
import smart_canteen_backend.demo.dto.MenuDto;

import java.util.List;
import java.util.Map;

public interface MenuItemService {

    List<MenuDto> getMenuItems();

    MenuDto createMenuItems(AddMenuRequestDto addMenuRequestDto);


    void deleteMenuItemById(Long id);

    MenuDto updateMenuById(Long id, AddMenuRequestDto addMenuRequestDto);

    MenuDto updatePartialMenuItem(Long id, Map<String, Object> updates);

    void updateAvailability(Long id, boolean available);
}
