/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sistemabiblioteca;

import java.util.Calendar;
import sistemabiblioteca.controladores.Controlador;
import sistemabiblioteca.entidades.Livro;
import sistemabiblioteca.telas.TelaInicial;

/**
 *
 * @author Indiara
 */
public class SistemaBiblioteca {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Controlador ctrl = new Controlador();
       /* System.out.println(ctrl.geraDataDevolucao().get(Calendar.DAY_OF_MONTH) + "/" + ctrl.geraDataDevolucao().get(Calendar.MONTH) + "/" + ctrl.geraDataDevolucao().get(Calendar.YEAR));
        Calendar c = Calendar.getInstance();
        c.setTime(ctrl.geraDataEmprestimo());
        System.out.println(c.get(Calendar.DATE));
        System.out.println(c.get(Calendar.MONTH)); // janeiro é 0
        System.out.println(c.get(Calendar.YEAR));*/
        ctrl.inicia();

    }

}
