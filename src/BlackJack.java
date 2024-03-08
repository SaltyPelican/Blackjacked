import java.util.ArrayList;
import java.util.Scanner;

public class BlackJack {

    private Deck deck;
    private ArrayList<Card> player;
    private ArrayList<Card> dealer;
    private static int bankroll = 1000; // amount of money player has
    private static int bet; // amount of money player bets
    private static boolean firstBet; // whether the player has bet before to decide how to introduce bet method

    // need to do something with printing current bet
    //
    Scanner kb;

    public BlackJack() {
        deck = new Deck();
        player = new ArrayList<>();
        dealer = new ArrayList<>();
        kb = new Scanner(System.in);
    }

    public static void main(String[] args) {
        BlackJack game = new BlackJack();
        game.run();
    }

    private void run() {
        // dealCards()
        deck.shuffle();

        printBank();
        bet();

        player.add(deck.getCard());
        dealer.add(deck.getCard());
        player.add(deck.getCard());
        dealer.add(deck.getCard());

        System.out.println("Dealer hand: " + dealer.get(0) + " [?]");
        System.out.println("Player hand: " + player);

        playerTurn();
        dealerTurn();
        determineWinner();

        resetGame();
    }

    private void playRound() {
        // dealCards()
        deck.shuffle();

        printBank();
        bet();

        player.add(deck.getCard());
        dealer.add(deck.getCard());
        player.add(deck.getCard());
        dealer.add(deck.getCard());

        System.out.println("Dealer hand: " + dealer.get(0) + " [?]");
        System.out.println("Player hand: " + player);


        playerTurn();
        dealerTurn();
        determineWinner();
    }

    public void determineWinner() {
        if (calcHandValue(player) <= 21) {
            if (calcHandValue(dealer) > 21 || calcHandValue(player) > calcHandValue(dealer)) {
                System.out.println("Player wins!");
                bankroll = bankroll + (2 * bet);
                printBank();
            }
            else if (calcHandValue(player) < calcHandValue(dealer)) {
                System.out.println("Dealer wins!");
                printBank();
            }
            else {
                System.out.println("It's a tie!");
                bankroll = bankroll + bet;
                printBank();
            }

        }
    }

    private void resetGame() {
        System.out.println("Do you want to play again? (y/n)");
        String response = kb.nextLine();
        if (response.equals("y")){
            BlackJack game = new BlackJack();
            game.run();
        }
        // remakes a game object and runs it
        else if (response.equals("n")){
            System.out.println("Thank you for playing.");
            printBank();
            return;
        }
        // just continues on with the code ending
        else {
            System.out.println("Please choose a valid input.");
            resetGame();
        }
        // redoes the method if a different response was given than y or n
    }

    private void printBank() {
        String returnString = "Your money: " + bankroll;
        System.out.println(returnString);
    }

    private void bet() {
        if (!firstBet) {
            System.out.println("How much would you like to bet? (2-500)");
            while (!kb.hasNextInt()) {
                System.out.println("Please enter a valid integer.");
                kb.next(); // consume the invalid input
            }
            bet = kb.nextInt();
            kb.nextLine();
            if (bet < 2 || bet > 500) {
                System.out.println("Please choose a bet within the range.");
                bet(); // recalls method if player chooses amount outside of range of money they can bet
                return;
            }
            else if(bet > bankroll) {
                System.out.println("Please choose a bet within the amount of money you have.");
                bet();
                return;
            }
            bankroll -= bet;
        }
        else {
            System.out.println("Current bet: " + bet);
            System.out.println("How much would you like to bet? (2-500)");
            while (!kb.hasNextInt()) {
                System.out.println("Please enter a valid integer.");
                kb.next(); // consume the invalid input
            }
            bet = kb.nextInt();
            kb.nextLine();
            if (bet < 2 || bet > 500) {
                System.out.println("Please choose a bet within the range.");
                bet(); // recalls method if player chooses amount outside of range of money they can bet
                return;
            }
            else if(bet > bankroll) {
                System.out.println("Please choose a bet within the amount of money you have.");
                bet();
                return;
            }
            bankroll -= bet;
        }
    }

    private void playerTurn() {
        String response;
        do {
            System.out.println("Hit or stay?");
            response = kb.nextLine();
            if (response.equals("hit")) {
                player.add(deck.getCard());
                System.out.println("Player hand: " + player);
                if (calcHandValue(player) > 21) {
                    System.out.println("Bust! Player loses.");
                    return;
                } // If they are over 21 (accounting for aces), then they will bust
            }
        } while (response.equals("hit"));
    }

    private void dealerTurn() {
        while (calcHandValue(dealer) < 17) {
            dealer.add(deck.getCard());
        }
        System.out.println("Dealer hand: " + dealer);
    }
    // Adds another card to dealer's hand if they are under 17

    private int calcHandValue(ArrayList<Card> hand) {
        int totalValue = 0;
        int numAces = 0;

        for (Card card : hand) {
            int value = card.getValue();
            if (value == 14) { // Ace
                numAces++;
                totalValue += 11; // Ace initially counts as 11
            } else if (value > 10) { // Face cards
                totalValue += 10;
            } else {
                totalValue += value;
            }
        }

        // Adjust the value of Aces if necessary
        while (totalValue > 21 && numAces > 0) {
            totalValue -= 10; // Change Ace value from 11 to 1
            numAces--;
        }

        return totalValue;
    }


}
