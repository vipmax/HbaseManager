import HbaseViewer.HbaseViewer;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by maksim_petrov on 1/20/15.
 */
public class Runner {
    public static void main(String[] args) throws IllegalAccessException, IOException, InvocationTargetException {
        MainForm mainForm = new MainForm();
        mainForm.setVisible(true);
        mainForm.setViewer(new HbaseViewer());




    }
}
