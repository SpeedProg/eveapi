package com.beimin.eveapi.response;

import java.time.OffsetDateTime;
import com.beimin.eveapi.handler.ApiError;

public class ApiResponse {
	private int version;
	private OffsetDateTime currentTime;
	private OffsetDateTime cachedUntil;
	private ApiError error;

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	public OffsetDateTime getCurrentTime() {
		return currentTime;
	}

	public void setCurrentTime(OffsetDateTime currentTime) {
		this.currentTime = currentTime;
	}

	public OffsetDateTime getCachedUntil() {
		return cachedUntil;
	}

	public void setCachedUntil(OffsetDateTime cachedUntil) {
		this.cachedUntil = cachedUntil;
	}

	public boolean hasError() {
		return error != null;
	}

	public ApiError getError() {
		return error;
	}

	public void setError(ApiError error) {
		this.error = error;
	}
}
