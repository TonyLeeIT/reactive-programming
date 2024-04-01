package org.example.reactiveprograming.model;

import lombok.*;

@Data
@Builder
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class Product extends BaseModel {
    private String id;
    private String name;
    private Integer qty;
    private Long price;
}
