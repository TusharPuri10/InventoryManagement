package UI;
import javax.swing.*;
import java.awt.*;

public class P4_Logs extends JPanel {
    int userid;
    String username;
    String usertype;
    public P4_Logs(int userid, String username, String usertype) {
        this.userid = userid;
        this.username = username;
        this.usertype = usertype;
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        JLabel titleLabel = new JLabel("Logs Page (Feature will be released in version 2)");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(titleLabel, BorderLayout.CENTER);
    }
}