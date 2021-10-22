package Model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class Statistic implements Comparable<Statistic>{
    private String date;
    private int win;
    private String nick;

    public Statistic(String nick, int win){
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        this.date = fmt.format(LocalDateTime.now());
        this.win = win;
        this.nick = nick;
    }

    public String getNick(){
        return nick;
    }

    public int getWin(){
        return win;
    }

    public String getDate(){
        return date;
    }

    @Override
    public int compareTo(Statistic compareStat) {
        int compareQuantity = ((Statistic) compareStat).getWin();

        //ascending order
        return compareQuantity - this.getWin();
    }
}
