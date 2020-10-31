package com.IcecreamApp.DTO;

import java.util.List;

public class JwtDTO {
	
	private String token;
	private String type;
	private String username;
	private List<String> roles;

	public JwtDTO(String accessToken, String type, String username, List<String> roles) {
		this.token = accessToken;
		this.type = type;
		this.username = username;
		this.roles = roles;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public List<String> getRoles() {
		return roles;
	}

	public void setRoles(List<String> roles) {
		this.roles = roles;
	}
	
}