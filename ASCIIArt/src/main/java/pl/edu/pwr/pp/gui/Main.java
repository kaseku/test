package pl.edu.pwr.pp.gui;

import pl.edu.pwr.pp.ImageConverter;
import pl.edu.pwr.pp.ImageConverterHQ;
import pl.edu.pwr.pp.ImageConverterI;
import pl.edu.pwr.pp.ImageFileWriter;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import static java.awt.image.BufferedImage.TYPE_INT_RGB;
import static oracle.jrockit.jfr.events.Bits.intValue;

/**
 * Created by mateuszniezgoda on 22/05/16.
 */
public class Main extends JFrame {
    private JButton readPic;
    private JButton savePic;
    private pl.edu.pwr.pp.gui.j.JImage picture;
    private JPanel holder;
    private JTextField msgBox;
    private JRadioButton LQuality;
    private JRadioButton HQuality;
    private JButton scalePic;

    private BufferedImage pic;
    private ButtonGroup qualityGroup;
    private int orgWidth;
    private int orgHight;


    public Main() {
        super("Szwagier, pacz jaki obraz, it's bjutiful!");
        

        setContentPane(holder);
        pack();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        group();

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
                        orgWidth = pic.getWidth();
                        orgHight = pic.getHeight();
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
                                if (pic.getType() != BufferedImage.TYPE_BYTE_GRAY) {
                                    Color c = new Color(pic.getRGB(j, i));
                                    intensities[i][j] = (int) (0.2989 * c.getRed() + 0.5870 * c.getGreen() + 0.1140 * c.getBlue());
                                } else {
                                    intensities[i][j] = new Color(pic.getRGB(j, i)).getRed();
                                }
                            }
                        }
                        // przekształć odcienie szarości do znaków ASCII
                        char[][] ascii;

                        if (!LQuality.isSelected()) {
                            ascii = ImageConverterHQ.intensitiesToAscii(intensities);
                            System.out.println("HighQuality converter");
                        } else {
                            ascii = ImageConverter.intensitiesToAscii(intensities);
                        }
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

        scalePic.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if (pic != null && savePic.isEnabled()){
                    new SelectRes() {
                        public void small(){
                            // skalowanie obrazu do rozmiaru 80
                            pic = resizeImage(pic,pic.getType(),80,80*pic.getHeight()/pic.getWidth());
                            picture.setImage(pic);
                            picture.repaint();
                            savePic.repaint();
                            System.out.println("Scaling complete");
                        }

                        public void medium(){
                            // skalowanie obrazu do rozmiaru 160
                            pic = resizeImage(pic,pic.getType(),160,160*pic.getHeight()/pic.getWidth());
                            picture.setImage(pic);
                            picture.repaint();
                            savePic.repaint();
                            System.out.println("Scaling complete");
                        }
                          Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
                          double width = screenSize.getWidth();


                        public void maxRes(){
                            // skalowanie obrazu do szerokosci ekranu
                            pic = resizeImage(pic, pic.getType(),intValue(width), intValue(width)*pic.getHeight()/pic.getWidth());
                            picture.setImage(pic);
                            picture.repaint();
                            savePic.repaint();
                            System.out.println("Scaling complete");
                        }

                        public void orginal(){
                            // skalowanie obrazu do szerokosci orginalnej
                            pic = resizeImage(pic, pic.getType(),orgWidth, orgHight);
                            picture.setImage(pic);
                            picture.repaint();
                            savePic.repaint();
                            System.out.println("Scaling complete");
                        }

                        public void onError(String ex)
                        {
                            msgBox.setText(ex);
                        }
                    };

                }
            }
        });

        setVisible(true);
    }

    private void group()
    {
        qualityGroup = new ButtonGroup();
        qualityGroup.add(LQuality);
        qualityGroup.add(HQuality);
    }

    private static BufferedImage resizeImage(BufferedImage originalImage, int type, int w, int h) {
        BufferedImage resizedImage = new BufferedImage(w,h,type);
        Graphics2D g = resizedImage.createGraphics();
        g.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
                RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g.drawImage(originalImage, 0, 0, w, h, null);
        g.dispose();
        return resizedImage;
    }
}
