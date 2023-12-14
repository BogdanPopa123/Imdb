package org.example.guiViews;

import javax.swing.*;
import java.awt.*;

public class UpdateProdActorGUI extends JFrame {
    public UpdateProdActorGUI(){



        JButton updateActorButton = new JButton("Update Actor");
        updateActorButton.addActionListener(e -> {
            new UpdateActorGUI();
        });

        JButton updateMovieButton = new JButton("Update Movie");
        updateMovieButton.addActionListener(e -> {
            new UpdateMovieGUI();
        });

        JButton updateSeriesButton = new JButton("Update Series");
        updateSeriesButton.addActionListener(e -> {
            new UpdateSeriesGUI();
        });


        setLayout(new GridLayout(3, 1));

        JPanel updateActorButtonFlow = new JPanel(new FlowLayout());
        updateActorButtonFlow.add(updateActorButton);

        JPanel updateMovieButtonFlow = new JPanel(new FlowLayout());
        updateMovieButtonFlow.add(updateMovieButton);

        JPanel updateSeriesButtonFlow = new JPanel(new FlowLayout());
        updateSeriesButtonFlow.add(updateSeriesButton);

        add(updateActorButtonFlow);
        add(updateMovieButtonFlow);
        add(updateSeriesButtonFlow);

        setSize(600, 400);
        setLocationRelativeTo(null);
        setVisible(true);
    }
}
