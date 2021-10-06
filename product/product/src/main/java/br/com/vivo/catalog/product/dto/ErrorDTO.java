package br.com.vivo.catalog.product.dto;

public class ErrorDTO {

	private Integer status_code;
	private StringBuilder message;
	
	public ErrorDTO(Integer status_code, StringBuilder errorMessage) {
		this.status_code = status_code;
		this.message = errorMessage;
	}

	public Integer getStatus_code() {
		return status_code;
	}

	public StringBuilder getMessage() {
		return message;
	}
	
}
