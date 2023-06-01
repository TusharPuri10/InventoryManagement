package UI;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class P2_Retailers extends JPanel {
    JTable jt;
    public P2_Retailers() {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

//        JLabel titleLabel = new JLabel("Retailers Page");
//        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
//        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
//        add(titleLabel, BorderLayout.CENTER);
        String [] header={"name","age"};
        String [][] data={{"akash","20"},{"pankaj","24"},{"pankaj","24"},{"pankaj","24"},{"pankaj","24"},{"akash","20"},{"akash","20"},{"akash","20"},{"akash","20"},{"akash","20"},{"akash","20"},{"akash","20"},{"akash","20"},{"akash","20"},{"akash","20"},{"akash","20"},{"akash","20"},{"akash","20"},{"akash","20"},{"akash","20"},{"akash","20"},{"akash","20"},{"akash","20"},{"akash","20"},{"akash","20"},{"akash","20"},{"akash","20"},{"akash","20"},{"akash","20"},{"akash","20"},{"akash","20"},{"akash","20"},{"akash","20"},{"akash","20"},{"akash","20"},{"akash","20"},{"akash","20"},{"akash","20"},{"akash","20"},{"akash","20"},{"akash","20"},{"akash","20"},{"akash","20"}};


        DefaultTableModel model = new DefaultTableModel(data,header);

        JTable table = new JTable(model);

        table.setPreferredScrollableViewportSize(new Dimension(450,63));
        table.setFillsViewportHeight(true);

        JScrollPane js=new JScrollPane(table);
        js.setVisible(true);
        add(js);

    }
}