import java.io.File;
import java.io.FileNotFoundException;
import java.util.Objects;
import java.util.Scanner;

/**
 * You can also develop help functions and new classes for your system. You
 * can change the skeleton code if you need but you do not allow to remove the
 * methods provided in this class.
 */
public class SocialNetwork {
    private ADS2Graph userNetwork;
    private String[] ADS2List;


    public SocialNetwork(){

    }

    /**
     * Loading social network data from files.
     * The dataset contains two separate files for user names (NameList.csv) and
     * the network distributions (SocialNetworkData.csv).
     * Use file I/O functions to load the data.You need to choose suitable data
     * structure and algorithms for an effective loading function
     */
    public void Load(){
        ADS2List = new String[100];
        int counter = 0;

        String[] currentLine;
        try {
            Scanner scanner = new Scanner(new File("NameList.csv"));

            try {
                scanner.useDelimiter("\r\n");

                while(scanner.hasNext()) {
                    currentLine = scanner.next().split("\",\"");
                    ADS2List[counter++] = currentLine[0];
                }
            } catch (Throwable var12) {
                try {
                    scanner.close();
                } catch (Throwable var9) {
                    var12.addSuppressed(var9);
                }

                throw var12;
            }

            scanner.close();
        } catch (FileNotFoundException var13) {
            System.err.println("File error");
        }

//        System.out.println(ADS2List[0]);
//        System.out.println(ADS2List[2]);
//        System.out.println(ADS2List[4]);

        // Create Graph
        userNetwork = new ADS2Graph(counter);

        try {
            Scanner scanner = new Scanner(new File("SocialNetworkData.csv"));

            try {
                scanner.useDelimiter("\r\n");

                while(scanner.hasNext()) {
                    currentLine = scanner.next().split(",");
                    userNetwork.AddEdge(Integer.parseInt(currentLine[0]) - 1, Integer.parseInt(currentLine[1]) - 1, Double.parseDouble(currentLine[2]));

                }
            } catch (Throwable var10) {
                try {
                    scanner.close();
                } catch (Throwable var8) {
                    var10.addSuppressed(var8);
                }

                throw var10;
            }

            scanner.close();
        } catch (FileNotFoundException var11) {
            System.err.println("File error");
        }


//        //test nameList
//        for (int i = 0; i < ADS2List.length; ++i) {
//            System.out.print(i+" "+ ADS2List[i]+" -> ");
//        }

//        //weightVal Check -> 10,1,0.292671937   [NOTE TO ME: VALUE NEEDS TO BE TRIMMED AS ITS CREATING RANDOM DIGITS, still works(?) but not accurate]
//        System.out.println("\n"+userNetwork.GetWeight(0, 41));
//        System.out.println("\n"+userNetwork.GetWeight(0, 50));
//        System.out.println("\n"+userNetwork.GetWeight(0, 9));
//        System.out.println("\n"+userNetwork.GetWeight(0, 8));



    }


    /**
     * Locating a user from the network
     * @param fullName users full name as a String
     * @return return the ID based on the user list. return -1 if the user do not exist
     * based on your algorithm, you may also need to locate the reference
     * of the node from the graph.
     */
    public int FindUserID(String fullName){

        for(int i = 0; i < ADS2List.length; ++i){
            if(Objects.equals(fullName, ADS2List[i])){
                return i;
            }
        }
        return -1;
    }


    /**
     * Listing ALL the friends belongs to the user
     * You need to retrieval all the directly linked nodes and return their full names.
     * Current Skeleton only have some dummy data. You need to replace the output
     * by using your own algorithms.
     * @param currentUserName use FindUserID or other help functions to locate
     * the user in the graph first.
     * @return You need to return all the user names in a String Array directly
     * linked to the users node.
     */
    public String[] GetMyFriends(String currentUserName){
        String[] myFriends = new String[100];
        int counter = 0;
        int userID = FindUserID(currentUserName);

        for(int i = 0; i < userNetwork.ReturnAdMLeng(); ++i){
            if(userNetwork.GetWeight(userID, i) != 0){
                myFriends[counter] = ADS2List[i];
                counter++;
            }
        }
        return myFriends;
    }

    /**
     * Listing the top 10 recommended friends for the user
     * In the task, you need to calculate the shortest distance between the
     * current user and all other non-directly linked users. Pick up the top ten
     * closest candidates and return their full names.
     * Use some help functions for sorting and shortest distance algorithms
     * @param currentUserName use FindUserID or other help functions to locate
     * the user in the graph first.
     * @return You need to return all the user names in a String Array containing
     * top 10 closest candidates.
     */
    public String[] GetRecommended (String currentUserName){
        String[] recommended = new String[10];
        int userID = FindUserID(currentUserName);

        // Shortest Path Algo -- Find all users that aren't friends and their distance away [WORKING]
        double[][] allPaths = userNetwork.ShortestPathAlgo(userID);

        // Sort Algo -- Take those paths and return the shortest 10
//        double[][]sortedFriends = userNetwork.SortAlgo(allPaths);
        userNetwork.heapSort(allPaths);


        // Turn into String[]
        for(int i = 0; i < 10; i++) {
            recommended[i] = ("No."+(int)allPaths[0][i]+" "+ADS2List[(int) allPaths[0][i]] + " W:" + allPaths[1][i]);
        }

        return recommended;
    }
}
