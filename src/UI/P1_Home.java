package UI;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class P1_Home extends JPanel {
    public P1_Home() {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        JPanel titlePanel = new JPanel();
        titlePanel.setBorder(new EmptyBorder(10, 10, 10, 10)); // Set the padding
        titlePanel.setBackground(Color.WHITE);
        titlePanel.setLayout(new BorderLayout()); // Set layout to BorderLayout

        JLabel titleLabel = new JLabel("Welcome"); // TODO: Add name of the employee here
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER); // Align label to the center
        titleLabel.setVerticalAlignment(SwingConstants.TOP); // Align label to the top

        JLabel textLabel = new JLabel("Navigate to different pages to check the stats and place orders");
        textLabel.setFont(new Font("Arial", Font.ITALIC, 18));
        textLabel.setHorizontalAlignment(SwingConstants.CENTER); // Align label to the center

        titlePanel.add(titleLabel, BorderLayout.NORTH); // Add titleLabel to the top
        titlePanel.add(Box.createRigidArea(new Dimension(0, 45)), BorderLayout.CENTER);
        titlePanel.add(textLabel, BorderLayout.SOUTH); // Add textLabel to the center
        add(titlePanel, BorderLayout.NORTH);
    }
}

