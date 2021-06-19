package com.scratchbling.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Set;

@Entity
@Data
@Table(name = "items")
public class Item {

    @Id
    @GeneratedValue
    private long id;

    @Column(name = "item_name")
    private String name;

    @Column(name = "item_description")
    private String description;

    @Column(name = "item_size")
    @Enumerated(EnumType.STRING)
    @ElementCollection
    private Set<ItemSize> size;

    @Column(name = "item_cost")
    private double price;
}
