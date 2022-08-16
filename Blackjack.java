import java.util.Scanner;
import java.util.ArrayList;
import java.util.Random;

public class Blackjack {
    // Initialize variables
    static String[] rank = {"Ace", "Two", "Three", "Four", "Five", "Six", "Seven",
            "Eight", "Nine", "Ten", "Jack", "Queen", "King"};
    static String[] suit = {"Spades", "Clubs", "Hearts", "Diamonds"};
    static int[] value = {11, 2, 3, 4, 5, 6, 7, 8, 9, 10, 10, 10,10};
    static Scanner scan = new Scanner(System.in);
    ArrayList<String> deck;
    int pTotal, hTotal;

    // constructor
    public Blackjack() {
        hTotal = 0;
        pTotal = 0;
        deck = new ArrayList<>();
    }

    /**
     * Loops through all values of arrays rank and suit to add to ArrayList deck
     * @return deck - mutable ArrayList of 52 cards
     */
    public static ArrayList createDeck() {
        ArrayList<String> deck = new ArrayList<>();
        for (int i = 0; i < rank.length; i++) {
            for (int j = 0; j < suit.length; j++) {
                deck.add(rank[i] + " of " + suit[j]);
            }
        }
        return deck;
    }

    /**
     * Randomly selects an element (card) in ArrayList deck
     * Removes card from deck, so the card cannot be repeated
     * @return card - randomized element in ArrayList deck
     */
    public String getCard() {
        Random rand = new Random();

        int sRandomIndex = rand.nextInt(deck.size());
        String card = deck.get(sRandomIndex);
        deck.remove(sRandomIndex);
        return card;
    }

    /**
     * Initializes player's two cards
     * @return array of pc1, pc2
     */
    public String[] getPCard() {
        String pc1 = getCard();
        String pc2 = getCard();
        return new String[] {pc1, pc2};
    }

    /**
     * Initializes house's two cards
     * @return array of hc1, hc2
     */
    public String[] getHCard() {
        String hc1 = getCard();
        String hc2 = getCard();
        return new String[] {hc1, hc2};
    }

    /**
     * Uses selected index to find numeric value for the card
     * @param card - randomly selected element in ArrayList deck
     * @param total - represents either pTotal or hTotal
     * @return val - integer value for the card
     */
    public int getVal(String card, int total) {
        int val = -1;
        String[] splitCard = card.split(" ");

        for (int i = 0; i < rank.length; i++) {
           if (splitCard[0].equals(rank[i])) {
              val = value[i];
           }
        }

        return val;
    }

    /**
     * Initializes hitCard by calling getCard() and getVal() for the numeric value
     * Updates and prints pTotal
     * @return pTotal - integer that represents the player's total
     */
    public int playerHit() {
        String hitCard = getCard();
        pTotal += getVal(hitCard, pTotal);
        System.out.println("You got a " + hitCard + ". Your total is " + pTotal);
        return pTotal;
    }

    /**
     * Follows Blackjack rules that if the house's total is lower or equal to 16, the house hits
     * Initializes hitCard by calling getCard() and getVal() for the numeric value
     * Updates and prints hTotal
     * @return hTotal - integer that represents the house's total
     */
    public int houseHit() {
        while (hTotal <= 16) {
            String hitCard = getCard();
            hTotal += getVal(hitCard, hTotal);
            System.out.println("The house hit and got a " + hitCard + ". Their total is " + hTotal);
        }
        return hTotal;
    }

    /**
     * Tests conditions to detect a winner by comparing pTotal and hTotal
     * Prints the winner
     */
    public void detectWinner() {
        if (pTotal > 21) {
            System.out.println("You busted. Sorry, you lose.");
        }
        else if (hTotal > 21) {
            System.out.println("The house busted! Congrats, you win.");
        }
        else if (hTotal == 21) {
            System.out.println("The house's total is 21. Sorry, you lose.");
        }
        else if (pTotal == hTotal) {
            System.out.println("It's a tie!");
        }
        else if (pTotal > hTotal) {
            System.out.println("Congrats! You win.");
        }
        else if (pTotal < hTotal) {
            System.out.println("Sorry, you lose.");
        }
    }

    // main method
    public static void main(String[] args) {
        while (true) {
            System.out.print("\n-----Do you want to play Blackjack? (y/n): ");
            char player_choice = scan.next().charAt(0);
            if (player_choice == 'y') {
                // declares object game
                Blackjack game = new Blackjack();
                game.deck = createDeck();

                System.out.print("\nYour cards are: ");
                String[] pCards = game.getPCard();
                System.out.println(pCards[0] + " and " + pCards[1]);
                int pv1 = game.getVal(pCards[0], game.pTotal);
                int pv2 = game.getVal(pCards[1], game.pTotal);
                game.pTotal =+ pv1 + pv2;
                System.out.println("Your total is " + game.pTotal);

                // checks immediately if pTotal equals 21, so the player and house cannot hit/stand
                if (game.pTotal == 21) {
                    System.out.println("Congrats! You win the game.");
                    break;
                }

                System.out.print("The house's 1st card is: ");
                String[] hCards = game.getHCard();
                System.out.print(hCards[0]);
                int hv1 = game.getVal(hCards[0], game.hTotal);
                int hv2 = game.getVal(hCards[1], game.hTotal);
                game.hTotal =+ hv1 + hv2;
                System.out.println(" [which is worth " + hv1 + "]");

                // starts the hit/stand process
                while (game.pTotal < 21) {
                    System.out.print("\nHit or stand? (h/s): ");
                    player_choice = scan.next().charAt(0);
                    if (player_choice == 'h') {
                        game.playerHit();
                    }
                    else if (player_choice == 's') {
                        System.out.println("The house's 2nd card is " + hCards[1] + ". Their total is " + game.hTotal);
                        game.houseHit();
                        break;
                    }
                }
                game.detectWinner();
            }
            else {
                System.out.println("Thank you for playing.");
                System.exit(0);
            }
        }
    }
}