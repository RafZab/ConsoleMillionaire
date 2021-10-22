package Model;

import java.util.Date;

public class Statistic {
    private Date date;
    private int win;
    private String nick;

    public Statistic(String nick, int win){
        this.date = new Date();
        this.win = win;
        this.nick = nick;
    }

    public String getNick(){
        return nick;
    }

    public int getWin(){
        return win;
    }

    public Date getDate(){
        return date;
    }

}
