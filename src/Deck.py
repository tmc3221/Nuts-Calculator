import random
from Card import Card

class Deck:
    def __init__(self):
        """Initialize a new deck of cards."""
        self.suits = ['Hearts', 'Diamonds', 'Clubs', 'Spades']
        self.ranks = ['2', '3', '4', '5', '6', '7', '8', '9', '10', 'Jack', 'Queen', 'King', 'Ace']
        self._create_deck()
        self.shuffled = False
        self.dealt_count = 0

    def _create_deck(self):
        """Create a new deck of 52 cards"""
        self.cards = [Card(rank, suit) for rank in self.ranks for suit in self.suits]
        self.dealt_count = 0

    def shuffle(self):
        """Shuffle the deck"""
        random.shuffle(self.cards)
        self.shuffled = True

    def draw(self):
        """Draw a card from the top of the deck"""
        if self.is_empty():
            raise ValueError("Cannot draw from an empty deck!")
        self.dealt_count += 1
        return self.cards.pop()

    def reset(self):
        """Reset the deck to its original unshuffled state"""
        self._create_deck()
        self.shuffled = False

    def is_empty(self):
        """Check if the deck is empty"""
        return len(self.cards) == 0

    def cards_left(self):
        """Return the number of cards left in the deck"""
        return len(self.cards)

    def __str__(self):
        """Return a string representation of the deck"""
        return f"Deck({len(self.cards)} cards, shuffled={self.shuffled}, dealt_count={self.dealt_count})"

# Example usage:
deck = Deck()
deck.shuffle()
print(deck.draw())
print(deck.draw())
print(deck)
