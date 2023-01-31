package view;

import javax.swing.JButton;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Color;
import java.awt.AlphaComposite;
import javax.swing.*;
import javax.swing.UIManager.LookAndFeelInfo;

public  class SoftJButton extends JButton {

    private static final JButton lafDeterminer = new JButton();
    private static final long serialVersionUID = 1L;
    private boolean rectangularLAF;
    private float alpha = 1f;

    public SoftJButton() {
        this(null, null);
    }

   public SoftJButton(String text) {
        this(text, null);
    }

   public  SoftJButton(String text, Icon icon) {
        super(text, icon);
        setOpaque(false);
        setFocusPainted(false);
    }

    public float getAlpha() {
        return alpha;
    }

    public void setAlpha(float alpha) {
        this.alpha = alpha;
        repaint();
    }

    @Override
    public void paintComponent(java.awt.Graphics g) {
        java.awt.Graphics2D g2 = (java.awt.Graphics2D) g;
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
        if (rectangularLAF && isBackgroundSet()) {
            Color c = getBackground();
            g2.setColor(c);
            g.fillRect(0, 0, getWidth(), getHeight());
        }
        super.paintComponent(g2);
    }


 //   public void updateUI() {
 //       super.updateUI();
 //       lafDeterminer.updateUI();
 //       rectangularLAF = lafDeterminer.isOpaque();
 //   }
}

