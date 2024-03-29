package exercise.mapper;

import exercise.dto.ProductCreateDTO;
import exercise.dto.ProductDTO;
import exercise.dto.ProductUpdateDTO;
import exercise.model.Product;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public abstract class ProductMapper {

    @Mapping(target = "title",source = "name")
    @Mapping(target = "price",source = "cost")
    @Mapping(target = "vendorCode",source = "barcode")
    abstract public ProductDTO toDTO(Product product);

    @Mapping(target = "name",source = "title")
    @Mapping(target = "cost",source = "price")
    @Mapping(target = "barcode",source = "vendorCode")
    abstract public Product toEntity(ProductCreateDTO productDTO);

    @Mapping(target = "cost",source = "price")
    abstract public void update(ProductUpdateDTO productUpdateDTO, @MappingTarget Product product);

}
