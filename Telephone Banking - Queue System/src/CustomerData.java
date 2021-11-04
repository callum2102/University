import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class CustomerData {
    // Initial number of customers
    private int numberOfCustomers = 10000;
    private int arraySize = 10000;
    // Initialise the array
    private Customer[] loadedData = new Customer[arraySize];

    // Customer Object
    public static class Customer {
        int indexID; // Included indexID so that don't need to search loadedData by userID, just update via indexID which is much faster
        String customerID;
        float customerBalance;
        CustomerRequest request;
        // Constructor
        Customer(int iID, String cID, float balance){
            indexID = iID;
            customerID = cID;
            customerBalance = balance;
            request = null;
        }
    }

    CustomerData() throws FileNotFoundException {
        try (Scanner scanner = new Scanner(new File("BankUserDataset10K.csv"))) {

            int currentIndex = 0;
            scanner.useDelimiter("\r\n");
            while(scanner.hasNext()){
                String currentLine[] = scanner.next().split(",");
                String currentID = currentLine[0];
                float currentBalance = Float.parseFloat(currentLine[1]);

                System.out.println("Loading..."+currentID+"\t£"+currentBalance);

                // Load the currentID and currentBalance into the userData array at the currentIndex
                loadedData[currentIndex] = new Customer(currentIndex, currentID, currentBalance);
                System.out.println(loadedData[currentIndex]);
                currentIndex++;

            }
        }

        catch (FileNotFoundException e){
            System.err.println(e.getLocalizedMessage());
        }
    }

    public void addNewCustomer(String userID, float balance) {
        if (numberOfCustomers == arraySize) { // If array is full, recreate the array with extra 20% space
            // Create a temp store for the current array
            Customer[] tempData = loadedData;
            // Recreate loadedData with a larger array size (20%)
            arraySize = numberOfCustomers + numberOfCustomers / 5;
            loadedData = new Customer[arraySize];
            // Load the new array with customer data
            for (int i = 0; i < numberOfCustomers; i++) {
                loadedData[i] = tempData[i];
            }
            // Add the new customer to the array
            loadedData[numberOfCustomers] = new Customer(numberOfCustomers, userID, balance);
            System.out.println("Customer added.\n" + loadedData[numberOfCustomers].customerID + " " + loadedData[numberOfCustomers].customerBalance);
            numberOfCustomers++;
        } else {
            // Add the new customer to the array
            loadedData[numberOfCustomers] = new Customer(numberOfCustomers, userID, balance);
            System.out.println("Customer added.\n" + loadedData[numberOfCustomers].customerID + " £" + loadedData[numberOfCustomers].customerBalance);
            numberOfCustomers++;
        }
    }

    public void deleteCustomer(Customer toDelete) {
        // Loop to copy elements after indexID to the previous index
        for(int i = toDelete.indexID; i < numberOfCustomers; i++) {
            loadedData[i] = loadedData[i+1];
        }
        numberOfCustomers--;
        System.out.println("Withdrawn £"+toDelete.customerBalance+"\nRecord "+toDelete.customerID+" deleted.");
    }

    public void updateBalance(int indexID, float newBalance) {
        loadedData[indexID].customerBalance = newBalance;
    }

    public void showBalance(int indexID) {
        System.out.println(loadedData[indexID].customerID + "'s balance is now £" + loadedData[indexID].customerBalance);
    }

    public Customer selectRandomCustomer(int rand_int) {
        return loadedData[rand_int];
    }

    public boolean duplicateCheck(String userID) {
        for(int i =0; i < numberOfCustomers; i++) {
            if(userID == loadedData[i].customerID) {return true;}
        }
        return false;
    }

    public Customer getUserInfo(String userID) {
        for(int i = 0; i < numberOfCustomers; i++) {
            if(userID == loadedData[i].customerID) {
                return loadedData[i];
            }
        }
        return null;
    }
}

/*
###### Original code for addNewCustomer and deleteCustomer. I originally rebuilt the array with either an added or removed data point and kept the array maxed out.
###### I have now decided to initialise the array with a larger size. This means that the delete function will only need to move the records that have a higher index down
###### And the addNewCustomer function won't need to recreate the DB with an additional space every time a new user is added.
###### This is because the functions would've been resource consuming with the 10k file, space is cheap so this is a better way to do it.
###### A LinkedList may have been a good choice for the data structure, however I think grabbing the data by the indexID may be better than searching through the LinkedList.
###### A LinkedList would be more efficient for deleting records

    public void addNewCustomer(String userID, float balance) {
        // Temporarily store the current DB
        Customer[] tempData = loadedData;
        // Create an array with an additional data point
        loadedData = new Customer[numberOfCustomers+1];
        // Load the new array with customer data
        for(int i = 0; i <= numberOfCustomers-1; i++) {
            loadedData[i] = tempData[i];
        }
        // Add the new customer to the new array
        loadedData[numberOfCustomers] = new Customer(numberOfCustomers, userID, balance);
        System.out.println("Customer added.\n" + loadedData[numberOfCustomers].customerID + " " + loadedData[numberOfCustomers].customerBalance);
        numberOfCustomers++;
    }

    public void deleteCustomer(int indexID) {
        // Temporarily store the current DB
        Customer[] tempData = loadedData;
        // Create an array with one less data point
        loadedData = new Customer[numberOfCustomers-1];

        // Loop to copy elements before indexID
        for(int i = 0; i < indexID; i++) {
            loadedData[i] = tempData[i];
        }
        // Loop to copy elements after indexID
        for(int i = indexID +1; i < numberOfCustomers; i++) {
            loadedData[i-1] = tempData[i];
        }
        numberOfCustomers--;
    }
 */
