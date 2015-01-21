import HbaseViewer.HbaseViewer;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
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
    private JButton showButton;
    private JTextArea typesArea;
    private JTable table1;
    private JButton saveButton;
    private JButton loadButton;
    private HbaseViewer viewer;

    public MainForm() {
        setContentPane(rootPanel);
        setSize(600, 300);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("HBase manager");

        showButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {


                TableMetadata tableMetadata = getColumnInfo();
                System.out.println("tableMetadata = " + tableMetadata);

                try {
                    List<List> rows = viewer.getData(tableMetadata.getTableName(),tableMetadata.getColumnTypes());
                    show(tableMetadata, rows);
                } catch (IOException e1) {
                    e1.printStackTrace();
                } catch (InvocationTargetException e1) {
                    e1.printStackTrace();
                } catch (IllegalAccessException e1) {
                    e1.printStackTrace();
                }

            }
        });
        loadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                List<String> rows = FileService.openFileWithFileChooser();
                if (rows != null) {
                    typesArea.setText("");
                    for (String row : rows) typesArea.append(row + "\n");
                }
            }
        });

        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String text = typesArea.getText();
                FileService.saveDataWithFileChooser(text);
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


    public void show(TableMetadata tableMetadata, List<List> rows) {

        DefaultTableModel dataModel = new DefaultTableModel(rows.size(), tableMetadata.getColumnTypes().keySet().size());
        dataModel.setColumnIdentifiers(tableMetadata.getColumnTypes().keySet().toArray());
        for (int i = 0; i < rows.size(); i++) {
            List row = rows.get(i);
            for (int i1 = 0; i1 < row.size(); i1++) dataModel.setValueAt(row.get(i1), i, i1);
        }

        table1.setModel(dataModel);
    }
}
