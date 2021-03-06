package com.selfhy.bus.payload.request;
import io.swagger.annotations.ApiModelProperty;

public class UserUpdatePasswordRequest {
    @ApiModelProperty(hidden = true)
	private Long id;

    private String password;

    public UserUpdatePasswordRequest() {
	}

	public UserUpdatePasswordRequest(String password) {
		this.password = password;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

    public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
