# Hand.py
class Hand:
    def __init__(self):
        self.cards = []
    
    def get_cards(self):
        """
        Returns the cards in the hand
        """
        return self.cards

    def add_card(self, card):
        """
        Adds a card to the hand
        """
        self.cards.append(card)

    def get_hand_value(self):
        """
        Calculates the value of the hand
        """
        value = 0
        for card in self.cards:
            if card.rank.isdigit():
                value += int(card.rank)
            elif card.rank in ['Jack', 'Queen', 'King']:
                value += 10
            elif card.rank == 'Ace':
                value += 11
        return value

    def __str__(self):
        """
        Returns a string representation of the hand
        """
        return ', '.join(str(card) for card in self.cards)
    
    def __hash__(self):
        return hash((self.rank, self.suit))
