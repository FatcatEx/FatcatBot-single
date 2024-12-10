/*****   學號：413226178、413226271         *****/
/*****   姓名：楊茗翔、簡稔祖         *****/


import java.util.*;

public class GameRoom implements Room {
    private static final int MIN_PLAYERS = 2;
    private static final int MAX_PLAYERS = 4;
    private final List<String> players; // 儲存玩家名稱
    private boolean isGameActive;

    public GameRoom() {
        this.players = new ArrayList<>();
        this.isGameActive = false;
    }

    // 加入房間
    @Override
    public boolean joinRoom(String playerName) {
        if (isFull()) {
            System.out.println("房間已滿，無法加入玩家：" + playerName);
            return false;
        }
        players.add(playerName);
        System.out.println(playerName + " 加入了房間！");
        return true;
    }

    // 離開房間
    @Override
    public boolean leaveRoom(String playerName) {
        if (players.remove(playerName)) {
            System.out.println(playerName + " 離開了房間！");
            return true;
        } else {
            System.out.println("玩家不在房間內！");
            return false;
        }
    }

    // 檢查是否可以開始遊戲
    public boolean canStartGame() {
        return players.size() >= MIN_PLAYERS && !isGameActive;
    }

    public boolean isFull() {
        return players.size() >= MAX_PLAYERS;
    }

    public boolean isExist(String playerName) {
        return players.contains(playerName);
    }

    // 開始遊戲
    public void startGame(String gameType) {
        if (!canStartGame()) {
            System.out.println("無法開始遊戲！至少需要 " + MIN_PLAYERS + " 名玩家。");
            return;
        }

        isGameActive = true;
        System.out.println("遊戲開始！遊戲類型：" + gameType);

        // 開始指定類型的遊戲
        if (gameType.equalsIgnoreCase(".blackjack")) {
            new Blackjack(players).play();
        } else if (gameType.equalsIgnoreCase(".texas")) {
            new Texas(players).play();
        }

        isGameActive = false;
    }

    // 獲取房間狀態
    public void displayRoomStatus() {
        System.out.println("當前房間玩家：" + players);
        System.out.println("遊戲進行中：" + isGameActive);
    }
}
