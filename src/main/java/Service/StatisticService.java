package Service;

import Model.Statistic;

import java.util.ArrayList;

public class StatisticService {
    private ArrayList<Statistic> statistics = new ArrayList<>();

    public ArrayList<Statistic> getStatistics(){
        return statistics;
    }

    public void addToStatistic(String nick, int win){
        statistics.add(new Statistic(nick,win));
    }
}
