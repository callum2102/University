import java.util.Random;

public class CustomerRequest {
    public int request; //The indication of which request the customer have
    public float amountToChange;//For open account, save and withdraw money task
    public String id;//Input manually in the main class for bank user database.
    public final Random rand;

    /**
     * Generate a virtual customer with one specific request
     * The request index numbers are:
     * 0: open a new account
     * 1: close the account
     * 2: check balance
     * 3: save money
     * 4: withdraw money
     */

    CustomerRequest(){
        request = 0;
        amountToChange = 0;
        rand=new Random();
    }

    public void newRequest() {
        //Generate a random amount of money (0.1~10.1) to change (only for open/save/withdraw)
        float random = (float) (rand.nextDouble()*10+0.1);

        //Generate a number for random tasks between 0-99
        int rand_int = rand.nextInt(100);

        //10% of the tasks are "open accounts"
        if (rand_int>=0 && rand_int<10)      {Main.currentRequest.request = 0; Main.currentRequest.amountToChange= (float) (Math.round((random*100)*100.0)/100.0);}

        //5% of the tasks are "close accounts"
        if (rand_int>=10 && rand_int<15)    {Main.currentRequest.request = 1; Main.currentRequest.amountToChange= 0.0F;}

        //35% of the tasks are "check balance"
        if (rand_int>=15 && rand_int<50)    {Main.currentRequest.request = 2; Main.currentRequest.amountToChange= 0.0F;}

        //25% of the tasks are "save money"
        if (rand_int>=50 && rand_int<75)    {Main.currentRequest.request = 3; Main.currentRequest.amountToChange= (float) (Math.round((random)*100.0)/100.0);}

        //25% of the tasks are "withdraw money"
        if (rand_int>=75 && rand_int<=100)   {Main.currentRequest.request = 4; Main.currentRequest.amountToChange= (float) (Math.round((random)*100.0)/100.0);}

    }
}

