package com.github.grupo3.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class RepoRequestDTO {

	private String name;

	private String description;

	@JsonProperty("private")
	private boolean privado;
}
