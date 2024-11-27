import java.util.*;

public class Texas extends CardGame {
    private final Map<String, List<PokerCard>> playerHands; // 玩家手牌
    private final List<PokerCard> communityCards; // 公共牌
    private final Map<String, Integer> playerChips; // 玩家籌碼
    private int pot; // 獎池
    private int dealerIndex; // 莊家位置
    private static final int smallBlind = 50; // 小盲注
    private static final int bigBlind = 100; // 大盲注

    public Texas(List<String> players) {
        super(players);
        this.playerHands = new HashMap<>();
        this.communityCards = new ArrayList<>();
        this.playerChips = new HashMap<>();
        this.pot = 0;
        this.dealerIndex = 0;

        // 初始化玩家籌碼
        for (String player : players) {
            playerHands.put(player, new ArrayList<>());
            playerChips.put(player, 1000);
        }
    }

    // 遊戲初始化：洗牌 + 發牌 + 盲注
    @Override
    protected void setupGame() {
        System.out.println("=== 開始 Texas Hold'em 遊戲 ===");
        shuffleDeck();
        placeBlinds();
        dealHands();
    }

    // 每一輪的遊戲行為
    @Override
    protected void playRound() {
        System.out.println("\n公共牌：" + communityCards);
        bettingRound();

        if (communityCards.size() == 0) {
            dealCommunityCards(3); // 翻牌圈
        } else if (communityCards.size() < 5) {
            dealCommunityCards(1); // 轉牌或河牌
        }
    }

    // 判斷遊戲是否結束
    @Override
    protected boolean isGameOver() {
        return players.size() <= 1 || communityCards.size() == 5;
    }

    // 宣告贏家
    @Override
    protected void determineWinner() {
        System.out.println("\n=== 最終攤牌階段 ===");
        Map<String, Integer> scores = new HashMap<>();

        for (String player : players) {
            List<PokerCard> allCards = new ArrayList<>(playerHands.get(player));
            allCards.addAll(communityCards);
            int score = calculateScore(allCards);
            scores.put(player, score);

            System.out.println(player + " 的手牌：" + playerHands.get(player));
            System.out.println("公共牌：" + communityCards);
            System.out.println("組合牌分數：" + score);
        }

        String winner = Collections.max(scores.entrySet(), Map.Entry.comparingByValue()).getKey();
        System.out.println("\n贏家是：" + winner);
        playerChips.put(winner, playerChips.get(winner) + pot);
        System.out.println(winner + " 獲得彩金：" + pot);

        System.out.println("\n遊戲結束！");
    }

    // 計算手牌的分數（此處簡化為示例邏輯）
    private int calculateScore(List<PokerCard> cards) {
        return cards.stream().mapToInt(PokerCard::getValue).sum(); // 加總所有牌的值
    }

    // 發手牌
    private void dealHands() {
        for (String player : players) {
            playerHands.get(player).add(deck.remove(0));
            playerHands.get(player).add(deck.remove(0));
        }
    }

    // 發公共牌
    private void dealCommunityCards(int count) {
        for (int i = 0; i < count; i++) {
            communityCards.add(deck.remove(0));
        }
    }

    // 處理盲注
    private void placeBlinds() {
        int smallBlindIndex = (dealerIndex + 1) % players.size();
        int bigBlindIndex = (dealerIndex + 2) % players.size();

        String smallBlindPlayer = players.get(smallBlindIndex);
        String bigBlindPlayer = players.get(bigBlindIndex);

        playerChips.put(smallBlindPlayer, playerChips.get(smallBlindPlayer) - smallBlind);
        playerChips.put(bigBlindPlayer, playerChips.get(bigBlindPlayer) - bigBlind);

        pot += smallBlind + bigBlind;

        System.out.println(smallBlindPlayer + " 下小盲注：" + smallBlind);
        System.out.println(bigBlindPlayer + " 下大盲注：" + bigBlind);
    }

    // 喊注階段
    private void bettingRound() {
        System.out.println("\n=== 開始一輪喊注 ===");
        Iterator<String> iterator = players.iterator();
        Scanner scanner = new Scanner(System.in);

        while (iterator.hasNext()) {
            String player = iterator.next();

            System.out.println("\n" + player + " 的回合！");
            System.out.println("你的手牌是：" + playerHands.get(player));
            System.out.println("你的籌碼：" + playerChips.get(player));
            System.out.println("當前總彩金：" + pot);

            System.out.println(player + "，請選擇：(1) 跟注 / (2) 加注 / (3) 蓋牌");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1 -> System.out.println(player + " 選擇了跟注。");
                case 2 -> {
                    System.out.println("請輸入加注金額：");
                    int raise = scanner.nextInt();
                    if (raise <= playerChips.get(player)) {
                        playerChips.put(player, playerChips.get(player) - raise);
                        pot += raise;
                        System.out.println(player + " 加注了：" + raise + "，剩餘籌碼：" + playerChips.get(player));
                    } else {
                        System.out.println("加注失敗，籌碼不足！");
                    }
                }
                case 3 -> {
                    System.out.println(player + " 選擇了蓋牌。");
                    iterator.remove();
                }
                default -> System.out.println("無效的選擇，跳過此玩家。");
            }
        }
    }
}
