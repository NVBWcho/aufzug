package com.example.aufzug.repository;
import com.example.aufzug.entities.SPNVObject;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
public class JsonReader {

    public static List<SPNVObject> readJsonArrayFromFile(String filePath) throws IOException {

         InputStream inputStream = JsonReader.class.getResourceAsStream(filePath);

        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(inputStream, objectMapper.getTypeFactory().constructCollectionType(List.class, SPNVObject.class));
    }

    public  List<SPNVObject> getSPNVList() {
        try {
            List<SPNVObject> myObjects = readJsonArrayFromFile("/data/exampleSPNV.json");
            /*for (SPNVObject myObject : myObjects) {
                System.out.println(myObject.toString());
            }*/
            return myObjects;
        } catch (IOException e) {
            e.printStackTrace();
            return  null;
        }
    }
}
