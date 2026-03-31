package smart_canteen_backend.demo.service;


import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import smart_canteen_backend.demo.dto.MenuDto;
import smart_canteen_backend.demo.entity.MenuItem;
import smart_canteen_backend.demo.repository.MenuItemRepository;

import java.util.List;

import static java.util.stream.Collectors.toList;

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
}
