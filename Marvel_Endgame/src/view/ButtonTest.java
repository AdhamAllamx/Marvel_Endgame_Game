
package view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Color;
import java.awt.AlphaComposite;
import javax.swing.*;
import javax.swing.UIManager.LookAndFeelInfo;

public class ButtonTest {

    private JFrame frame;
    private JButton opaqueButton1;
    private JButton opaqueButton2;
    private SoftJButton softButton1;
    private SoftJButton softButton2;
    private Timer alphaChanger;

    public void createAndShowGUI() {
       /// = new JButton("Opaque Button1");
      //  opaqueButton2 = new JButton("Opaque Button2");
     //   softButton1 = new SoftJButton("Transparent Button1");
        softButton2 = new SoftJButton("Transparent Button2");
      

       
        alphaChanger = new Timer(30, new ActionListener() {

            private float incrementer = -.03f;

            @Override
            public void actionPerformed(ActionEvent e) {
                float newAlpha = softButton1.getAlpha() + incrementer;
                if (newAlpha < 0) {
                    newAlpha = 0;
                    incrementer = -incrementer;
                } else if (newAlpha > 1f) {
                    newAlpha = 1f;
                    incrementer = -incrementer;
                }
                softButton1.setAlpha(newAlpha);
                softButton2.setAlpha(newAlpha);
            }
        });
        alphaChanger.start();
        Timer uiChanger = new Timer(3500, new ActionListener() {

            private final LookAndFeelInfo[] laf = UIManager.getInstalledLookAndFeels();
            private int index = 1;

            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    UIManager.setLookAndFeel(laf[index].getClassName());
                    SwingUtilities.updateComponentTreeUI(frame);
                    opaqueButton1.setText(laf[index].getClassName());
                    softButton1.setText(laf[index].getClassName());
                } catch (Exception exc) {
                    exc.printStackTrace();
                }
                index = (index + 1) % laf.length;
            }
        });
        uiChanger.start();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {

            @Override
            public void run() {
                new ButtonTest().createAndShowGUI();
            }
        });
    }
}

