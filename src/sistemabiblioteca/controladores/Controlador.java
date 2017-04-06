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
import javax.swing.table.DefaultTableModel;
import sistemabiblioteca.entidades.Cliente;
import sistemabiblioteca.entidades.Emprestimo;
import sistemabiblioteca.entidades.Livro;
import sistemabiblioteca.telas.TelaAlteracaoLivros;
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
    private ArrayList<Emprestimo> emprestimos;
    private TelaInicial telaInicial;
    private TelaEmprestimo telaEmprestimo;
    private TelaDevolucao telaDevolucao;
    private TelaCadastroLivros telaLivros;
    private TelaCadastroCliente telaClientes;
    private TelaBusca telaBusca;
    private TelaAlteracaoLivros telaAlteracao;

    public Controlador() {
        this.livros = new ArrayList<>();
        this.clientes = new ArrayList<>();
        this.emprestimos = new ArrayList<>();
        this.telaInicial = new TelaInicial(this);
        this.telaEmprestimo = new TelaEmprestimo(this);
        this.telaDevolucao = new TelaDevolucao(this);
        this.telaLivros = new TelaCadastroLivros(this);
        this.telaClientes = new TelaCadastroCliente(this);
        this.telaBusca = new TelaBusca(this);
        this.telaAlteracao = new TelaAlteracaoLivros(this);

    }

    
    public void inicia() {
        getTelaInicial().setVisible(true);
    }
    
    
    /*Inicio do CRUD de livros*/
    public void cadastrarLivro(String codigo, String nome, String descricao) {
        Livro livro = new Livro(codigo, nome, descricao);
        int verificador = 0;
        /*-o laço verifica se já existe algum livro cadastrado com o código passado como parametro
        -caso exista, uma variavel verificadora é incrementada*/
        for (Livro l : livros) {
            if (l.getCodigo().equals(codigo)) {
                verificador++;
            }
        }
        /*a condição para o livro ser cadastrado é de que não haja nenhum código igual
        caso houver código igual, além do livro não ser cadastrado a tela exibira uma caixa de aviso
        quando a caixa for fechada, um novo código será criado pelo gerador de códigos e pode acontecer
        outra tentativa de cadastramento*/
        if (verificador == 0) {
            livro.setStatus(true);
            livros.add(livro);
        } else {
            JOptionPane.showMessageDialog(telaLivros, "CÓDIGO JÁ CADASTRADO!");
        }

    }
    
    /*seleciona o livro na tabela de livros da tela de buscas pegando o código para adicioná-lo no campo
    código da tela  de empréstimo*/
    public void selecionarLivro(){
        int row = telaBusca.getjTable1().getSelectedRow();
        telaEmprestimo.getCodigo().setText(telaBusca.getjTable1().getValueAt(row, 1).toString());
    
    }
    
    public void removerLivro(String codigo) {
        for (Livro livro : livros) {
            if (livro.getCodigo().equals(codigo)) {
                livros.remove(livro);
                break;
            }
        }
    }
    /*Fim do CRUD de livros*/
    
    
    
    /*mesmo esquema do cadastramento de livros*/
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
          JOptionPane.showMessageDialog(telaClientes, "CLIENTE JA CADASTRADO OU CPF INCORRETO. Favor, cadastrar o cliente '" + nome + "' novamente");
        }

    }

   

    public void emprestar(String codigo, String cpf) {
        for (Cliente c : clientes) {
            if (c.getCPF().equals(cpf)) {
                if (c.getLivros().size() < 5) {
                    for (Livro l : livros) {
                        if (l.getCodigo().equals(codigo)) {
                            c.getLivros().add(l);
                            Emprestimo e = new Emprestimo(c, l, geraDataEmprestimo(), geraDataDevolucao());
                            emprestimos.add(e);
                            l.setStatus(false);
                        } else {
                            JOptionPane.showMessageDialog(telaEmprestimo, "LIVRO NÃO ENCONTRADO!");
                        }
                    }
                } else {
                    JOptionPane.showMessageDialog(telaEmprestimo, "IMPOSSIVEL REALIZAR EMPRÉSTIMO! Cliente alcançou o limite máximo de livros à serem retirados!");
                }
            } else {
                JOptionPane.showMessageDialog(telaEmprestimo, "CPF NÃO ENCONTRADO!");
            }
        }
    }

     public void devolver(String codigo) {
        Livro livro = getLivroPorCodigo(codigo);
        for (Emprestimo emp : emprestimos) {
            if (emp.getLivro().getCodigo().equals(codigo)) {
                livro.setStatus(true);
                emp.getCliente().getLivros().remove(livro);
                emprestimos.remove(emp);
                break;
            }
        }

    }
    
    
    public Livro getLivroPorCodigo(String codigo) {
        Livro livro = new Livro();
        for (Livro l : livros) {
            if (l.getCodigo().equals(codigo)) {
                livro = l;
                break;
            } else {
                System.err.println("LIVRO N ENCONTRADO");
            }
        }
        return livro;
    }

   

    public int verificaAtraso(String codigo) {
        Calendar data = Calendar.getInstance();
        int dia = data.get(Calendar.DAY_OF_MONTH);
        int mes = data.get(Calendar.MONTH);
        int diasAtraso = 0;
        for (Emprestimo emp : emprestimos) {
            if (emp.getLivro().getCodigo().equals(codigo)) {
                if (emp.getDataDevolucao().get(Calendar.DAY_OF_MONTH) > dia) {
                    diasAtraso = emp.getDataDevolucao().get(Calendar.DAY_OF_MONTH) - dia;
                    if (diasAtraso <= 7) {
                        return 0;
                    }
                }
            }
        }
        return diasAtraso;
    }

    
     /*Manipulador de Telas*/
    public void limparCamposCliente() {
        telaClientes.getCPF().setText(null);
        telaClientes.getNome().setText(null);

    }
    public void limparCamposLivro() {

        telaLivros.getjTextArea1().setText(null);
        telaLivros.getjTextField1().setText(null);
        telaLivros.getCodigotf().setText(geradorCodigo());
    }
    public void limparCamposEmprestimo() {
        telaEmprestimo.getCodigo().setText(null);
        telaEmprestimo.getCpf().setText(null);
        telaEmprestimo.getNome().setText(null);
        telaEmprestimo.getTitulo().setText(null);
    }

    public void atualizarLivro() {
        if (!telaAlteracao.getTitulo().getText().equals(null)) {
            getLivroPorCodigo(telaAlteracao.getCodigo().getText()).setTitulo(telaAlteracao.getTitulo().getText());
        }
        if (!telaAlteracao.getDescricao().getText().equals(null)) {
            getLivroPorCodigo(telaAlteracao.getCodigo().getText()).setDescricao(telaAlteracao.getDescricao().getText());
        }
    }
    
    public Date geraDataEmprestimo() {
        Date data = new Date();
        SimpleDateFormat formatador = new SimpleDateFormat("dd/MM/yyyy");
        formatador.format(data);
        return data;
    }
    
     public String dataEmprestimoToString() {
        Calendar c = Calendar.getInstance();
        c.setTime(geraDataEmprestimo());
        int dia = c.get(Calendar.DATE);
        int mes = c.get(Calendar.MONTH) + 1; // janeiro é 0
        int ano = c.get(Calendar.YEAR);
        return dia + "/" + mes + "/" + ano;
    }


   
    public String dataDevolucaoToString() {
        int dia = geraDataDevolucao().get(Calendar.DAY_OF_MONTH);
        int mes = geraDataDevolucao().get(Calendar.MONTH) + 1;
        int ano = geraDataDevolucao().get(Calendar.YEAR);
        return dia + "/" + mes + "/" + ano;
    }

    public Calendar geraDataDevolucao() {
        Calendar calendario = Calendar.getInstance();
        calendario.setTime(geraDataEmprestimo());
        calendario.add(Calendar.DATE, +14);
        return calendario;
    }

    
    //   Gera um código aleatório e automático com o qual o livro será cadastrado
    public String geradorCodigo() {
        int codigo = (new Random()).nextInt(1000);
        for (Livro l : livros) {
            if (l.getCodigo().equals(codigo)) {
                codigo = (new Random()).nextInt(1000);
            } else {
                break;
            }
        }
        String cod = Integer.toString(codigo);
        return cod;
    }
   
    /*TELA DE EMPRÉSTIMO*/
    /*Quando o código do livro é digitado, seguido do enter, o titulo do livro aparece no campo titulo
    funciona igualmente para o cpf e o nome do cliente*/
    public String atualizaTituloTela(String cod) {
        String t = "Este código não corresponde à nenhum livro!";;
        for (Livro l : livros) {
            if (l.getCodigo().equals(cod)) {
                t = l.getTitulo();
            }
        }
        return t;
    }

    public String atualizaNomeTela(String cod) {
        String n = "CPF inexistente no sistema";
        for (Cliente c : clientes) {
            if (c.getCPF().equals(cod)) {
                n = c.getNome();
            }
        }
        return n;
    }
    /*TELA DE EMPRÉSTIMO*/

    
    /*adiciona as linhas da tabela da tela de buscas conforme os livros que estão cadastrados no sistema*/
    public void atualizaListaLivros() {
        DefaultTableModel dtm = new DefaultTableModel();
        
        dtm.addColumn("Titulo");
        dtm.addColumn("Codigo");
        dtm.addColumn("Disponibilidade");

        for (Livro l : livros) {
            String status;
            if (l.getStatus() == false) {
                status = "Indisponivel";
            } else {
                status = "Disponivel";
            }
            dtm.addRow(new Object[]{l.getTitulo(), l.getCodigo(), status});
        }

        telaBusca.getjTable1().setModel(dtm);

    }

    /*quando um titulo de livro é digitado no campo livro, o método é executado 
    e a tabela de livros atualiza-se somente com os titulos correspondentes*/
    public void atualizaPesquisaLivros(String titulo) {
        DefaultTableModel dtm = new DefaultTableModel();
        dtm.addColumn("Titulo");
        dtm.addColumn("Codigo");
        dtm.addColumn("Disponibilidade");

        for (Livro l : livros) {
            String status = "Disponivel";
            if (l.getTitulo().equals(titulo)) {
                if (l.getStatus() == false) {
                    status = "Indisponivel";
                }
            }
            dtm.addRow(new Object[]{l.getTitulo(), l.getCodigo(), status});
        }

        telaBusca.getjTable1().setModel(dtm);

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
        atualizaListaLivros();
    }

    public ArrayList<Emprestimo> getEmprestimos() {
        return emprestimos;
    }

    public void setEmprestimos(ArrayList<Emprestimo> emprestimos) {
        this.emprestimos = emprestimos;
    }

    public TelaAlteracaoLivros getTelaAlteracao() {
        return telaAlteracao;
    }

    public void setTelaAlteracao() {
       telaAlteracao.setVisible(true);
    }

    
    
   
}
