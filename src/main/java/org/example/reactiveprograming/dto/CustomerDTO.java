package org.example.reactiveprograming.dto;

import lombok.Getter;

public record CustomerDTO(@Getter int id,
                          @Getter String name) {
}
