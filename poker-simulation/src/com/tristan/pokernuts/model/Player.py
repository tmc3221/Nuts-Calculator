'''
Represents a player in the game. 
A player has a name, a hand of two cards, a number of chips, and a current bet.
'''
# Player.py
class Player:
    def __init__(self, name, chips=1000):
        self.name = name
        self.hand = []  # Two cards
        self.chips = chips
        self.current_bet = 0

    def place_bet(self, amount):
        if amount > self.chips:
            raise ValueError(f"{self.name} doesn't have enough chips!")
        self.chips -= amount
        self.current_bet += amount

    def show_hand(self):
        return ', '.join(str(card) for card in self.hand)  

    def __str__(self):
        return f"{self.name} - Chips: {self.chips}, Hand: {', '.join(str(card) for card in self.hand)}"
