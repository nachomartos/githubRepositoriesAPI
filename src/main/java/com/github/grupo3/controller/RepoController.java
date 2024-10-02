package com.github.grupo3.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.github.grupo3.dto.request.RepoRequestDTO;
import com.github.grupo3.service.RepoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/v1")
@Tag(name = "Repositorios")
public class RepoController {

	@Autowired
	RepoService repoService;

	/*----------------------------------------------------------------------------*/

	@Operation(summary = "Obtener los repositorios de un usuario", description = "Retorna los repositorios de un usuario en función del username proporcionado")
	@GetMapping("/users/{username}/repos")
	public ResponseEntity<?> getAll(@PathVariable() String username) {
		return new ResponseEntity<>(repoService.getAll(username), HttpStatus.OK);
	}

	@Operation(summary = "Crear un repositorio", description = "Crea un repositorio")
	@PostMapping("/user/repos")
	public ResponseEntity<?> crearRepo(@RequestBody RepoRequestDTO repoRequestDTO) {
		ResponseEntity<?> res = repoService.create(repoRequestDTO);
		return res;
	}

	@Operation(summary = "Actualizar un repositorio", description = "Actualiza un repositorio")
	@PatchMapping("/repos/{owner}/{repo}")
	public ResponseEntity<?> actualizarRepo(@PathVariable() String owner, @PathVariable() String repo, @RequestBody RepoRequestDTO repoRequestDTO) {
		ResponseEntity<?> res = repoService.update(repoRequestDTO, owner, repo);
		return res;
	}

	@Operation(summary = "Borrar un repositorio", description = "Borra un repositorio")
	@DeleteMapping("/repos/{owner}/{repo}")
	public String borrarRepo(@PathVariable() String owner, @PathVariable() String repo) {
		ResponseEntity<?> res = repoService.delete(owner, repo);
		if(res.getStatusCode() == HttpStatus.NO_CONTENT) {
			return "Su repo fue borrado exitosamente";
		}
		else {
			return "Hubo un error";
		}
	}

}
