
package mashup.game;

/**
 *
 * @author Elizabeth
 */
public class Question {

    private final int dbID;
    private final String prompt;
    private final String answer;

    public Question(int dbID, String prompt, String answer) {
        this.dbID = dbID;
        this.prompt = prompt;
        this.answer = answer;
    }

    public int getID() {
        return dbID;
    }

    public void printPrompt() {
        System.out.println(prompt);
    }

    public String getAnswer() {
        return answer;
    }
}
