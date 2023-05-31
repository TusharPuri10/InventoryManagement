package UI;
import javax.swing.*;
import java.awt.*;

public class P4_Logs extends JPanel {
    public P4_Logs() {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        JLabel titleLabel = new JLabel("Logs Page");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(titleLabel, BorderLayout.CENTER);
    }
}