package smart_canteen_backend.demo.service;


import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import smart_canteen_backend.demo.dto.AddMenuRequestDto;
import smart_canteen_backend.demo.dto.MenuDto;
import smart_canteen_backend.demo.entity.MenuItem;
import smart_canteen_backend.demo.repository.MenuItemRepository;

import java.util.List;

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

    @Override
    public MenuDto createMenuItems(AddMenuRequestDto addMenuRequestDto) {

        MenuItem newMenu = modelMapper.map(addMenuRequestDto, MenuItem.class);

        MenuItem savedMenu = menuItemRepository.save(newMenu);
        return modelMapper.map(savedMenu, MenuDto.class);
    }

    @Override
    public void deleteMenuItemById(Long id) {
        MenuItem menuItem = menuItemRepository.findById(id).
                orElseThrow(() -> new RuntimeException("Menu Item Not found" + id));

        menuItemRepository.delete(menuItem);
    }
}

