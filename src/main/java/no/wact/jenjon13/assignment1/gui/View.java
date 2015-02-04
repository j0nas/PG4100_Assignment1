package no.wact.jenjon13.assignment1.gui;

import no.wact.jenjon13.assignment1.car.LeaseCar;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.*;
import java.util.*;
import java.util.List;

public class View implements Observer {
    private final Map<LeaseCar, JTextArea> objectsTexts;

    public View(final String title, List<LeaseCar> cars) {
        final JFrame jFrame = new JFrame(title);
        jFrame.setLayout(new GridLayout(cars.size(), 1));

        objectsTexts = new HashMap<>(cars.size());
        final JTextArea[] textAreas = new JTextArea[cars.size()];
        for (int i = 0; i < textAreas.length; i++) {
            textAreas[i] = new JTextArea(3, 10);
            textAreas[i].append(cars.get(i).getRegistrationNumber() + "\n");
            textAreas[i].setBorder(new BevelBorder(BevelBorder.LOWERED));
            jFrame.add(textAreas[i]);

            objectsTexts.put(cars.get(i), textAreas[i]);
        }

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
