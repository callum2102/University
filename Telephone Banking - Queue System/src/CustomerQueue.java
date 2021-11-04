import java.util.Random;

public class CustomerQueue {

    private int queueIndex = 0;
    private int queueSize = 20;
    private CustomerData.Customer[] queue = new CustomerData.Customer[queueSize];

    public CustomerData.Customer popFromQueue() {
        // Take index 0 from the array and save to popCustomer
        CustomerData.Customer popCustomer = queue[0];
        // Recreate Array without index 0
        for(int i = 0; i < queueIndex; i++) {
            queue[i] = queue[i+1];
            }
        queueIndex--;
        return popCustomer;
    }

    public void ListQueue() {
        for(int i = 0; i < queueIndex; i++) {
//            CustomerData.Customer listCustomer = queue[i];
            System.out.println((i+1)+" "+queue[i].customerID + " £" + queue[i].customerBalance);
        }
    }

    public int getQueueIndex() {
        return queueIndex;
    }

    public void randomQueue() {
        if (queueSize == queueIndex) { // If array is full, recreate the array with extra 20% space
            // Create a temp store for the current array
            CustomerData.Customer[] tempData = queue;
            // Recreate loadedData with a larger array size (20%)
            queueSize = queueSize + queueSize/5;
            queue = new CustomerData.Customer[queueSize];
            // Load the new array with customer data
            for (int i = 0; i < queueIndex; i++) {
                queue[i] = tempData[i];
            }
            // Select a random int 0-9999
            Random rand = new Random();
            int rand_int = rand.nextInt(10000);
            // Use random int to select a customer to queue
            CustomerData.Customer pushCustomer = Main.userData.selectRandomCustomer(rand_int);
            // Get customer request
            pushCustomer.request = new CustomerRequest();
            // Add customer to the queue
            queue[queueIndex] = pushCustomer;
        } else{
            // Select a random int 0-99
            Random rand = new Random();
            int rand_int = rand.nextInt(100);
            // Use random int to select a customer to queue
            CustomerData.Customer pushCustomer = Main.userData.selectRandomCustomer(rand_int);
            // Get customer request
            pushCustomer.request = new CustomerRequest();
            // Add customer to the queue
            queue[queueIndex] = pushCustomer;
        }
        System.out.println("Customer " + queue[queueIndex].customerID + " added to queue.");
        queueIndex++;
    }

}

/*
    public void pushToQueue(CustomerData.Customer user) { // Couldn't get push via userID to work in main
        // Get customer request
        user.request = new CustomerRequest();
        // Add customer to the queue
        queue[queueIndex] = user;
        System.out.println("Customer "+queue[queueIndex].customerID+" added to queue.");
        queueIndex++;
    }
 */