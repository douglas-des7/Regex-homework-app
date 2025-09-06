import java.nio.file.*;
import java.util.*;
import java.io.*;


import java.util.List;

public class LectorTxT {
    private boolean checkFile(String filePath){
        File ruta = new File(filePath);
        if (ruta.exists()){
            System.out.println("El archivo existe");
            return true;
        }
        else {
            System.out.println("No eciste el archivo");
            return false;
        }
    }
    public String[] getCadenas(String filePath){
        if(!checkFile(filePath)){return null;}
        List<String> cadenas = new ArrayList<>();
        try (Scanner sc = new Scanner(new File(filePath))){
            while (sc.hasNextLine()){
                String cadenabase = sc.nextLine();
                if (cadenabase.contains(" ") || cadenabase.contains("\t")){
                    String[] subcadenas = cadenabase.split("\\s+");
                    cadenas.addAll(Arrays.asList(subcadenas));
                    for (String palabra : subcadenas) {
                        System.out.println(palabra);
                    }
                }
                else {
                    cadenas.add(cadenabase);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return cadenas.toArray(new String[0]);
    }
    public void saveFile(String fileOutput, String[] datos){
        checkFile(fileOutput);
        try(PrintWriter grabador = new PrintWriter(new File(fileOutput))){
            for (String d: datos){
                grabador.println(d);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
