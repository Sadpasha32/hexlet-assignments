package exercise.specification;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import exercise.dto.ProductParamsDTO;
import exercise.model.Product;

@Component
public class ProductSpecification {

    public Specification<Product> build(ProductParamsDTO productParamsDTO) {
        return witCategoryId(productParamsDTO.getCategoryId())
                .and(witTitleCont(productParamsDTO.getTitleCont()))
                .and(withPriceGreater(productParamsDTO.getPriceGt()))
                .and(withPriceLess(productParamsDTO.getPriceLt()))
                .and(withRatingGreater(productParamsDTO.getRatingGt()));
    }

    private Specification<Product> witCategoryId(Long categoryId) {
        return (root, query, cb) -> categoryId == null ? cb.conjunction() : cb.equal(root.get("category").get("id"), categoryId);
    }

    private Specification<Product> witTitleCont(String titleCont) {
        return (root, query, cb) -> titleCont == null ? cb.conjunction() : cb.like(root.get("title"), "%" + titleCont + "%");
    }

    private Specification<Product> withPriceLess(Integer price) {
        return (root, query, cb) -> price == null ? cb.conjunction() : cb.lessThan(root.get("price"), price);
    }

    private Specification<Product> withPriceGreater(Integer price) {
        return (root, query, cb) -> price == null ? cb.conjunction() : cb.greaterThan(root.get("price"), price);
    }

    private Specification<Product> withRatingGreater(Double rating) {
        return (root, query, cb) -> rating == null ? cb.conjunction() : cb.greaterThan(root.get("rating"), rating);
    }

}
