import HbaseViewer.HbaseViewer;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.List;

/**
 * Created by maksim_petrov on 1/20/15.
 */
public class MainForm extends JFrame {
    private JPanel rootPanel;
    private JTextArea tableTextArea;
    private JButton showButton;
    private JTextArea typesArea;
    private HbaseViewer viewer;

    public MainForm() {
        setContentPane(rootPanel);
        setSize(600, 300);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        showButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tableTextArea.setText("Wait");


                TableMetadata tableMetadata = getColumnInfo();
                System.out.println("tableMetadata = " + tableMetadata);

                try {
                    List<List> rows = viewer.getData(tableMetadata.getTableName(),tableMetadata.getColumnTypes());
                    show(rows);
                } catch (IOException e1) {
                    e1.printStackTrace();
                } catch (InvocationTargetException e1) {
                    e1.printStackTrace();
                } catch (IllegalAccessException e1) {
                    e1.printStackTrace();
                }

            }
        });
    }

    public void setViewer(HbaseViewer viewer) {
        this.viewer = viewer;
    }

    private TableMetadata getColumnInfo() {
        String text = typesArea.getText();
        String[] rows = text.split("\n");
        TableMetadata tableMetadata = new TableMetadata();
        for (String row : rows) {
            System.out.println("row = " + row);
            row = row.trim();
            if (tableMetadata.getTableName()== null && row.startsWith("table:"))  tableMetadata.setTableName(row.split("table:")[1].trim());

            if (row.startsWith("column:")) {
                String colType = row.split("column:")[1].trim();
                System.out.println("colType = " + colType);
                String[] colNameAndType = colType.split(" ");
                System.out.println("colNameAndType = " + Arrays.toString(colNameAndType));
                tableMetadata.addColumn(colNameAndType[0],colNameAndType[1]);
            }

        }

        return tableMetadata;
    }

    public void show(List<List> rows) {
        tableTextArea.setText("");
        for (List row : rows) tableTextArea.append(row + " \n");
    }
}
