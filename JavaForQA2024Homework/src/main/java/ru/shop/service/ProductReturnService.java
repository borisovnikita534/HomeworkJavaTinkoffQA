package ru.shop.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.shop.exception.BadProductReturnCountException;
import ru.shop.exception.EntityNotFoundException;
import ru.shop.model.Order;
import ru.shop.model.ProductReturn;
import ru.shop.repository.ProductReturnRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProductReturnService {
    private final ProductReturnRepository repository;

    public void add(Order order, long count) {
        if (order.getCount() < count) {
            throw new BadProductReturnCountException();
        }
        repository.save(new ProductReturn(UUID.randomUUID(), order.getId(), LocalDate.now(), count));
    }

    public List<ProductReturn> getAll(){
        return repository.findAll();
    }

    public ProductReturn getById(UUID id){
        return repository.findById(id).orElseThrow(EntityNotFoundException::new);
    }
}