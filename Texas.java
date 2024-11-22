import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Texas {
    private List<PokerCard> deck;          // 撲克牌堆
    private List<PokerCard> communityCards; // 公共牌
    private List<PokerCard> playerHand;    // 玩家手牌
    private List<PokerCard> dealerHand;    // 莊家手牌
    private int playerChips;              // 玩家籌碼
    private int currentBet;               // 當前下注金額

    public Texas() {
        this.deck = createDeck();          // 初始化撲克牌堆
        this.communityCards = new ArrayList<>();
        this.playerHand = new ArrayList<>();
        this.dealerHand = new ArrayList<>();
        this.playerChips = 1000;           // 玩家起始籌碼
        this.currentBet = 0;               // 初始下注為 0
        shuffleDeck();                     // 洗牌
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

    // 發玩家與莊家的手牌
    public void dealHands() {
        playerHand.add(deck.remove(0));
        playerHand.add(deck.remove(0));
        dealerHand.add(deck.remove(0));
        dealerHand.add(deck.remove(0));
    }

    // 發公共牌
    public void dealCommunityCards(int count) {
        for (int i = 0; i < count; i++) {
            communityCards.add(deck.remove(0));
        }
    }

    // 顯示玩家手牌與公共牌
    public void displayState(String stage) {
        System.out.println("=== " + stage + " ===");
        System.out.println("你的手牌：" + playerHand);
        System.out.println("公共牌：" + communityCards);
        System.out.println("剩餘籌碼：" + playerChips);
        System.out.println("====================");
    }

    // 玩家下注
    public boolean placeBet() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("請下注！（最少 50，最多剩餘籌碼）");
        int bet = scanner.nextInt();
        if (bet < 50 || bet > playerChips) {
            System.out.println("下注金額無效，請重新輸入。");
            return false;
        }
        playerChips -= bet;
        currentBet += bet;
        System.out.println("你下注了：" + bet + "。當前總下注為：" + currentBet);
        return true;
    }

    // 計算手牌分數 (簡化版)
    public int calculateHandScore(List<PokerCard> hand, List<PokerCard> communityCards) {
        List<PokerCard> allCards = new ArrayList<>(hand);
        allCards.addAll(communityCards);
        int score = 0;
        for (PokerCard card : allCards) {
            score += card.getValue(); // 簡單加總撲克牌點數作為分數
        }
        return score;
    }

    // 比較玩家和莊家的手牌分數
    public void determineWinner() {
        int playerScore = calculateHandScore(playerHand, communityCards);
        int dealerScore = calculateHandScore(dealerHand, communityCards);
        System.out.println("你的分數：" + playerScore);
        System.out.println("莊家的分數：" + dealerScore);
        if (playerScore > dealerScore) {
            System.out.println("你贏了！贏得：" + currentBet * 2);
            playerChips += currentBet * 2;
        } else if (playerScore < dealerScore) {
            System.out.println("你輸了。");
        } else {
            System.out.println("平手，退回下注金額：" + currentBet);
            playerChips += currentBet;
        }
        currentBet = 0;
    }

    // 遊戲流程
    public void play() {
        Scanner scanner = new Scanner(System.in);
        while (playerChips > 0) {
            System.out.println("開始新的一局德州撲克！");
            dealHands();
            if (!placeBet()) continue;

            // 發翻牌 (Flop)
            dealCommunityCards(3);
            displayState("翻牌階段");
            if (!placeBet()) continue;

            // 發轉牌 (Turn)
            dealCommunityCards(1);
            displayState("轉牌階段");
            if (!placeBet()) continue;

            // 發河牌 (River)
            dealCommunityCards(1);
            displayState("河牌階段");
            if (!placeBet()) continue;

            // 比較結果
            System.out.println("莊家的手牌：" + dealerHand);
            determineWinner();

            // 是否繼續遊戲
            System.out.println("是否繼續遊戲？(Y/N)");
            String choice = scanner.next().toUpperCase();
            if (choice.equals("N")) {
                break;
            }
        }

        if (playerChips <= 0) {
            System.out.println("你的籌碼已經用完，遊戲結束！");
        } else {
            System.out.println("遊戲結束！你剩餘的籌碼為：" + playerChips);
        }
    }
}
