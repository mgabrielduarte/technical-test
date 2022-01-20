package com.backendtest.inditex.zararetail.service;

import com.backendtest.inditex.zararetail.restmodel.ProductDetail;
import com.backendtest.inditex.zararetail.service.implementation.SimilarProductsServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestTemplate;
import org.webjars.NotFoundException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class SimilarProductsServiceImplTest {

    @Mock
    RestTemplate restTemplate;

    @InjectMocks
    SimilarProductsServiceImpl similarProductsService;

    @Before
    public void init() {
        ReflectionTestUtils.setField(similarProductsService, "url", "http://localhost:3001/product/");
        ReflectionTestUtils.setField(similarProductsService, "similarIdUrl", "{productId}/similarids");
        ReflectionTestUtils.setField(similarProductsService, "similarProductUrl", "{productId}");
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void getSimilarProductsOkResultTest() {
        String[] idList = {"2", "3", "4"};
        ProductDetail productDetail2 = createProductDetail("2", "Product 2", 15.99f, true);
        ProductDetail productDetail3 = createProductDetail("3", "Product 3", 20.10f, true);
        ProductDetail productDetail4 = createProductDetail("4", "Product 4", 67.99f, true);

        Map<String, String> map1 = new HashMap<>();
        map1.put("productId", "1");
        Map<String, String> map2 = new HashMap<>();
        map2.put("productId", "2");
        Map<String, String> map3 = new HashMap<>();
        map3.put("productId", "3");
        Map<String, String> map4 = new HashMap<>();
        map4.put("productId", "4");

        when(restTemplate.getForObject("http://localhost:3001/product/" + "{productId}/similarids", String[].class, map1))
                .thenReturn(idList);
        when(restTemplate.getForObject("http://localhost:3001/product/{productId}", ProductDetail.class, map2))
                .thenReturn(productDetail2);
        when(restTemplate.getForObject("http://localhost:3001/product/{productId}", ProductDetail.class, map3))
                .thenReturn(productDetail3);
        when(restTemplate.getForObject("http://localhost:3001/product/{productId}" , ProductDetail.class, map4))
                .thenReturn(productDetail4);

        List<ProductDetail> productDetailList = similarProductsService.getSimilarProducts("1");
        assertEquals(3, productDetailList.size());
    }

    @Test
    public void getSimilarProductsNotFoundExceptionTest() {
        String[] idList = {"2", "3"};
        ProductDetail productDetail2 = createProductDetail("2", "Product 2", 15.99f, true);

        Map<String, String> map = new HashMap<>();
        map.put("productId", "1");
        Map<String, String> map2 = new HashMap<>();
        map2.put("productId", "2");
        Map<String, String> map3 = new HashMap<>();
        map3.put("productId", "3");

        when(restTemplate.getForObject("http://localhost:3001/product/" + "{productId}/similarids", String[].class, map))
                .thenReturn(idList);
        when(restTemplate.getForObject("http://localhost:3001/product/{productId}", ProductDetail.class, map2))
                .thenReturn(productDetail2);
        when(restTemplate.getForObject("http://localhost:3001/product/{productId}", ProductDetail.class, map3))
                .thenThrow(NotFoundException.class);

//        List<ProductDetail> productDetailList = similarProductsService.getSimilarProducts("1");
        assertThrows(NotFoundException.class, () ->similarProductsService.getSimilarProducts("1"));
    }

    private ProductDetail createProductDetail(String id, String name, float price, boolean availability) {
        ProductDetail productDetail = new ProductDetail();
        productDetail.setId(id);
        productDetail.setName(name);
        productDetail.setPrice(price);
        productDetail.setAvailability(availability);
        return productDetail;
    }
}
