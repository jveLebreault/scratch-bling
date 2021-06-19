package com.scratchbling.repository;

import com.scratchbling.model.Item;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ItemRepository extends PagingAndSortingRepository<Item, Long> {
    Page<Item> findByNameIgnoreCaseContaining(String name, Pageable pageable);
}
