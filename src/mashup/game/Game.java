
package mashup.game;

import mashup.database.QuestionDBWriter;
import mashup.database.QuestionDBLoader;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.Queue;
import java.util.Scanner;

/**
 *
 * @author Elizabeth
 */
public class Game {

    private final Queue<Question> questionQueue = buildQuestionQueue();
    private final Scanner reader = new Scanner(System.in);
    private final InputScrubber scrubber = new InputScrubber();
    private final EditDistanceCalculator distanceCalculator = new EditDistanceCalculator();
    private final UserInterface userInterface = new UserInterface();

//    Make unit tests at some point!
//    consider implementing point system
//    consider tracking time taken to answer and awarding points based on speed(also customizing win message if particularly fast)
//    implement hints, SFW filter, ability for user to write contribute own riddles
    
    private Queue<Question> buildQuestionQueue() {
        new QuestionDBWriter().writeToDatabase();
        List<Question> questions = new QuestionDBLoader().getData();
        Collections.shuffle(questions);
        return new LinkedList<>(questions);
    }

    public void start() {
        userInterface.printIntro();
        runGame();
        userInterface.printGameOverMessage();
    }

    private void runGame() {
        while (!questionQueue.isEmpty()) {
            Question question = questionQueue.element();
            question.printPrompt();
            String cleanInput = scrubber.scrubText(reader.nextLine());
            handleInput(question, cleanInput);
            userInterface.printCorrectAnswer(question);
        }
    }

    private void handleInput(Question question, String cleanInput) {
        Optional<Command> optCommand = Command.getCommand(cleanInput);
        if (optCommand.isPresent()) {
            handleCommand(optCommand.get());
        } else {
            handleGuess(question, cleanInput);
        }
    }

    private void handleCommand(Command command) {
        if (command == Command.QUIT) {
            endGame();
        } else if (command == Command.SKIP) {
            questionQueue.remove();
        } else if (command == Command.MENU) {
            userInterface.printMenu();
            runGame();
        } else if (command == Command.HINT) {
            System.out.println("Not yet supported. Sorry :(");
            runGame();
        }
    }

    private void endGame() {
        userInterface.printQuitMessage();
        System.exit(0);
    }

    private void handleGuess(Question question, String cleanGuess) {
        if (isCorrect(question, cleanGuess)) {
            userInterface.printWinMessage();
        } else {
            userInterface.printLoseMessage();
        }
        questionQueue.remove();
    }

    private boolean isCorrect(Question question, String cleanGuess) {
        String cleanAnswer = scrubber.scrubText(question.getAnswer());
        int editDistance = distanceCalculator.findDistance(cleanGuess, cleanAnswer);
        double percentError = (double) editDistance / cleanAnswer.length();
        return percentError <= .15;
    }
}
