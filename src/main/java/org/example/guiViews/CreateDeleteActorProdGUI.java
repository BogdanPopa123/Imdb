package org.example.guiViews;

import javax.swing.*;
import java.awt.*;

public class CreateDeleteActorProdGUI extends JFrame {

    public CreateDeleteActorProdGUI() {

        JButton addActorButton = new JButton("Add Actor");
        addActorButton.addActionListener(e -> {
            new AddActorGUI();
        });

        JButton addMovieButton = new JButton("Add Movie");
        addMovieButton.addActionListener(e -> {
            new AddMovieGUI();
        });

        JButton addSeriesButton = new JButton("Add Series");
        addSeriesButton.addActionListener(e -> {
            new AddSeriesGUI();
        });

        JButton deleteButton = new JButton("Delete item");
        deleteButton.addActionListener(e -> {
            new DeleteItemGUI();
        });

        setLayout(new GridLayout(4, 1));

        JPanel addActorButtonFlow = new JPanel(new FlowLayout());
        addActorButtonFlow.add(addActorButton);

        JPanel addMovieButtonFlow = new JPanel(new FlowLayout());
        addMovieButtonFlow.add(addMovieButton);

        JPanel addSeriesButtonFlow = new JPanel(new FlowLayout());
        addSeriesButtonFlow.add(addSeriesButton);

        JPanel deleteButtonFlow = new JPanel(new FlowLayout());
        deleteButtonFlow.add(deleteButton);

        add(addActorButtonFlow);
        add(addMovieButtonFlow);
        add(addSeriesButtonFlow);
        add(deleteButtonFlow);

        setSize(600, 400);
        setLocationRelativeTo(null);
        setVisible(true);
    }
}
