package UI;
import javax.swing.*;
import java.awt.*;

public class P2_Retailers extends JPanel {
    public P2_Retailers() {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        JLabel titleLabel = new JLabel("Retailers Page");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(titleLabel, BorderLayout.CENTER);
    }
}