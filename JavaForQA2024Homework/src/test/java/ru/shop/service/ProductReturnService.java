package ru.shop.service;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import ru.shop.exception.BadProductReturnCountException;
import ru.shop.exception.EntityNotFoundException;
import ru.shop.model.Order;
import ru.shop.model.ProductReturn;
import ru.shop.repository.ProductReturnRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ProductReturnServiceTest {

    private final ProductReturnRepository repository = Mockito.mock(ProductReturnRepository.class);
    private final ProductReturnService service = new ProductReturnService(repository);

    @Test
    void shouldSaveProductReturn() {
        // given
        var order = new Order(UUID.randomUUID(), UUID.randomUUID(), UUID.randomUUID(), 3L, 200L);
        // when
        service.add(order, 3L);
        // then
        Mockito.verify(repository, Mockito.times(1)).save(ArgumentMatchers.any());
    }

    @Test
    void shouldThrowWhenReturningCountMoreOrderCount() {
        var order = new Order(UUID.randomUUID(), UUID.randomUUID(), UUID.randomUUID(), 2L, 200L);
        assertThatThrownBy(() -> service.add(order, 3))
                .isInstanceOf(BadProductReturnCountException.class);
    }

    @Test
    void shouldGetAllProductReturns() {
        //given
        var productReturn1 = new ProductReturn(UUID.randomUUID(), UUID.randomUUID(), LocalDate.now(), 9);
        var productReturn2 = new ProductReturn(UUID.randomUUID(), UUID.randomUUID(), LocalDate.now(), 4);
        Mockito.when(repository.findAll()).thenReturn(
                List.of(productReturn1, productReturn2)
        );
        // when
        List<ProductReturn> returns = service.getAll();
        //then
        Mockito.verify(repository).findAll();
        assertThat(returns).isEqualTo(List.of(productReturn1, productReturn2));
    }

    @Test
    void shouldGetProductReturnById() {
        //given
        UUID id = UUID.randomUUID();
        var mockedProductReturn = new ProductReturn(id, UUID.randomUUID(), LocalDate.now(), 9);
        Mockito.when(repository.findById(id))
                .thenReturn(Optional.of(mockedProductReturn));

        // when
        ProductReturn productReturn = service.getById(id);

        // then
        assertThat(productReturn).isEqualTo(mockedProductReturn);
    }

    @Test
    void shouldThrowWhenProductReturnNotFound() {
        assertThatThrownBy(
                () -> service.getById(UUID.randomUUID())
        ).isInstanceOf(EntityNotFoundException.class);
    }
}