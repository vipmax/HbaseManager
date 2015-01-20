import HbaseViewer.HbaseViewer;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by maksim_petrov on 1/20/15.
 */
public class MainForm extends JFrame {
    private JPanel rootPanel;
    private JTextArea textArea;
    private JButton showButton;
    private JTextArea typesArea;
    private JTextField tableNameTextField;
    private HbaseViewer viewer;

    public MainForm() {
        setContentPane(rootPanel);
        setSize(600, 300);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        showButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String tableName = tableNameTextField.getText();
                Map<String, String> columnTypes = getColumnTypes();

                try {
                    List<List> lists = viewer.showTable(tableName, columnTypes);
                    showList(lists);
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

    private Map<String, String> getColumnTypes() {
        Map<String, String> stringClassMap = new HashMap<>();
        
        stringClassMap.put("ID", "Long");
        stringClassMap.put("Project", "String");
        return stringClassMap;
    }

    public void showList(List<List> lists) {
        for (List row : lists) textArea.append(row.toString() + " \n");
    }
}
