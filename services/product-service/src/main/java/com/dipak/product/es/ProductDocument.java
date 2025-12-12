package com.dipak.product.es;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.*;
import lombok.*;

@Document(indexName = "products")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductDocument {
    @Id private Long id;
    @Field(type = FieldType.Text, analyzer = "standard") private String title;
    @Field(type = FieldType.Text, analyzer = "standard") private String description;
    @Field(type = FieldType.Double) private Double price;
    @Field(type = FieldType.Keyword) private String brand;
    @Field(type = FieldType.Keyword) private String category;
    @Field(type = FieldType.Double) private Double averageRating;
}
