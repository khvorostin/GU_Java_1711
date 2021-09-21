package course1.lesson8.counter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CounterWindow extends JFrame {

    private int value;

    private static final String NO_SAVED_YET = "Сохранить значение";
    private static final String SAVED_VALUE = "Вставить сохранённое";
    private boolean isSaved = false;
    private int savedValue;

    public CounterWindow(int initialValue) {

        setTitle("Счётчик");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setBounds(300, 300, 400, 200);

        Font font = new Font("Arial", Font.BOLD, 32);

        JLabel counterValueView = new JLabel();
        counterValueView.setFont(font);
        counterValueView.setHorizontalAlignment(SwingConstants.CENTER);
        add(counterValueView, BorderLayout.CENTER);

        value = initialValue;
        counterValueView.setText(String.valueOf(value));

        JButton buttonDecr = new JButton("<");
        buttonDecr.setFont(font);
        add(buttonDecr, BorderLayout.WEST);
        buttonDecr.addActionListener(e -> counterValueView.setText(String.valueOf(--value)));

        JButton buttonIncr = new JButton(">");
        buttonIncr.setFont(font);
        add(buttonIncr, BorderLayout.EAST);
        buttonIncr.addActionListener(e -> counterValueView.setText(String.valueOf(++value)));

        JButton buttonReset = new JButton("Сброс");
        buttonReset.setFont(font);
        add(buttonReset, BorderLayout.SOUTH);
        buttonReset.addActionListener(e -> {
            value = initialValue;
            counterValueView.setText(String.valueOf(value));
        });

        JButton buttonSave = new JButton(CounterWindow.NO_SAVED_YET);
        buttonSave.setFont(font);
        add(buttonSave, BorderLayout.NORTH);
        buttonSave.addActionListener(e -> {
            if (!isSaved) {
                savedValue = value;
                buttonSave.setText(CounterWindow.SAVED_VALUE);
            } else {
                value = savedValue;
                counterValueView.setText(String.valueOf(value));
                buttonSave.setText(CounterWindow.NO_SAVED_YET);
            }

            isSaved = !isSaved;
        });

        setVisible(true);
    }
}
