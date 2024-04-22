package org.example;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {

        crudTelefono N = new crudTelefono();
        N.conexion();
        //N.crearUsuario2();
        N.leerUsuarios();
        System.out.println(".");
       // N.UpdateTelefono();
        N.eliminarUsuario();
        System.out.println(".");
        N.leerUsuarios();

    }
}