package com.backendtest.inditex.zararetail.controllers;

import com.backendtest.inditex.zararetail.restmodel.ProductDetail;
import com.backendtest.inditex.zararetail.restmodel.SimilarProducts;
import com.backendtest.inditex.zararetail.service.SimilarProductsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
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

import java.util.List;

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
                            array = @ArraySchema(schema = @Schema(implementation = ProductDetail.class)),
                            examples = {
                                    @ExampleObject("[{\"id\":\"1\",\"name\":\"Shirt\",\"price\":9.99,\"availability\":true}, " +
                                            "{\"id\":\"2\",\"name\":\"Dress\",\"price\":19.99,\"availability\":true}]")
                            }) }),
            @ApiResponse(responseCode = "404", description = "Product Not found.",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "There was a problem. Please try again later.",
                    content = @Content)})
    @GetMapping("/{productId}/similar")
    public ResponseEntity<List<ProductDetail>> getSimilarProducts(@Parameter(description = "id of product") @PathVariable String productId) {
            return new ResponseEntity<>(similarProductsService.getSimilarProducts(productId), HttpStatus.OK);
    }
}
