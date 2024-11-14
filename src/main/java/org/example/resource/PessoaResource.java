package org.example.resource;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.example.entitiesfinal.Pessoa;
import org.example.repositories.PessoaRepo;
import org.example.services.PessoaService;

import java.util.List;

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
            // Validação de atributos
            if (!pessoaService.validarNome(pessoa.getNome())) {
                return Response.status(Response.Status.BAD_REQUEST).entity("Nome é obrigatório.").build();
            }
            if (!pessoaService.validarCpf(pessoa.getCpf())) {
                return Response.status(Response.Status.BAD_REQUEST).entity("CPF inválido.").build();
            }
            if (!pessoaService.validarEmail(pessoa.getEmail())) {
                return Response.status(Response.Status.BAD_REQUEST).entity("Email inválido.").build();
            }
            if (!pessoaService.validarSenha(pessoa.getSenha())) {
                return Response.status(Response.Status.BAD_REQUEST).entity("Senha deve ter no mínimo 8 caracteres.").build();
            }
            if (pessoa.getPlanos() == null || pessoa.getPlanos().isEmpty()) {
                return Response.status(Response.Status.BAD_REQUEST).entity("Plano é obrigatório.").build();
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
}

