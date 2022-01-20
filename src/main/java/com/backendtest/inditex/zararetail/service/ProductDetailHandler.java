package com.backendtest.inditex.zararetail.service;

import com.backendtest.inditex.zararetail.restmodel.ProductDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
public class ProductDetailHandler {

    @Autowired
    SimilarProductsService similarProductsService;

    public Mono<ServerResponse> getAllProducts(ServerRequest serverRequest) {
        String productId = serverRequest.pathVariable("productId");
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(similarProductsService.getSimilarProducts(productId), ProductDetail.class);
    }
}
