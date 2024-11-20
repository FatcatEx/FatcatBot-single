/*****   學號：413226178、413226271         *****/
/*****   姓名：楊茗翔、簡稔祖         *****/


import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

public class RandomGenerator {

    // 擲骰子功能，根據給定的面數生成隨機結果
    public String Roll(String message) {
        // 分割訊息，使用 "r" 分割為兩部分
        String[] parts = message.split("r");

        // 檢查格式是否正確，應為 ".r6" 的形式
        if (parts.length != 2 || !parts[1].matches("\\d+")) {
            return " 格式錯誤！使用例：.r6"; // 如果格式錯誤，返回提示
        }

        // 解析骰子的面數
        int sides = Integer.parseInt(parts[1]);
        
        // 使用 ThreadLocalRandom 生成隨機數字，範圍是 1 到 sides（包括 sides）
        int result = ThreadLocalRandom.current().nextInt(1, sides + 1);
        
        // 返回擲骰結果
        return String.valueOf(result);
    }

    // 加權隨機選擇功能，根據給定的事件和對應的權重進行隨機選擇
    public String weightedRandomSelection(String message) {
        // 移除前綴，獲取事件字符串並去除空格
        message = message.substring(3).trim();
        
        // 分割事件字符串，使用 "#" 分隔不同事件
        String[] events = message.split("#");

        // 檢查事件數量是否為空，若為空則提示錯誤
        if (events.length == 0) {
            return " 錯誤的輸入！請提供事件選項。";
        }

        // 提示用戶輸入每個事件的權重
        Scanner scanner = new Scanner(System.in);
        System.out.print("請輸入各事件權重（以,隔開）: ");
        String[] weightsStr = scanner.nextLine().split(",");

        // 檢查權重數量是否與事件數量相符
        if (weightsStr.length != events.length) {
            return " 事件數量與權重數量不符!";
        }

        // 定義權重陣列，並計算權重的總和
        int[] weights = new int[events.length];
        int totalWeight = 0;

        // 嘗試將輸入的權重轉換為數字並計算總權重
        try {
            for (int i = 0; i < weightsStr.length; i++) {
                weights[i] = Integer.parseInt(weightsStr[i]);
                totalWeight += weights[i];
            }
        } catch (NumberFormatException e) {
            return " 權重格式錯誤，請輸入有效數字！"; // 若格式錯誤，返回錯誤提示
        }

        // 生成一個隨機權重值，範圍是 1 到總權重
        int randomWeight = ThreadLocalRandom.current().nextInt(totalWeight) + 1;
        int cumulativeWeight = 0;

        // 根據隨機權重值選擇對應的事件
        for (int i = 0; i < weights.length; i++) {
            cumulativeWeight += weights[i];
            if (randomWeight <= cumulativeWeight) {
                return "決策結果：" + events[i]; // 返回選中的事件
            }
        }

        return " 決策失敗，請檢查輸入！"; // 如果未選中任何事件，返回錯誤提示
    }
}

