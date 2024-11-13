package org.example;

import org.example.entitiesfinal.MeioDeTransporte;
import org.example.entitiesfinal.Pessoa;
import org.example.repositories.PessoaRepo;
import org.example.repositories.TrajetoRepository;
import org.example.services.DistanciaService;

public class Teste {
    public static void main(String[] args) {
        try {

            PessoaRepo pessoaRepo = new PessoaRepo();
            TrajetoRepository trajetoRepository = new TrajetoRepository();
            DistanciaService distanciaService = new DistanciaService(trajetoRepository, pessoaRepo);

            // Login da pessoa já cadastrada no sistema
            Pessoa pessoaLogada = pessoaRepo.login("rafaelsouza.rs14@gmail.com", "RafaelTeste04");
            if (pessoaLogada != null) {
                System.out.println("Login bem-sucedido para: " + pessoaLogada.getNome());
            } else {
                System.out.println("Falha no login. Verifique as credenciais.");
                return;
            }

            for (int i = 0; i < 3; i++) {
                String origem = "Rua Otília, 596, Vila Esperança, São Paulo, SP";
                String destino = "Rua mandu, São Paulo";
                MeioDeTransporte meioDeTransporte = MeioDeTransporte.BICICLETA_ELETRICA;


                distanciaService.registrarTrajeto(pessoaLogada.getId(), origem, destino, meioDeTransporte);

            }

            Pessoa pessoaAtualizada = pessoaRepo.getPessoaById(pessoaLogada.getId());
            if (pessoaAtualizada != null) {
                System.out.println("Pontuação atual de " + pessoaAtualizada.getNome() + ": " + pessoaAtualizada.getPontos());
            } else {
                System.out.println("Falha ao carregar a pontuação atualizada para " + pessoaLogada.getNome());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
