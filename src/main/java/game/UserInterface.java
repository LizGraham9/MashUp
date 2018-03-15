
package game;

/**
 *
 * @author Elizabeth
 */
class UserInterface {

    void printIntro() {
        System.out.println("Welcome to MashUp!");
//        add full explanation of how the puns work & example at some point
        System.out.println("Type any of the following commands at any point in the game:");
        printMenu();
        System.out.println("Here we go:");
        System.out.println();
    }

    void printMenu() {
        System.out.println();
        System.out.println("    ***********Menu***********");
        System.out.println("    \"Quit\" - Exits game");
        System.out.println("    \"Skip\" - Skips to next riddle");
        System.out.println("    \"Menu\" - Displays command options");
//        System.out.println("    \"Hint\" - Gives a clue (support this at some point)");
        System.out.println("    **************************");
        System.out.println();
    }

    void printWinMessage() {
        System.out.println("You're Grrreeeaaat!");
//        to implement: pull randomly from list of possible win messages
    }

    void printLoseMessage() {
        System.out.println("Wrong!");
//        to implement: pull randomly from list of possible lose messages
    }

    void printCorrectAnswer(Question question) {
//        to consider: should answer still display if player guesses correctly?
        System.out.println();
        System.out.println("The answer was: \"" + question.getAnswer() + "\"");
        System.out.println();
    }

    void printQuitMessage() {
        System.out.println();
        System.out.println("Goodbye! Thanks for playing!");
    }

    void printGameOverMessage() {
        System.out.println();
        System.out.println("NO MORE QUESTIONS!");
    }
}
