package HbaseViewer;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.KeyValue;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.filter.*;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.log4j.Logger;


import static org.apache.hadoop.hbase.filter.CompareFilter.CompareOp.*;

/**
 * Created by maksim_petrov on 1/20/15.
 */
public class HbaseViewer {
    private static final Logger LOGGER = Logger.getLogger("HBaseViewerLogger");
    private final static String HOST_NAME_COLUMN = "HostName";
    private final static String ITEM_ID_COLUMN = "ItemID";
    private final static String PROJECT_COLUMN = "Project";
    private final static String REGION_COLUMN = "Region";
    private final static String MAX_VALUE_COLUMN = "MaxValue";
    private final static String MIN_VALUE_COLUMN = "MinValue";
    private final static String SUM_VALUE_COLUMN = "SumValue";
    private final String HOUR_OF_DAY_COLUMN = "HourOfDay";
    private final String DAY_OF_WEEK_COLUMN = "DayOfWeek";
    private final String WEEK_OF_YEAR_COLUMN = "WeekOfYear";
    private final String MONTH_OF_YEAR_COLUMN = "MonthOfYear";
    private final String YEAR_COLUMN = "Year";
    private final String NUMBER_COLUMN = "Number";

    private Configuration configuration = new Configuration();
    private static final String IP = "127.0.0.1";           //localhost
    private Map<String, String> metricNames;
    private Map<String, String> metricUnits;

    public HbaseViewer() {

    }


    private void simpleOut(String tableName) {

        try {
            HTable table = new HTable(configuration, tableName);
            Scan s = new Scan();
            ResultScanner ss = table.getScanner(s);
            System.out.println(tableName);
            for (Result r : ss) {
                System.out.println();
                for (KeyValue kv : r.raw()) {

                    String family = Bytes.toString(kv.getFamily());
                    if (family.equals("HostID") || family.equals("WorkStationID") || family.equals("ZabbixServerID") || family.equals("Parameter")| family.equals("ZabbixServerParameter")) {

                        Long value = Bytes.toLong(kv.getValue());
                        System.out.print(family);
                        System.out.println(" value = " + value);
                        continue;
                    }
                    String value = Bytes.toString(kv.getValue());
                    System.out.print(family);
                    System.out.println(" value = " + value);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    private void getrows(String tableName) {

        try {
            HTable table = new HTable(configuration, tableName);
            Scan s = new Scan();
            ResultScanner ss = table.getScanner(s);
            System.out.println(tableName);
            List<Object> resultRows = new LinkedList<>();
            for (Result r : ss) {
                System.out.println();
                StringBuilder row = new StringBuilder();
                for (KeyValue kv : r.raw()) {

                    String family = Bytes.toString(kv.getFamily());
                    String value = Bytes.toString(kv.getValue());
                    System.out.print(family);
                    System.out.println(" value = " + value);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    private void showLine(String leftAlignFormat, List valueList) {
//        LOGGER.debug(valueList.toString());
        for (int i = 0; i < valueList.size(); i++) {

            System.out.format(leftAlignFormat, valueList.get(i).toString());
        }
    }

    public List<List> showTable(String tableName, Map<String, String> columnTypes) throws IOException, InvocationTargetException, IllegalAccessException {

        HTable table = new HTable(configuration, tableName);
        Scan s = new Scan();
        ResultScanner rows = table.getScanner(s);
        System.out.println(tableName);

        List titleList = new LinkedList<>();

        List<List> resultRows = new LinkedList<>();
        for (Result row : rows) {
            System.out.println();
            List valueList = new LinkedList<>();

            for (KeyValue col : row.raw()) {

                String column = Bytes.toString(col.getFamily());

                if (columnTypes.containsKey(column)) {

                    String columnClass = columnTypes.get(column);
                    try {
                        Method method = Bytes.class.getMethod("to" + columnClass, byte[].class);
                        Object value = method.invoke(Bytes.class, col.getValue());
                        valueList.add(value);
                    } catch (NoSuchMethodException e) {
                        System.out.println("Exception! columnClass = " + columnClass);
                    }

                }
            }
            resultRows.add(valueList);



        }

        return resultRows;
    }

//    private void showTables() throws InvocationTargetException, NoSuchMethodException, IllegalAccessException, IOException {
//
//        Map<String, String> stringClassMap = new HashMap<>();
//        stringClassMap.put("ID", "Long");
//        stringClassMap.put("Project", "String");
//        showTable("DimProject", stringClassMap, 16);
//
//        stringClassMap = new HashMap<>();
//        stringClassMap.put("ID", "Long");
//        stringClassMap.put("Region", "String");
//        showTable("DimRegion", stringClassMap, 16);
//
//        stringClassMap = new HashMap<>();
//        stringClassMap.put("Description", "String");
//        stringClassMap.put("Parameter", "Long");
//        stringClassMap.put("ShortName", "String");
//        stringClassMap.put("Unit", "String");
//        showTable("D_ParameterDescriptions", stringClassMap, 16);
//
//        stringClassMap = new HashMap<>();
//        stringClassMap.put("Connection", "String");
//        stringClassMap.put("DataSourceType", "String");
//        stringClassMap.put("ID", "Long");
//        stringClassMap.put("LastTimeCall", "Long");
//        stringClassMap.put("Password", "String");
//        stringClassMap.put("Project", "Long");
//        stringClassMap.put("Region", "Long");
//        stringClassMap.put("User", "String");
//        showTable("S_ZabbixServers", stringClassMap, 40);
//
//        stringClassMap = new HashMap<>();
//        stringClassMap.put("ID", "Long");
//        stringClassMap.put("HostID", "Long");
//        stringClassMap.put("WorkStationName", "String");
//        stringClassMap.put("ZabbixServerID", "Long");
//        stringClassMap.put("WorkStationStartDate", "Long");
//        showTable("S_WorkStations", stringClassMap, 40);
//
//    }

    public void getDataIterator() {

    }
}
