package smart_canteen_backend.demo.service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import smart_canteen_backend.demo.entity.MenuItem;
import smart_canteen_backend.demo.repository.MenuItemRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MenuItemServiceImpl implements MenuItemService {
    private final MenuItemRepository menuItemRepository;

    @Override
    public List<MenuItem> getAllMenuItems() {
        List<MenuItem> menuItems = menuItemRepository.findAll();

        return menuItems;
    }
}
