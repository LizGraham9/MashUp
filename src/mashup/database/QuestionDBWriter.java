
package mashup.database;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author Elizabeth
 */
public class QuestionDBWriter {

   public void writeToDatabase() {
        try {
            ConnectionManager statementGenerator = new ConnectionManager();
            Connection con = statementGenerator.getCon();
            Statement statement = statementGenerator.getStatement();

            DatabaseMetaData metaData = con.getMetaData();
            ResultSet resultSet = metaData.getTables(null, null, "MASHDATA", null);

            if (!resultSet.next()) { // only writes a new table if it hasn't already been created
                createTable(statement);
                populateTable(statement);
            }

        } catch (FileNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    private void createTable(Statement statement) throws SQLException {
        statement.executeUpdate("CREATE TABLE MashData (id INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),"
                + " prompt VARCHAR(255),"
                + " answer VARCHAR(255))");
    }

    private void populateTable(Statement statement) throws FileNotFoundException, SQLException {
        List<String> dataRows = parseFileIntoRows("src/mashup/mashDataSampleDB.txt");
        String insertStatement = buildInsertStatement(dataRows);
        statement.executeUpdate(insertStatement);
    }

    private List<String> parseFileIntoRows(String filePath) throws FileNotFoundException {
        File file = new File(filePath);
        Scanner reader = new Scanner(file);
        List<String> dataRows = new ArrayList<>();
        while (reader.hasNext()) {
            dataRows.add(reader.nextLine());
        }
        reader.close();
        return dataRows;
    }

    private String buildInsertStatement(List<String> dataRows) {
        StringBuilder sb = new StringBuilder();
        sb.append("INSERT INTO MashData (prompt, answer) VALUES ");

        for (String dataRow : dataRows) {
            String[] parsedRow = dataRow.split("\t");
            String prompt = parsedRow[0];
            String answer = parsedRow[1];
            sb.append("('");
            sb.append(prompt);
            sb.append("', '");
            sb.append(answer);
            sb.append("')");

            if (dataRows.indexOf(dataRow) < dataRows.size() - 1) {
                sb.append(" , ");
            }
        }
        return sb.toString();
    }
}
