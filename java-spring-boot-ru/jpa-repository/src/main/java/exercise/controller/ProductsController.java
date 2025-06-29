package exercise.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.data.domain.Sort;

import java.util.List;

import exercise.model.Product;
import exercise.repository.ProductRepository;
import exercise.exception.ResourceNotFoundException;

@RestController
@RequestMapping("/products")
public class ProductsController {

    @Autowired
    private ProductRepository productRepository;

    // BEGIN
    @GetMapping(path = "")
    public List<Product> showByFilter(@RequestParam(required = false) Integer min, Integer max) {
        List<Product> productList;
        if ((min != null) & (max != null)) {
            productList = productRepository.findByPriceBetweenOrderByPrice(min, max);
        } else if (max != null) {
            productList = productRepository.findByPriceLessThanOrderByPrice(max);
        } else if (min != null) {
            productList = productRepository.findByPriceGreaterThanOrderByPrice(min);
        } else {
         productList = productRepository.findAll(Sort.by(Sort.Order.asc("price")));
        }
        return productList;
    }
    // END

    @GetMapping(path = "/{id}")
    public Product show(@PathVariable long id) {

        var product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product with id " + id + " not found"));

        return product;
    }
}
