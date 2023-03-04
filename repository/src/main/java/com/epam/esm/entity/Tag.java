package com.epam.esm.entity;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Tag implements Comparable<Tag> {
    private Long id;
    private String name;

    @Override
    public int compareTo(Tag tag) {
        return this.getName().compareTo(tag.getName());
    }
}
