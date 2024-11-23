/*****   學號：413226178、413226271         *****/
/*****   姓名：楊茗翔、簡稔祖         *****/


import java.util.*;

public class Texas {
    private final List<String> players; // 玩家名稱清單
    private final Map<String, List<PokerCard>> playerHands; // 每位玩家的手牌
    private final Map<String, Integer> playerChips; // 每位玩家的籌碼
    private final List<PokerCard> communityCards; // 公共牌
    private List<PokerCard> deck; // 撲克牌堆
    private int pot; // 總彩金
    private final int smallBlind; // 小盲注金額
    private final int bigBlind; // 大盲注金額
    private int dealerIndex; // 本輪莊家索引

    public Texas(List<String> players) {
        this.players = new ArrayList<>(players); // 深拷貝，避免直接修改
        this.playerHands = new HashMap<>();
        this.playerChips = new HashMap<>();
        this.communityCards = new ArrayList<>();
        this.deck = createDeck();
        this.pot = 0;
        this.smallBlind = 50;
        this.bigBlind = 100;
        this.dealerIndex = 0;

        for (String player : players) {
            playerHands.put(player, new ArrayList<>());
            playerChips.put(player, 1000); // 每位玩家初始籌碼
        }
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

    // 洗牌與切牌
    private void shuffleAndCutDeck() {
        Collections.shuffle(deck, new Random());
        int cutPoint = new Random().nextInt(deck.size());
        List<PokerCard> topHalf = deck.subList(0, cutPoint);
        List<PokerCard> bottomHalf = deck.subList(cutPoint, deck.size());
        deck = new ArrayList<>(bottomHalf);
        deck.addAll(topHalf);
    }

    // 發手牌給每位玩家
    private void dealHands() {
        for (String player : players) {
            playerHands.get(player).add(deck.remove(0));
            playerHands.get(player).add(deck.remove(0));
        }
    }

    // 發公共牌
    private void dealCommunityCards(int count) {
        burnCard(); // 消一張牌
        for (int i = 0; i < count; i++) {
            communityCards.add(deck.remove(0));
        }
    }

    // 消牌
    private void burnCard() {
        deck.remove(0);
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

    // 處理一輪下注
    private void bettingRound() {
        System.out.println("\n=== 開始新的一輪喊注 ===");
        Iterator<String> iterator = players.iterator();
        Scanner scanner = new Scanner(System.in);

        while (iterator.hasNext()) {
            String player = iterator.next();

            // 顯示當前玩家的手牌資訊
            System.out.println("\n" + player + " 的回合！");
            System.out.println("你的手牌是：" + playerHands.get(player));
            System.out.println("你的籌碼：" + playerChips.get(player));
            System.out.println("當前總彩金：" + pot);

            // 玩家選擇行動
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
                    iterator.remove(); // 玩家放棄遊戲
                }
                default -> System.out.println("無效的選擇，跳過此玩家。");
            }
        }
    }


    // 計算手牌分數
    private int calculateScore(List<PokerCard> hand) {
        int score = 0;
        for (PokerCard card : hand) {
            score += Math.min(card.getValue(), 10); // JQK為10
        }
        return score;
    }

    // 比較大小並分配彩金
    private void determineWinner() {
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
    }

    

    // 主流程
    public void play() {
        System.out.println("=== 歡迎來到德州撲克遊戲 ===");
        shuffleAndCutDeck(); // 洗牌與切牌
        System.out.println("牌堆已洗牌並切牌完成！");
        placeBlinds(); // 盲注
        dealHands(); // 發底牌
        System.out.println("每位玩家的底牌已發放。");

        // 各輪流程
        System.out.println("\n=== 翻牌前（Preflop）階段 ===");
        bettingRound();
        dealCommunityCards(3); // 翻牌
        System.out.println("\n公共牌（翻牌圈）：" + communityCards);
        bettingRound();
        dealCommunityCards(1); // 轉牌
        System.out.println("\n公共牌（轉牌圈）：" + communityCards);
        bettingRound();
        dealCommunityCards(1); // 河牌
        System.out.println("\n公共牌（河牌圈）：" + communityCards);
        bettingRound();

        // 攤牌
        if (players.size() > 1) {
            determineWinner();
        } else {
            String winner = players.get(0);
            System.out.println("僅剩一名玩家：" + winner + "，自動獲勝！");
            playerChips.put(winner, playerChips.get(winner) + pot);
        }

        System.out.println("\n=== 遊戲結束 ===");
        for (String player : players) {
            System.out.println(player + " 的最終籌碼：" + playerChips.get(player));
        }
        System.out.println("感謝遊玩德州撲克！");
    }
}
