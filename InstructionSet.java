/*****   學號：413226178、413226271         *****/
/*****   姓名：楊茗翔、簡稔祖         *****/

import java.io.IOException;

public class InstructionSet {
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
        ".calarm"
    };
 
    public String[] HelpIntrodution = {
        "擲骰指令 .rx (x為骰子面數，例如 .r6 將生成1-6之間的隨機數)",
        "輪盤指令 .rrx#y#z (x, y, z為事件名，例如 .rr吃飯#睡覺#玩遊戲)",
        "新建記事本 .cnote (創建一個新的記事本)",
        "刪除記事本 .dnote (刪除現有記事本)",
        "查看記事本 .note (顯示指定記事內容)",
        "加入遊戲房間 .jroom (加入指定的遊戲房間)",
        "離開遊戲房間 .eroom (退出當前遊戲房間)",
        "開始21點 (需要在房間內) .blackjack",
        "開始德州撲克 (需要在房間內) .texas",
        "查詢指令集 .help (顯示所有可用指令的詳細說明)",
        "新建鬧鐘 .calarm (新建一個鬧鐘)",
    };
 
    private final RandomGenerator randomGenerator = new RandomGenerator();
    private final Note note = new Note();
    private final Alarm alarm = new Alarm();

    public void botSet() throws IOException{
        alarm.setAlarm();
    }
 
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
             
             }
             
             case 6 -> {
             
             }
             
             case 7 -> {
                System.out.println("21點遊戲即將開始...");
                 new Blackjack().play();
             }
             
             case 8 -> {
                System.out.println("德州撲克遊戲即將開始...");
                new Texas().play();    
             }
             
             case 9 -> {
                System.out.println("请使用以下指令操作Bot:");
                 for (String intro : HelpIntrodution) {
                     System.out.println(intro);
                 }
             }

             case 10 -> {
                new Alarm().CreateAlarm();
             }
             

             default -> System.out.println(" 無效指令，請使用 .help 查看可用指令。");
         }
     }
 }
 

