/*****   學號：413226178、413226271         *****/
/*****   姓名：楊茗翔、簡稔祖         *****/


import java.util.*;

public class GameRoom {
    private static final int MIN_PLAYERS = 2;
    private static final int MAX_PLAYERS = 4;
    private final List<String> players; // 儲存玩家名稱
    private boolean isGameActive;

    public GameRoom() {
        this.players = new ArrayList<>();
        this.isGameActive = false;
    }

    // 加入房間
    public synchronized boolean joinRoom(String playerName) {
        if (players.size() >= MAX_PLAYERS) {
            System.out.println("房間已滿，無法加入！");
            return false;
        }
        if (players.contains(playerName)) {
            System.out.println("玩家已經在房間內！");
            return false;
        }
        players.add(playerName);
        System.out.println(playerName + " 加入了房間！");
        return true;
    }

    // 離開房間
    public synchronized boolean leaveRoom(String playerName) {
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

    // 開始遊戲
    public void startGame(String gameType) {
        if (!canStartGame()) {
            System.out.println("玩家數不足或遊戲已經在進行中！");
            return;
        }
        isGameActive = true;
        System.out.println("遊戲開始！遊戲類型：" + gameType);
        if (gameType.equalsIgnoreCase(".blackjack")) {
            new Blackjack(players).play();;
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

