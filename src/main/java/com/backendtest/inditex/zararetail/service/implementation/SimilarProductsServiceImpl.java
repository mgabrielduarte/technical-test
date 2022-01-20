package com.backendtest.inditex.zararetail.service.implementation;

import com.backendtest.inditex.zararetail.restmodel.ProductDetail;
import com.backendtest.inditex.zararetail.service.SimilarProductsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.webjars.NotFoundException;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

import static com.backendtest.inditex.zararetail.common.utils.convertArrayToList;
import static com.backendtest.inditex.zararetail.common.utils.urlBuilder;

@Service
public class SimilarProductsServiceImpl implements SimilarProductsService {

    public static final Logger logger = LoggerFactory.getLogger(SimilarProductsServiceImpl.class);
    public static final String NOT_FOUND_MSG = "Product Not found.";

    @Autowired
    private RestTemplate restTemplate;

    @Value("${platform.url}")
    private String url;

    @Value("${platform.similarId}")
    private String similarIdUrl;

    @Value("${platform.similarProduct}")
    private String similarProductUrl;

    @Override
    public List<ProductDetail> getSimilarProducts(String productId) {
        List<ProductDetail> similarProductsList = new ArrayList<>();
        List<String> similarProductIds = getSimilarProductIds(productId);
        if (!similarProductIds.isEmpty()) {
            try {
                List<CompletableFuture<ProductDetail>> futures = similarProductIds.stream()
                    .map(item -> {
                        return CompletableFuture.supplyAsync(() -> {
                            return getProductDetail(item);
                        });
                    })
                    .collect(Collectors.toList());

            similarProductsList = futures.stream()
                    .map(CompletableFuture::join)
                    .collect(Collectors.toList());
            } catch (Exception e) {
                throw new NotFoundException(NOT_FOUND_MSG);
            }
        }
        return similarProductsList;
    }

    private List<String> getSimilarProductIds(String productId) {
        String similarIdsRequestUrl = urlBuilder(url, similarIdUrl);
        Map<String, String> map = new HashMap<>();
        map.put("productId", productId);
         String[] result = restTemplate.getForObject(similarIdsRequestUrl, String[].class, map);

        return convertArrayToList(result);
    }

    private ProductDetail getProductDetail(String productId) {
        String similarProductRequestUrl = urlBuilder(url, similarProductUrl);
        Map<String, String> map = new HashMap<>();
        map.put("productId", productId);

        return restTemplate.getForObject(similarProductRequestUrl, ProductDetail.class, map);
    }
}
