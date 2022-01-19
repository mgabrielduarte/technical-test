package com.backendtest.inditex.zararetail.controllers;

import com.backendtest.inditex.zararetail.restmodel.SimilarProducts;
import com.backendtest.inditex.zararetail.service.SimilarProductsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/product")
@Tag(name = "SimilarProducts", description = "Similar Products API.")
public class SimilarProductsController {

    @Autowired
    private SimilarProductsService similarProductsService;

    @Operation(operationId = "get-product-similar", summary = "Similar products")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = SimilarProducts.class,
                                    description = "List of similar products to a given one ordered by similarity")) }),
            @ApiResponse(responseCode = "404", description = "Product Not found",
                    content = @Content) })
    @GetMapping("/{productId}/similar")
    public ResponseEntity<?> getSimilarProducts(@Parameter(description = "id of product") @PathVariable String productId) {
        SimilarProducts similarProducts = similarProductsService.getSimilarProducts(productId);
        if ( !similarProducts.getItems().isEmpty() ) {
            return new ResponseEntity<>(similarProducts, HttpStatus.OK);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{ description: Product Not found }");
        }
    }
}
