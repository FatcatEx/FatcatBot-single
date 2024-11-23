
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Alarm {
     private long delayInSeconds;// 距離鬧鐘設定時間的秒數
     private final String[] AlarmName = new String[100];// 鬧鐘的名字
     private int Alarmnum = 0;// 目前指向的鬧鐘編號
     private final List<Thread> virtualThreads = new ArrayList<>();
     String path = "D:/LineBotAlarm/AlarmSet";
     
     //在打開linebot時讀取之前的鬧鐘信息
     public void setAlarm() throws IOException{
      // 時間格式
      DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm");

       //檢查是否存在對應鬧鐘TXT，若存在，讀取其中參數並設定鬧鐘
       for(int i = 0;i < 100;i++){
         String iString = i + "";
         Path Alarmpath = Paths.get(path + iString + ".txt");// 存儲鬧鐘信息的地方

      if(Files.exists(Alarmpath)){
            // 将整个文件内容读取为字串後分別讀取名字和目標時間
            String check = Files.readString(Paths.get(path + iString + ".txt"));
            String[] infoStr = check.split(",");
            String inputTime;
            AlarmName[i] = infoStr[0];
            inputTime = infoStr[1];
            Alarmnum = i;
            LocalDateTime alarmTime = LocalDateTime.parse(inputTime, formatter);
            LocalDateTime now = LocalDateTime.now();

        if(alarmTime.isBefore(now)){
         Files.delete(Alarmpath);
         System.out.println("鬧鐘 " + AlarmName[i] + " 已超時！");
         break;
        }
        delayInSeconds = ChronoUnit.SECONDS.between(now, alarmTime);
        StartAlarm(inputTime);
      }
      }

     }

      private void StartAlarm(String inputTime) {
         try {
             String AlarmnumString = Alarmnum + "";
             Path Alarmpath = Paths.get(path + AlarmnumString + ".txt");// 存儲鬧鐘信息的地方
             StringBuilder note = new StringBuilder();// 暫存TXT內容
             
             // 如果沒有對應路徑和TXT則創建
             if(!Files.exists(Alarmpath.getParent())){
                 Files.createDirectories(Alarmpath.getParent());
             }
             if(!Files.exists(Alarmpath)){
                 Files.createFile(Alarmpath);
             }
             note.append(AlarmName[Alarmnum]).append(",");
             note.append(inputTime);
             Files.write(Alarmpath, note.toString().getBytes());
             System.out.println(delayInSeconds);

             // 啟動虛擬線程來並行運行鬧鐘的時間檢查
             Thread vt = Thread.ofVirtual().start(() -> {
               // 開啟鬧鐘
            int recentAlarm = Alarmnum;
            ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
            scheduler.schedule(() -> {
               System.out.println(AlarmName[recentAlarm] + " 鬧鐘時間到了。");
               AlarmName[recentAlarm] = null;
               scheduler.shutdown();
            }, delayInSeconds, TimeUnit.SECONDS);
              }
              );
              virtualThreads.add(vt);
         } catch (IOException ex) {
         }
      }
   
   

      
     public int CreateAlarm() {
        // 接受鬧鐘時間輸入並設定參數
        Scanner scanner = new Scanner(System.in);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm");
        System.out.println("請輸入鬧鐘的名稱，在時間到了之後會顯示鬧鐘的名稱");
        for(Alarmnum = 0;Alarmnum < 100;Alarmnum++){
        if(AlarmName[Alarmnum] == null){
           AlarmName[Alarmnum] = scanner.nextLine();
           break;
        }
     }
        System.out.println("請輸入鬧鐘時間 (格式: yyyy/MM/dd HH:mm)：");
        String inputTime = scanner.nextLine();
        LocalDateTime alarmTime = LocalDateTime.parse(inputTime, formatter);
        LocalDateTime now = LocalDateTime.now();

        if(alarmTime.isBefore(now)){
          System.out.println("鬧鐘時間不能在當前時間之前！");
          return 0;
        }
        delayInSeconds = ChronoUnit.SECONDS.between(now, alarmTime);
        StartAlarm(inputTime);
        System.out.println("鬧鐘創建成功！");
        return 0;
     }

}
