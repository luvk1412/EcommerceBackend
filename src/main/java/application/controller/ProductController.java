package application.controller;

import application.model.HttpResponse;
import application.model.Product;
import application.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController {
    @Autowired
    ProductService productService;

    @RequestMapping(value="/add",method = RequestMethod.POST)
    public Product addProduct(@RequestBody Product product){
        return productService.addProduct(product);
    }

    @RequestMapping(value="/update/id={id}", method = RequestMethod.PUT)
    public HttpResponse updateProduct(@PathVariable Integer id, @RequestBody Product product){
        product.setId(id);
        return productService.updateProduct(product);
    }

    @RequestMapping(value="/quantity/update/id={id}&quantity={quantity}",method = RequestMethod.PUT)
    public HttpResponse updateProductQuantity(@PathVariable Integer id, @PathVariable Integer quantity){
        return productService.updateProductQuantity(id, quantity);
    }

    @RequestMapping(value="/search/{filter}/prefix={prefix}/page={pageNumber}&limit={limit}", method = RequestMethod.GET)
    public List<Product> searchProducts(@PathVariable String filter, @PathVariable String prefix, @PathVariable int pageNumber, @PathVariable int limit){
        return productService.searchProducts(filter, prefix, pageNumber, limit);
    }

    @RequestMapping(value="/delete/id={id}",method = RequestMethod.DELETE)
    public HttpResponse deleteProduct(@PathVariable Integer id){
        return productService.deleteProduct(id);
    }
}
