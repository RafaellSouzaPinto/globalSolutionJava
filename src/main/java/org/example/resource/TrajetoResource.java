package org.example.resource;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.example.entitiesfinal.MeioDeTransporte;
import org.example.entitiesfinal.Pessoa;
import org.example.entitiesfinal.Trajeto;
import org.example.repositories.PessoaRepo;
import org.example.repositories.TrajetoRepository;
import org.example.services.DistanciaService;
import java.util.List;
@Path("/trajetos")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class TrajetoResource {
    private final TrajetoRepository trajetoRepository;
    private final PessoaRepo pessoaRepo;
    private final DistanciaService distanciaService;
    public TrajetoResource() {
        this.trajetoRepository = new TrajetoRepository();
        this.pessoaRepo = new PessoaRepo();
        this.distanciaService = new DistanciaService(trajetoRepository, pessoaRepo);
    }
    @POST
    @Path("/registrar")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response registrarTrajeto(Trajeto trajeto) {
        try {
            // Validações
            if (trajeto.getPessoa() == null || trajeto.getPessoa().getId() == 0) {
                return Response.status(Response.Status.BAD_REQUEST).entity("Parâmetro 'pessoa' e seu 'id' são obrigatórios.").build();
            }
            if (trajeto.getOrigem() == null || trajeto.getOrigem().isEmpty()) {
                return Response.status(Response.Status.BAD_REQUEST).entity("Parâmetro 'origem' é obrigatório.").build();
            }
            if (trajeto.getDestino() == null || trajeto.getDestino().isEmpty()) {
                return Response.status(Response.Status.BAD_REQUEST).entity("Parâmetro 'destino' é obrigatório.").build();
            }
            if (trajeto.getMeioDeTransporte() == null) {
                return Response.status(Response.Status.BAD_REQUEST).entity("Parâmetro 'meioDeTransporte' é obrigatório.").build();
            }


            // Chamada ao serviço com todos os parâmetros
            distanciaService.registrarTrajeto(
                    trajeto.getPessoa().getId(),
                    trajeto.getOrigem(),
                    trajeto.getDestino(),
                    trajeto.getMeioDeTransporte()
            );


            return Response.status(Response.Status.CREATED).entity("Trajeto registrado com sucesso!").build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Erro ao registrar trajeto: " + e.getMessage()).build();
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
    @Path("/usuario/{pessoaId}")
    public Response buscarTrajetosPorPessoaId(@PathParam("pessoaId") int pessoaId) {
        try {
            List<Trajeto> trajetos = trajetoRepository.buscarPorPessoaId(pessoaId);
            return Response.ok(trajetos).build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Erro ao buscar trajetos: " + e.getMessage()).build();
        }
    }
    @GET
    @Path("/usuario/{pessoaId}/pontos-e-creditos")
    public Response consultarPontosECreditos(@PathParam("pessoaId") int pessoaId) {
        try {
            Pessoa pessoa = pessoaRepo.getPessoaById(pessoaId);
            if (pessoa != null) {
                int pontos = pessoa.getPontos();
                int creditos = pessoa.getCreditos();
                return Response.ok("Pontuação: " + pontos + ", Créditos: " + creditos).build();
            } else {
                return Response.status(Response.Status.NOT_FOUND).entity("Pessoa não encontrada.").build();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Erro ao consultar pontuação e créditos: " + e.getMessage()).build();
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