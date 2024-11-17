package org.example.services;

import org.example.entities.Pessoa;
import org.example.repositories.PessoaRepo;

public class PessoaService {

    private PessoaRepo pessoaRepo;

    public PessoaService() {
        this.pessoaRepo = new PessoaRepo();
    }

    public boolean validarNome(String nome) {
        return nome != null && !nome.trim().isEmpty();
    }

    public boolean validarCpf(String cpf) {
        return cpf != null && cpf.matches("^(\\d{3}\\.?\\d{3}\\.?\\d{3}-?\\d{2})$");
    }

    public boolean validarEmail(String email) {
        return email != null && email.matches("^[\\w-\\.]+@[\\w-]+\\.[a-zA-Z]{2,}$");
    }

    public boolean validarSenha(String senha) {
        return senha != null && senha.length() >= 8;
    }

    public boolean validarCadastroPessoa(Pessoa pessoa) {
        return validarNome(pessoa.getNome()) &&
                validarCpf(pessoa.getCpf()) &&
                validarEmail(pessoa.getEmail()) &&
                validarSenha(pessoa.getSenha());
    }

    public boolean cpfOuEmailExistem(Pessoa pessoa) throws Exception {
        return pessoaRepo.cpfExiste(pessoa.getCpf()) || pessoaRepo.emailExiste(pessoa.getEmail());
    }
}
