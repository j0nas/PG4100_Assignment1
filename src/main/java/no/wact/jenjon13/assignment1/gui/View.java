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

/**
 * View class representing user GUI/presentation layer.
 */
public class View extends Observable implements Observer {
    private final Map<LeaseCar, JTextArea> objectsTexts;

    /**
     * Constructor method, initializes the GUI.
     *
     * @param title Title of the GUI window frame.
     * @param cars  A list of ListCars. Their respective attributes will be presented to the user.
     */
    public View(final String title, List<LeaseCar> cars) {
        final JFrame jFrame = new JFrame(title);
        jFrame.setLayout(new GridLayout(2, 2));
        final JPanel leaseCarContain = new JPanel(new GridLayout());

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
            @Override
            public void keyTyped(final KeyEvent e) {
            }

            @Override
            public void keyPressed(final KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    actionListener.actionPerformed(null);
                }
            }

            @Override
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

    /**
     * Gets called when the instance that this class observes notifies observers of updates.
     * This view observes updates that occur in the model, and is set as such by the controller.
     * Provides a more loosely coupled attachement to the model.
     * Recieves updates from the model, which this class reflects in the presentation.
     *
     * @param o   The Observable that dispatched the update call.
     * @param arg The argument that the Observable object passed in the update dispatcher method.
     * @see no.wact.jenjon13.assignment1.Controller#update
     */
    @Override
    public void update(final Observable o, final Object arg) {
        final JTextArea carText = objectsTexts.get(arg);

        final StringBuilder builder = new StringBuilder();
        final LeaseCar car = (LeaseCar) arg;
        builder.append(car.getRegistrationNumber() + "\n");
        builder.append("Leased by: " + (car.getLeasedBy() != null ? car.getLeasedBy().getCustomerName() : "") + "\n");
        builder.append("Times leased: " + String.valueOf(car.getLeasedTimes()));
        carText.setText(builder.toString());
    }
}
