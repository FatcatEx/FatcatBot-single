public class PokerCard {
    private String suit;  // 撲克牌花色
    private int value;    // 撲克牌數值

    public PokerCard(String suit, int value) {
        this.suit = suit;
        this.value = value;
    }

    public String getSuit() {
        return suit;
    }

    public int getValue() {
        return value;
    }

    @Override
    public String toString() {
        String cardName;
        switch (value) {
            case 1: cardName = "Ace"; break;
            case 11: cardName = "Jack"; break;
            case 12: cardName = "Queen"; break;
            case 13: cardName = "King"; break;
            default: cardName = String.valueOf(value);
        }
        return cardName + " of " + suit;
    }
}
