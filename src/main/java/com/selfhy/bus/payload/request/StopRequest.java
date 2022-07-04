package com.selfhy.bus.payload.request;
import javax.validation.constraints.NotBlank;
import io.swagger.annotations.ApiModelProperty;

public class StopRequest {
	
	@ApiModelProperty(hidden = true)
	private Long id;

	private String code;

	private String name;

	private String detail;

	public StopRequest() {
	}

	public StopRequest(Long id, @NotBlank String code, @NotBlank String name, @NotBlank String detail) {
		this.id = id;
		this.code = code;
		this.name = name;
		this.detail = detail;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}


	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

}
