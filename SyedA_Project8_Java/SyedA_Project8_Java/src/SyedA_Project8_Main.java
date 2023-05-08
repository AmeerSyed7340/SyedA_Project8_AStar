import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

class AStarNode{
    public int [] config = new int[9];
    public int gStar;
    public int fStar;
    public int hStar;
    public AStarNode next;
    public AStarNode parent;

    //methods
    public AStarNode(int[]config, int gStar, int fStar, int hStar, AStarNode next, AStarNode parent){
        this.config = config;
        this.gStar = gStar;
        this.fStar = fStar;
        this.hStar = hStar;
        this.next = next;
        this.parent = parent;
    }//constructor

    public void printNode(AStarNode node){
        //print parents fStar and config
        //Check if parent is null or not
        if(node.parent != null){
            System.out.print("<" + node.parent.fStar + " [ ");
            for(int i = 0; i < node.parent.config.length; i++){
                System.out.print(node.parent.config[i] + " ");
            }
        }
        else{
            System.out.print("< Parent NULL [NO PARENT CONFIG");
        }

        System.out.print("] :: [");

        //print node's fStar and config
        System.out.print(node.fStar + " [ ");
        for(int i = 0; i < node.config.length; i++){
            System.out.print(node.config[i] + " ");
        }
        System.out.println("]>");
    }//printNode
}//AStarNode class

class AStar{
    public AStarNode startNode;
    public AStarNode Open; // sorted llist with dummy node in ascending order w.r.t fStar values.
    public AStarNode childList; // Unsorted llist with dummy node.
    int[][] table = new int[9][9]; // position table. May hard code this..
    int[] initConfig = new int[9]; // store initial state of 8 puzzle
    int[] goalConfig = new int[9]; // store goal state of 8 puzzle
    int[] dummyConfig = new int[9];

    //methods
    public AStar(){
        //Set up dummy config
        for(int i = 0; i < 9; i++){
            dummyConfig[i] = -1;
        }
        //Set up table manually
        table[0][0] = 0;
        table[0][1] = 2;
        table[0][2] = 1;
        table[0][3] = 2;
        table[0][4] = 1;
        table[0][5] = 2;
        table[0][6] = 1;
        table[0][7] = 2;
        table[0][8] = 1;

        table[1][0] = 2;
        table[1][1] = 0;
        table[1][2] = 1;
        table[1][3] = 2;
        table[1][4] = 3;
        table[1][5] = 4;
        table[1][6] = 3;
        table[1][7] = 2;
        table[1][8] = 1;

        table[2][0] = 1;
        table[2][1] = 1;
        table[2][2] = 0;
        table[2][3] = 1;
        table[2][4] = 2;
        table[2][5] = 3;
        table[2][6] = 2;
        table[2][7] = 3;
        table[2][8] = 2;

        table[3][0] = 2;
        table[3][1] = 2;
        table[3][2] = 1;
        table[3][3] = 0;
        table[3][4] = 1;
        table[3][5] = 2;
        table[3][6] = 3;
        table[3][7] = 4;
        table[3][8] = 3;

        table[4][0] = 1;
        table[4][1] = 3;
        table[4][2] = 2;
        table[4][3] = 1;
        table[4][4] = 0;
        table[4][5] = 1;
        table[4][6] = 2;
        table[4][7] = 3;
        table[4][8] = 2;

        table[5][0] = 2;
        table[5][1] = 4;
        table[5][2] = 3;
        table[5][3] = 2;
        table[5][4] = 1;
        table[5][5] = 0;
        table[5][6] = 1;
        table[5][7] = 2;
        table[5][8] = 3;

        table[6][0] = 1;
        table[6][1] = 3;
        table[6][2] = 2;
        table[6][3] = 3;
        table[6][4] = 2;
        table[6][5] = 1;
        table[6][6] = 0;
        table[6][7] = 1;
        table[6][8] = 2;

        table[7][0] = 2;
        table[7][1] = 2;
        table[7][2] = 3;
        table[7][3] = 4;
        table[7][4] = 3;
        table[7][5] = 2;
        table[7][6] = 1;
        table[7][7] = 0;
        table[7][8] = 1;


        table[8][0] = 1;
        table[8][1] = 1;
        table[8][2] = 2;
        table[8][3] = 3;
        table[8][4] = 2;
        table[8][5] = 3;
        table[8][6] = 2;
        table[8][7] = 1;
        table[8][8] = 0;
    } //constructor

    public int computeHStar(int[]nodeConfig, int[] goalConfig){
        //Step 0
        System.out.println("\nEntering computeHStar method");
        int sum = 0; //local var
        int i = 0; //local var

        //Step 7
        while(i < 9){
            //Step 1
            int pl = nodeConfig[i];

            //Step 2
            int j = 0;

            //Step 5
            while(j < 9){
                //Step 3
                if(goalConfig[j] == pl){
                    sum += table[i][j];
                    break;
                }

                //Step 4
                j++;
            }

            //Step 6
            i++;
        }
        //Step 8
        System.out.println("Leaving computeHStar method: sum = " + sum);

        //Step 9
        return sum;
    }//computerHStar

    public AStarNode expandChildList(AStarNode currentNode){
        //Step 0
        System.out.println("\nEntering expandChildList method");
        System.out.println("Printing currentNode");
        currentNode.printNode(currentNode);

        //Set tmpList config
        int[] tmpListConfig = copyArray(dummyConfig);
        AStarNode tmpList = new AStarNode(tmpListConfig, 0, 0, 0, null, null);

        //Step 1
        int i = 0; //local var

        //Step 3
        while(currentNode.config[i] != 0 && i < 9){
            //test
            //System.out.println("CurrentNode.config[" +i +"]: " + currentNode.config[i]);

            //Step 2
            if(currentNode.config[i] != 0){
                i++;
                //test
                //System.out.println("i: " + i);
            }
        }

        //Step 4
        int zeroPosition;
        if(i >= 9){
            System.out.println("Something is wrong, currentNode does not have a zero in it");
            return null;
        }
        else{
            zeroPosition  = i;
            System.out.println("Find the zero position in currentNode at position i = " + zeroPosition);
        }

        //Step 5
        int j = 0; //looking for the neighbor of zeroPosition

        //Step 8
        while(j < 9){
            //Step 6
            if(table[zeroPosition][j] == 1){ //found a position with 1 distance from the zeroPosition
                //set config to currentNode.config
                int[] newNodeConfig = copyArray(currentNode.config);
                AStarNode newNode = new AStarNode(newNodeConfig, 999, 999, 999, null, currentNode);
                //test newNode config
                //printConfig(newNode.config);
                //printConfig(currentNode.config);

                newNode.config[j] = 0;
                newNode.config[zeroPosition] = currentNode.config[j];

                //test config newNode
                //printConfig(newNode.config);
                //printConfig(currentNode.config);

                if(!checkAncestors(newNode)){ //if not one of currentNode's ancestors.
                    newNode.next = tmpList.next;
                    tmpList.next = newNode;
                }
            }
            //Step 7
            j++;
        }

        //Step 9
        System.out.println("Leaving expandChildList method and printing tmpList");
        printList(tmpList);

        //Step 10
        return tmpList;
    }//expandChildList

    public void OpenInsert(AStarNode node){
        AStarNode spot = findSpot(Open, node);
        node.next = spot.next;
        spot.next = node;
    }//OpenInsert

    //Helper function for OpenInsert
    public AStarNode findSpot(AStarNode listHead, AStarNode newNode){
        AStarNode spot = listHead;
        while(spot.next != null && spot.next.fStar < newNode.fStar){
            spot = spot.next;
        }
        return spot;
    }//findSpot

    //remove should take as parameter; OPEN or childList only
    public AStarNode remove(AStarNode list){
        if(list.next == null){
            return null;
        }else{
            AStarNode temp = list.next;
            list.next = temp.next;
            temp.next = null;
            return temp;
        }
    }//remove

    boolean match(int[]config1, int[]config2){
        return config1 == config2; //Simplified form of if(config1==config2)
    }//match

    boolean checkAncestors(AStarNode child){ //maybe can be used inside printNode to check for parent
        AStarNode parent = child.parent;
        while(parent != null){
            if(match(child.config, parent.config)){
                return true;
            }
            parent = parent.parent;
        }
        return false;
    }//checkAncestors

    public void printList(AStarNode node){
        System.out.println("\nIn printList");
        AStarNode temp = node;
        while(temp != null){
            temp.printNode(temp);
            temp = temp.next;
        }
    }//printList

    public void printSolution(){
        //Placeholder
        System.out.println("In printSolution");
    }//printSolution

    //Helper function to print configs - remove afterward
    public void printConfig(int[]config){
        System.out.print("[");
        for(int i = 0; i < 9;i ++){
            System.out.print(config[i]);
        }
        System.out.print("]");
        System.out.println();
    }//printConfig

    //My own method to help copy arrays
    public int[] copyArray(int[] arr1) {
        int[] arr2 = new int[arr1.length];
        for (int i = 0; i < arr1.length; i++) {
            arr2[i] = arr1[i];
        }
        return arr2;
    }//copy array

}//AStar class
public class SyedA_Project8_Main {
    public static void main(String[] args) {
        //Step 0
        File inFile1 = new File("src/Start1.txt");
        File inFile2 = new File("src/goal1.txt");
        try{
            //Scanners for the 2 input files: Start1 and Start 2
            Scanner sc1 = new Scanner(inFile1);
            Scanner sc2 = new Scanner(inFile2);

            //Create AStar object to access attributes
            AStar aStar = new AStar();

            //integer variable to feed "data" into from inFile1
            int num4Init;

            //counter to represent the index of initConfig
            int initIndex = 0;
            while(sc1.hasNext()){
                num4Init = sc1.nextInt();
                aStar.initConfig[initIndex] = num4Init;
                initIndex++;
            }


            //integer variable to feed "data" into from inFile2
            int num4Goal;

            //counter to represent the index of initConfig
            int goalIndex = 0;

            while(sc2.hasNext()){
                num4Goal = sc2.nextInt();
                aStar.goalConfig[goalIndex] = num4Goal;
                goalIndex++;
            }

            //Set a start node with initConfig read from inFile1 (Start1.txt)
            int[]startNodeConfig = aStar.copyArray(aStar.initConfig);
            aStar.startNode = new AStarNode(startNodeConfig,0,9999,9999,null,null);

            //Set dummy nodes for Open, Close and childList
            int[]dummy4Open = aStar.copyArray(aStar.dummyConfig);
            aStar.Open = new AStarNode(dummy4Open, 0,0,0,null,null);

            //Close list not being used this project
            //int[]dummy4Close = aStar.copyArray(aStar.dummyConfig);
            //aStar.Close = new AStarNode(dummy4Close, 0,0,0,null,null);

            int[]dummy4childList = aStar.copyArray(aStar.dummyConfig);
            aStar.childList = new AStarNode(dummy4childList, 0,0,0,null,null);

            //Step 1
            aStar.startNode.gStar = 0;
            aStar.startNode.hStar = aStar.computeHStar(aStar.startNode.config, aStar.goalConfig);
            aStar.startNode.fStar = aStar.startNode.gStar + aStar.startNode.hStar;
            aStar.OpenInsert(aStar.startNode);

            //Test
            /*AStarNode temp = aStar.Open;
            while(temp != null){
                System.out.println(temp.gStar + ", " + temp.hStar + ", " + temp.fStar);
                temp = temp.next;
            }*/

            //Step 2 - initialize
            //currentNode is a local variable
            AStarNode currentNode = aStar.remove(aStar.Open);
            //Step 10
            while(currentNode != null || aStar.Open.next != null){

                //Step 2 - after initialization
                //SHOULD BE A DEBUG STATEMENT
                System.out.println("\nThis is the currentNode:");
                currentNode.printNode(currentNode);

                //test config
                //System.out.println("Printing configs of current and goal");
                //aStar.printConfig(currentNode.config);
                //aStar.printConfig(aStar.goalConfig);

                //Step 3
                if(currentNode != null && aStar.match(currentNode.config, aStar.goalConfig)){
                    System.out.println("A solution is found!!");
                    aStar.printSolution();
                    System.exit(0);
                }

                //Step 4
                aStar.childList = aStar.expandChildList(currentNode);
                //test
                System.out.println("\nPrinting childList");
                aStar.childList.printNode(aStar.childList);

                //Step 5 - initialize
                AStarNode child = aStar.remove(aStar.childList);
                //Step 8
                while(child != null){
                    //Step5 - After initialization
                    //DEBUG
                    System.out.println("In main(), remove node from childList and printing");
                    child.printNode(child);

                    //Step 6
                    child.gStar = currentNode.gStar+1;
                    child.hStar = aStar.computeHStar(child.config, aStar.goalConfig);
                    child.fStar = child.gStar + child.hStar;
                    child.parent = currentNode; //back pointer

                    //Step 7
                    aStar.OpenInsert(child);

                    //Step 9
                    //OUTFILE
                    System.out.println("Below is Open List");
                    aStar.printList(aStar.Open);

                    child = aStar.remove(aStar.childList);//to ensure loop ends
                }

                currentNode = aStar.remove(aStar.Open); // ensure loop ends
            }


            //Step 11
            if(aStar.Open.next == null && currentNode.config != aStar.goalConfig){
                //OUTFILE
                System.out.println("Message: no solution can be found in the search!");
            }


            //Step 12
            //close Scanners
            sc1.close();
            sc2.close();
        }catch (FileNotFoundException e){
            System.out.println(e);
        }
    }
}
