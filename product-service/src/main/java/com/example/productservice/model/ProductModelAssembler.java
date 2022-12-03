package com.example.productservice.model;

import com.example.productservice.controller.ProductController;
import org.springframework.beans.BeanUtils;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

/**
 * The ProductModelAssembler class extends the HATEOAS Representation Model and is required for pagination.
 * It converts the Product entity to the ProductModel.
 *
 * @author TannnerLankford1223
 * @since 2022-12-2
 */
@Component
public class ProductModelAssembler extends RepresentationModelAssemblerSupport<Product, ProductModel> {
    public ProductModelAssembler() {
        super(ProductController.class, ProductModel.class);
    }

    @Override
    public ProductModel toModel(Product entity) {
        ProductModel model = new ProductModel();
        BeanUtils.copyProperties(entity, model);
        return model;
    }
}
