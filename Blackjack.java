/*****   學號：413226178、413226271         *****/
/*****   姓名：楊茗翔、簡稔祖         *****/


import java.util.*;

public class Blackjack extends CardGame {
    private final Map<String, List<PokerCard>> playerHands;
    private final Map<String, Boolean> playerStatus; // 玩家狀態，true 表示已停牌或爆牌

    public Blackjack(List<String> players) {
        super(players);
        this.playerHands = new HashMap<>();
        this.playerStatus = new HashMap<>();
        for (String player : players) {
            playerHands.put(player, new ArrayList<>());
            playerStatus.put(player, false); // 初始化為未停牌或爆牌
        }
    }

    @Override
    protected void setupGame() {
        System.out.println("=== 開始 Blackjack 遊戲 ===");
        dealInitialCards();
    }

    @Override
    protected void playRound() {
        while (!isGameOver()) {
            for (String player : players) {
                if (playerStatus.get(player)) continue; // 跳過已停牌或爆牌的玩家

                List<PokerCard> hand = playerHands.get(player);
                System.out.println(player + " 的手牌：" + hand);
                System.out.println(player + " 的目前分數：" + calculateScore(hand));

                System.out.println(player + "，你想要 (1) 要牌 還是 (2) 停牌？");
                Scanner scanner = new Scanner(System.in);
                String choice = scanner.nextLine();

                if (choice.equals("1")) {
                    hit(hand);
                    System.out.println(player + " 抽到了：" + hand.get(hand.size() - 1));
                    int score = calculateScore(hand);
                    System.out.println(player + " 的目前分數：" + score);

                    if (score > 21) {
                        System.out.println(player + " 爆牌了！");
                        playerStatus.put(player, true); // 爆牌視為回合結束
                    } else if (score == 21) {
                        System.out.println(player + " 達到 21 點！");
                        playerStatus.put(player, true); // 自動停牌
                    }
                } else if (choice.equals("2")) {
                    playerStatus.put(player, true); // 選擇停牌
                } else {
                    System.out.println("無效的選項！");
                }
            }
        }
    }

    @Override
    protected boolean isGameOver() {
        // 當所有玩家都停牌或爆牌時，遊戲結束
        return playerStatus.values().stream().allMatch(status -> status);
    }

    @Override
    protected void determineWinner() {
        System.out.println("=== 遊戲結果 ===");
        Map<String, Integer> scores = new HashMap<>();
        for (String player : players) {
            int playerScore = calculateScore(playerHands.get(player));
            scores.put(player, playerScore);
            System.out.println(player + " 的最終手牌：" + playerHands.get(player) + "，分數：" + playerScore);
        }

        // 過濾有效分數（21 以下）
        scores.entrySet().removeIf(entry -> entry.getValue() > 21);

        if (scores.isEmpty()) {
            System.out.println("所有玩家都爆牌了！");
        } else {
            int maxScore = Collections.max(scores.values());
            List<String> winners = new ArrayList<>();
            for (Map.Entry<String, Integer> entry : scores.entrySet()) {
                if (entry.getValue() == maxScore) {
                    winners.add(entry.getKey());
                }
            }

            if (winners.size() == 1) {
                System.out.println("贏家是：" + winners.get(0) + "，分數：" + maxScore);
            } else {
                System.out.println("平局！贏家是：" + String.join(", ", winners) + "，分數：" + maxScore);
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
    }

    private void hit(List<PokerCard> hand) {
        hand.add(deck.remove(0));
    }
}
