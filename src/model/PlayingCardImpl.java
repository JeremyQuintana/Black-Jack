package model;

import model.interfaces.*;

public class PlayingCardImpl implements PlayingCard {
	
	public final PlayingCard.Suit suit;
	public final PlayingCard.Value value;
	
	//instantiates the cards identity
	public PlayingCardImpl(PlayingCard.Suit suit, PlayingCard.Value value) {
		this.suit = suit;
		this.value = value;
	}
	
	//returns the cards face suit
	public PlayingCard.Suit getSuit() {
		return suit;
	}
	
	//returns the cards face value
	public PlayingCard.Value getValue() {
		return value;
	}
	
	//returns the cards score in terms of black jack
	public int getScore() {
		switch(value) {
		case ACE: return 1;
		case TWO: return 2;
		case THREE: return 3;
		case FOUR: return 4;
		case FIVE: return 5;
		case SIX: return 6;
		case SEVEN: return 7;
		case EIGHT: return 8;
		case NINE: return 9;
		case TEN: return 10;
		case JACK: return 10;
		case QUEEN: return 10;
		case KING: return 10;
		default: return 0;
		}
	}
	
	//returns a readable string of the cards face value and score
	public String toString() {
		return "Suit: " + suit + ", Value: " + value + ", Score: " + getScore();
	}
	
	//checks if this card is equal to the passed in card
	//returns false if not equal
	public boolean equals(PlayingCard card) {
		if(card.getSuit().equals(suit) && card.getValue().equals(value)) return true;
		return false;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((suit == null) ? 0 : suit.hashCode());
		result = prime * result + ((value == null) ? 0 : value.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (obj instanceof PlayingCard)
			return false;
		PlayingCardImpl other = (PlayingCardImpl) obj;
		if (suit != other.suit)
			return false;
		if (value != other.value)
			return false;
		return true;
	}
	
	
}
