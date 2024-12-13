/*****   學號：413226178、413226271         *****/
/*****   姓名：楊茗翔、簡稔祖         *****/


import java.io.IOException;
import java.util.Scanner;

public class LineBot extends InstructionSet {
    public static void main(String[] args) {
        System.out.println("歡迎使用 LineBot 系統!");
        LineBot linebot = new LineBot();
        boolean botRun = true;
        try {
            linebot.botSet();
        } catch (IOException e) {
        }

        //循環執行以接受多次指令
        while (botRun) {
            linebot.handleInputEvent();
        }
    }

    //處理輸入指令
    public void handleInputEvent() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("請輸入指令: ");
        String message = scanner.nextLine().trim().toLowerCase();
        ReceiveInstruction(message);
    }
}
