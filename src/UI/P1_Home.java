package UI;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class P1_Home extends JPanel {
    public P1_Home(String username) {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        JPanel titlePanel = new JPanel(new BorderLayout());
        titlePanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        titlePanel.setBackground(Color.WHITE);

        // Create the logout button
        JButton logoutButton = new JButton("Logout");
        logoutButton.setFont(new Font("Arial", Font.PLAIN, 12));
        logoutButton.setFocusPainted(false);
        logoutButton.setPreferredSize(new Dimension(140, 25));

        // Add action listener to the logout button
        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Perform logout action here
                // ...

                // Open the login page again
                LoginPage loginPage = new LoginPage();
                loginPage.setVisible(true);
                SwingUtilities.getWindowAncestor(P1_Home.this).dispose();
            }
        });

        JLabel titleLabel = new JLabel("Welcome " + username);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER); // Align label to the center
        titleLabel.setVerticalAlignment(SwingConstants.TOP); // Align label to the top

        JLabel textLabel = new JLabel("Navigate to different pages to check the stats and place orders");
        textLabel.setFont(new Font("Arial", Font.ITALIC, 18));
        textLabel.setHorizontalAlignment(SwingConstants.CENTER); // Align label to the center

        JPanel logoutPanel = new JPanel();
        logoutPanel.setOpaque(false);
        logoutPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        logoutPanel.add(logoutButton);

        titlePanel.add(logoutPanel, BorderLayout.NORTH);
        titlePanel.add(titleLabel, BorderLayout.CENTER);
        titlePanel.add(textLabel, BorderLayout.SOUTH);

        add(titlePanel, BorderLayout.NORTH);
    }
}
