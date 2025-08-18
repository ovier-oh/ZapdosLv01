package com.ovier;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;


public class App {
    public static void main( String[] args ){
        Scanner scanner = new Scanner(System.in);

        System.out.println("=== Generador of Settings JSON ==="); 

        // Solicitar datos al usuario 
        System.out.print("Enter technology: ");
        String marca = scanner.nextLine(); 

        System.out.print("Enter Rel_job: ");
        String dispositivo = scanner.nextLine();
        
        System.out.print("Ingrese el Id: ");
        String id = scanner.nextLine();

        System.out.print("Ingrese el año: ");
        String year = scanner.nextLine();
        
        System.out.print("Ingrese el modelo: ");
        String model = scanner.nextLine();

        // Crear objeto con los datos 
        DeviceInfo deviceInfo = new DeviceInfo(marca, dispositivo, id, year, model);

        // Convertir a JSON y guarda en archvio 
        ObjectMapper mapper = new ObjectMapper(); 
        mapper.enable(SerializationFeature.INDENT_OUTPUT);

        try{
            // Crear nombre de archivo unico 
            String fileName = "device_" + id + "_"+ model +";.json";

            // Escribir en archivo 
            mapper.writeValue(new File(fileName), deviceInfo);
            System.out.println("Archivo JSON generado existosamente: " + fileName);
        }catch (IOException e){
            System.err.println("Error JSON generado el archivo JSON: " + e.getMessage());
        } finally{
            scanner.close();
        }
    }

    static class DeviceInfo{
        private String marca; 
        private String dispositivo;
        private String id; 
        private String year;
        private String model;

        public DeviceInfo(String marca, String dispositivo, String id, String year, String model){
            this.marca = marca;
            this.dispositivo = dispositivo; 
            this.id = id;
            this.year = year;
            this.model = model;
        }

        // Getters 
        public String getMarca(){ return marca; }
        public String getDispositivo(){ return dispositivo; }
        public String getId(){ return id; }
        public String getYear() { return year; }
        public String getModel() { return model; }
    }

}
