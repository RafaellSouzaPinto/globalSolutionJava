package org.example.resource;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.example.entities.Pessoa;
import org.example.repositories.PessoaRepo;
import org.example.services.PessoaService;

import java.util.List;
import java.util.Map;

@Path("/pessoas")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PessoaResource {

    private PessoaRepo pessoaRepo = new PessoaRepo();
    private PessoaService pessoaService = new PessoaService();

    @POST
    @Path("/cadastro")
    public Response cadastrarPessoa(Pessoa pessoa) {
        try {
            // Validação de atributos obrigatórios, incluindo o plano
            if (pessoa.getNome() == null || pessoa.getNome().isEmpty()) {
                return Response.status(Response.Status.BAD_REQUEST).entity("Nome é obrigatório.").build();
            }
            if (pessoa.getCpf() == null || pessoa.getCpf().isEmpty() || !pessoaService.validarCpf(pessoa.getCpf())) {
                return Response.status(Response.Status.BAD_REQUEST).entity("CPF inválido.").build();
            }
            if (pessoa.getEmail() == null || pessoa.getEmail().isEmpty() || !pessoaService.validarEmail(pessoa.getEmail())) {
                return Response.status(Response.Status.BAD_REQUEST).entity("Email inválido.").build();
            }
            if (pessoa.getSenha() == null || pessoa.getSenha().length() < 8) {
                return Response.status(Response.Status.BAD_REQUEST).entity("Senha deve ter no mínimo 8 caracteres.").build();
            }
            if (!"Plano Verdí".equalsIgnoreCase(pessoa.getPlanos()) && !"Plano Super Verdí".equalsIgnoreCase(pessoa.getPlanos())) {
                return Response.status(Response.Status.BAD_REQUEST).entity("Plano inválido. Escolha entre 'Plano Verdí' e 'Plano Super Verdí'.").build();
            }

            // Verificação de duplicidade de CPF e Email
            if (pessoaService.cpfOuEmailExistem(pessoa)) {
                return Response.status(Response.Status.CONFLICT).entity("CPF ou Email já cadastrado.").build();
            }

            boolean sucesso = pessoaRepo.cadastrarPessoa(pessoa);
            if (sucesso) {
                return Response.status(Response.Status.CREATED).entity("Usuário cadastrado com sucesso.").build();
            } else {
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Erro ao cadastrar usuário.").build();
            }
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Erro no banco de dados: " + e.getMessage()).build();
        }
    }



    // 2. Login
    @POST
    @Path("/login")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response login(Pessoa pessoa) {
        try {
            Pessoa usuario = pessoaRepo.login(pessoa.getEmail(), pessoa.getSenha());
            if (usuario != null) {
                // Sucesso no login
                return Response.ok(usuario).build();
            } else {
                // Credenciais inválidas
                return Response.status(Response.Status.UNAUTHORIZED).entity("Email ou senha inválidos.").build();
            }
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Erro no banco de dados: " + e.getMessage()).build();
        }
    }

    // 3. Alteração de Senha
    @PUT
    @Path("/{id}/alterarSenha")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response alterarSenha(
            @PathParam("id") int id,
            @FormParam("senhaAtual") String senhaAtual,
            @FormParam("novaSenha") String novaSenha,
            @FormParam("confirmacaoNovaSenha") String confirmacaoNovaSenha) {
        try {
            if (novaSenha == null || novaSenha.length() < 8) {
                return Response.status(Response.Status.BAD_REQUEST).entity("A nova senha deve ter no mínimo 8 caracteres.").build();
            }
            if (!novaSenha.equals(confirmacaoNovaSenha)) {
                return Response.status(Response.Status.BAD_REQUEST).entity("A nova senha e a confirmação não coincidem.").build();
            }

            boolean sucesso = pessoaRepo.alterarSenha(id, senhaAtual, novaSenha);
            if (sucesso) {
                return Response.ok("Senha alterada com sucesso.").build();
            } else {
                return Response.status(Response.Status.UNAUTHORIZED).entity("Senha atual incorreta ou usuário não encontrado.").build();
            }
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Erro no banco de dados: " + e.getMessage()).build();
        }
    }

    // 4. Exclusão de Conta
    @DELETE
    @Path("/{id}")
    public Response excluirConta(@PathParam("id") int id) {
        try {
            boolean sucesso = pessoaRepo.excluirConta(id);
            if (sucesso) {
                return Response.ok("Conta excluída com sucesso.").build();
            } else {
                return Response.status(Response.Status.NOT_FOUND).entity("Usuário não encontrado.").build();
            }
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Erro no banco de dados: " + e.getMessage()).build();
        }
    }

    // 5. Obter Pessoa por ID
    @GET
    @Path("/{id}")
    public Response getPessoaById(@PathParam("id") int id) {
        try {
            Pessoa pessoa = pessoaRepo.getPessoaById(id);
            if (pessoa != null) {
                return Response.ok(pessoa).build();
            } else {
                return Response.status(Response.Status.NOT_FOUND).entity("Usuário não encontrado.").build();
            }
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Erro no banco de dados: " + e.getMessage()).build();
        }
    }

    // 6. Listar todas as Pessoas
    @GET
    public Response getAllPessoas() {
        try {
            List<Pessoa> pessoas = pessoaRepo.getAllPessoas();
            return Response.ok(pessoas).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Erro no banco de dados: " + e.getMessage()).build();
        }
    }
    @PUT
    @Path("/{id}/trocarPlano")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response trocarPlano(@PathParam("id") int id, Map<String, String> body) {
        try {
            // Extrair o novo plano do corpo da requisição
            String novoPlano = body.get("novoPlano");

            // Validar o novo plano
            if (novoPlano == null ||
                    (!"Plano Verdí".equalsIgnoreCase(novoPlano) && !"Plano Super Verdí".equalsIgnoreCase(novoPlano))) {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity("Plano inválido. Escolha entre 'Plano Verdí' e 'Plano Super Verdí'.")
                        .build();
            }

            // Buscar o usuário pelo ID
            Pessoa pessoa = pessoaRepo.getPessoaById(id);
            if (pessoa == null) {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("Usuário não encontrado.")
                        .build();
            }

            // Verificar se o usuário já está no plano solicitado
            if (pessoa.getPlanos().equalsIgnoreCase(novoPlano)) {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity("O usuário já está no plano escolhido.")
                        .build();
            }

            // Atualizar o plano no banco de dados
            boolean sucesso = pessoaRepo.trocarPlano(id, novoPlano);
            if (sucesso) {
                return Response.ok("Plano alterado com sucesso para: " + novoPlano).build();
            } else {
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                        .entity("Erro ao alterar o plano.")
                        .build();
            }
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Erro no banco de dados: " + e.getMessage())
                    .build();
        }
    }



}

