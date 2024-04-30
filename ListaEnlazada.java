package org.example.Helpers;


import org.example.Models.Nodo;

public class ListaEnlazada<T> {
        private Nodo<T> cabeza;// Nodo inicial de la lista


        // Constructor de la clase ListaEnlazada
        public ListaEnlazada() {
            this.cabeza = null;
        }


        // Método para insertar un valor en la lista


        public void insertar(T TipoTelefono) {


            Nodo<T> TELEFONO = new Nodo<>(TipoTelefono);  //TELEFONO2

            if (cabeza == null) {  //

                cabeza = TELEFONO; // TELEFONO1
            } else {
                Nodo<T> actual = cabeza;//TELEFONO1 galaxi
                while (actual.siguiente != null) {  //

                    actual = actual.siguiente;//TELEFONO1 EN EL VALOR DE TELEFONO1.SIGUIENTE Y DENTRO ESTA SU VALOR Y EL TELEFONO2

                }
                actual.siguiente = TELEFONO;  // TELEFONO2
            }
        }













        // Método para imprimir los valores de la lista
        public void imprimir() {
            Nodo<T> actual = cabeza;
            while (actual != null) {  // Recorrer la lista y imprimir cada valor
                System.out.println(actual.valor);
                actual = actual.siguiente;
            }
        }
    }





