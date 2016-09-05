/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sistemabiblioteca.entidades;

import java.util.ArrayList;

/**
 *
 * @author Indiara
 */
public class Cliente {
    private String cpf;
    private String nome;
    private ArrayList<Livro> livros;

    public Cliente(String codigo, String nome) {
        this.cpf = codigo;
        this.nome = nome;
        this.livros= new ArrayList<>();
    }

        
    public String getCPF() {
        return cpf;
    }

    public void setCPF(String codigo) {
        this.cpf = codigo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public ArrayList<Livro> getLivros() {
        return livros;
    }

    public void setLivros(Livro livro) {
        livros.add(livro);
    }
    
    
    
}
