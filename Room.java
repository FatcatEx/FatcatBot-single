interface Room {
     boolean joinRoom(String playerName);
     boolean leaveRoom(String playerName);
     void startGame(String gameType);
     public void displayRoomStatus();
}
