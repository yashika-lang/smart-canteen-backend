package smart_canteen_backend.demo.service;

import smart_canteen_backend.demo.dto.AddMenuRequestDto;
import smart_canteen_backend.demo.dto.MenuDto;

import java.util.List;

public interface MenuItemService {

    List<MenuDto> getMenuItems();

    MenuDto createMenuItems(AddMenuRequestDto addMenuRequestDto);


    void deleteMenuItemById(Long id);
}
