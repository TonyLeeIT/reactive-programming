package org.example.reactiveprograming.dto;

import lombok.Builder;

@Builder
public record ProductDTO(String id,
                         String name,
                         int qty,
                         long price) {
}
