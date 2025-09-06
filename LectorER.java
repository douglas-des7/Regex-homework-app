import java.util.List;
import  java.util.Scanner;
public class LectorER {
    public static void main(String[] args){
        /*
        String inputPath = "C:/Users/loque/Downloads/IntText.txt";
        String outputPath = "C:/Users/loque/Downloads/OutText.txt";
        */
        Scanner sc = new Scanner(System.in);
        String inputPath, outputPath, expresionRegular;
        System.out.println("Archivo de texto(Entrada): ");
        inputPath = sc.nextLine();
        System.out.println("Archivo de salida");
        outputPath = sc.nextLine();
        System.out.println("Expresión regular");
        expresionRegular = sc.nextLine();
        LectorTxT app = new LectorTxT();
        String[] cadenas = app.getCadenas(inputPath);
        app.saveFile(outputPath, cadenas);

    }
}
