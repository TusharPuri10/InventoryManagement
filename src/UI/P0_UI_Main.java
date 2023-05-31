package UI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class P0_UI_Main extends JFrame {
    private JPanel sidePanel;
    private JPanel contentPanel;

    private JPanel homePanel;
    private JPanel employeesPanel;
    private JPanel retailersPanel;
    private JPanel productsPanel;
    private JPanel logsPanel;

    public P0_UI_Main(String userType) {
        setTitle(userType + " Page");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(800, 500));

        // Create the side panel
        sidePanel = new JPanel(new GridBagLayout());
        sidePanel.setPreferredSize(new Dimension(200, getHeight()));
        sidePanel.setBackground(Color.LIGHT_GRAY);

        JButton homeButton = createSidePanelButton("Home");
        JButton retailersButton = createSidePanelButton("Retailers");
        JButton employeesButton = createSidePanelButton("Employees"); //TODO: add if statement
        JButton productsButton = createSidePanelButton("Products");
        JButton logsButton = createSidePanelButton("Logs");

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
//        gbc.gridy = GridBagConstraints.RELATIVE;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        gbc.insets = new Insets(10, 10, 10, 10);
//        gbc.anchor = GridBagConstraints.NORTH; // Set the anchor to NORTH

        // Add buttons to the side panel with equal width
        sidePanel.add(homeButton, gbc);
        gbc.gridy++;
        sidePanel.add(retailersButton, gbc);
        if(userType=="Administrator")
        {
            gbc.gridy++;
            sidePanel.add(employeesButton, gbc);
        }
        gbc.gridy++;
        sidePanel.add(productsButton, gbc);
        gbc.gridy++;
        sidePanel.add(logsButton, gbc);

        // Add an empty component to occupy remaining vertical space
        gbc.gridy++;
        gbc.weighty = 1.0;
        sidePanel.add(Box.createVerticalGlue(), gbc);

        // Create the content panel
        contentPanel = new JPanel(new CardLayout());
        contentPanel.setBackground(Color.WHITE);

        homePanel = new P1_Home();
        retailersPanel = new P2_Retailers();
        employeesPanel = new P5_Employees();
        productsPanel = new P3_Products();
        logsPanel = new P4_Logs();

        contentPanel.add(homePanel, "Home");
        contentPanel.add(retailersPanel, "Retailers");
        if(userType=="Administrator")
            contentPanel.add(employeesPanel, "Employees");
        contentPanel.add(productsPanel, "Products");
        contentPanel.add(logsPanel, "Logs");

        // Add action listeners to the buttons
        homeButton.addActionListener(new PageButtonActionListener("Home"));
        retailersButton.addActionListener(new PageButtonActionListener("Retailers"));
        if(userType=="Administrator")
            employeesButton.addActionListener(new PageButtonActionListener("Employees"));
        productsButton.addActionListener(new PageButtonActionListener("Products"));
        logsButton.addActionListener(new PageButtonActionListener("Logs"));

        // Add the side panel and content panel to the frame
        getContentPane().add(sidePanel, BorderLayout.WEST);
        getContentPane().add(contentPanel, BorderLayout.CENTER);

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private JButton createSidePanelButton(String text) {
        JButton button = new JButton(text);
        button.setPreferredSize(new Dimension(180, 40)); // Set the button size
        button.setFont(new Font("Arial", Font.PLAIN, 16));
        button.setFocusPainted(false); // Remove focus border
        return button;
    }

    private class PageButtonActionListener implements ActionListener {
        private String page;

        public PageButtonActionListener(String page) {
            this.page = page;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            CardLayout cardLayout = (CardLayout) contentPanel.getLayout();
            cardLayout.show(contentPanel, page);

            // Highlight the selected button
            highlightButton((JButton) e.getSource());
        }

        private void highlightButton(JButton selectedButton) {
            // Reset the background color of all buttons
            for (Component component : sidePanel.getComponents()) {
                if (component instanceof JButton) {
                    JButton button = (JButton) component;
                    button.setBackground(UIManager.getColor("Button.background"));
                }
            }

            // Highlight the selected button
            Color highlightColor = new Color(239, 245, 247);;; // RGB values for blue color
            selectedButton.setBackground(highlightColor);
        }
    }
}

