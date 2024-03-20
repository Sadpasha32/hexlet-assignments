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

    @GetMapping
    public List<Product> getByParams(@RequestParam(name = "min", required = false) Integer min, @RequestParam(name = "max", required = false) Integer max) {
        var sort = Sort.by(Sort.Order.asc("price"));
        if (max != null && min != null) {
            return productRepository.findByPriceBetween(min, max, sort);
        } else if (min != null) {
            return productRepository.findByPriceGreaterThanEqual(min, sort);
        } else if (max != null) {
            return productRepository.findByPriceLessThanEqual(max, sort);
        } else {
            return productRepository.findAll(sort);
        }
    }

    @GetMapping(path = "/{id}")
    public Product show(@PathVariable long id) {

        var product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product with id " + id + " not found"));

        return product;
    }
}
