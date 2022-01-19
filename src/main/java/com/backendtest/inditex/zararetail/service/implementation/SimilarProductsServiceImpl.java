package com.backendtest.inditex.zararetail.service.implementation;

import com.backendtest.inditex.zararetail.restmodel.ProductDetail;
import com.backendtest.inditex.zararetail.restmodel.SimilarProduct;
import com.backendtest.inditex.zararetail.restmodel.SimilarProducts;
import com.backendtest.inditex.zararetail.service.SimilarProductsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Service
public class SimilarProductsServiceImpl implements SimilarProductsService {

    /** The logger. */
    public static final Logger logger = LoggerFactory.getLogger(SimilarProductsServiceImpl.class);

    @Autowired
    private RestTemplate restTemplate;

    @Value("${platform.url}")
    private String url;

    @Value("${platform.similarId}")
    private String similarIdUrl;

    @Value("${platform.similarProduct}")
    private String similarProductUrl;

    @Override
    public SimilarProducts getSimilarProducts(String productId) {
        List<ProductDetail> similarProductsList = new ArrayList<>();
            SimilarProduct similarProductIds = getSimilarProductIds(productId);
            if (!similarProductIds.getItems().isEmpty()) {
                try {
                    similarProductIds.getItems().parallelStream().forEach(item -> {
                        ProductDetail productDetail = getProductDetail(item);
                        similarProductsList.add(productDetail);
                    });

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        return new SimilarProducts().setItems(similarProductsList);
    }

    private SimilarProduct getSimilarProductIds(String productId) {
        String similarIdsRequestUrl = urlBuilder(url, similarIdUrl);
        Map<String, String> map = new HashMap<>();
        map.put("productId", productId);

        return restTemplate.getForObject(similarIdsRequestUrl, SimilarProduct.class, map);
    }

    private ProductDetail getProductDetail(String productId) {
        String similarProductRequestUrl = urlBuilder(url, similarProductUrl);
        Map<String, String> map = new HashMap<>();
        map.put("productId", productId);

        return restTemplate.getForObject(similarProductRequestUrl, ProductDetail.class, map);
    }

    private String urlBuilder(String url, String endpoint) {
        return new StringBuilder().append(url).append(endpoint).toString();
    }
}
