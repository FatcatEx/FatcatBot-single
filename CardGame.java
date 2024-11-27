import java.util.*;

public abstract class CardGame {
    protected List<String> players; // 玩家名稱清單
    protected List<PokerCard> deck; // 撲克牌堆

    public CardGame(List<String> players) {
        this.players = new ArrayList<>(players); // 深拷貝避免修改原清單
        this.deck = createDeck();
        shuffleDeck();
    }

    // 建立一副完整的撲克牌
    protected List<PokerCard> createDeck() {
        List<PokerCard> deck = new ArrayList<>();
        String[] suits = {"紅心", "方塊", "梅花", "黑桃"};
        for (String suit : suits) {
            for (int value = 1; value <= 13; value++) {
                deck.add(new PokerCard(suit, value));
            }
        }
        return deck;
    }

    // 洗牌
    protected void shuffleDeck() {
        Collections.shuffle(deck, new Random());
    }

    // 模板方法：遊戲的主要流程
    public final void play() {
        setupGame();
        while (!isGameOver()) {
            playRound();
        }
        determineWinner();
    }

    // 抽象方法：每個遊戲的具體實現
    protected abstract void setupGame(); // 初始化遊戲
    protected abstract void playRound(); // 每回合的邏輯
    protected abstract boolean isGameOver(); // 遊戲結束條件
    protected abstract void determineWinner(); // 決定贏家
}
