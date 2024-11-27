import java.util.*;

public class Blackjack extends CardGame {
    private final Map<String, List<PokerCard>> playerHands;
    private final List<PokerCard> dealerHand;

    public Blackjack(List<String> players) {
        super(players);
        this.playerHands = new HashMap<>();
        this.dealerHand = new ArrayList<>();
        for (String player : players) {
            playerHands.put(player, new ArrayList<>());
        }
    }

    @Override
    protected void setupGame() {
        System.out.println("=== 開始 Blackjack 遊戲 ===");
        dealInitialCards();
    }

    @Override
    protected void playRound() {
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
    }

    @Override
    protected boolean isGameOver() {
        return true; // Blackjack 一局結束後即遊戲結束
    }

    @Override
    protected void determineWinner() {
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

    // 計算分數
    private int calculateScore(List<PokerCard> hand) {
        int score = 0;
        int aceCount = 0;

        for (PokerCard card : hand) {
            int value = card.getValue();
            if (value > 10) {
                score += 10;
            } else if (value == 1) {
                aceCount++;
                score += 11;
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

    private void dealInitialCards() {
        for (String player : players) {
            playerHands.get(player).add(deck.remove(0));
            playerHands.get(player).add(deck.remove(0));
        }
        dealerHand.add(deck.remove(0));
        dealerHand.add(deck.remove(0));
    }

    private void hit(List<PokerCard> hand) {
        hand.add(deck.remove(0));
    }
}
