/*****   學號：413226178、413226271         *****/
/*****   姓名：楊茗翔、簡稔祖         *****/

import java.io.IOException;
import java.util.Scanner;


public class InstructionSet {

    //指令集
    public String[] Instruction = {
        ".r",
        ".rr",
        ".cnote",
        ".dnote",
        ".note",
        ".jroom",
        ".eroom",
        ".blackjack",
        ".texas",
        ".help",
        ".calarm",
        ".botoff"
    };
 
    //指令集說明
    public String[] HelpIntrodution = {
        "擲骰指令 .rx (x為骰子面數，例如 .r6 將生成1-6之間的隨機數)",
        "輪盤指令 .rrx#y#z (x, y, z為事件名，例如 .rr吃飯#睡覺#玩遊戲)",
        "新建記事本 .cnote (創建一個新的記事本)",
        "刪除記事本 .dnote (刪除現有記事本)",
        "查看記事本 .note (顯示當前所有記事內容)",
        "加入遊戲房間 .jroom (加入指定的遊戲房間)",
        "離開遊戲房間 .eroom (退出當前遊戲房間)",
        "開始21點 (需要在房間內) .blackjack",
        "開始德州撲克 (需要在房間內) .texas",
        "查詢指令集 .help (顯示所有可用指令的詳細說明)",
        "新建鬧鐘 .calarm (新建一個鬧鐘)",
        "關閉機器人 .botoff (關閉程序)"
    };
 
    private final RandomGenerator randomGenerator = new RandomGenerator();
    private final Note note = new Note();
    private final GameRoomProxy gameRoom = new GameRoomProxy();

    public void botSet() throws IOException{
        Alarm alarm = Alarm.getInstance();
        alarm.setAlarm();
    }

    //接受指令後比對指令集內容，分別處理。
    public void ReceiveInstruction(String message) {
         int function = -1;
         
 
         for (int i = 0; i < Instruction.length; i++) {
             if (message.startsWith(Instruction[i])) {
                 function = i;
             }
         }
         
         switch (function) {
             case 0 -> {
                System.out.println(" 擲骰結果：" + randomGenerator.Roll(message));
             }
             case 1 -> {
                System.out.println(" 輪盤結果：" + randomGenerator.weightedRandomSelection(message));
             }
             case 2 -> {
                    System.out.println(note.CreateTXT());
             }
             
             case 3 -> {
                System.out.println(note.DeleteTXT());
             }
             
             case 4 -> {
                System.out.println(note.ReadTXT());
             }
             
             case 5 -> {
                System.out.println("請輸入你的玩家名稱：");
                String playerName = new Scanner(System.in).nextLine();
                gameRoom.joinRoom(playerName);
                gameRoom.displayRoomStatus();
            }
            
            case 6 -> {
                System.out.println("請輸入你的玩家名稱：");
                String playerName = new Scanner(System.in).nextLine();
                gameRoom.leaveRoom(playerName);
                gameRoom.displayRoomStatus();
            }
             
            case 7 -> {
                System.out.println("21點遊戲...");
                gameRoom.startGame(".blackjack");
            }
             
            case 8 -> {
                System.out.println("德州撲克遊戲...");
                gameRoom.startGame(".texas");  
            }
             
            case 9 -> {
                System.out.println("请使用以下指令操作Bot:");
                 for (String intro : HelpIntrodution) {
                     System.out.println(intro);
                }
            }

            case 10 -> {
                Alarm alarm = Alarm.getInstance();
                alarm.CreateAlarm();
            }

            case 11 ->{
                System.exit(0);
            }
            
            default -> System.out.println(" 無效指令，請使用 .help 查看可用指令。");
         }
     }
 }
 
