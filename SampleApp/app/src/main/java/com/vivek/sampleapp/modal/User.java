package com.vivek.sampleapp.modal;

import android.support.annotation.NonNull;
import android.support.annotation.Size;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public class User implements Serializable{

	private static final long serialVersionUID = 1L;
	private Long id;
	@NonNull
	@Size(max=32)
	private String username;
	@NonNull
	@Size(max=16, min = 6)
	private String password;
	@NonNull
	private String mqttTopicId;
	@NonNull
	private String mac;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getMqttTopicId() {
		return mqttTopicId;
	}

	public void setMqttTopicId(String mqttTopicId) {
		this.mqttTopicId = mqttTopicId;
	}

	public String getMac() {
		return mac;
	}

	public void setMac(String mac) {
		this.mac = mac;
	}


}
