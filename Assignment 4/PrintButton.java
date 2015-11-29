/*
 * Matt Asnes
 * COMP-86 Assignment 4
 *
 * This class is a simple button where display text and printed text
 * are the same string, passed to the constructor. When clicked
 * it prints its string.
 */


import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class PrintButton extends JButton {

        private String s1;

        public PrintButton(String s1) {
                this.s1 = s1;

                this.setText(s1);

                this.addActionListener(
                        new ActionListener() {
                                public void actionPerformed(ActionEvent e) {
                                        System.out.println(PrintButton.this.s1);
                                }
                        }
                ); 
        }

}