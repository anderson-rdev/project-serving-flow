package com.management.controller;

import com.management.DTOs.MensagemResponse;
import com.management.DTOs.PessoaRequest;
import com.management.DTOs.PessoaResponse;
import com.management.exception.ResourceNotFoundException;
import com.management.service.PessoaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

// Para consistência, estou usando "Usuários" como definido na Tag e no RequestMapping.
@Tag(name = "Usuários", description = "Operações para gerenciamento de usuários")
@RestController
@RequestMapping("/api/v1/usuarios")
public class PessoaController {

    private final PessoaService pessoaService;

    public PessoaController(PessoaService pessoaService) {
        this.pessoaService = pessoaService;
    }

    // Endpoint para cadastrar um novo usuário no sistema.
    @Operation(summary = "Cadastrar novo usuário",
            description = "Cria um novo usuário com seus dados básicos, contatos e endereços.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Usuário cadastrado com sucesso",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = PessoaResponse.class))),

            @ApiResponse(responseCode = "400", description = "Erro de validação nos dados de entrada",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = MensagemResponse.class))), // Padronizado com seu DTO de erro

            @ApiResponse(responseCode = "409", description = "Conflito: Recurso já existente (ex: CPF ou E-mail já cadastrado)",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = MensagemResponse.class))),

            @ApiResponse(responseCode = "500", description = "Erro interno do servidor",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = MensagemResponse.class)))
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PessoaResponse cadastrar(@Valid @RequestBody PessoaRequest pessoaRequest) {
        return pessoaService.cadastrar(pessoaRequest);
    }

    // Busca um usuário específico pelo seu ID.
    @Operation(summary = "Buscar usuário por ID",
            description = "Recupera os dados de um usuário específico com base em seu ID único.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuário encontrado com sucesso",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = PessoaResponse.class))),

            // O 404 é tratado automaticamente pelo @ExceptionHandler
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = MensagemResponse.class))),

            @ApiResponse(responseCode = "500", description = "Erro interno do servidor",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = MensagemResponse.class)))
    })
    @GetMapping("/{id}")
    public ResponseEntity<PessoaResponse> buscarPorId(@PathVariable Long id) {
        // A lógica de "não encontrado" deve estar no Service, que lança ResourceNotFoundException
        PessoaResponse response = pessoaService.buscarPorId(id);
        return ResponseEntity.ok(response);
    }

    // Atualiza os dados de um usuário existente.
    @Operation(summary = "Atualizar usuário por ID",
            description = "Atualiza os dados de um usuário existente. Apenas os campos fornecidos no request body serão atualizados.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuário atualizado com sucesso",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = PessoaResponse.class))),

            @ApiResponse(responseCode = "400", description = "Erro de validação nos dados de entrada",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = MensagemResponse.class))),

            @ApiResponse(responseCode = "404", description = "Usuário não encontrado",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = MensagemResponse.class))),

            @ApiResponse(responseCode = "500", description = "Erro interno do servidor",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = MensagemResponse.class)))
    })
    @PutMapping("/{id}")
    public ResponseEntity<PessoaResponse> alterar(
            @PathVariable Long id,
            @Valid @RequestBody PessoaRequest pessoaRequest) {

        // A lógica de buscar, mesclar dados e salvar foi movida para o service.
        // O controller apenas delega a responsabilidade.
        PessoaResponse response = pessoaService.alterar(id, pessoaRequest);
        return ResponseEntity.ok(response);
    }

    // Exclui um usuário do sistema pelo seu ID.
    @Operation(summary = "Excluir usuário por ID",
            description = "Remove um usuário do sistema com base em seu ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Usuário excluído com sucesso",
                    content = @Content), // Sem conteúdo no corpo da resposta

            @ApiResponse(responseCode = "404", description = "Usuário não encontrado",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = MensagemResponse.class))),

            @ApiResponse(responseCode = "500", description = "Erro interno do servidor",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = MensagemResponse.class)))
    })
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT) // Define o status de sucesso padrão como 204
    public ResponseEntity<Void> deletarPessoa(@PathVariable Long id) {
        // O service lança ResourceNotFoundException se o ID não existir
        pessoaService.excluir(id);

        // O padrão REST para um DELETE bem-sucedido é retornar 204 No Content
        return ResponseEntity.noContent().build();
    }

    // Handler global para exceções do tipo ResourceNotFoundException neste controller.
    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public MensagemResponse handleResourceNotFound(ResourceNotFoundException ex) {
        return new MensagemResponse(ex.getMessage());
    }

}