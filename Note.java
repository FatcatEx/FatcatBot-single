import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

public class Note extends Alarm {
    String path = "D:/LineBotTXT/";
    public String CreateTXT(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("請輸入要創建的筆記名。");
        String TXTname = scanner.nextLine();
        String inputline;
        StringBuilder note = new StringBuilder();
        Path TXTpath = Paths.get(path + TXTname + ".txt");
        try{
            if(!Files.exists(TXTpath.getParent())){
               Files.createDirectories(TXTpath.getParent());
            }
            if(Files.exists(TXTpath)){
                Files.createFile(TXTpath);
            }
                System.out.println("請輸入筆記內容,並且在不需要輸入內容後輸入單個空格");
                while(true){
                inputline = scanner.nextLine();
                if(inputline.equals(" ")){
                    break;
                }
                note.append(inputline).append("\n");
            }
                Files.write(TXTpath, note.toString().getBytes());
                return "筆記創建/修改成功！";
        }catch(IOException e){
            return "筆記創建/修改失敗！錯誤原因： "+ e.getMessage();
        }
        
    }

    public String DeleteTXT(){
        Path TXTpath;
        Scanner scanner = new Scanner(System.in);
        System.out.println("请输入要删除的笔记名。");
        String TXTname = scanner.nextLine().trim().toLowerCase();
        TXTpath = Paths.get(path + TXTname + ".txt");
        try{
            if(!Files.exists(TXTpath)){
               return "不存在筆記！";
            }
                Files.delete(TXTpath);
                return "筆記刪除成功！";
        }catch(IOException e){
            return "筆記刪除失敗！錯誤原因： "+ e.getMessage();
        }
        
    }
    
    public String ReadTXT(){
        Path TXTpath;
        Scanner scanner = new Scanner(System.in);
        System.out.println("请输入要查看的笔记名。");
        String TXTname = scanner.nextLine().trim().toLowerCase();
        TXTpath = Paths.get(path + TXTname + ".txt");
        
        try{
            if(!Files.exists(TXTpath)){
               return "不存在筆記！";
            }
                Files.lines(TXTpath).forEach(System.out::println);
                return "筆記查看成功！";
        }catch(IOException e){
            return "筆記查看失敗！錯誤原因： "+ e.getMessage();
        }
    }
}
