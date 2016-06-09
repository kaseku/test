package pl.edu.pwr.pp.gui.j;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Created by mateuszniezgoda on 23/05/16.
 */
public class JImage extends JComponent {

    private BufferedImage pic;

    public void setImage(BufferedImage img) {
        this.pic = img;

    }

    @Override
    public void paintComponent(Graphics g) {

        super.paintComponent(g);

        if (this.pic != null) {
            g.drawImage(this.pic, 0, 0, null);
            System.out.println(this.pic.getHeight());
        } else {
            System.out.println("there is no pic?");
        }

    }
}
