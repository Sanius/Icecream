package com.IcecreamApp.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.IcecreamApp.entity.Order;
import com.IcecreamApp.exception.ResourceNotFoundException;
import com.IcecreamApp.repository.OrderRepository;

@Service
public class OrderService extends GeneralService<Order, OrderRepository> {

	public OrderService(OrderRepository repository) {
		super(repository);
		this.entityName = "order";
	} 

	@Override
	public Order update(long id, Order entity) {

//		Optional<Order> currentEntityWrapper = this.repository.findById(id);
//
//	    if (!currentEntityWrapper.isPresent()) {
//	        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//	    }
//	    entity.setForeignKey(currentEntityWrapper.get());
//	    this.repository.saveAndFlush(entity);
//		return new ResponseEntity<>(entity, HttpStatus.OK);
		
		Optional<Order> currentEntityWrapper = repository.findById(id);
		if (!currentEntityWrapper.isPresent()) {
			throw new ResourceNotFoundException(this.entityName, id);
	    }
		entity.setForeignKey(currentEntityWrapper.get());
		return this.repository.save(entity);
	}
}
