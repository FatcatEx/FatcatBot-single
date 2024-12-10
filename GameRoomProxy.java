/*****   學號：413226178、413226271         *****/
/*****   姓名：楊茗翔、簡稔祖         *****/


public class GameRoomProxy implements Room{

    GameRoom gameroom = new GameRoom();

    @Override
    public boolean joinRoom(String playerName) {
        if (gameroom.isFull()){
            System.out.println("房間已滿，無法加入！");
            return false;
        }
        if (gameroom.isExist(playerName)) {
            System.out.println("玩家已經在房間內！");
            return false;
        }
        return gameroom.joinRoom(playerName);
    }

    @Override
    public boolean leaveRoom(String playerName) {
       if (!gameroom.isExist(playerName)){
        System.out.println("玩家不在房間內！");
        return false;
       }
       return gameroom.leaveRoom(playerName);
    }

    @Override
    public void startGame(String gameType) {
        if (!gameroom.canStartGame()) {
            System.out.println("玩家數不足或遊戲已經在進行中！");
        }
        gameroom.startGame(gameType);
    }

    @Override
    public void displayRoomStatus() {
        gameroom.displayRoomStatus();
    }

}