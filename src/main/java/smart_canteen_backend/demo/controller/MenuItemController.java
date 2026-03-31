package smart_canteen_backend.demo.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import smart_canteen_backend.demo.dto.MenuDto;

import smart_canteen_backend.demo.entity.MenuItem;
import smart_canteen_backend.demo.service.MenuItemService;

import java.util.List;

@RestController
@RequestMapping("/menu")
@RequiredArgsConstructor
public class MenuItemController {

    private final MenuItemService menuItemService;

    @GetMapping
    public ResponseEntity<List<MenuDto>> getMenuItems()
    {
        return ResponseEntity.status(HttpStatus.OK).body(menuItemService.getMenuItems());
    }
    


}
