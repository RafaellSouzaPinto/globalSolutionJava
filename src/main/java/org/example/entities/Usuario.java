package org.example.entities;

import java.util.ArrayList;
import java.util.List;

public class Usuario extends Pessoa {

    private List<Viagem> viagens;

    public Usuario() {
        this.viagens = new ArrayList<>();
    }

    public Usuario(List<Viagem> viagens) {
        this.viagens = viagens != null ? viagens : new ArrayList<>();
    }

    public Usuario(int id, String nome, String cpf, String email, String senha, List<Viagem> viagens) {
        super(id, nome, cpf, email, senha);
        this.viagens = viagens != null ? viagens : new ArrayList<>();
    }

    public List<Viagem> getViagens() {
        return viagens;
    }

    public void setViagens(List<Viagem> viagens) {
        this.viagens = viagens;
    }
    public void addViagem(Viagem viagem) {
        if (viagem != null) {
            this.viagens.add(viagem);
            viagem.setUsuario(this);
        }
    }
    public int getTotalPontos() {
        return viagens.stream().mapToInt(Viagem::getPontosGanhos).sum();
    }

    public double getTotalCreditosCarbono() {
        return getTotalPontos() / 4200.0;
    }

    public double getTotalDistancia() {
        return viagens.stream().mapToDouble(Viagem::getDistancia).sum();
    }

    public double getReducaoEmissoes() {
        double reducaoPorKm = 1.0 / 4200;
        return getTotalDistancia() * reducaoPorKm;
    }


}
