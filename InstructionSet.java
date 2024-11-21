


public class InstructionSet {
    public String[] Instruction = {
        ".r",
        ".rr",
        ".create note",
        ".delete note",
        ".note",
        ".create room",
        ".join room",
        ".exit room",
        ".blackjack",
        ".texas",
        ".help"
    };
 
    public String[] HelpIntrodution = {
        "擲骰指令 .rx (x為骰子面數，例如 .r6 將生成1-6之間的隨機數)",
        "輪盤指令 .rrx#y#z (x, y, z為事件名，例如 .rr吃飯#睡覺#玩遊戲)",
        "新建記事本 .create note (創建一個新的記事本)",
        "刪除記事本 .delete note (刪除現有記事本)",
        "查看記事本 .note (顯示當前所有記事內容)",
        "創建遊戲房間 .create room (建立新的遊戲房間)",
        "加入遊戲房間 .join room (加入指定的遊戲房間)",
        "離開遊戲房間 .exit room (退出當前遊戲房間)",
        "開始21點 (需要在房間內) .blackjack",
        "開始德州撲克 (需要在房間內) .texas",
        "查詢指令集 .help (顯示所有可用指令的詳細說明)"
    };
 
    private RandomGenerator randomGenerator = new RandomGenerator();
    
 
    public void ReceiveInstruction(String message) {
         int function = -1;
 
         for (int i = 0; i < Instruction.length; i++) {
             if (message.startsWith(Instruction[i])) {
                 function = i;
             }
         }
 
         switch (function) {
             case 0 -> {
                 String result = randomGenerator.Roll(message);
                 System.out.println(" 擲骰結果：" + result);
             }
             case 1 -> {
                 String decision = randomGenerator.weightedRandomSelection(message);
                 System.out.println(" 輪盤結果：" + decision);
             }
             case 2 -> {

             }
             
             case 3 -> {
             
             }
             
             case 4 -> {
             
             }
             
             case 5 -> {
             
             }
             
             case 6 -> {
             
             }
             
             case 7 -> {
             
             }
             
             case 8 -> {
                 System.out.println("21點遊戲即將開始...");
                 new Blackjack().play();
             }
             
             case 9 -> {
                System.out.println("德州撲克遊戲即將開始...");
                new Texas().play();
             }
             
             case 10 -> {
                 System.out.println("请使用以下指令操作Bot:");
                 for (String intro : HelpIntrodution) {
                     System.out.println(intro);
                 }
             }
             default -> System.out.println(" 無效指令，請使用 .help 查看可用指令。");
         }
     }
 }
 

