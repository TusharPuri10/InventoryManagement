package UI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginPage extends JFrame {
    public String currentUser;
    JLabel label1;
    JList<String> list;
    JButton button1;

    public LoginPage() {
        setTitle("Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(600, 400)); // Increase the window size

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 10, 10, 10);

        label1 = new JLabel("Wholesale Inventory Management System");
        label1.setFont(new Font("Arial", Font.BOLD, 20));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        panel.add(label1, gbc);

        String[] user = {"Administrator", "Employee"};
        list = new JList<>(user);
        list.setFont(new Font("Arial", Font.PLAIN, 16));
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        panel.add(list, gbc);

        button1 = new JButton("Login");
        button1.setPreferredSize(new Dimension(100, 30));
        button1.setFont(new Font("Arial", Font.PLAIN, 14));
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        panel.add(button1, gbc);

        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                String userType = (String) list.getSelectedValue();
                P0_UI_Main mainPage = new P0_UI_Main(userType);
                mainPage.setVisible(true);
                dispose(); // Close the login page
            }
        });

        panel.setBackground(Color.WHITE); // Set panel background color
        getContentPane().setBackground(Color.WHITE); // Set frame background color
        getContentPane().add(panel);
        pack();
        setLocationRelativeTo(null); // Center the frame on the screen
        setVisible(true);
    }

}
