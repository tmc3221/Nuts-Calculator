from Deck import Deck
from Player import Player
import random

'''
This class represents the poker game
Uses singleton design pattern
Five players at the table
Follows Texas Hold'em rules
'''
# TexasHoldEm.py
class TexasHoldEm:
    _shared_state = {}

    def __init__(self):
        self.__dict__ = self._shared_state
        if not hasattr(self, "initialized"):  # Ensure it's initialized only once
            self.players = []
            self.deck = Deck()
            self.community_cards = []
            self.pot = 0
            self.current_bet = 0
            self.little_blind_player = None
            self.big_blind_player = None
            self.initialized = True

    def start_game(self, num_players=5):
        """
        Initializes the game with the given number of players
        """
        self.players = [Player(f"Player {i + 1}") for i in range(num_players)]
        self.deck.shuffle()

        # Deal two cards to each player
        for player in self.players:
            player.hand = [self.deck.draw(), self.deck.draw()]

        # Reset community cards
        self.community_cards = []

        self._select_blinds()

    def _select_blinds(self):
        """
        Randomly selects two adjacent players for little and big blinds
        """
        # Randomly select an index for the little blind
        little_blind_index = random.randint(0, len(self.players) - 1)
        # The big blind is the next player in the circular order
        big_blind_index = (little_blind_index + 1) % len(self.players)

        self.little_blind_player = self.players[little_blind_index]
        self.big_blind_player = self.players[big_blind_index]

        print(f"Little Blind: {self.little_blind_player.name}")
        print(f"Big Blind: {self.big_blind_player.name}")

    def deal_community_cards(self, stage):
        """
        Deals community cards for 'flop', 'turn', or 'river'
        """
        if stage == "flop":
            self.community_cards.extend([print(self.deck.draw()) for _ in range(3)])
        elif stage in ["turn", "river"]:
            self.community_cards.append(print(self.deck.draw()))

    def play_round(self):
        """
        Simulates a round of poker
        """
        self.start_game()
        print("Starting a new round...")
        
        # Pre-flop
        self._betting_round("Pre-flop")
        self.deal_community_cards("flop")
        self._betting_round("Flop")
        self.deal_community_cards("turn")
        self._betting_round("Turn")
        self.deal_community_cards("river")
        self._betting_round("River")
        
        # Evaluate hands
        

    def _betting_round(self, stage):
        """
        Placeholder for betting logic during each stage
        """
        print(f"{stage} betting round... (simplified for now)")

if __name__ == "__main__":
    game = TexasHoldEm()
    game.start_game()  # Initialize the game and assign blinds

    print("\nPlayers:")
    for player in game.players:
        print(player.name)

    game.play_round()  # Simulate a round of poker

    for player in game.players:
        print("\n")
        print(player.name)
        print(player.show_hand())