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
import java.util.Map;


@RestController
@RequestMapping("/menu")
@RequiredArgsConstructor
public class MenuItemController {

    private final MenuItemService menuItemService;


    @GetMapping
    public ResponseEntity<List<MenuDto>> getMenuItems() {
        return ResponseEntity.status(HttpStatus.OK).body(menuItemService.getMenuItems());
    }

    @PostMapping

    public ResponseEntity<MenuDto> createMenuItems(@RequestBody @Valid AddMenuRequestDto addMenuRequestDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(menuItemService.createMenuItems(addMenuRequestDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMenuItem(@PathVariable Long id) {
        menuItemService.deleteMenuItemById(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")

    public ResponseEntity<MenuDto> updateMenuItem(@PathVariable Long id, @RequestBody AddMenuRequestDto addMenuRequestDto) {
        MenuDto updatedItem = menuItemService.updateMenuById(id, addMenuRequestDto);

        return ResponseEntity.ok(updatedItem);

    }

    @PatchMapping("/{id}")
    public ResponseEntity<MenuDto> updatePartialMenuItem(@PathVariable Long id, @RequestBody Map<String, Object> updates)
    {
        return ResponseEntity.ok(menuItemService.updatePartialMenuItem(id,updates));
    }
    @PutMapping("/{id}/availability")
    public ResponseEntity<?> updateAvailability(
            @PathVariable Long id,
            @RequestParam boolean available
    ) {
        menuItemService.updateAvailability(id, available);
        return ResponseEntity.ok("Updated");
    }

}

