
package database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;
import game.Question;

/**
 *
 * @author Elizabeth
 */
public class QuestionDBLoader {

    public List<Question> getData() {
        try {
            return resultsToList();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; // ask thomas about this
    }

    private List<Question> resultsToList() throws SQLException {
        List<Question> resultsList = new LinkedList<>();
        ResultSet results = getResultSet();

        while (results.next()) {
            int dbID = results.getInt("ID");
            String prompt = results.getString("PROMPT");
            String answer = results.getString("ANSWER");
            Question entry = new Question(dbID, prompt, answer);
            resultsList.add(entry);
        }

        return resultsList;
    }

    private ResultSet getResultSet() throws SQLException {
        ConnectionManager statementGenerator = new ConnectionManager();
        Statement statement = statementGenerator.getStatement();

        String query = "SELECT * FROM MASHDATA";
//            look into selecting specific columns
        ResultSet results = statement.executeQuery(query);
//           
        return results;
    }

}
