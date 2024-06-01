package ru.shop.controller;

import org.springframework.web.bind.annotation.*;
import ru.shop.model.Order;
import ru.shop.model.ProductReturn;
import ru.shop.service.ProductReturnService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/return")
public class ProductReturnController {

    private final ProductReturnService service;

    public ProductReturnController(ProductReturnService service) {
        this.service = service;
    }

    @GetMapping
    public List<ProductReturn> getAll(){
        return service.getAll();
    }

    @GetMapping("/{id}")
    public ProductReturn getById(@PathVariable UUID id){
        return service.getById(id);
    }

    @PostMapping
    public void add(@RequestBody Order order, @PathVariable long count){
        service.add(order,count);
    }
}