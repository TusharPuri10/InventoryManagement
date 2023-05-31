package UI;
import javax.swing.*;
import java.awt.*;

public class P3_Products extends JPanel {
    public P3_Products() {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        JLabel titleLabel = new JLabel("Products Page");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(titleLabel, BorderLayout.CENTER);
    }
}