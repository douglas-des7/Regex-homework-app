import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.formdev.flatlaf.FlatLightLaf;

public class GUI {
    static String inFile = null;
    static String outFile = null;
    static JFileChooser fileSelector = new JFileChooser(System.getProperty("user.dir"));
    static JFrame frame = new JFrame();
    static String regexText = null;

    public static JPanel createFilePanel(String labelText, boolean save) {
        JPanel panel = new JPanel();

        JTextField field = new JTextField();
        JButton button = new JButton("Explorar archivos...");
        JLabel  label = new JLabel(labelText);

        panel.add(label);
        panel.add(Box.createGlue());
        panel.add(field);
        panel.add(button);
        panel.setLayout(new GridLayout(2, 2, 30, 5));

        ActionListener f;

        if( save ) {
            f = new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    int State = GUI.fileSelector.showSaveDialog(GUI.frame);

                    if (State == JFileChooser.APPROVE_OPTION) {
                        field.setText(GUI.fileSelector.getSelectedFile().getAbsolutePath());
                    }
                }
            };
        }
        else {
            f = new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    int State = GUI.fileSelector.showOpenDialog(GUI.frame);

                    if (State == JFileChooser.APPROVE_OPTION) {
                        field.setText(GUI.fileSelector.getSelectedFile().getAbsolutePath());
                    }
                }
            };
        }

        button.addActionListener(f);

        field.setSize(300, 10);
        panel.setSize(480, 60);

        return panel;
    }

    public static void start() {
        FlatLightLaf.setup();

        frame.setResizable(false);


        JLabel regexLabel = new JLabel("Expresión regular:");
        JTextField regexField = new JTextField();
        JPanel inPanel = GUI.createFilePanel("Archivo de entrada", false);
        JPanel outPanel = GUI.createFilePanel("Archivo de salida", true);
        JButton exeButton = new JButton("Ejecutar");
        JLabel statusLabel = new JLabel();

        regexLabel.setBounds(10, 10, 480, 30);
        regexField.setBounds(10, 40, 480, 30);
        inPanel.setLocation(10, 100);
        outPanel.setLocation(10, 200);
        exeButton.setBounds(200, 300, 100, 50);
        statusLabel.setBounds(10, 400, 480, 50);

        frame.add(regexLabel);
        frame.add(regexField);
        frame.add(inPanel);
        frame.add(outPanel);
        frame.add(exeButton);
        frame.add(statusLabel);

        exeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                statusLabel.setText("");
                GUI.inFile =  ((JTextField) inPanel.getComponent(2)).getText();
                GUI.outFile = ((JTextField) outPanel.getComponent(2)).getText();
                GUI.regexText = regexField.getText();

                if( GUI.regexText == null || GUI.regexText.contentEquals("") ) {
                    statusLabel.setText("Regex indefinido");
                    return;
                }

                if( ! LectorTxT.checkFile(GUI.inFile) ){
                    statusLabel.setText("No existe el archivo de entrada");
                    return;
                }

                {
                    File writeFile = new File(GUI.outFile);
                    if (GUI.outFile.contentEquals("") || GUI.outFile == null || writeFile.getParent() == null || !(new File(writeFile.getParent())).exists()) {
                        statusLabel.setText("La ruta del archivo de salida no es válida");
                        return;
                    }
                }

                String[] palabras = LectorTxT.getCadenas(GUI.inFile);

                if(palabras == null) {
                    statusLabel.setText("Documento de entrada en blanco");
                    return;
                }

                /*
                System.out.println(m.find());  // Si encontró una similitud
                System.out.println(m.group()); // Texto generado
                 */
                Pattern pattern = Pattern.compile(GUI.regexText);
                List<String> writeData = new ArrayList<>() {};

                for(String palabra : palabras) {
                    Matcher matcher = pattern.matcher(palabra);
                    if(matcher.matches()) {
                        writeData.add(palabra);
                    }
                }

                String[] writeArrayData = new String[writeData.size()];
                writeArrayData = writeData.toArray(writeArrayData);

                LectorTxT.saveFile(GUI.outFile, writeArrayData);

                statusLabel.setText("Se escribió correctamente");
            }
        });

        frame.setSize(500, 500);

        frame.setLayout(null);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}