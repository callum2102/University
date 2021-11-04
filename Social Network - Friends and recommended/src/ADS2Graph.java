public class ADS2Graph {
    private double[][] AdM; // adjacency matrix
    private boolean[] visited = new boolean[100];
    private double[] tentativeDistance = new double[100];
    private int[] fromList = new int[100];

    ADS2Graph(int numOfV) {
        if (numOfV > 0) {
            this.AdM = new double[numOfV][numOfV];
            this.visited = new boolean[numOfV];
        } else {
            System.err.println("Number of the vertices should be larger than zero");
        }

    }

    public int ReturnAdMLeng(){
        return this.AdM.length;
    }

    public void AddEdge(int index1, int index2, double weight) {
        if (index1 == index2) {
            System.err.println("two indices should be different");
        } else {
            this.AdM[index1][index2] = weight;
            this.AdM[index2][index1] = weight;
        }
    }

    public boolean IsAdjacent(int index1, int index2) {
        return this.AdM[index1][index2] != 0.0 && this.AdM[index2][index1] != 0.0;
    }

    private void ResetVisited(){
        for(int i = 0; i < visited.length; i++){
            visited[i] = false;
        }
    }

    private void InitTentativeList(int startID) {
        tentativeDistance = new double[visited.length];
        for(int i = 0; i < tentativeDistance.length; i++){
            tentativeDistance[i] = Integer.MAX_VALUE;
        }
        tentativeDistance[startID] = 0;
    }

    private void InitFromList(int startID) {
        fromList = new int[visited.length];
        for(int i = 0; i < fromList.length; i++){
            fromList[i] = -1;
        }
        fromList[startID] = startID;

    }

//    public double[][] SortAlgo(double[][] allPaths) {
//        double[][] recommended = new double[2][10];
//        boolean sorted = false;
//
//        while (!sorted) {
//            int counter = 0;
//
//            for(int i = 0; i < visited.length-1; i++) {
//                if(allPaths[1][i] > allPaths[1][i+1]) {
//                    double temp1 = allPaths[0][i+1]; // ID
//                    double temp2 = allPaths[1][i+1]; // WEIGHT
//
//                    allPaths[0][i+1] = allPaths[0][i];
//                    allPaths[1][i+1] = allPaths[1][i];
//
//                    allPaths[0][i] = temp1;
//                    allPaths[1][i] = temp2;
//
//                    counter++;
//                }
//            }
//            // When sort is done, create recommended
//            if(counter == 0){
//                sorted = true;
//                for(int i = 0; i < 10; i++){
//                    recommended[0][i] = allPaths[0][i];
//                    recommended[1][i] = allPaths[1][i];
//                }
//            }
//        }
//        return recommended;
//    }

    static void heapify(double[][] array, int length, int i) {
        int leftChild = 2*i+1;
        int rightChild = 2*i+2;
        int largest = i;

        // if the lchild is larger than parent
        if (leftChild < length && array[1][leftChild] > array[1][largest]) {
            largest = leftChild;
        }

        // if the rchild is larger than parent
        if (rightChild < length && array[1][rightChild] > array[1][largest]) {
            largest = rightChild;
        }

        // if swap needed
        if (largest != i) {
            double temp0 = array[0][i];
            double temp1 = array[1][i];

            array[0][i] = array[0][largest];
            array[1][i] = array[1][largest];

            array[0][largest] = temp0;
            array[1][largest] = temp1;
            heapify(array, length, largest);
        }
    }

    public static void heapSort(double[][] array) {
        if (array.length == 0) return;

        int length = 100;

        for (int i = length / 2 - 1; i >= 0; i--)
            heapify(array, length, i);

        for (int i = length - 1; i >= 0; i--) {
            double temp0 = array[0][0];
            double temp1 = array[1][0];

            array[0][0] = array[0][i];
            array[1][0] = array[1][i];

            array[0][i] = temp0;
            array[1][i] = temp1;

            heapify(array, i, 0);
        }
        for(int i = 0; i < 10; i++) {
            System.out.println(array[0][i] + " " + array[1][i]);
        }
    }


        private int FindNewCurrent(int destination) {
        double min_tentative = Integer.MAX_VALUE;
        int newCurrent = destination;

        for(int  i = 0; i < tentativeDistance.length; i++){
            if(!visited[i] && min_tentative > tentativeDistance[i]){
                min_tentative = tentativeDistance[i];
                newCurrent = i;
            }
        }

        return newCurrent;
    }

    public double[][] ShortestPathAlgo(int currentUser) {
        double[][] allPaths = new double[2][100]; // 0 INDEX userID, 1 COL weight
        //set IDs - working
        for(int i = 0; i < visited.length; i++){
            allPaths[0][i] = i;
        }

        // remove friends from the pool and set path value to max - working
        boolean[] visited = new boolean[100];
        int[] friends = ReturnFriends(currentUser);
        for(int i = 0; i < friends.length; i++){
            visited[friends[i]] = true;
            allPaths[1][friends[i]] = Double.MAX_VALUE;
        }

        //remove self from pool - working
        visited[currentUser] = true;
        allPaths[1][currentUser] = Double.MAX_VALUE;


        // find paths for all except visited - working
        for(int i = 0; i < visited.length; i++){
            if(!visited[i]) {
                allPaths[1][i] = FindPath(currentUser, i);
            }
        }
        return allPaths;
    }

    public double FindPath(int startID, int destination) {

        ResetVisited();
        InitTentativeList(startID);
        InitFromList(startID);

        int current = startID;

        // WHILE LOOP
        while(current != destination && tentativeDistance[current] != Integer.MAX_VALUE){
            visited[current] = true;
            for(int i = 0; i < AdM.length; i++){
                if(!visited[i] && IsAdjacent(current, i) && tentativeDistance[i] >(tentativeDistance[current] + GetWeight(current, i))){
                    tentativeDistance[i] = (tentativeDistance[current] + GetWeight(current, i));
                    fromList[i] = current;
                }
            }
            current = FindNewCurrent(destination);
        }

        return tentativeDistance[destination];
    }

        private int[] ReturnFriends(int currentUser) {
            int counter = 0;
            int[] findFriends = new int[20];

            for(int i = 0; i < this.ReturnAdMLeng(); ++i){
                if(IsAdjacent(currentUser, i)){
                    findFriends[counter] = i;
                    counter++;
                }
            }
            // Return in a trimmed array
            int[] friendList = new int[counter];
            for(int i = 0; i < counter; i++){
                friendList[i] = findFriends[i];
            }

            return friendList;
    }

        public double GetWeight(int index1, int index2) {return this.AdM[index1][index2];}
}

