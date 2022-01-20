package com.backendtest.inditex.zararetail.service;

import com.backendtest.inditex.zararetail.restmodel.ProductDetail;
import reactor.core.publisher.Flux;

import java.util.List;

public interface SimilarProductsService {

    Flux<ProductDetail> getSimilarProducts(String id);
}
