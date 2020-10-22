package com.IcecreamApp.DTO;

import java.util.Set;

public class ProductDTO extends BaseDTO {

	private String name;
	
	private String description;

	private String image;
	
	private double price;
	
	private int status;

    private Set<FeedbackDTO> feedbacks;
    
    private Set<OrderDetailDTO> orderDetails;

	private CategoryDTO category;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public Set<FeedbackDTO> getFeedbacks() {
		return feedbacks;
	}

	public void setFeedbacks(Set<FeedbackDTO> feedbacks) {
		this.feedbacks = feedbacks;
	}

	public Set<OrderDetailDTO> getOrderDetails() {
		return orderDetails;
	}

	public void setOrderDetails(Set<OrderDetailDTO> orderDetails) {
		this.orderDetails = orderDetails;
	}

	public CategoryDTO getCategory() {
		return category;
	}

	public void setCategory(CategoryDTO category) {
		this.category = category;
	}
	
}
