package Service;

import Model.Statistic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class StatisticService {
    private ArrayList<Statistic> statistics = new ArrayList<>();

    public ArrayList<Statistic> getStatistics(){
        Collections.sort(statistics);
        return statistics;
    }

    public void addToStatistic(String nick, int win){
        statistics.add(new Statistic(nick,win));
    }
}
