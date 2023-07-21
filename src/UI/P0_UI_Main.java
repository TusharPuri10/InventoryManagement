package UI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;

public class P0_UI_Main extends JFrame {
    private static JPanel sidePanel;
    private static JPanel contentPanel;

    private JPanel homePanel;
    private JPanel employeesPanel;
    private JPanel retailersPanel;
    private JPanel productsPanel;
    private JPanel logsPanel;
    public static JButton selectButton;

    public P0_UI_Main(String userType,String username, int userid, Connection connection) {
        setTitle(userType + " Page");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(800, 500));

        // Create the side panel
        sidePanel = new JPanel(new GridBagLayout());
        sidePanel.setPreferredSize(new Dimension(200, getHeight()));
        sidePanel.setBackground(Color.LIGHT_GRAY);

        JButton homeButton = createSidePanelButton("Home");
        JButton retailersButton = createSidePanelButton("Retailers");
        JButton employeesButton = createSidePanelButton("Employees");
        JButton productsButton = createSidePanelButton("Products");
        JButton logsButton = createSidePanelButton("Logs");
        selectButton = new JButton("Select");
        selectButton.setVisible(false); // Hide the button initially

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
        gbc.gridy++;
        sidePanel.add(selectButton, gbc);

        // Add an empty component to occupy remaining vertical space
        gbc.gridy++;
        gbc.weighty = 1.0;
        sidePanel.add(Box.createVerticalGlue(), gbc);

        // Create the content panel
        contentPanel = new JPanel(new CardLayout());
        contentPanel.setBackground(Color.WHITE);

        homePanel = new P1_Home(username);
        retailersPanel = new P2_Retailers(connection);
        employeesPanel = new P5_Employees(connection,username,userid);
        productsPanel = new P3_Products(userType,connection,username,userid);
        logsPanel = new P4_Logs(userid,username,userType);

        contentPanel.add(homePanel, "Home");
        contentPanel.add(retailersPanel, "Retailers");
        if(userType=="Administrator")
            contentPanel.add(employeesPanel, "Employees");
        contentPanel.add(productsPanel, "Products");
        contentPanel.add(logsPanel, "Logs");

        // Add action listeners to the buttons
        homeButton.addActionListener(new PageButtonActionListener("Home","",0,-1));
        retailersButton.addActionListener(new PageButtonActionListener("Retailers","",0,-1));
        if(userType=="Administrator")
            employeesButton.addActionListener(new PageButtonActionListener("Employees","",0,-1));
        productsButton.addActionListener(new PageButtonActionListener("Products","",0,-1));
        logsButton.addActionListener(new PageButtonActionListener("Logs","",0,-1));

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

    static class PageButtonActionListener implements ActionListener {
        private  int selectedRow;
        private String page;
        private String previousPage;
        private int selectionMode;
        public PageButtonActionListener(String page, String previousPage, int selectionMode, int selectedRow) {
            this.page = page;
            this.previousPage = previousPage;
            this.selectionMode = selectionMode;
            this.selectedRow = selectedRow;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            CardLayout cardLayout = (CardLayout) contentPanel.getLayout();
            cardLayout.show(contentPanel, page);

            // Highlight the selected button
            if(selectionMode==1)
            {
                selectButton.setVisible(true);
                selectButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        // Handle the "Select" button action here
                        // You can trigger the necessary logic or method calls
                        int selectedRow2=P2_Retailers.selectRetailer();
                        if (selectedRow2 == -1) {
                            JOptionPane.showMessageDialog(null, "Please select a retailer from the table.", "Choose Retailer", JOptionPane.INFORMATION_MESSAGE);
                        }else{
                            Thread t1 = new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    P3_Products.sellDialog(selectedRow,selectedRow2);
                                }
                            });
                            Thread t2 = new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    // Switch back to the previous page after selecting the retailer
                                    cardLayout.show(contentPanel, previousPage);
                                    selectButton.setVisible(false);//hide it again
                                }
                            });

                            t1.start();
                            t2.start();
                        }
                    }
                });
            }
            else {
                selectButton.setVisible(false);
                highlightButton((JButton) e.getSource());
            }
                // make a select button here or make a select button and make it visible
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

