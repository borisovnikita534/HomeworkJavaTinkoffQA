package ru.shop.service;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.shop.exception.EntityNotFoundException;
import ru.shop.model.Product;
import ru.shop.model.ProductType;
import ru.shop.repository.ProductRepository;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

class ProductServiceTest {
    private final ProductRepository repository = Mockito.mock();
    private final ProductService productService = new ProductService(repository);

    @Test
    void shouldGetProduct() {
        // given
        UUID productId = UUID.randomUUID();
        Product mockedProduct = new Product(productId, "productName", 300, ProductType.GOOD);
        when(repository.findById(productId)).thenReturn(Optional.of(mockedProduct));

        // when
        Product product = productService.getById(productId);

        // AssertJ
        assertThat(product).isEqualTo(mockedProduct);
    }
    @Test
    void shouldThrowWhenProductNotFound() {
        assertThatThrownBy(
                () -> productService.getById(UUID.randomUUID())
        ).isInstanceOf(EntityNotFoundException.class);
    }
}