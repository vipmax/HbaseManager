import java.util.HashMap;
import java.util.Map;

/**
 * Created by maksim_petrov on 1/20/15.
 */
public class TableMetadata {
    private String tableName;
    private String ip;
    private Map<String, String> columnTypes;

    public TableMetadata() {
        columnTypes = new HashMap<>();
    }

    @Override
    public String toString() {
        return "Table{" +
                "name='" + tableName + '\'' +
                ", ip='" + ip + '\'' +
                ", columnTypesAndNames =" + columnTypes +
                '}';
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Map<String, String> getColumnTypes() {
        return columnTypes;
    }

    public void setColumnTypes(Map<String, String> columnTypes) {
        this.columnTypes = columnTypes;
    }

    public void addColumn(String name, String type) {
        columnTypes.put(name, type);
    }
}
