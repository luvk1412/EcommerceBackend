package application.controller;

import application.model.HttpResponse;
import application.model.Product;
import application.model.ProductUpdateObject;
import application.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@Validated
@RequestMapping("/product")
public class ProductController {
    @Autowired
    ProductService productService;

    @PostMapping("/add")
    public Product addProduct(@Valid @RequestBody Product product){
        return productService.addProduct(product);
    }

    @PutMapping("/update/id={id}")
    public void updateProduct(@PathVariable @NotNull Integer id, @Valid @RequestBody ProductUpdateObject product){
        product.setId(id);
        productService.updateProduct(product);
    }

    @GetMapping("/search/{filter}/prefix={prefix}/page={pageNumber}&limit={limit}")
    public List<Product> searchProducts(@PathVariable @NotNull String filter, @PathVariable String prefix, @PathVariable @NotNull @Min(1) Integer pageNumber, @PathVariable @NotNull @Min(1) Integer limit){
        return productService.searchProducts(filter, prefix, pageNumber, limit);
    }

    @DeleteMapping(value="/delete/id={id}")
    public void deleteProduct(@PathVariable @NotNull Integer id){
        productService.deleteProduct(id);
    }
}
