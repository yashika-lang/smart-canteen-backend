package smart_canteen_backend.demo.controller;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import smart_canteen_backend.demo.dto.AddMenuRequestDto;
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

    @PostMapping

    public ResponseEntity<MenuDto> createMenuItems(@RequestBody @Valid AddMenuRequestDto addMenuRequestDto)
    {
        return ResponseEntity.status(HttpStatus.CREATED).body(menuItemService.createMenuItems(addMenuRequestDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMenuItem(@PathVariable Long id){
        menuItemService.deleteMenuItemById(id);
        return ResponseEntity.noContent().build();


    }


}
