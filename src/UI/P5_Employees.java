package UI;
import javax.swing.*;
import java.awt.*;

public class P5_Employees extends JPanel {
    public P5_Employees() {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        JLabel titleLabel = new JLabel("Employees Page");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(titleLabel, BorderLayout.CENTER);
    }
}