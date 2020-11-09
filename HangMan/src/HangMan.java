//import java.util.Scanner;
//
//public class HangMan {
//
//		static boolean runGame = true;
//		static StringBuilder wordToGuess = new StringBuilder("hardware");
//		static StringBuilder hiddenWord = new StringBuilder("########");
//		static StringBuilder previousHiddenWord = new StringBuilder(hiddenWord.length());
//		static char guessedLetter = 0;
//		static int guessCounter = 0;
//		static int currentPlayer = 1;
//		static char player1Guess = 0;
//		static char player2Guess = 0;
//		static int updatedCounter = 0;
//
//	public static void main(String[] args) {
//
//	Scanner kb = new Scanner(System.in);
//		
//		while(runGame) {
//			
//			//caputer current player guess
//			System.out.println("The word to guess is " + hiddenWord.toString());
//			System.out.println("Player" + currentPlayer + " make your guess: ");
//			guessedLetter = kb.next().charAt(0);
//			System.out.println("Your guess was \"" + guessedLetter + "\"");
//			
//			//set previous hidden word before entering loop updating w/guess
//			int previousCounter = updatedCounter;
//			
//			//iterate through hidden word, replace correct guessed chars if there
//			for(int i = 0; i < wordToGuess.length(); i++){
//				if(wordToGuess.charAt(i) == guessedLetter) {
//					hiddenWord.setCharAt(i, guessedLetter);
//					updatedCounter++;
//				}//end if
//			}//end for
//			
//			if(updatedCounter > previousCounter) {
//			System.out.println("You guessed correctly! " + hiddenWord.toString());			
//			}	
//			else{
//				guessCounter++;
//				//hangman switch
//				switch(guessCounter) {
//					
//					case 1:
//						System.out.println(" +--| ");
//						System.out.println(" |  |");
//						System.out.println(" 0  |");
//						System.out.println("    |");
//						System.out.println("    |");
//						System.out.println("    |");
//						System.out.println("____|");
//						break;
//					case 2:
//						System.out.println(" +--| ");
//						System.out.println(" |  |");
//						System.out.println(" 0  |");
//						System.out.println(" |  |");
//						System.out.println("    |");
//						System.out.println("    |");
//						System.out.println("____|");
//						break;
//					case 3:
//						System.out.println("  +--| ");
//						System.out.println("  |  |");
//						System.out.println("  0  |");
//						System.out.println(" \\|  |");
//						System.out.println("     |");
//						System.out.println("     |");
//						System.out.println("_____|");
//						break;
//					case 4:
//						System.out.println("  +--| ");
//						System.out.println("  |  |");
//						System.out.println("  0  |");
//						System.out.println(" \\|/ |");
//						System.out.println("     |");
//						System.out.println("     |");
//						System.out.println("_____|");
//						break;
//					case 5:
//						System.out.println("  +--| ");
//						System.out.println("  |  |");
//						System.out.println("  0  |");
//						System.out.println(" \\|/ |");
//						System.out.println("  |  |");
//						System.out.println("     |");
//						System.out.println("_____|");
//						break;
//					case 6:
//						System.out.println("  +--| ");
//						System.out.println("  |  |");
//						System.out.println("  0  |");
//						System.out.println(" \\|/ |");
//						System.out.println("  |  |");
//						System.out.println(" /   |");
//						System.out.println("_____|");
//						break;
//					case 7:
//						System.out.println("  +--| ");
//						System.out.println("  |  |");
//						System.out.println("  0  |");
//						System.out.println(" \\|/ |");
//						System.out.println("  |  |");
//						System.out.println(" /\\  |");
//						System.out.println("_____|");
//						System.out.println("Hangman, you lose");
//						runGame = false;
//						System.out.println("To play again, enter one, else enter 0");
//						int toContinue = kb.nextInt();
//							if (toContinue == 1) {runGame = true;}
//						break;				
//				}//end switch	
//			}//end else
//			if (currentPlayer == 1) {currentPlayer++;}
//			else {currentPlayer = 1;}
//		}//while
//	}//end main
//}//end class
//
//
//
//
//
//
//
//
//
