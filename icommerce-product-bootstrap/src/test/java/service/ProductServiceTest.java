package service;

import com.icommerce.product.application.dto.ProductDTO;
import com.icommerce.product.application.dto.ProductSearchCriteria;
import com.icommerce.product.application.dto.SearchCriteria;
import com.icommerce.product.application.service.ProductService;
import com.icommerce.product.application.service.UserService;
import com.icommerce.product.domain.entity.Product;
import com.icommerce.product.domain.event.*;
import com.icommerce.product.domain.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.catchThrowable;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {

    @Mock
    ProductRepository productRepository;

    @Mock
    List<ProductSearchCriteria> criteriaList;

    @Mock
    UserService userService;

    @Mock
    MongoTemplate mongoTemplate;

    @Mock
    UserActivitiesHistoricalEventPublisher userActivitiesHistoricalEventPublisher;

    @Mock
    ProductChangelogHistoricalEventPublisher productChangelogHistoricalEventPublisher;

    @InjectMocks
    ProductService productService;

    @Test
    public void save_should_delegate_to_repository() {
        // Given
        ProductDTO productDTO = mock(ProductDTO.class);
        Product savedProduct = mock(Product.class);
        when(productRepository.save(any(Product.class))).thenReturn(savedProduct);
        // When
        Product result = productService.save(productDTO);
        // Then
        assertThat(result).isSameAs(savedProduct);
        verify(productRepository).save(any(Product.class));
        verify(userActivitiesHistoricalEventPublisher).publish(any(UserActivitiesHistoricalEvent.class));
        verify(productChangelogHistoricalEventPublisher).publish(any(ProductChangelogHistoricalEvent.class));
    }

    @Test
    public void update_should_throw_not_found_if_id_incorrect() {
        // Given
        ProductDTO product = new ProductDTO();
        product.setId("1");
        when(productRepository.findById("1")).thenReturn(Optional.empty());
        // When
        Throwable throwable = catchThrowable(() -> productService.update(product));
        // Then
        assertThat(throwable).isInstanceOf(RuntimeException.class);
        assertThat(throwable).hasMessage("Could not found product with id 1");
        verify(productRepository).findById("1");
        verify(productRepository, never()).save(any(Product.class));
        verify(userActivitiesHistoricalEventPublisher).publish(any(UserActivitiesHistoricalEvent.class));
        verify(productChangelogHistoricalEventPublisher, never()).publish(any(ProductChangelogHistoricalEvent.class));
    }

    @Test
    public void deleteBy_should_delegate_to_repository() {
        // When
        productService.deleteBy("id");
        // Then
        verify(productRepository).deleteById("id");
        verify(userActivitiesHistoricalEventPublisher).publish(any(UserActivitiesHistoricalEvent.class));
    }

    @Test
    public void findDetailBy_should_delegate_to_repository() {
        // Given
        Product product = mock(Product.class);
        when(productRepository.findById("id")).thenReturn(Optional.of(product));
        // When
        Optional<Product> result = productService.findDetailBy("id");
        // Then
        assertThat(result.get()).isSameAs(product);
        verify(productRepository).findById("id");
        verify(userActivitiesHistoricalEventPublisher).publish(any(UserActivitiesHistoricalEvent.class));
    }

    @Test
    public void search_should_deletgate_mongo_template() {
        // Given
        Pageable pageable = PageRequest.of(1, 10);
        SearchCriteria searchCriteria = mock(SearchCriteria.class);
        List resultList = Arrays.asList(mock(Product.class));
        when(mongoTemplate.find(any(Query.class), eq(Product.class))).thenReturn(resultList);
        // When
        Page<Product> result = productService.search(searchCriteria, pageable);
        // Then
        assertThat(result.getContent()).isEqualTo(resultList);
        verify(productRepository, never()).findAll();
        verify(userActivitiesHistoricalEventPublisher).publish(any(UserActivitiesHistoricalEvent.class));
    }
}
