package com.github.grupo3.service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.grupo3.dto.request.RepoRequestDTO;
import com.github.grupo3.dto.resonse.RepoResponseDTO;

@Service
public class RepoService {

	@Autowired
	RestTemplate restTemplate;

	@Value("${token.bearer}")
	private String bearerToken;

	@Value("${service.url}")
	private String serviceUrl;

	/*----------------------------------------------------------------------------*/

	/**
	 * https://docs.github.com/es/rest/repos/repos?apiVersion=2022-11-28#list-repositories-for-a-user
	 * 
	 * @param username
	 * @return
	 */
	public List<RepoResponseDTO> getAll(String username) {

		RepoResponseDTO[] response = restTemplate.getForObject(serviceUrl + "/users/" + username + "/repos",
				RepoResponseDTO[].class);

		return Arrays.asList(response);
	}

	/**
	 * https://docs.github.com/es/rest/repos/repos?apiVersion=2022-11-28#create-a-repository-for-the-authenticated-user
	 * 
	 * @param repoRequestDTO
	 * @return
	 */
	public ResponseEntity<String> create(RepoRequestDTO repoRequestDTO) {

		ResponseEntity<String> response = null;
		String url = serviceUrl + "/user/repos";

		// Configuracion de los encabezados
		HttpHeaders headers = new HttpHeaders();
		headers.set("Content-Type", "application/json");
		headers.set("Authorization", "Bearer " + bearerToken);

		// Armado del body
		Map<String, Object> requestBody = new HashMap<>();
		requestBody.put("name", repoRequestDTO.getName());
		requestBody.put("description", repoRequestDTO.getDescription());
		requestBody.put("private", repoRequestDTO.isPrivado());

		ObjectMapper objectMapper = new ObjectMapper();

		HttpEntity<String> entity;

		try {
			entity = new HttpEntity<>(objectMapper.writeValueAsString(requestBody), headers);
			response = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);

		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return response;
	}

	/**
	 * https://docs.github.com/es/rest/repos/repos?apiVersion=2022-11-28#update-a-repository
	 *
	 * @param repoRequestDTO, owner, repo
	 * @return
	 */
	public ResponseEntity<String> update(RepoRequestDTO repoRequestDTO, String owner, String repo) {

		ResponseEntity<String> response = null;
		String url = serviceUrl + "/repos/" + owner + "/" + repo;

		// Configuracion de los encabezados
		HttpHeaders headers = new HttpHeaders();
		headers.set("Content-Type", "application/json");
		headers.set("Authorization", "Bearer " + bearerToken);

		// Armado del body
		Map<String, Object> requestBody = new HashMap<>();
		requestBody.put("name", repoRequestDTO.getName());
		requestBody.put("description", repoRequestDTO.getDescription());
		requestBody.put("private", repoRequestDTO.isPrivado());

		ObjectMapper objectMapper = new ObjectMapper();

		HttpEntity<String> entity;

		try {
			entity = new HttpEntity<>(objectMapper.writeValueAsString(requestBody), headers);
			response = restTemplate.exchange(url, HttpMethod.PATCH, entity, String.class);

		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return response;
	}

	/**
	 * https://docs.github.com/es/rest/repos/repos?apiVersion=2022-11-28#delete-a-repository
	 *
	 * @param owner, repo
	 * @return
	 */
	public ResponseEntity<String> delete(String owner, String repo) {

		ResponseEntity<String> response = null;
		String url = serviceUrl + "/repos/" + owner + "/" + repo;

		// Configuracion de los encabezados
		HttpHeaders headers = new HttpHeaders();
		headers.set("Content-Type", "application/json");
		headers.set("Authorization", "Bearer " + bearerToken);

		HttpEntity<String> entity;

		try {
			entity = new HttpEntity<>(headers);
			response = restTemplate.exchange(url, HttpMethod.DELETE, entity, String.class);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return response;
	}
}
