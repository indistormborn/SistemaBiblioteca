/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sistemabiblioteca.controladores;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;
import javax.swing.JOptionPane;
import sistemabiblioteca.entidades.Cliente;
import sistemabiblioteca.entidades.Livro;
import sistemabiblioteca.telas.TelaBusca;
import sistemabiblioteca.telas.TelaCadastroCliente;
import sistemabiblioteca.telas.TelaCadastroLivros;
import sistemabiblioteca.telas.TelaInicial;
import sistemabiblioteca.telas.TelaEmprestimo;
import sistemabiblioteca.telas.TelaDevolucao;

/**
 *
 * @author Indiara
 */
public class Controlador {

    private ArrayList<Livro> livros;
    private ArrayList<Cliente> clientes;
    private TelaInicial telaInicial;
    private TelaEmprestimo telaEmprestimo;
    private TelaDevolucao telaDevolucao;
    private TelaCadastroLivros telaLivros;
    private TelaCadastroCliente telaClientes;
    private TelaBusca telaBusca;

    public Controlador() {
        this.livros = new ArrayList<>();
        this.clientes = new ArrayList<>();
        this.telaInicial = new TelaInicial(this);
        this.telaEmprestimo = new TelaEmprestimo(this);
        this.telaDevolucao = new TelaDevolucao(this);
        this.telaLivros = new TelaCadastroLivros(this);
        this.telaClientes = new TelaCadastroCliente(this);
        this.telaBusca = new TelaBusca(this);
    }

//    public String geradorCodigo(){
//        int codigo = (new Random()).nextInt(1000);
//        for(Livro l : livros){
//            if(l.getCodigo().equals(codigo)){
//                codigo=(new Random()).nextInt(1000);
//            }else{
//                break;
//            }
//        }
//        String cod = Integer.toString(codigo);
//        telaLivros.setCodigo(cod);
//        return cod;
//    }
    
    public void cadastraLivro(String codigo, String nome, String descricao) {
        Livro livro = new Livro(codigo, nome, descricao);
        int verificador=0;
        for(Livro l : livros){
            if(livro.getCodigo().equals(codigo)){
                verificador++;
            }
        }
        if(verificador==0){
            livros.add(livro);
        }else{
            JOptionPane.showMessageDialog(telaLivros, "CÓDIGO JÁ CADASTRADO!");
        }
        

    }

    public void removerLivro(Integer codigo) {
        for (Livro livro : livros) {
            if (livro.getCodigo().equals(codigo)) {
                livros.remove(livro);
                break;
            }
        }
    }

    public void cadastrarCliente(String cpf, String nome) {
        int verificador = 0;
        for (Cliente c : clientes) {
            if (c.getCPF().equals(cpf)) {
                verificador++;
                break;
            }
        }
        if (verificador == 0) {
            Cliente cliente = new Cliente(cpf, nome);
            clientes.add(cliente);
        } else {
            System.out.println("CLIENTE JA CADASTRADO OU CPF INCORRETO. Favor, cadastrar o cliente '" + nome + "' novamente");
        }

    }

    public void limparCamposCliente(){
        telaClientes.getCPF().setText("");
        telaClientes.getNome().setText("");
                
    }
    public void limparCamposLivro(){
        telaLivros.getCodigotf().setText(null);
        telaLivros.getjTextArea1().setText(null);
        telaLivros.getjTextField1().setText(null);
    }
    
    public void emprestar(Integer codigo, Integer cpf) {
        for (Cliente c : clientes) {
            if (c.getCPF().equals(cpf)) {
                for (Livro l : livros) {
                    if (l.getCodigo().equals(codigo)) {
                        c.getLivros().add(l);
                        l.setCliente(c);
                        l.setStatus(false);
                    } else {
                        System.err.println("LIVRO N ENCONTRADO");
                    }
                }
            } else {
                System.err.println("CPF NÃO ENCONTRADO");
            }
        }
    }

    public void devolver(Integer codigo) {
        for (Livro l : livros) {
            if (l.getCodigo().equals(codigo)) {
                l.getCliente().getLivros().remove(l);
                l.setCliente(null);
                l.setStatus(true);
            } else {
                System.err.println("LIVRO N ENCONTRADO");
            }
        }
    
}
    public Date geraDataEmprestimo(){
        Date data = new Date();
        SimpleDateFormat formatador = new SimpleDateFormat("dd/MM/yyyy");
        formatador.format(data);
        return data;
    }
    
    public Calendar geraDataDevolucao(){
        Calendar c = Calendar.getInstance();
        c.setTime(geraDataEmprestimo());
        c.add(Calendar.DATE, +7);
              
        return c;
    }

public void testeDeImpressao() {
        for (Livro livro : livros) {
            System.out.println(livro.getTitulo() + " || " + livro.getCodigo());
        }
    }

    public ArrayList<Livro> getLivros() {
        return livros;
    }

    public void setLivros(ArrayList<Livro> livros) {
        this.livros = livros;
    }

    public ArrayList<Cliente> getClientes() {
        return clientes;
    }

    public void setClientes(ArrayList<Cliente> clientes) {
        this.clientes = clientes;
    }

    public TelaInicial getTelaInicial() {
        return telaInicial;
    }

   

    public TelaEmprestimo getTelaEmprestimo() {
        return telaEmprestimo;
    }

    public void setTelaEmprestimo() {
        telaEmprestimo.setVisible(true);
    }

    public TelaDevolucao getTelaDevolucao() {
        return telaDevolucao;
    }

    public void setTelaDevolucao() {
        telaDevolucao.setVisible(true);
    }

    public TelaCadastroLivros getTelaLivros() {
        return telaLivros;
    }

    public void setTelaLivros() {
        telaLivros.setVisible(true);
    }

    public TelaCadastroCliente getTelaClientes() {
        return telaClientes;
    }

    public void setTelaClientes() {
        telaClientes.setVisible(true);
    }

    public TelaBusca getTelaBusca() {
        return telaBusca;
    }

    public void setTelaBusca() {
        telaBusca.setVisible(true);
    }

    public void inicia(){
        getTelaInicial().setVisible(true);
    }
}
