package com.backendtest.inditex.zararetail.service;

import com.backendtest.inditex.zararetail.restmodel.ProductDetail;

import java.util.List;

public interface SimilarProductsService {

    List<ProductDetail> getSimilarProducts(String id);
}
