package pl.edu.pwr.pp.gui;

import pl.edu.pwr.pp.ImageFileReader;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URL;
import java.util.InvalidPropertiesFormatException;

/**
 * Created by mateuszniezgoda on 22/05/16.
 */
public class ReadFile extends JFrame {
    private JRadioButton fromDrive;
    private JTextField drivePath;
    private JButton fileChooser;
    private JButton cancel;
    private JPanel holder;
    private JRadioButton fromUrl;
    private JButton read;
    private JTextField urlPath;
    private ButtonGroup group;

    public ReadFile() {
        super("TestowoKlaso");

        group = new ButtonGroup();
        group.add(fromUrl);
        group.add(fromDrive);

        setContentPane(holder);
        pack();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);

        cancel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);

                setVisible(false);
                dispose();
            }
        });

        read.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);

                if (fromDrive.isSelected()) {
                    String name = drivePath.getText();
                    System.out.println(name);

                    try {
                        ImageFileReader pgmReader = new ImageFileReader();
                        try {
                            onFile(pgmReader.getImage(name));
                            dispose();
                        } catch (InvalidPropertiesFormatException e2) {
                            onError(e2.getMessage());
                            onFile(ImageIO.read(new File(name)));
                            dispose();
                        }
                    } catch (IOException e1) {
                        onError(e1.getMessage());
                        e1.printStackTrace();
                    }
                } else {
                    try {
                        String name = "tmp.tmp";
                        BufferedInputStream in = null;
                        FileOutputStream fout = null;
                        try {
                            in = new BufferedInputStream(new URL(urlPath.getText()).openStream());
                            fout = new FileOutputStream(name);

                            final byte data[] = new byte[1024];
                            int count;
                            while ((count = in.read(data, 0, 1024)) != -1) {
                                fout.write(data, 0, count);
                            }


                            ImageFileReader pgmReader = new ImageFileReader();
                            try {
                                onFile(pgmReader.getImage(name));
                                dispose();
                            } catch (InvalidPropertiesFormatException e2) {
                                onError(e2.getMessage());
                                onFile(ImageIO.read(new File(name)));
                                dispose();
                            }
                        } finally {
                            if (in != null) {
                                in.close();
                            }
                            if (fout != null) {
                                fout.close();
                            }
                        }
                    } catch (FileNotFoundException e1) {
                        System.out.println(e1.getMessage());
                        onError(e1.getMessage());
                    }  catch (IOException e1) {
                        onError(e1.getMessage());
                        System.out.println(e1.getMessage());
                    }
                }
            }
        });

        fileChooser.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                JFileChooser fc = new JFileChooser();
                FileNameExtensionFilter filter = new FileNameExtensionFilter("Images", "jpg", "pgm");
                fc.setFileFilter(filter);
                int returnVal = fc.showOpenDialog(ReadFile.this);
                if(returnVal == JFileChooser.APPROVE_OPTION) {
                    drivePath.setText(fc.getSelectedFile().getAbsolutePath());
                }
            }
        });
    }

    public void onFile(BufferedImage f)
    {
    }

    public void onError(String message)
    {

    }

}