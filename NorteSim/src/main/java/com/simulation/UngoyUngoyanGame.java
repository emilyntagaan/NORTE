package com.simulation;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;

import org.jline.reader.LineReader;
import org.jline.reader.LineReaderBuilder;
import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;

public class UngoyUngoyanGame {
    private static final String[] CARDS = {
            "A♠", "A♦", "2♠", "2♦", "3♠", "3♦", "4♠", "4♦",
            "5♠", "5♦", "6♠", "6♦", "7♠", "7♦", "8♠", "8♦",
            "9♠", "9♦", "JOKER"
    };

    private static String[] deck = new String[CARDS.length];
    private static String[] playerHand;
    private static String[][] computerHands;
    private static String playerName;
    private static String[] computerNames;

    public static void main(String[] args) throws IOException {
        Terminal terminal = TerminalBuilder.builder().system(true).build();
        LineReader reader = LineReaderBuilder.builder().terminal(terminal).build();

        System.out.println("=== Welcome to Ungoy-Ungoyan! ===");

        playerName = reader.readLine("Enter your player name: ");
        int numComputers = Integer.parseInt(reader.readLine("How many computer players do you want to play against? (1-3): "));
        int totalPlayers = numComputers + 1;

        setupGame(totalPlayers);
        dealCards(totalPlayers);

        System.out.println("\n🎉 Game starts! Let’s see who gets the 'Ungoy'! 🎉");
        playGame(reader, totalPlayers);
    }

    private static void setupGame(int totalPlayers) {
        System.arraycopy(CARDS, 0, deck, 0, CARDS.length);
        Collections.shuffle(Arrays.asList(deck));

        int handSize = deck.length / totalPlayers;
        playerHand = new String[handSize];
        computerHands = new String[totalPlayers - 1][handSize];

        // Simple computer names like "Computer 1", "Computer 2"
        computerNames = new String[totalPlayers - 1];
        for (int i = 0; i < computerNames.length; i++) {
            computerNames[i] = "Computer " + (i + 1);
        }
    }

    private static void dealCards(int totalPlayers) {
        int cardIndex = 0;

        for (int i = 0; i < playerHand.length; i++) {
            playerHand[i] = deck[cardIndex++];
        }

        for (int i = 0; i < computerHands.length; i++) {
            for (int j = 0; j < computerHands[i].length; j++) {
                computerHands[i][j] = deck[cardIndex++];
            }
        }

        playerHand = removePairs(playerHand);
        for (int i = 0; i < computerHands.length; i++) {
            computerHands[i] = removePairs(computerHands[i]);
        }
    }

    private static void playGame(LineReader reader, int totalPlayers) {
        boolean gameOn = true;
        int currentPlayer = 0;

        while (gameOn) {
            clearScreen();
            String currentPlayerName = (currentPlayer == 0) ? playerName : computerNames[currentPlayer - 1];

            System.out.println("\n🎉 " + currentPlayerName + "'s turn! 🎉");

            if (currentPlayer == 0) {
                showHand(playerHand);
                int target = Integer.parseInt(reader.readLine("Pick a player to draw from (1-" + (totalPlayers - 1) + "): "));
                String targetName = computerNames[target - 1];

                int cardIndex = (int) (Math.random() * computerHands[target - 1].length);
                String drawnCard = computerHands[target - 1][cardIndex];

                System.out.println("🃏 You drew: " + drawnCard + " from " + targetName);
                playerHand = addCard(playerHand, drawnCard);
                computerHands[target - 1] = removeCard(computerHands[target - 1], cardIndex);

                playerHand = removePairs(playerHand);
            } else {
                int target = (currentPlayer == 1) ? 0 : (int) (Math.random() * (totalPlayers - 1));
                if (target == currentPlayer - 1) target = 0;

                String targetName = (target == 0) ? playerName : computerNames[target - 1];
                int cardIndex = (int) (Math.random() * playerHand.length);
                String drawnCard = playerHand[cardIndex];

                System.out.println(computerNames[currentPlayer - 1] + " drew a card from " + targetName);
                computerHands[currentPlayer - 1] = addCard(computerHands[currentPlayer - 1], drawnCard);
                playerHand = removeCard(playerHand, cardIndex);

                computerHands[currentPlayer - 1] = removePairs(computerHands[currentPlayer - 1]);
            }

            if (playerHand.length == 1) {
                System.out.println("\n😱 " + playerName + " is the 'Ungoy'! Better luck next time!");
                gameOn = false;
            } else {
                for (int i = 0; i < computerHands.length; i++) {
                    if (computerHands[i].length == 1) {
                        System.out.println("\n🎉 " + computerNames[i] + " is the 'Ungoy'! You win!");
                        gameOn = false;
                        break;
                    }
                }
            }

            currentPlayer = (currentPlayer + 1) % totalPlayers;
        }
    }

    private static void showHand(String[] hand) {
        System.out.println("🖐️ Your hand: " + Arrays.toString(hand));
    }

    private static String[] addCard(String[] hand, String card) {
        String[] newHand = Arrays.copyOf(hand, hand.length + 1);
        newHand[hand.length] = card;
        return newHand;
    }

    private static String[] removeCard(String[] hand, int index) {
        String[] newHand = new String[hand.length - 1];
        int j = 0;
        for (int i = 0; i < hand.length; i++) {
            if (i != index) newHand[j++] = hand[i];
        }
        return newHand;
    }

    private static String[] removePairs(String[] hand) {
        Arrays.sort(hand);
        String[] newHand = new String[hand.length];
        int index = 0;

        for (int i = 0; i < hand.length; i++) {
            if (i < hand.length - 1 && hand[i].substring(0, hand[i].length() - 1).equals(hand[i + 1].substring(0, hand[i + 1].length() - 1))) {
                System.out.println("🧹 Removed pair: " + hand[i] + " and " + hand[i + 1]);
                i++;
            } else {
                newHand[index++] = hand[i];
            }
        }

        return Arrays.copyOf(newHand, index);
    }

    private static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
}