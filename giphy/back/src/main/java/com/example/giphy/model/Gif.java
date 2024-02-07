package com.example.giphy.model;

public class Gif {

	private String url;
	private String username;
	private String title;
	
	public Gif(String url, String username, String title) {
		this.url = url;
		this.username = username;
		this.title = title;
	}
	
	public String getUrl() {
		return url;
	}

	public String getUsername() {
		return username;
	}

	public String getTitle() {
		return title;
	}
	
}
