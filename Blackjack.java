import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Blackjack {
    private List<PokerCard> deck;       // 撲克牌堆
    private List<PokerCard> playerHand; // 玩家手牌
    private List<PokerCard> dealerHand; // 莊家手牌

    public Blackjack() {
        this.deck = createDeck();       // 初始化撲克牌堆
        this.playerHand = new ArrayList<>();
        this.dealerHand = new ArrayList<>();
        shuffleDeck();                  // 洗牌
    }

    // 建立一副完整的撲克牌
    private List<PokerCard> createDeck() {
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
    private void shuffleDeck() {
        Random random = new Random();
        for (int i = 0; i < deck.size(); i++) {
            int j = random.nextInt(deck.size());
            PokerCard temp = deck.get(i);
            deck.set(i, deck.get(j));
            deck.set(j, temp);
        }
    }

    // 發初始兩張牌給玩家和莊家
    public void dealInitialCards() {
        playerHand.add(deck.remove(0));
        playerHand.add(deck.remove(0));
        dealerHand.add(deck.remove(0));
        dealerHand.add(deck.remove(0));
    }

    // 要牌
    public void hit(List<PokerCard> hand) {
        hand.add(deck.remove(0));
    }

    // 計算分數
    public int calculateScore(List<PokerCard> hand) {
        int score = 0;
        int aceCount = 0;

        for (PokerCard card : hand) {
            int value = card.getValue();
            if (value > 10) {
                score += 10; // J, Q, K 算 10 分
            } else if (value == 1) {
                aceCount++;
                score += 11; // A 初始算 11 分
            } else {
                score += value;
            }
        }

        // 調整 A 的分數 (如果分數 > 21，則把 A 從 11 分改為 1 分)
        while (score > 21 && aceCount > 0) {
            score -= 10;
            aceCount--;
        }

        return score;
    }

    // 遊戲流程
    public void play() {
        dealInitialCards();
        System.out.println("你的手牌：" + playerHand);
        System.out.println("莊家的明牌：" + dealerHand.get(0));

        // 玩家回合
        while (true) {
            System.out.println("目前你的分數：" + calculateScore(playerHand));
            System.out.println("你想要 (1) 要牌 還是 (2) 停牌？");
            String choice = System.console().readLine().toUpperCase();
            if (choice.equals("1")) {
                hit(playerHand);
                System.out.println("你抽到了：" + playerHand.get(playerHand.size() - 1));
                if (calculateScore(playerHand) > 21) {
                    System.out.println("爆牌！你的最終分數：" + calculateScore(playerHand));
                    return;
                }
            } else if (choice.equals("2")) {
                break;
            } else {
                System.out.println("無效的選項，請輸入 '1' 或 '2'。");
            }
        }

        // 莊家回合
        System.out.println("莊家的回合開始。");
        System.out.println("莊家的手牌：" + dealerHand);
        while (calculateScore(dealerHand) < 17) {
            hit(dealerHand);
            System.out.println("莊家抽到了：" + dealerHand.get(dealerHand.size() - 1));
        }

        // 比較結果
        int playerScore = calculateScore(playerHand);
        int dealerScore = calculateScore(dealerHand);
        System.out.println("你的最終手牌：" + playerHand + "（分數：" + playerScore + "）");
        System.out.println("莊家的最終手牌：" + dealerHand + "（分數：" + dealerScore + "）");
        if (dealerScore > 21 || playerScore > dealerScore) {
            System.out.println("你贏了！");
        } else if (playerScore < dealerScore) {
            System.out.println("你輸了！");
        } else {
            System.out.println("平手！");
        }
    }
}
