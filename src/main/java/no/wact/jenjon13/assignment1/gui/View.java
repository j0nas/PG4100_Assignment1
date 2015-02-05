package no.wact.jenjon13.assignment1.gui;

import no.wact.jenjon13.assignment1.car.LeaseCar;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.*;
import java.util.List;

public class View extends Observable implements Observer {
    private final Map<LeaseCar, JTextArea> objectsTexts;

    public View(final String title, List<LeaseCar> cars) {
        final JFrame jFrame = new JFrame(title);
        final JPanel leaseCarContain = new JPanel(new GridLayout());

        jFrame.setLayout(new GridLayout(2, 2));

        objectsTexts = new HashMap<>(cars.size());
        final JTextArea[] textAreas = new JTextArea[cars.size()];
        for (int i = 0; i < textAreas.length; i++) {
            textAreas[i] = new JTextArea(3, 10);
            textAreas[i].append(cars.get(i).getRegistrationNumber() + "\n");
            textAreas[i].setBorder(new BevelBorder(BevelBorder.LOWERED));
            textAreas[i].setEditable(false);
            leaseCarContain.add(textAreas[i]);

            objectsTexts.put(cars.get(i), textAreas[i]);
        }

        jFrame.add(leaseCarContain);

        final JPanel newCustomerCompoentsContain = new JPanel(new GridLayout(1, 2));
        final JTextField addCustomerTxt = new JTextField();
        final ActionListener actionListener = new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                setChanged();
                notifyObservers(addCustomerTxt.getText());
                addCustomerTxt.setText("");
            }
        };

        addCustomerTxt.addKeyListener(new KeyListener() {
            public void keyTyped(final KeyEvent e) {
            }

            public void keyPressed(final KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    actionListener.actionPerformed(null);
                }
            }

            public void keyReleased(final KeyEvent e) {
            }
        });
        newCustomerCompoentsContain.add(addCustomerTxt);


        final JButton addCustomerBtn = new JButton("Add customer");
        addCustomerBtn.addActionListener(actionListener);
        newCustomerCompoentsContain.add(addCustomerBtn);

        jFrame.add(newCustomerCompoentsContain);
        jFrame.pack();
        jFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        jFrame.setLocationRelativeTo(null);
        jFrame.setVisible(true);
    }

    @Override
    public void update(final Observable o, final Object arg) {
        final JTextArea carText = objectsTexts.get(arg);

        final StringBuilder builder = new StringBuilder();
        final LeaseCar car = (LeaseCar) arg;
        builder.append(car.getRegistrationNumber() + "\n");
        builder.append("Leased by: " + car.getLeasedBy() + "\n");
        builder.append("Times leased: " + String.valueOf(car.getLeasedTimes()));

        carText.setText(builder.toString());
    }
}
