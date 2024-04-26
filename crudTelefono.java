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

       // int swich = Integer.parseInt(JOptionPane.showInputDialog("1 Modificar Marca\n2 Modificar Modelo\n INGRESA EL NUMERO"));
        int swich = Integer.parseInt(JOptionPane.showInputDialog("MODIFICAR CARACTERISTICA \n1  Marca\n2    Modelo\n3   Sistema\n4  Tamaño de Pantalla\n5   Memoria Ram\n6  Almacenamiento\n7 Camara\n8 Resolucion de Camara\n9 Tipo de Telefno\n   INGRESA EL NUMERO"));


        String ImeiConsola;

        switch (swich) {
            case 1:
                //aqui modificamos la marca
                ImeiConsola = JOptionPane.showInputDialog("Ingrese IMEI:");//aqui puedo crear un metodo que reciba los paramatros y se modifique mas optimo
                String MARCA = JOptionPane.showInputDialog("Ingrese la nueva Marca:");
                collection.updateOne(Filters.eq("IMEI", ImeiConsola),
                        Updates.set("Marca", MARCA));//puedo editarlo para que me devuelva solo la linea del que estoy editanto
                        //Updates.set("Marca",nom)
                System.out.println("Telefono actualizado");

                break;
            case 2:
                //aqui modificamos el modelo
                ImeiConsola  = JOptionPane.showInputDialog("Ingrese IMEI:");
                String model = JOptionPane.showInputDialog("Ingrese el nuevo Modelo:");
                collection.updateOne(Filters.eq("IMEI", ImeiConsola),
                        Updates.set("Modelo", model));
                System.out.println("Telefono actualizado");

                break;
            case 3:
                //aqui modifiacamos el sistema
                ImeiConsola = JOptionPane.showInputDialog("Ingrese IMEI:");
                String sistem = JOptionPane.showInputDialog("Ingrese el nuevo Sistema:");
                collection.updateOne(Filters.eq("IMEI", ImeiConsola),
                        Updates.set("Sistema", sistem));
                System.out.println("Telefono actualizado");

                break;
            case 4:
                //aqui modificamos el tamaño de la pantalla
                ImeiConsola = JOptionPane.showInputDialog("Ingrese IMEI:");
                Double tamPant = Double.parseDouble(JOptionPane.showInputDialog("Ingrese el nuevo Tamaño de Pantalla:"));
                collection.updateOne(Filters.eq("IMEI", ImeiConsola),
                        Updates.set("tamañoPantalla", tamPant));
                System.out.println("Telefono actualizado");

                break;
            case 5:
                //aqui modificamos la memoria Ram
                ImeiConsola= JOptionPane.showInputDialog("Ingrese IMEI:");
                int ram =Integer.parseInt(JOptionPane.showInputDialog("Ingrese la nueva cantidad de Ram:"));
                collection.updateOne(Filters.eq("IMEI", ImeiConsola),
                        Updates.set("memoriaRAM", ram));
                System.out.println("Telefono actualizado");

                break;
            case 6:
                //aqui modificamos el almacenamiento
                ImeiConsola = JOptionPane.showInputDialog("Ingrese IMEI:");
                int alamacenamiento =Integer.parseInt(JOptionPane.showInputDialog("Ingrese el nuevo Almacenanmiento:"));
                collection.updateOne(Filters.eq("IMEI", ImeiConsola),
                        Updates.set("Modelo", alamacenamiento));
                System.out.println("Telefono actualizado");

                break;
            case 7:
                //aqui modificamos la camara
                ImeiConsola = JOptionPane.showInputDialog("Ingrese IMEI:");
                int subSwich = Integer.parseInt(JOptionPane.showInputDialog("1  Tiene Camara\n2 No Tiene Camara\n   INGRESA EL NUMERO"));
                switch (subSwich) {

                    case 1:
                        //si tiene Camara

                        Boolean camera = true;
                        collection.updateOne(Filters.eq("IMEI", ImeiConsola),
                                Updates.set("tieneCamara", camera));
                        System.out.println("Telefono actualizado");

                        break;
                    case 2:
                        Boolean camera2 = false;
                        collection.updateOne(Filters.eq("IMEI", ImeiConsola),
                                Updates.set("tieneCamara", camera2));
                        System.out.println("Telefono actualizado");

                        break;
                    default:
                        System.out.println("Valor no aceptado");
                }

                break;
            case 8:
                //aqui modificamos la resolucin es tipo entero

                ImeiConsola = JOptionPane.showInputDialog("Ingrese IMEI:");
                int resolution =Integer.parseInt(JOptionPane.showInputDialog("Ingrese la nueva resolución:"));
                collection.updateOne(Filters.eq("IMEI", ImeiConsola),
                        Updates.set("resolucionCamara", resolution));
                System.out.println("Telefono actualizado");

                break;
            case 9:
                //aqui modificamos tipo de telefono
                ImeiConsola = JOptionPane.showInputDialog("Ingrese IMEI:");
                int subSwich2 = Integer.parseInt(JOptionPane.showInputDialog("1  Es SmartPhonea\n2 No es EsmartPhone\n   INGRESA EL NUMERO"));
                switch (subSwich2) {

                    case 1:
                        //si tiene Camara

                        Boolean smart = true;
                        collection.updateOne(Filters.eq("IMEI", ImeiConsola),
                                Updates.set("esSmarphone", smart));
                        System.out.println("Telefono actualizado");

                        break;
                    case 2:
                        Boolean smart2 = false;
                        collection.updateOne(Filters.eq("IMEI", ImeiConsola),
                                Updates.set("esSmarphone", smart2));
                        System.out.println("Telefono actualizado");

                        break;
                    default:
                        System.out.println("Valor no aceptado");
                }


                break;
            default:
                System.out.println("Valor no aceptado");
        }
    }

//    public void updateParametros(){
//        int swich = Integer.parseInt(JOptionPane.showInputDialog("1 Modificar Marca\n2 Modificar Modelo\n INGRESA EL NUMERO"));
//        int n = swich;
//
//        if ( n = 1;){
//            String var = "Marca";
//        }
//
//
//
//        String id = JOptionPane.showInputDialog("Ingrese IMEI:");
//
//        String model = JOptionPane.showInputDialog("Ingrese el nuevo Modelo:");
//        collection.updateOne(Filters.eq("IMEI", id),
//                Updates.set("Modelo", model));
//        System.out.println("Telefono actualizado");
//
//
//    }








    public  void eliminarUsuario() {
        String imei = JOptionPane.showInputDialog("Ingrese el IMEI:");

        collection.deleteOne(Filters.eq("IMEI", imei));

        System.out.println("Usuario eliminado");
    }




}







