package com.selfhy.bus.payload.request;

import javax.validation.constraints.NotBlank;

public class GetTripByStopRequest {
	private Long sourceStopid;
	
	private Long destStopId;

	
	public GetTripByStopRequest() {
		
	}

	public GetTripByStopRequest(@NotBlank Long sourceStopid, @NotBlank Long destStopId) {
		this.sourceStopid = sourceStopid;
		this.destStopId = destStopId;
	}

	public Long getSourceStopid() {
		return sourceStopid;
	}

	public void setSourceStopid(Long sourceStopid) {
		this.sourceStopid = sourceStopid;
	}

	public Long getDestStopId() {
		return destStopId;
	}

	public void setDestStopId(Long destStopId) {
		this.destStopId = destStopId;
	}
}
