package org.example.DataAcces;


import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoCursor;
import org.bson.Document;

import java.util.Arrays;


import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import org.example.Helpers.ListaEnlazada;
import org.example.Models.mdTelefono;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class crudTelefono {

    public MongoClient mongoClient;
    public MongoDatabase database;
    public MongoCollection <Document> collection;

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

    public  void eliminarUsuario() {
        String imei = JOptionPane.showInputDialog("Ingrese el IMEI:");

        collection.deleteOne(Filters.eq("IMEI", imei));

        System.out.println("Usuario eliminado");
    }

    public void  agregarDocumeto(){
      try {
          //Agregar elementos por medio de un Arraylis y documentos

          Document newFile = new Document("Marca", "Xiaomi")
                  .append("Modelo", "Y9")
                  .append("Sistema", "Snapdragon")
                  .append("tamañoPantalla",5.2)
                  .append("memoriaRAM", 12)
                  .append("Almacenamieto", 64)
                  .append("tieneCamara", true)
                  .append("resolucionCamara", 64)
                  .append("esSmarphone", true)
                  .append("IMEI", "00001");

          Document newFile2 = new Document("Marca", "Alcatel")
                  .append("Modelo", "Pixi")
                  .append("Sistema", "Android")
                  .append("tamañoPantalla", 4.2)
                  .append("memoriaRAM", 64)
                  .append("Almacenamieto", 16)
                  .append("tieneCamara", true)
                  .append("resolucionCamara", 8)
                  .append("esSmarphone", true)
                  .append("IMEI", "00002");


          Document newFile3 = new Document("Marca", "Huawei")
                  .append("Modelo", "Y330")
                  .append("Sistema", "Android")
                  .append("tamañoPantalla", 4.5)
                  .append("memoriaRAM", 5)
                  .append("Almacenamieto", 16)
                  .append("tieneCamara", true)
                  .append("resolucionCamara", 8)
                  .append("esSmarphone", true)
                  .append("IMEI", "00003");

        Document newFile4 = new Document("Marca", "Toyota")
                .append("Modelo", "exp")
                .append("Sistema", "V6")
                .append("tamañoPantalla", 2.4)
                .append("memoriaRAM", 3)
                .append("Almacenamieto", 16)
                .append("tieneCamara", true)
                .append("resolucionCamara", 6)
                .append("esSmarphone", false)
                .append("IMEI", "00004");

        Document newFile5 = new Document("Marca", "Samsumg")
                .append("Modelo", "A10")
                .append("Sistema", "Android 11")
                .append("tamañoPantalla", 4.5)
                .append("memoriaRAM", 4)
                .append("Almacenamieto", 32)
                .append("tieneCamara", true)
                .append("resolucionCamara", 16)
                .append("esSmarphone", true)
                .append("IMEI", "00005");

        Document newFile6 = new Document("Marca", "Experia")
                .append("Modelo", "Z10")
                .append("Sistema", "Snapdragon")
                .append("tamañoPantalla", 5.5)
                .append("memoriaRAM", 10)
                .append("Almacenamieto", 10)
                .append("tieneCamara", true)
                .append("resolucionCamara", 22)
                .append("esSmarphone", true)
                .append("IMEI", "00006");

        Document newFile7 = new Document("Marca", "Iphone")
                .append("Modelo", "4")
                .append("Sistema", "IOS")
                .append("tamañoPantalla", 4.2)
                .append("memoriaRAM", 3)
                .append("Almacenamieto", 16)
                .append("tieneCamara", true)
                .append("resolucionCamara", 12)
                .append("esSmarphone", true)
                .append("IMEI", "00007");

        Document newFile8 = new Document("Marca", "Blue")
                .append("Modelo", "12s")
                .append("Sistema", "Android")
                .append("tamañoPantalla", 2.4)
                .append("memoriaRAM", 5)
                .append("Almacenamieto", 32)
                .append("tieneCamara", true)
                .append("resolucionCamara", 24)
                .append("esSmarphone", false)
                .append("IMEI", "00008");


        collection.insertMany(Arrays.asList(newFile, newFile2, newFile3,newFile4, newFile5, newFile6, newFile7, newFile8));
          System.out.println("se agregaron Exitosamente");


      }catch (Exception e) {
        System.err.println("the Upload File have a problem: " + e.getMessage());
    }




    }

    public  void listaEnlazada(){

        ListaEnlazada<Document> listaEnlazada = new ListaEnlazada<>();
        // Insertar los documentos existentes en la base de datos en la lista enlazada


        MongoCursor<Document> cursor = collection.find().iterator();
        while (cursor.hasNext()) {
            Document documento = cursor.next();
            listaEnlazada.insertar(documento);
        }

        // Imprimir la lista enlazada
        listaEnlazada.imprimir();




    }














    // Conexión a la base de datos MongoDB

      // MongoClient mongoClients = MongoClients.create("mongodb://localhost:27017");

//    {
//        //MongoDatabase databases = mongoClient.getDatabase("test");
//        //MongoCollection<Document> collections = database.getCollection("telefonos");
//
//        //  Crear la lista enlazada
//
//
//        ListaEnlazada<Document> listaEnlazada = new ListaEnlazada<>();
//
//        // Insertar los documentos existentes en la base de datos en la lista enlazada
//        MongoCursor<Document> cursor = collection.find().iterator();
//        while (cursor.hasNext()) {
//            Document documento = cursor.next();
//            listaEnlazada.insertar(documento);
//        }
//
//        // Imprimir la lista enlazada
//        listaEnlazada.imprimir();
//    }


//        // Insertar nuevos documentos en la base de datos
//        Document nuevoDocumento1 = new Document("marca", "Nokia")
//                .append("modelo", "3310")
//                .append("sistemaOperativo", "Symbian")
//                .append("tamanoPantalla", 2.4)
//                .append("memoriaRAM", 0.5)
//                .append("almacenamientoInterno", 16)
//                .append("tieneCamara", true)
//                .append("resolucionCamara", 2)
//                .append("esSmartphone", false)
//                .append("imei", "000000000000000");
//
//        Document nuevoDocumento2 = new Document("marca", "Motorola")
//                .append("modelo", "Moto G")
//                .append("sistemaOperativo", "Android")
//                .append("tamanoPantalla", 6.4)
//                .append("memoriaRAM", 4)
//                .append("almacenamientoInterno", 64)
//                .append("tieneCamara", true)
//                .append("resolucionCamara", 48)
//                .append("esSmartphone", true)
//                .append("imei", "111111111111111");
//
//        Document nuevoDocumento3 = new Document("marca", "Sony")
//                .append("modelo", "Xperia XZ")
//                .append("sistemaOperativo", "Android")
//                .append("tamanoPantalla", 5.2)
//                .append("memoriaRAM", 3)
//                .append("almacenamientoInterno", 32)
//                .append("tieneCamara", true)
//                .append("resolucionCamara", 23)
//                .append("esSmartphone", true)
//                .append("imei", "222222222222222");
//
//        // Agregar los nuevos documentos a la colección
//        collection.insertMany(Arrays.asList(nuevoDocumento1, nuevoDocumento2, nuevoDocumento3));
//
//        // Insertar los documentos anteriores en la colección
//        Document documento1 = new Document("marca", "Samsung")
//                .append("modelo", "Galaxy S21")
//                .append("sistemaOperativo", "Android")
//                .append("tamanoPantalla", 6.2)
//                .append("memoriaRAM", 8)
//                .append("almacenamientoInterno", 128)
//                .append("tieneCamara", true)
//                .append("resolucionCamara", 64)
//                .append("esSmartphone", true)
//                .append("imei", "123456789");
//
//        Document documento2 = new Document("marca", "Apple")
//                .append("modelo", "iPhone 12")
//                .append("sistemaOperativo", "iOS")
//                .append("tamanoPantalla", 6.1)
//                .append("memoriaRAM", 4)
//                .append("almacenamientoInterno", 256)
//                .append("tieneCamara", true)
//                .append("resolucionCamara", 12)
//                .append("esSmartphone", true)
//                .append("imei", "987654321");
//
//        Document documento3 = new Document("marca", "Xiaomi")
//                .append("modelo", "Redmi Note 10")
//                .append("sistemaOperativo", "Android")
//                .append("tamanoPantalla", 6.43)
//                .append("memoriaRAM", 6)
//                .append("almacenamientoInterno", 128)
//                .append("tieneCamara", true)
//                .append("resolucionCamara", 48)
//                .append("esSmartphone", true)
//                .append("imei", "135792468");
//
//        Document documento4 = new Document("marca", "Google")
//                .append("modelo", "Pixel 5")
//                .append("sistemaOperativo", "Android")
//                .append("tamanoPantalla", 6.0)
//                .append("memoriaRAM", 8)
//                .append("almacenamientoInterno", 128)
//                .append("tieneCamara", true)
//                .append("resolucionCamara", 12.2)
//                .append("esSmartphone", true)
//                .append("imei", "246813579");
//
//        Document documento5 = new Document("marca", "OnePlus")
//                .append("modelo", "8T")
//                .append("sistemaOperativo", "Android")
//                .append("tamanoPantalla", 6.55)
//                .append("memoriaRAM", 12)
//                .append("almacenamientoInterno", 256)
//                .append("tieneCamara", true)
//                .append("resolucionCamara", 48)
//                .append("esSmartphone", true)
//                .append("imei", "987654321");
//
//        collection.insertMany(Arrays.asList(documento1, documento2, documento3, documento4, documento5));
//
//    } catch (Exception e) {
//        System.err.println("Error al conectar a la base de datos MongoDB: " + e.getMessage());
//    }
}



















