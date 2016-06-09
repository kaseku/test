package pl.edu.pwr.pp.gui;

import javax.imageio.ImageIO;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.InvalidPropertiesFormatException;
import javax.swing.*;


/**
 * Created by Rahl on 2016-06-09.
 */
public class SelectRes extends JFrame  {
    private JPanel holder;
    private JRadioButton orginalRes;
    private JRadioButton smallRes;
    private JRadioButton maxRes;
    private JRadioButton mediumRes;
    private JButton okButton;
    private JButton cancelButton;
    private ButtonGroup group;

    public SelectRes() {
        super("Skalowanie");

        group = new ButtonGroup();
        group.add(smallRes);
        group.add(mediumRes);
        group.add(maxRes);
        group.add(orginalRes);

        setContentPane(holder);
        pack();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);

        cancelButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);

                setVisible(false);
                dispose();
            }
        });

        okButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if(smallRes.isSelected()){
                    small();
                    setVisible(false);
                    dispose();
                }

                if(mediumRes.isSelected()){
                    medium();
                    setVisible(false);
                    dispose();
                }

                if(maxRes.isSelected()){
                    maxRes();
                    setVisible(false);
                    dispose();
                }

                if(orginalRes.isSelected()){
                    orginal();
                    setVisible(false);
                    dispose();
                }


            }
        });
    }

    public void small()
    {
    }
    public void medium()
    {
    }
    public void maxRes()
    {
    }
    public void orginal()
    {
    }
}
