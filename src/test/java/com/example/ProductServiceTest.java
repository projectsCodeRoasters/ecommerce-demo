package com.example;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    private ProductService serviceWithMockedRepo() {
        ProductService service = new ProductService();
        try {
            var field = ProductService.class.getDeclaredField("productRepository");
            field.setAccessible(true);
            field.set(service, productRepository);
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException(e);
        }
        return service;
    }

    @Test
    void dado_cliente_vip_con_producto_caro_aplica_descuento_30_por_ciento() {
        ProductService service = serviceWithMockedRepo();
        Product product = new Product("Portátil", new BigDecimal("200.00"), 10);
        when(productRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

        service.applyDiscount(List.of(product), 1);

        assertThat(product.getPrice()).isEqualByComparingTo("140.00");
    }

    @Test
    void dado_cliente_vip_con_producto_barato_aplica_descuento_20_por_ciento() {
        ProductService service = serviceWithMockedRepo();
        Product product = new Product("Ratón", new BigDecimal("50.00"), 10);
        when(productRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

        service.applyDiscount(List.of(product), 1);

        assertThat(product.getPrice()).isEqualByComparingTo("40.00");
    }

    @Test
    void dado_cliente_vip_con_stock_alto_aplica_descuento_extra_5_por_ciento() {
        ProductService service = serviceWithMockedRepo();
        Product product = new Product("Teclado", new BigDecimal("50.00"), 60);
        when(productRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

        service.applyDiscount(List.of(product), 1);

        assertThat(product.getPrice()).isEqualByComparingTo("38.00");
    }

    @Test
    void dado_cliente_regular_aplica_descuento_10_por_ciento() {
        ProductService service = serviceWithMockedRepo();
        Product product = new Product("Monitor", new BigDecimal("100.00"), 10);
        when(productRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

        service.applyDiscount(List.of(product), 2);

        assertThat(product.getPrice()).isEqualByComparingTo("90.00");
    }

    @Test
    void dado_cliente_regular_con_stock_bajo_recarga_10_por_ciento() {
        ProductService service = serviceWithMockedRepo();
        Product product = new Product("Webcam", new BigDecimal("100.00"), 3);
        when(productRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

        service.applyDiscount(List.of(product), 2);

        assertThat(product.getPrice()).isEqualByComparingTo("99.00");
    }

    @Test
    void dado_liquidacion_aplica_descuento_50_por_ciento() {
        ProductService service = serviceWithMockedRepo();
        Product product = new Product("Auriculares", new BigDecimal("100.00"), 10);
        when(productRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

        service.applyDiscount(List.of(product), 3);

        assertThat(product.getPrice()).isEqualByComparingTo("50.00");
    }

    @Test
    void dado_tipo_de_cliente_desconocido_no_modifica_el_precio() {
        ProductService service = serviceWithMockedRepo();
        Product product = new Product("Cámara", new BigDecimal("100.00"), 10);
        when(productRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

        service.applyDiscount(List.of(product), 99);

        assertThat(product.getPrice()).isEqualByComparingTo("100.00");
    }

    @Test
    void dado_lista_nula_devuelve_optional_vacio() {
        ProductService service = serviceWithMockedRepo();

        Optional<Product> result = service.applyDiscount(null, 1);

        assertThat(result).isEmpty();
    }

    @Test
    void dado_lista_con_elementos_nulos_los_ignora() {
        ProductService service = serviceWithMockedRepo();
        Product product = new Product("Impresora", new BigDecimal("100.00"), 10);
        when(productRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

        List<Product> products = new java.util.ArrayList<>();
        products.add(null);
        products.add(product);

        Optional<Product> result = service.applyDiscount(products, 3);

        assertThat(result).contains(product);
        assertThat(product.getPrice()).isEqualByComparingTo("50.00");
    }

    @Test
    void dado_nombre_existente_encuentra_el_producto_ignorando_mayusculas() {
        ProductService service = serviceWithMockedRepo();
        Product product = new Product("Portátil", new BigDecimal("100.00"), 10);
        when(productRepository.findAll()).thenReturn(List.of(product));

        Optional<Product> result = service.findByName("PORTÁTIL");

        assertThat(result).contains(product);
    }

    @Test
    void dado_nombre_inexistente_no_encuentra_el_producto() {
        ProductService service = serviceWithMockedRepo();
        when(productRepository.findAll()).thenReturn(List.of());

        Optional<Product> result = service.findByName("Inexistente");

        assertThat(result).isEmpty();
    }

    @Test
    void dado_stock_suficiente_hasEnoughStock_devuelve_true() {
        ProductService service = serviceWithMockedRepo();
        Product product = new Product("Silla", new BigDecimal("50.00"), 10);
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));

        boolean result = service.hasEnoughStock(1L, 5);

        assertThat(result).isTrue();
    }

    @Test
    void dado_stock_insuficiente_hasEnoughStock_devuelve_false() {
        ProductService service = serviceWithMockedRepo();
        Product product = new Product("Mesa", new BigDecimal("50.00"), 2);
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));

        boolean result = service.hasEnoughStock(1L, 5);

        assertThat(result).isFalse();
    }

    @Test
    void dado_producto_inexistente_hasEnoughStock_devuelve_false() {
        ProductService service = serviceWithMockedRepo();
        when(productRepository.findById(1L)).thenReturn(Optional.empty());

        boolean result = service.hasEnoughStock(1L, 5);

        assertThat(result).isFalse();
    }
}
