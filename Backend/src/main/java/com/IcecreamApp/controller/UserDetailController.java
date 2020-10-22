package com.IcecreamApp.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.IcecreamApp.entity.UserDetail;
import com.IcecreamApp.service.UserDetailService;

@RestController
public class UserDetailController {
	
	private UserDetailService userDetailService;
	
	public UserDetailController(UserDetailService userDetailService) {
		this.userDetailService = userDetailService;
	}

    @GetMapping(value = "/user-details")
    public ResponseEntity<List<UserDetail>> getAllUserDetails() {        
    	return userDetailService.readAll();
    }

    @GetMapping(value = "/user-details/{id}")
    public ResponseEntity<UserDetail> getUserDetailById(@PathVariable("id") Long id) {
    	return userDetailService.readById(id);
    }

    @PostMapping(value = "/user-details")
    public ResponseEntity<UserDetail> createUserDetail(@RequestBody UserDetail userDetail) {
    	return this.userDetailService.create(userDetail);
    }

    @PutMapping(value = "/user-details/{id}")
    public ResponseEntity<UserDetail> updateUserDetail(@PathVariable("id") Long id, @RequestBody UserDetail userDetail) {
    	return this.userDetailService.update(id, userDetail);
    }

    @DeleteMapping(value = "/user-details/{id}")
    public ResponseEntity<UserDetail> deleteUserDetail(@PathVariable("id") Long id) {
    	return this.userDetailService.delete(id);
    }
}