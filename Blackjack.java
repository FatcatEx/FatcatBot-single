/*****   學號：413226178、413226271         *****/
/*****   姓名：楊茗翔、簡稔祖         *****/

import java.util.*;

public class Blackjack {
    private final List<String> players; // 玩家名稱清單
    private final List<PokerCard> deck;
    private final Map<String, List<PokerCard>> playerHands;
    private final List<PokerCard> dealerHand;

    public Blackjack(List<String> players) {
        this.players = players;
        this.deck = createDeck();
        this.playerHands = new HashMap<>();
        this.dealerHand = new ArrayList<>();
        for (String player : players) {
            playerHands.put(player, new ArrayList<>());
        }
        shuffleDeck();
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
        Collections.shuffle(deck, new Random());
    }

    // 發初始兩張牌
    private void dealInitialCards() {
        for (String player : players) {
            playerHands.get(player).add(deck.remove(0));
            playerHands.get(player).add(deck.remove(0));
        }
        dealerHand.add(deck.remove(0));
        dealerHand.add(deck.remove(0));
    }

    // 計算分數
    private int calculateScore(List<PokerCard> hand) {
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

        while (score > 21 && aceCount > 0) {
            score -= 10;
            aceCount--;
        }
        return score;
    }

    // 要牌
    private void hit(List<PokerCard> hand) {
        hand.add(deck.remove(0));
    }

    // 遊戲流程
    public void play() {
        dealInitialCards();

        for (String player : players) {
            List<PokerCard> hand = playerHands.get(player);
            System.out.println(player + " 的手牌：" + hand);

            while (true) {
                System.out.println(player + " 的目前分數：" + calculateScore(hand));
                System.out.println(player + "，你想要 (1) 要牌 還是 (2) 停牌？");
                Scanner scanner = new Scanner(System.in);
                String choice = scanner.nextLine();

                if (choice.equals("1")) {
                    hit(hand);
                    System.out.println(player + " 抽到了：" + hand.get(hand.size() - 1));
                    if (calculateScore(hand) > 21) {
                        System.out.println(player + " 爆牌了！");
                        break;
                    }
                } else if (choice.equals("2")) {
                    break;
                } else {
                    System.out.println("無效的選項！");
                }
            }
        }

        // 莊家回合
        System.out.println("莊家的回合開始！");
        while (calculateScore(dealerHand) < 17) {
            hit(dealerHand);
            System.out.println("莊家抽到了：" + dealerHand.get(dealerHand.size() - 1));
        }

        // 比較結果
        System.out.println("莊家的最終手牌：" + dealerHand + "，分數：" + calculateScore(dealerHand));
        for (String player : players) {
            int playerScore = calculateScore(playerHands.get(player));
            int dealerScore = calculateScore(dealerHand);
            System.out.println(player + " 的最終分數：" + playerScore);

            if (playerScore > 21 || (dealerScore <= 21 && dealerScore > playerScore)) {
                System.out.println(player + " 輸了！");
            } else if (playerScore == dealerScore) {
                System.out.println(player + " 平手！");
            } else {
                System.out.println(player + " 贏了！");
            }
        }
    }
}