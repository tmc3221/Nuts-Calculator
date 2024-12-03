package com.example.poker_api;

import com.example.poker_api.dsa.list.List;

/**
 * Class to represent a Poker Hand
 * Contains a list of cards in the hand
 * 
 * @author Tristan Curtis (tmc3221)
 */
public class PokerHand {

    /** The list of cards in the poker hand */
    private List<String> cards;

    /**
     * Constructs a poker hand with a set of cards
     * @param cards the cards we are using to create the poker hand
     */
    public PokerHand(List<String> cards) {
        setCards(cards);
    }

    /**
     * Method to get the list of cards in the poker hand
     * @return the list of cards in the poker hand
     */
    public List<String> getCards() {
        return cards;
    }

    /**
     * Method to set the list of cards in the poker hand
     * @param cards the list of cards in the poker hand
     */
    private void setCards(List<String> cards) {
        this.cards = cards;
    }

    // TODO: Add a toString method to print out the cards in the hand
}
