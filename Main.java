import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {
    static String[] wordList = new String[1000];
    static int wordsCount = 0;
    static String word;
    static String hiddenWord;
    static String usedLetters;
    static int incorrectGuesses;
    static boolean gameWon;

    public static void main(String[] args) {
        // reading words from file
        try {
            File file = new File("wordlist.txt");
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                wordList[wordsCount++] = scanner.nextLine();
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        
        // select random word
        word = wordList[(int) (Math.random() * wordsCount)];
        hiddenWord = new String(new char[word.length()]).replace("\0", "_");
        usedLetters = "";
        incorrectGuesses = 0;
        gameWon = false;

        while (!gameWon && incorrectGuesses < 6) {
            System.out.println("Word: " + hiddenWord);
            System.out.println("Used letters: " + usedLetters);
            System.out.println("Incorrect guesses: " + incorrectGuesses);

            char letter = getLetter();
            if (usedLetters.indexOf(letter) != -1) {
                System.out.println("You already used this letter. Try again.");
                continue;
            }
            usedLetters += letter;

            if (word.indexOf(letter) == -1) {
                incorrectGuesses++;
            } else {
                for (int i = 0; i < word.length(); i++) {
                    if (word.charAt(i) == letter) {
                        hiddenWord = hiddenWord.substring(0, i) + letter + hiddenWord.substring(i + 1);
                    }
                }
            }
            gameWon = !hiddenWord.contains("_");
        }

        if (gameWon) {
            System.out.println("Congratulations! You won!");
        } else {
            System.out.println("Sorry, you lost. The word was " + word + ".");
        }
    }

    public static char getLetter() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Guess a letter: ");
        String input = scanner.nextLine();
        return input.charAt(0);
    }
}
