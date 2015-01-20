import java.util.HashMap;
import java.util.Map;

/**
 * Created by maksim_petrov on 1/20/15.
 */
public class TableMetadata {
    String tableName;
    Map<String, String> columnTypes;

    public TableMetadata() {
        columnTypes = new HashMap<>();
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public Map<String, String> getColumnTypes() {
        return columnTypes;
    }

    public void setColumnTypes(Map<String, String> columnTypes) {
        this.columnTypes = columnTypes;
    }

    @Override
    public String toString() {
        return "TableMetadata{" +
                "tableName='" + tableName + '\'' +
                ", columnTypes=" + columnTypes +
                '}';
    }

    public void addColumn(String name, String type) {
        columnTypes.put(name, type);
    }
}
