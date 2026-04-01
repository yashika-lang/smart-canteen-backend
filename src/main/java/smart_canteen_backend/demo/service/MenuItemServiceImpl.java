package smart_canteen_backend.demo.service;


import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import smart_canteen_backend.demo.dto.AddMenuRequestDto;
import smart_canteen_backend.demo.dto.MenuDto;
import smart_canteen_backend.demo.entity.MenuItem;
import smart_canteen_backend.demo.repository.MenuItemRepository;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class MenuItemServiceImpl implements MenuItemService {
    private final MenuItemRepository menuItemRepository;
    private final ModelMapper modelMapper;

    @Override                //get
    public List<MenuDto> getMenuItems() {

        List<MenuItem> menuItems = menuItemRepository.findAll();

        return menuItems.stream()
                .map(menuItem -> modelMapper.map(menuItem, MenuDto.class))
                .toList();
    }

    @Override  // POST
    public MenuDto createMenuItems(AddMenuRequestDto addMenuRequestDto) {

        MenuItem newMenu = modelMapper.map(addMenuRequestDto, MenuItem.class);

        MenuItem savedMenu = menuItemRepository.save(newMenu);
        return modelMapper.map(savedMenu, MenuDto.class);
    }

    @Override  // DELETE
    public void deleteMenuItemById(Long id) {
        MenuItem menuItem = menuItemRepository.findById(id).
                orElseThrow(() -> new RuntimeException("Menu Item Not found" + id));

        menuItemRepository.delete(menuItem);
    }

    @Override // PUT
    public MenuDto updateMenuById(Long id, AddMenuRequestDto addMenuRequestDto) {

        MenuItem menuItem = menuItemRepository.findById(id).
                orElseThrow(() -> new RuntimeException("Menu Item Not found" + id));
        // dto - entity
        modelMapper.map(addMenuRequestDto, menuItem);
        // save in db
        MenuItem saved = menuItemRepository.save(menuItem);

        return modelMapper.map(saved, MenuDto.class);

    }

    @Override     //PATCH
    public MenuDto updatePartialMenuItem(Long id, Map<String, Object> updates) {
        MenuItem menuItem = menuItemRepository.findById(id).
                orElseThrow(() -> new RuntimeException("Menu Item Not found" + id));
        updates.forEach((key, value) -> {
            switch (key) {
                case "price":
                    menuItem.setPrice((Integer) value);
                    break;

                case "name":
                    menuItem.setName((String) value);
                    break;

                case "available":
                    menuItem.setAvailable((Boolean) value);
                    break;

                default:
                    throw new IllegalArgumentException("Invalid field: " + key);
            }
        });
        MenuItem updatedMenu = menuItemRepository.save(menuItem);
        return modelMapper.map(updatedMenu, MenuDto.class);
    }
}
