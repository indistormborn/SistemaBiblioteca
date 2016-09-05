/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sistemabiblioteca.entidades;

/**
 *
 * @author Indiara
 */
public class Livro {
    private String codigo;
    private String titulo;
    private String descricao;
    private boolean status;
    private Cliente cliente;

    public Livro(String codigo, String nome, String descricao) {
        this.codigo = codigo;
        this.titulo = nome;
        this.descricao = descricao;
        
    }
    
    public Livro(String nome, String descricao){
        this.titulo=nome;
        this.descricao=descricao;
    }

        
    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String nome) {
        this.titulo = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public boolean getStatus() {
        return status;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }
    
    
    
}
