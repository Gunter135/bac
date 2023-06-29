import java.io.File;
import java.io.FileWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class Game {
    public static void main(String[] args) {
        game();
    }
    public static void game(){
        String input_num;
        Random random = new Random();
        StringBuilder stringBuilder = new StringBuilder();
        boolean game_ended = true;
        int game_number = 0;
        String last_game_num = "";
        try{
            File file = new File("result.txt");
            Scanner sc = new Scanner(file);
            while (sc.hasNextLine()) {
                game_ended = false;
                String str = sc.nextLine();
                String[] arr = str.split(" ");
                if(arr[0].equals("Game")){
                    game_number = Integer.parseInt(String.valueOf(arr[1].charAt(1)));
                    last_game_num = arr[6];
                }
                if(arr[0].equals("Строка")){
                    game_ended = true;
                }
            }
        }catch(Exception e){System.out.println(e);}
        if(game_ended){
            for (int i = 0; i < 4;i++){
                stringBuilder.append(random.nextInt(0,10));
            }
            try{
                LocalDateTime localDateTime = LocalDateTime.now();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
                String formattedDate = localDateTime.format(formatter);
                FileWriter fw = new FileWriter("result.txt",true);
                fw.write("Game №"+(game_number+1)+" "+formattedDate + " " + "Загаданная строка " + stringBuilder + "\n");
                fw.close();
            }catch(Exception e){System.out.println(e);}
        }else {
            stringBuilder.append(last_game_num);
        }
        System.out.println(stringBuilder);
        boolean flag = true;
        while(flag){
            Scanner sc = new Scanner(System.in);
            input_num = sc.nextLine();
            int bulls = 0;
            int cows = 0;
            StringBuilder temp = new StringBuilder();
            StringBuilder temp_input = new StringBuilder(input_num);
            for (int i = 0; i < 4;i++){
                char n = stringBuilder.charAt(i);
                char x = input_num.charAt(i);
                if (n == x){
                    bulls++;
                    temp.append("x");
                    temp_input.setCharAt(i,'y');
                }else {
                    temp.append(n);
                }
            }
            for (int i = 0; i < 4;i++) {
                char x = temp_input.charAt(i);
                String substr = temp.substring(0);
                if (substr.contains(Character.toString(x))) {
                    cows++;
                    temp.setCharAt(substr.indexOf(x),'x');
                }
            }
            StringBuilder res = new StringBuilder();
            if (cows == 1) {
                res.append(cows);
                res.append(" корова ");
            } else if (cows!= 0) {
                res.append(cows);
                res.append(" коровы ");
            }
            if (bulls == 1) {
                res.append(bulls);
                res.append(" бык ");
            } else if (bulls!= 0){
                res.append(bulls);
                res.append(" быка ");
            }

            try{
                FileWriter fw = new FileWriter("result.txt",true);
                fw.write("Запрос: " + input_num + " Ответ: " + res + "\n");
                fw.close();
            }catch(Exception e){System.out.println(e);}
            System.out.println(res);
            if (bulls == 4){
                flag = false;
                int attempts = 0;
                try{
                    File file = new File("result.txt");
                    Scanner sc1 = new Scanner(file);
                    while (sc1.hasNextLine()) {
                        String str = sc1.nextLine();
                        String[] arr = str.split(" ");
                        if(arr[0].equals("Запрос:")){
                            attempts++;
                        }else if (arr[0].equals("Game")) {
                            attempts = 0;
                        }
                    }
                    FileWriter fw = new FileWriter("result.txt",true);
                    if (attempts%10 == 1)
                        fw.write("Строка была угадана за: " + attempts + " попытку. " +" \n");
                    else if (attempts%10 < 5 && attempts!=11 && attempts!=12 && attempts!=13 && attempts!=14){
                        fw.write("Строка была угадана за: " + attempts + " попытки. " +" \n");
                    }else {
                        fw.write("Строка была угадана за: " + attempts + " попыток. " +" \n");
                    }
                    fw.close();
                }catch(Exception e){System.out.println(e);}
            }

        }
    }
}
