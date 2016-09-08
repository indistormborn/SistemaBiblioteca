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
      ctrl.inicia();

    }

}
