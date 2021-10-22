package Service;

public class PrizeService {
    private int sumOfPrize[] = new int[12];
    private int winner = 0;

    public PrizeService(){
        initSumOfPrize();
    }

    private void initSumOfPrize(){
        sumOfPrize[0] = 500;
        sumOfPrize[1] = 1000;
        sumOfPrize[2] = 2000;
        sumOfPrize[3] = 5000;
        sumOfPrize[4] = 10000;
        sumOfPrize[5] = 20000;
        sumOfPrize[6] = 40000;
        sumOfPrize[7] = 75000;
        sumOfPrize[8] = 125000;
        sumOfPrize[9] = 250000;
        sumOfPrize[10] = 500000;
        sumOfPrize[11] = 1000000;
    }

    public int getWinner(int number){
        return sumOfPrize[number];
    }
}
