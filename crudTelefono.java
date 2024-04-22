package org.example;

import org.bson.Document;


import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.*;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class crudTelefono {

    private MongoClient mongoClient;
    private MongoDatabase database;
    private MongoCollection <Document> collection;
    public void conexion (){
        //cadena de conexion, contiene la información de la insalacion del mongodb
        ConnectionString connectionString = new ConnectionString("mongodb://localhost:27017");    //local host 1
        MongoClientSettings settings = MongoClientSettings.builder()                              //se crean las configuraciones especificas para conexion y manejo de la base de datos
                .applyConnectionString(connectionString)
                .build();
        mongoClient = MongoClients.create(settings);
        database = mongoClient.getDatabase("Dispositivos");//miBasedatos
        collection = database.getCollection("Telefonos");//usuarios                //NOMBRE DE DB
        System.out.println("conexion realizada");
    }





    public void crearUsuario2(){
        Scanner scanner = new Scanner(System.in);
        mdTelefono telefono = new mdTelefono();
        telefono.setMarca(JOptionPane.showInputDialog("Ingrese la Marca del Telefono :"));
        telefono.setModelo(JOptionPane.showInputDialog("Ingrese el Modelo:"));
        telefono.setSistemaOperativo(JOptionPane.showInputDialog("Ingrese el Sistema Operativo:"));
        telefono.setTamanoPantalla  (Double.parseDouble( JOptionPane.showInputDialog("Ingrese el Tamaño de la Pantalla:")));
        telefono.setMemoriaRAM(Integer.parseInt(JOptionPane.showInputDialog("Ingrese Cantidad de RAM):")));
        telefono.setAlmacenamientoInterno(Integer.parseInt(JOptionPane.showInputDialog("Ingrese el Almacenamiento):")));

        int selec = Integer.parseInt(JOptionPane.showInputDialog("Tiene Camara \n 1  Si \n 2 No"));

            switch (selec) {
                case 1:
                    telefono.setTieneCamara(true);
                    break;
                case 2:
                   telefono.setTieneCamara(false);
                    break;
                default:
                    System.out.println("Opción no válida");
            }

        //telefono.setTieneCamara (Boolean.parseBoolean(JOptionPane.showInputDialog("Tiene Camara Si o No:")));

        telefono.setResolucionCamara(Integer.parseInt(JOptionPane.showInputDialog("Ingrese la Resolucion de la Camara):")));

        int selec2 = Integer.parseInt(JOptionPane.showInputDialog("Es un Smartphone \n 1  Si \n 2 No"));

        switch (selec2) {
            case 1:
                telefono.setEsSmartphone(true);
                break;
            case 2:
                telefono.setEsSmartphone(false);
                break;
            default:
                System.out.println("Opción no válida");
        }
        //telefono.setEsSmartphone (Boolean.parseBoolean(JOptionPane.showInputDialog("Es un Smartphone Si o No):")));

        telefono.setImei(JOptionPane.showInputDialog("Ingrese el Codigo IMEI:"));


        Document document = new Document("Marca",telefono.getMarca())
                .append("Modelo",telefono.getModelo())
                .append("Sistema",telefono.getSistemaOperativo())
                .append("tamañoPantalla",telefono.getTamanoPantalla())
                .append("memoriaRAM",telefono.getMemoriaRAM())
                .append("Almacenamieto",telefono.getAlmacenamientoInterno())
                .append("tieneCamara",telefono.isTieneCamara())
                .append("resolucionCamara",telefono.getResolucionCamara())
                .append("esSmarphone",telefono.isEsSmartphone())
                .append("IMEI",telefono.getImei());

        collection.insertOne(document);
        System.out.println("Telefono Creado");

    }

    public void  leerUsuarios(){
        List<mdTelefono> telefonos = new ArrayList<>();
        //leer todos los documentos
        for(Document doc : collection.find() ){
            mdTelefono u = new mdTelefono();
            u.setMarca(doc.getString("Marca"));
            u.setModelo(doc.getString("Modelo"));
            u.setSistemaOperativo(doc.getString("Sistema"));
            u.setTamanoPantalla(doc.getDouble("tamañoPantalla"));
            u.setMemoriaRAM(doc.getInteger("memoriaRAM"));
            u.setAlmacenamientoInterno(doc.getInteger("Almacenamieto"));
            u.setTieneCamara(doc.getBoolean("tieneCamara"));
            u.setResolucionCamara(doc.getInteger("resolucionCamara"));
            u.setEsSmartphone(doc.getBoolean("esSmarphone"));
            u.setImei(doc.getString("IMEI"));

            //aca se pueden agregar mas columnas
            telefonos.add(u);
        }
        for (mdTelefono u : telefonos){
           System.out.println("Marca:"+u.getMarca()+" Modelo:"+ u.getModelo()+" Sistema: "+u.getSistemaOperativo()
           +" Tamaño de Pantalla:"+ u.getTamanoPantalla()+" Memoria RAM:"+ u.getMemoriaRAM()+" Almacenamiento: "+u.getAlmacenamientoInterno()
           +" Tiene Camara:"+ u.isTieneCamara()+" Resolucion de Camara: "+u.getResolucionCamara()
           +" Es Smartphone:"+ u.isEsSmartphone()+" IMEI: "+u.getImei());

        }
    }


    public void UpdateTelefono() {

        int swich = Integer.parseInt(JOptionPane.showInputDialog("1 Modificar Marca\n2 Modificar Modelo\n INGRESA EL NUMERO"));
        switch (swich) {
            case 1:
                String id2 = JOptionPane.showInputDialog("Ingrese IMEI:");//aqui puedo crear un metodo que reciba los paramatros y se modifique mas optimo
                String MARCA = JOptionPane.showInputDialog("Ingrese la nueva Marca:");
                collection.updateOne(Filters.eq("IMEI", id2),
                        Updates.set("Marca", MARCA));//puedo editarlo para que me devuelva solo la linea del que estoy editanto
                        //Updates.set("Marca",nom)
                System.out.println("Telefono actualizado");

                break;
            case 2:
                String id = JOptionPane.showInputDialog("Ingrese IMEI:");
                String model = JOptionPane.showInputDialog("Ingrese el nuevo Modelo:");
                collection.updateOne(Filters.eq("IMEI", id),
                        Updates.set("Modelo", model));
                System.out.println("Telefono actualizado");

                break;
            default:
                System.out.println("Valor no aceptado");
        }

    }









    public  void eliminarUsuario() {
        String imei = JOptionPane.showInputDialog("Ingrese el IMEI:");

        collection.deleteOne(Filters.eq("IMEI", imei));

        System.out.println("Usuario eliminado");
    }




}







