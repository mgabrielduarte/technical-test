package com.backendtest.inditex.zararetail.restmodel;

import java.util.List;

public class SimilarProducts {

    private List<ProductDetail> items;

    public SimilarProducts() {
    }

    public List<ProductDetail> getItems() {
        return items;
    }

    public SimilarProducts setItems(List<ProductDetail> items) {
        this.items = items;
        return this;
    }

    @Override
    public String toString() {
        return "SimilarProducts{" +
                "items=" + items +
                '}';
    }
}
