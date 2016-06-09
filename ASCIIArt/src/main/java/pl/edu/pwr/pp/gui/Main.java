package pl.edu.pwr.pp.gui;

import pl.edu.pwr.pp.ImageConverter;
import pl.edu.pwr.pp.ImageFileWriter;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by mateuszniezgoda on 22/05/16.
 */
public class Main extends JFrame {
    private JButton readPic;
    private JLabel option1;
    private JButton savePic;
    private pl.edu.pwr.pp.gui.j.JImage picture;
    private JPanel holder;
    private JTextField msgBox;

    private BufferedImage pic;

    public Main() {
        super("Szwagier, pacz jaki obraz, it's bjutiful!");

        setContentPane(holder);

        pack();

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        readPic.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                new ReadFile() {
                    public void onFile(BufferedImage p) {
                        if (null != p) {
                            pic = p;
                            picture.setImage(p);
                            picture.repaint();
                            savePic.setEnabled(true);
                            msgBox.setText("Image has been read.");
                        } else {
                            savePic.setEnabled(false);
                        }

                        savePic.repaint();
                    }

                    public void onError(String ex)
                    {
                        msgBox.setText(ex);
                    }
                };
            }
        });

        savePic.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);

                if (pic != null && savePic.isEnabled()) {

                    JFrame parentFrame = new JFrame();
                    JFileChooser fileChooser = new JFileChooser();
                    fileChooser.setDialogTitle("Specify a file to save");
                    ImageConverter conventer = new ImageConverter();
                    ImageFileWriter writer = new ImageFileWriter();

                    int userSelection = fileChooser.showSaveDialog(parentFrame);

                    if (userSelection == JFileChooser.APPROVE_OPTION) {

                        try {
                            int rows = pic.getHeight(), columns = pic.getWidth();

                            ImageFileWriter imageFileWriter = new ImageFileWriter();
                            int[][] intensities = new int[rows][];
                            for (int i = 0; i < rows; i++) {
                                intensities[i] = new int[columns];
                                for (int j = 0; j < columns; j++) {
                                    intensities[i][j] = pic.getRGB(j, i) & 0xFF;
                                }
                            }
                            // przekształć odcienie szarości do znaków ASCII
                            char[][] ascii = ImageConverter.intensitiesToAscii(intensities);
                            // zapisz ASCII art do pliku tekstowego
                            imageFileWriter.saveToTxtFile(ascii, fileChooser.getSelectedFile() + ".txt");
                        } catch (Exception ex) {
                            ex.printStackTrace();
                            msgBox.setText(ex.getMessage());
                        }
                    }
                }
            }
        });

        setVisible(true);
    }
}
