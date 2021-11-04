import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * The telephone Banking System is an assignment for Algorithms and Data Structure 2
 * @author Jing Wang @ SHU
 * verison 1.1
 * Edited by - Callum Sumner 29032025
 */

public class Main {


    //State Machine parameters
    public enum State{START, STOP, WELCOME, LIST_ALL, PUSH, POP, TASK, REMOVE, NEW, SAVE, WITHDRAW, DISPLAY}
    public static State currentState = State.START;

    //Global variables
    public static Scanner inputOrder = new Scanner(System.in);
    public static Scanner inputID = new Scanner(System.in);
    public static Scanner inputUserID = new Scanner(System.in);

    public static CustomerQueue queue;
    public static CustomerData userData;
    public static CustomerRequest currentRequest = new CustomerRequest();
    private static CustomerData.Customer popCustomer;

    public static void main(String[] args) throws FileNotFoundException {
        while (currentState != State.STOP){
            switch (currentState){
                default: break;
                case START:     state_start();
                    break;
                case WELCOME:   state_welcome();
                    break;
                case LIST_ALL:  state_Q_listAll();
                    break;
                case PUSH:      state_Q_push();
                    break;
                case POP:       state_Q_pop();
                    break;
                case TASK:      state_task();
                    break;
                case REMOVE:    state_H_remove();
                    break;
                case NEW:       state_H_new();
                    break;
                case SAVE:      state_H_saveMoney();
                    break;
                case WITHDRAW:  state_H_reduceMoney();
                    break;
                case DISPLAY:   state_H_display();
                    break;
            }
        }

        clear();
    }

    private static void state_start() throws FileNotFoundException {
        System.out.println("System Initialization...");
        System.out.println("Load customer database...");
        userData = new CustomerData();
        System.out.println("Initializing queue...");
        queue = new CustomerQueue();
        currentState = State.WELCOME;
    }

    private static void state_welcome() {
        System.out.println("\n==Telephone Banking Control Centre==");
        System.out.println("Choose the index number from following options:\n1. Get the next customer\n2. Queueing a new customer\n3. Check current queue\n4. Quit");
        if (inputOrder.hasNext("1")) currentState = State.POP;
        if (inputOrder.hasNext("2")) currentState = State.PUSH;
        if (inputOrder.hasNext("3")) currentState = State.LIST_ALL;
        if (inputOrder.hasNext("4")) currentState = State.STOP;
        inputOrder.next();
    }

    private static void state_Q_listAll() {
        System.out.println("\nThere are "+ queue.getQueueIndex() + " customers in the queue:");

       if(queue.getQueueIndex() > 0) {
          queue.ListQueue();
       }
       pressAnyKeyToContinue();
       currentState = State.WELCOME;
    }

    private static void state_Q_push() { // See bottom for unsuccessful code. Using random user to show functionality.
        queue.randomQueue();
        currentState = State.WELCOME;
    }

    public static void state_Q_pop() {
        if(queue.getQueueIndex() == 0) {
            System.out.println("There are currently no customers in the queue");
            currentState = State.WELCOME;
        }
        else {
            popCustomer = queue.popFromQueue();
            currentRequest.newRequest();

            System.out.println("Current Customer: " + popCustomer.customerID);
            currentState = State.TASK;
        }
    }

    private static void state_task() {
        System.out.print("This customer want to ");
        switch (currentRequest.request){
            default: break;
            case 0: System.out.println("open a new account");
                System.out.println("Please input a new account ID:");
                currentRequest.id = inputID.nextLine();
                currentState = State.NEW;
                break;
            case 1: System.out.println("close the account");
                // Removed the input of account ID as the account ID is already known from the queue
                // Allows me to make changes to the account via indexID rather than searching the userID - much more efficient
                // As 85% of choices are balance changes/checks I decided this would be better than creating my own linkedlist to handle the userdata
                pressAnyKeyToContinue();
                currentState = State.REMOVE;
                break;
            case 2: System.out.println("check balance");
                pressAnyKeyToContinue();
                currentState = State.DISPLAY;
                break;
            case 3: System.out.println("save £"+currentRequest.amountToChange);
                pressAnyKeyToContinue();
                currentState = State.SAVE;
                break;
            case 4: System.out.println("withdraw £"+currentRequest.amountToChange);
                pressAnyKeyToContinue();
                currentState = State.WITHDRAW;
                break;

        }
    }

    private static void state_H_remove() {
        userData.deleteCustomer(popCustomer);
        currentState = State.WELCOME;
    }

    private static void state_H_new() {
        if (userData.duplicateCheck(currentRequest.id)) {
            System.out.println("That userID is already in use. Enter a new userID.");
            currentRequest.id = inputID.nextLine();
        } else {
            userData.addNewCustomer(currentRequest.id, currentRequest.amountToChange);
            currentState = State.WELCOME;
        }
    }

    private static void state_H_saveMoney() {
        userData.updateBalance(popCustomer.indexID, popCustomer.customerBalance + currentRequest.amountToChange);
        currentState =  State.DISPLAY;
    }

    private static void state_H_reduceMoney() {
        userData.updateBalance(popCustomer.indexID, popCustomer.customerBalance - currentRequest.amountToChange);
        currentState = State.DISPLAY;
    }

    private static void state_H_display() {
        userData.showBalance(popCustomer.indexID);
        currentState = State.WELCOME;
    }

    private static void clear() {
        System.out.println("Clearing data...");
        //Deleted by the Garbage collector.
        queue = null;
        userData = null;
        System.out.println("===See you next time===");
    }

    private static void pressAnyKeyToContinue()
    {
        System.out.println("Press Enter key to continue...");
        try
        {System.in.read();}
            catch(Exception e)
        {}
    }
}

/*
#### Couldn't get this to work. Trying to offer either entering r to push a random user to the queue or entering an ID to push specific user to queue.

    private static void state_Q_push() {
        // Use input to call pushToQueue
        System.out.println("Enter a valid userID to add to the queue: (r for random user)");
        currentRequest.id = inputUserID.nextLine();

        if(currentRequest.id == "r") {
            queue.randomQueue();
            currentState = State.WELCOME;
        } else {
            CustomerData.Customer user = userData.getUserInfo(currentRequest.id);
            if(user == null) {
                System.out.println("User does not exist.");
                currentState = State.WELCOME;
            } else {
                queue.pushToQueue(user);
            }
        }
        currentState = State.WELCOME;
    }
 */

