package com.backendtest.inditex.zararetail.service.implementation;

import com.backendtest.inditex.zararetail.restmodel.ProductDetail;
import com.backendtest.inditex.zararetail.service.SimilarProductsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import org.webjars.NotFoundException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.*;

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

    private WebClient webClient = WebClient.create(url);

    @Override
    public Flux<ProductDetail> getSimilarProducts(String productId) {
        Flux<ProductDetail> similarProductsList = null;
        List<String> similarProductIds = getSimilarProductIds(productId);
        if (!similarProductIds.isEmpty()) {
            try {
                logger.info("RETORNO POR EL FETCH");
                return fetchUsers(similarProductIds);
            } catch (Exception e) {
                logger.error(e.getMessage());
                throw new NotFoundException(NOT_FOUND_MSG);
            }
        }
//        return similarProductsList;
        logger.info("RETORNO NULL");
        return null;
    }

    private List<String> getSimilarProductIds(String productId) {
        String similarIdsRequestUrl = urlBuilder(url, similarIdUrl);
        Map<String, String> map = new HashMap<>();
        map.put("productId", productId);
         String[] result = restTemplate.getForObject(similarIdsRequestUrl, String[].class, map);

        return convertArrayToList(result);
    }

    private Mono<ProductDetail> getProductDetail(String productId) {
        String similarProductRequestUrl = urlBuilder(url, similarProductUrl);
        Map<String, String> map = new HashMap<>();
        map.put("productId", productId);
//
//        return restTemplate.getForObject(similarProductRequestUrl, ProductDetail.class, map);
        return webClient.get()
                .uri(similarProductRequestUrl, productId)
                .retrieve()
                .bodyToMono(ProductDetail.class);
    }

    public Flux<ProductDetail> fetchUsers(List<String> productIds) {
        return Flux.fromIterable(productIds)
                .flatMap(this::getProductDetail);
    }
}
