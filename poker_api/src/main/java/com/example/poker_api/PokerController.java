package com.example.poker_api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.poker_api.dsa.list.ArrayBasedList;
import com.example.poker_api.dsa.list.List;
import com.example.poker_api.dsa.map.LinearProbingHashMap;
import com.example.poker_api.dsa.map.Map;
import com.example.poker_api.dsa.map.Map.Entry;

/**
 * Controller class for the Poker API
 * Handles requests to the API and returns responses
 * Helps return data for the Poker API
 * 
 * @author Tristan Curtis (tmc3221)
 */
// TODO: Need to update the flush and consectuive logic to check
// for best hand without knowing other
// ie: if there is 5 6 7 on the board, asssume that someone has 8 9
@RestController
@RequestMapping("/api/poker")
public class PokerController {

    /** Holds the calculated nuts value as a String */
    private static String nuts = null;

    /** Holds the suit of the flush */
    private static String flush_val = null;

    /** Holds the high card of the flush */
    private static String straight_val = null;

    /**
     * Method to get the best hand from a list of cards
     * Calculates the NUTs hand using the best hand calculation logic
     * @param pokerHand the hand of cards we are using to calculate
     * the best hand from
     * @return the best hand from the list of cards
     */
    @PostMapping("/bestHand")
    public ResponseEntity<String> getBestHand(@RequestBody PokerHand pokerHand) {
        String bestHand = calculateBestHand(pokerHand.getCards());
        return ResponseEntity.ok(new BestHandResponse(bestHand));
    }

    /**
     * Method to calculate the best hand from a list of cards
     * @param cards the list of cards we are using to calculate 
     * the best hand from
     * @return The best hand from the list of cards
     */
    private String calculateBestHand(List<String> cards) {
        // Parse ranks and suits
        List<String> ranks = new ArrayBasedList<>();
        List<Character> suits = new ArrayBasedList<>();

        for (String card : cards) {
            ranks.addFirst(card.substring(0, card.length() - 1));
            suits.addFirst(card.charAt(card.length() - 1));
        }

        // Count the frequencies of each of the rank
        Map<String, Integer> rankFrequencies = new LinearProbingHashMap<>();
        Map<Character, Integer> suitFrequencies = new LinearProbingHashMap<>();
        for(String rank : ranks) {
            if(rankFrequencies.get(rank) == null) {
                rankFrequencies.put(rank, 1);
            } else {
                rankFrequencies.put(rank, rankFrequencies.get(rank) + 1);
            }
        }
        for(char suit : suits) {
            if(suitFrequencies.get(suit) == null) {
                suitFrequencies.put(suit, 1);
            } else {
                suitFrequencies.put(suit, suitFrequencies.get(suit) + 1);
            }
        }

        // Checks for a flush
        boolean flush = isFlush(suitFrequencies);

        // Check for a straight
        List<Integer> rankValues = new ArrayBasedList<>();
        for(Entry<String, Integer> rank : rankFrequencies.entrySet()) {
            rankValues.addFirst(rankToValue(rank.getKey()));
        }
        sort(rankValues);

        // Check for a straight
        boolean straight = isConsecutive(rankValues);

        // Check for specific hand rankings
        if (flush && straight && rankValues.get(14) != null) {
            nuts = "Royal Flush"; // Straight Flush with Ace high
            return nuts;
        } else if (straight && flush) {
            straight_flush(rankValues);
            return nuts;
        } else if (quads(rankFrequencies)) {
            return nuts;
        } else if (fullhouse(rankFrequencies)) {
            return nuts;
        } else if (flush) {
            return nuts;
        } else if (straight) {
            nuts = "Straight: " + straight_val + " high";
            return nuts;
        } else if (three_of_a_kind(rankFrequencies)) {
            return nuts;
        } else if (twoPair(rankFrequencies)) {
            return nuts;
        } else if (pair(rankFrequencies)) {
            return nuts;
        } else {
            // Default to High Card
            int highCard = rankValues.get(rankValues.size() - 1);
            nuts = "High Card: " + valueToRank(highCard);
        }

        return nuts;
    }

    /**
     * Private helper method to check for a pair
     * @param rankFrequencies the map of rank frequencies
     * @return if there is a three of a kind
     */
    private boolean pair(Map<String, Integer> rankFrequencies) {
        for(Entry<String, Integer> rank : rankFrequencies.entrySet()) {
            if(rank.getValue() == 2) {
                nuts = "Pair of two: " + rank.getKey();
                return true;
            }
        }
        return false;
    }

    /**
     * Helper method to check for two pairs
     * @param rankFrequencies the map of rank frequencies
     * @return if there are two pairs
     */
    private boolean twoPair(Map<String, Integer> rankFrequencies) {
        List<Integer> pairs = new ArrayBasedList<>();

        for (Entry<String, Integer> rank : rankFrequencies.entrySet()) {
            if (rank.getValue() == 2) {
                pairs.addLast(rankToValue(rank.getKey()));
            }
        }

        if (pairs.size() == 2) {
            // Sort the pairs to determine the higher and lower pair
            sort(pairs);
            Integer highPair = pairs.get(0);
            Integer lowPair = pairs.get(1);

            nuts = "Two Pair: " + highPair + " and " + lowPair;
            return true;
        }

        return false;
    }

    /**
     * Helper method to check for full houses
     * @param rankFrequencies the frequencies of the ranks
     * @return if there is a full house or not
     */
    private boolean fullhouse(Map<String, Integer> rankFrequencies) {
        boolean three = false;
        String three_val = "";

        boolean two = false;
        String two_val = "";

        for(Entry<String, Integer> rank : rankFrequencies.entrySet()) {
            if(rank.getValue() == 3) {
                three = true;
                three_val = rank.getKey();
            } else if(rank.getValue() == 2) {
                two = true;
                two_val = rank.getKey();
            }
        }
        if(three && two) {
            nuts = "Full House: " + three_val + " full of " + two_val;
            return true;
        }
        return false;
    }

    /**
     * Helper method to check for a straight flush
     * @param rankValues the list of rank values
     */
    private void straight_flush(List<Integer> rankValues) {
        nuts = "Straight Flush: " + valueToRank(rankValues.get(rankValues.size() - 1)) + " high of " + flush_val;
    }

    /**
     * Private helper method to check for three of a kind
     * @param rankFrequencies the map of rank frequencies
     * @return if there is a three of a kind
     */
    private boolean three_of_a_kind(Map<String, Integer> rankFrequencies) {
        for(Entry<String, Integer> rank : rankFrequencies.entrySet()) {
            if(rank.getValue() == 3) {
                nuts = "Three of a kind: " + rank.getKey();
                return true;
            }
        }
        return false;
    }

    /**
     * Private helper method to check for four of a kind
     * @param rankFrequencies the map of rank frequencies
     * @return if there is a four of a kind
     */
    private boolean quads(Map<String, Integer> rankFrequencies) {
       for(Entry<String, Integer> rank : rankFrequencies.entrySet()) {
           if(rank.getValue() == 4) {
               nuts = "Four of a kind: " + rank.getKey();
               return true;
           }
       }
       return false;
    }

    /**
     * Helper method to sort a list of integers
     * Uses merge sort
     * Delegates to merge helper method
     * @param values the list of values we are sorting
     */
    private void sort(List<Integer> values) {
        int n = values.size();

        // Base Case
        if (n < 2) {
            // Already sorted
            return;
        }

        // Recursive Case

        // Mid
        int mid = n / 2;

        // Left
        List<Integer> left = new ArrayBasedList<>();
        for (int i = 0; i < mid; i++) {
            left.addLast(values.get(i));
        }

        // Right
        List<Integer> right = new ArrayBasedList<>();
        for (int i = mid; i < n; i++) {
            right.addLast(values.get(i));
        }

        // Recursive call
        sort(left);
        sort(right);

        // Merge
        merge(left, right, values);
    }

    /**
     * Helper method to merge two sorted lists
     * @param left the left list we are merging
     * @param right the right list we are merging
     * @param result the result list that is merged
     */
    private void merge(List<Integer> left, List<Integer> right, List<Integer> result) {
        int resultIndex = 0;
        int leftIndex = 0;
        int rightIndex = 0;

        // Merge elements from left and right into result
        while (leftIndex < left.size() && rightIndex < right.size()) {
            if (left.get(leftIndex) >= right.get(rightIndex)) {
                result.set(resultIndex++, left.get(leftIndex++));
            } else {
                result.set(resultIndex++, right.get(rightIndex++));
            }
        }

        // Copy remaining elements from left
        while (leftIndex < left.size()) {
            result.set(resultIndex++, left.get(leftIndex++));
        }

        // Copy remaining elements from right
        while (rightIndex < right.size()) {
            result.set(resultIndex++, right.get(rightIndex++));
        }
    }

    /**
     * Helper method to calculate from rank to value
     * @param rank the rank we are converting
     * @return the new value of the rank
     */
    private int rankToValue(String rank) {
        return switch (rank) {
            case "A" -> 14;
            case "K" -> 13;
            case "Q" -> 12;
            case "J" -> 11;
            case "10" -> 10;
            default -> Integer.parseInt(rank);
        };
    }

    /**
     * Helper method to calculate from value to rank
     * @param value the value we are converting
     * @return the new rank of the value
     */
    private String valueToRank(int value) {
        return switch (value) {
            case 14 -> "A";
            case 13 -> "K";
            case 12 -> "Q";
            case 11 -> "J";
            default -> String.valueOf(value);
        };
    }

    /**
     * Checks for a flush
     * @param suitFrequencies the map of suit frequencies
     * @return if there is a flush or not
     */
    private boolean isFlush(Map<Character, Integer> suitFrequencies) {
        for(Entry<Character, Integer> suit : suitFrequencies.entrySet()) {
            if(suit.getValue() >= 5) {
                flush_val = String.valueOf(suit.getKey());
                return true;
            }
        }
        return false;
    }

    /**
     * Helper method to check if the values are consecutive
     * @param values the list of values we are checking
     * @return if the values are consecutive or not
     */
    private boolean isConsecutive(List<Integer> values) {
        for (int i = 1; i < values.size(); i++) {
            if (values.get(i) != values.get(i - 1) + 1) {
                return false;
            } else if(i == values.size() - 1)
                straight_val = valueToRank(i);
        }
        return values.size() >= 5;
    }

    /**
     * Private inner class to represent the server response
     * 
     * @author Tristan Curtis (tmc3221)
     */
    private static class BestHandResponse {

        /** The best hand possible */
        private String bestHand;
    
        /**
         * Constructs a BestHandResponse with a best hand
         * @param bestHand the best hand
         */
        public BestHandResponse(String bestHand) {
            this.bestHand = bestHand;
        }
    
        /**
         * Method to get the best hand
         * @return the best hand
         */
        @SuppressWarnings("unused")
        public String getBestHand() {
            return bestHand;
        }
    
        /**
         * Sets the best hand
         * @param bestHand the best hand
         */
        @SuppressWarnings("unused")
        public void setBestHand(String bestHand) {
            this.bestHand = bestHand;
        }
    }
}
