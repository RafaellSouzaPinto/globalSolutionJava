package org.example.resource;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.example.entities.Pessoa;
import org.example.entities.Trajeto;
import org.example.repositories.PessoaRepo;
import org.example.repositories.TrajetoRepo;
import org.example.services.DistanciaService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Path("/trajetos")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class TrajetoResource {
    private final TrajetoRepo trajetoRepository;
    private final PessoaRepo pessoaRepo;
    private final DistanciaService distanciaService;
    public TrajetoResource() {
        this.trajetoRepository = new TrajetoRepo();
        this.pessoaRepo = new PessoaRepo();
        this.distanciaService = new DistanciaService(trajetoRepository, pessoaRepo);
    }
    @POST
    @Path("/registrar")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response registrarTrajeto(Trajeto trajeto) {
        try {
            if (trajeto.getOrigem() == null || trajeto.getOrigem().isEmpty()) {
                return Response.status(Response.Status.BAD_REQUEST).entity("Parâmetro 'origem' é obrigatório.").build();
            }
            if (trajeto.getDestino() == null || trajeto.getDestino().isEmpty()) {
                return Response.status(Response.Status.BAD_REQUEST).entity("Parâmetro 'destino' é obrigatório.").build();
            }
            if (trajeto.getMeioDeTransporte() == null) {
                return Response.status(Response.Status.BAD_REQUEST).entity("Parâmetro 'meioDeTransporte' é obrigatório.").build();
            }

            // Chama o serviço e obtém o trajeto com dados atualizados
            Trajeto trajetoRegistrado = distanciaService.registrarTrajeto(
                    trajeto.getPessoa().getId(),
                    trajeto.getOrigem(),
                    trajeto.getDestino(),
                    trajeto.getMeioDeTransporte()
            );

            // Cria a resposta com os dados calculados
            Map<String, Object> response = new HashMap<>();
            response.put("mensagem", "Trajeto registrado com sucesso!");
            response.put("distanciaKm", trajetoRegistrado.getDistanciaKm());
            response.put("pontosGanhos", trajetoRegistrado.getPontos());

            return Response.status(Response.Status.CREATED).entity(response).build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Erro ao registrar trajeto: " + e.getMessage()).build();
        }
    }
    @GET
    @Path("/usuario/{pessoaId}")
    public Response buscarTrajetosPorPessoaId(@PathParam("pessoaId") int pessoaId) {
        try {
            List<Trajeto> trajetos = trajetoRepository.buscarPorPessoaId(pessoaId);
            return Response.ok(trajetos).build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Erro ao buscar trajetos: " + e.getMessage()).build();
        }
    }




    @GET
    @Path("/{id}")
    public Response buscarTrajetoPorId(@PathParam("id") int id) {
        try {
            Trajeto trajeto = trajetoRepository.buscarPorId(id);
            if (trajeto != null) {
                return Response.ok(trajeto).build();
            } else {
                return Response.status(Response.Status.NOT_FOUND).entity("Trajeto não encontrado.").build();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Erro ao buscar trajeto: " + e.getMessage()).build();
        }
    }


    @GET
    @Path("/usuario/{pessoaId}/total")
    public Response consultarPontosECreditos(@PathParam("pessoaId") int pessoaId) {
        try {
            Pessoa pessoa = pessoaRepo.getPessoaById(pessoaId);
            if (pessoa != null) {
                Map<String, Object> dadosUsuario = new HashMap<>();
                dadosUsuario.put("pontos", pessoa.getPontos());
                dadosUsuario.put("creditos", pessoa.getCreditos());
                dadosUsuario.put("distanciaAcumulada", pessoa.getDistanciaAcumulada());
                return Response.ok(dadosUsuario).build();
            } else {
                return Response.status(Response.Status.NOT_FOUND).entity("Pessoa não encontrada.").build();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Erro ao consultar pontuação e créditos: " + e.getMessage()).build();
        }
    }



    @GET
    public Response listarTodosOsTrajetos() {
        try {
            List<Trajeto> trajetos = trajetoRepository.buscarTodos();
            return Response.ok(trajetos).build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Erro ao listar todos os trajetos: " + e.getMessage()).build();
        }
    }
}