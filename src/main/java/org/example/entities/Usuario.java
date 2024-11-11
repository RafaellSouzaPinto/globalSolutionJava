package org.example.entities;

import java.util.ArrayList;
import java.util.List;

public class Usuario extends Pessoa {

    private Plano plano;
    private int pontos;

    private List<Endereco> enderecos;
    private List<Viagem> viagens;
    private List<Notificacao> notificacoes;
    private List<Feedback> feedbacks;

    public Usuario() {
        super();
        this.enderecos = new ArrayList<>();
        this.viagens = new ArrayList<>();
        this.notificacoes = new ArrayList<>();
        this.feedbacks = new ArrayList<>();
    }



    public Usuario(Plano plano, int pontos, List<Endereco> enderecos, List<Viagem> viagens, List<Notificacao> notificacoes, List<Feedback> feedbacks) {
        this.plano = plano;
        this.pontos = pontos;
        this.enderecos = enderecos;
        this.viagens = viagens;
        this.notificacoes = notificacoes;
        this.feedbacks = feedbacks;
    }

    public Usuario(int id, String nome, String cpf, String senha, String email, Plano plano, int pontos, List<Endereco> enderecos, List<Viagem> viagens, List<Notificacao> notificacoes, List<Feedback> feedbacks) {
        super(id, nome, cpf, senha, email);
        this.plano = plano;
        this.pontos = pontos;
        this.enderecos = enderecos;
        this.viagens = viagens;
        this.notificacoes = notificacoes;
        this.feedbacks = feedbacks;
    }

    public Plano getPlano() {
        return plano;
    }

    public void setPlano(Plano plano) {
        this.plano = plano;
    }

    public int getPontos() {
        return pontos;
    }

    public void setPontos(int pontos) {
        this.pontos = pontos;
    }

    public List<Endereco> getEnderecos() {
        return enderecos;
    }

    public void setEnderecos(List<Endereco> enderecos) {
        this.enderecos = enderecos;
    }

    public List<Viagem> getViagens() {
        return viagens;
    }

    public void setViagens(List<Viagem> viagens) {
        this.viagens = viagens;
    }

    public List<Notificacao> getNotificacoes() {
        return notificacoes;
    }

    public void setNotificacoes(List<Notificacao> notificacoes) {
        this.notificacoes = notificacoes;
    }

    public List<Feedback> getFeedbacks() {
        return feedbacks;
    }

    public void setFeedbacks(List<Feedback> feedbacks) {
        this.feedbacks = feedbacks;
    }

    // Método para adicionar pontos
    public void adicionarPontos(int pontos) {
        this.pontos += pontos;
    }

    // Método para resgatar recompensa
    public boolean resgatarRecompensa(Recompensa recompensa) {
        if (this.pontos >= recompensa.getPontosNecessarios()) {
            this.pontos -= recompensa.getPontosNecessarios();
            // Registrar troca de pontos
            return true;
        }
        return false;
    }
}
