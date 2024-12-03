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
    public void setCards(List<String> cards) {
        this.cards = cards;
    }
}
