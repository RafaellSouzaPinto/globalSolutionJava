package org.example.services;

import org.example.entities.Trajeto;

public class TrajetoService {

    public boolean validarOrigem(String origem) {
        return origem != null && !origem.trim().isEmpty();
    }

    public boolean validarDestino(String destino) {
        return destino != null && !destino.trim().isEmpty();
    }

    public boolean validarMeioDeTransporte(Object meioDeTransporte) {
        return meioDeTransporte != null;
    }

    public boolean validarDistancia(double distanciaKm) {
        return distanciaKm > 0;
    }

    public boolean validarCadastroTrajeto(Trajeto trajeto) {
        return trajeto.getPessoa() != null && trajeto.getPessoa().getId() > 0 &&
                validarOrigem(trajeto.getOrigem()) &&
                validarDestino(trajeto.getDestino()) &&
                validarMeioDeTransporte(trajeto.getMeioDeTransporte()) &&
                validarDistancia(trajeto.getDistanciaKm());
    }
}
