/* 
* AR: Christian Rodriguez
* CS1101
* IN: Dr. Meija
*/

import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

public class comp2 {

// Global variables defined here are special because their values never change during runtime.
// You should NOT define more global variables.

// Constants for defining the number of rows and columns the dungeon will have.
    static final int NUM_ROWS = 4;
    static final int NUM_COLUMNS = 4;

// Constants for defining room types. Feel free to use these in your code.
    static final int ROOM_TYPE_EMPTY = 0;
    static final int ROOM_TYPE_SHIELD = 1;
    static final int ROOM_TYPE_SWORD = 2;
    static final int ROOM_TYPE_DRAGON = 3;

    public static void main(String[] args) {

// Your code STARTS HERE.
    Scanner userScnr = new Scanner(System.in);
    int [][] dungeon = generateRandomDungeon();
// Varibles needed for code and requirements 
    int row = 0;
    int col = 0;
    int playerHealth = 5;
    boolean sword = false;
    boolean shield = false;
    boolean notEndGame = true;
    int [] currMove = {row , col};

    System.out.println("Oh no!");
    System.out.println("A dragon is killing our sheep and scaring our villagers, please help the knight put a stop to this nightmare!");
    System.out.println();
 //This while loop keeps the game running until the player is dead or has beat the dragon.
    while(notEndGame){
//This if statement will allow the game to run if the player is alive.
        if(playerHealth <= 0){
            notEndGame = false;
            System.out.println("You Died. The Dragon continues to eat the sheep.");
        }else{
        
        mainActionsDisplay(row,col,playerHealth,sword,shield);
        displayBoard(dungeon);
        
//Array prevMove is the previous move of a player used in option run away.
        int [] prevMove = currMove;

        currMove = playerMove(dungeon,row,col);
        row = currMove[0];
        col = currMove[1];

        
//Situations a player can face. 
            if(dungeon[row][col] == ROOM_TYPE_EMPTY){
//Entering an empty room and displaying the options a player can take.
//EXTRA CREDIT: Give player option when in an empty room. 
                System.out.println("There is nothing but junk in this room");

//EXTRA CREDIT: This method called displays the player options...
                playerInEmptyRoom();

                userScnr = new Scanner(System.in);
                String option = userScnr.nextLine();

// This loop assured a player enters a valid option verified by the validation method.
                while(!validOption(option)){
                    System.out.println("Invalid Option, please enter a valid option");
                    option = userScnr.nextLine();
                }

// Option A of entering an empty room randomizes the chances of doing nothing, getting hit, or healing.
                if(option.equalsIgnoreCase("A")){
                    int randomNum = ThreadLocalRandom.current().nextInt(0,2+1);

                    if(randomNum == 0){
                        System.out.println("You found nothing in the rubble and now youre dirty.");
                        System.out.println();
                    }else if(randomNum == 1){
                        System.out.println("You choose to look around the rubble");
                        System.out.println("Oh no! A tiny goblin hit you");
                        System.out.println("You lost a health point");
                        System.out.println();
                        playerHealth--;
                    }else if(randomNum == 2){
                        System.out.println("You found a health poition and drank it");
                        System.out.println("You gained one health point");
                        playerHealth++;
                    }
// Option B of entering an empty room does nothing and allows you to take your next move.
                }else if(option.equalsIgnoreCase("B")){
                    System.out.println();
                    System.out.println("You choose to carry on");
                    System.out.println("Smart move");  
                    System.out.println();
// Option C of entering an empty room randomizes the player's chances of dying instantly or gaining some health.
                }else if(option.equalsIgnoreCase("C")){
                    System.out.println();
                    System.out.println("Feeling lucky? well see...");

                    int randomNum = ThreadLocalRandom.current().nextInt(0,2+1);

                    if(randomNum == 0){
                        System.out.println("You tested faith and failed");
                        playerHealth = 0;
                    }else if(randomNum == 1){
                        System.out.println("You tested faith and succeded");
                        System.out.println("You gained 5 health points");
                        playerHealth += 5;
                    }else if(randomNum == 2){
                        System.out.println("You tested faith and succeded");
                        System.out.println("You gained 2 health points");
                        playerHealth += 2;
                    }
                }



            }else if(dungeon[row][col] == ROOM_TYPE_SHIELD){
// Entering the room with the shield.
                System.out.println();
                System.out.println("The Stalhrim Shield is in this room!");
                System.out.println("A King Mudcrab is guarding it.");
                playerActionsDisplay();
// Resetting Scanner
                userScnr = new Scanner(System.in);
                String option = userScnr.nextLine();
// This loop assures a player enters a valid option through the validation method. 
                while(!validOption(option)){
                    System.out.println("Invalid Option, please enter a valid option");
                    option = userScnr.nextLine();
                }  
// Option A of entering a room with the shield removes one health from doing nothing.                    
                if(option.equalsIgnoreCase("A")){
                    System.out.println("You have choosen to do nothing.");
                    playerHealth--;

// Option B of entering an empty room does nothing and allows you to take your next move.
                }else if(option.equalsIgnoreCase("B")){
                    System.out.println("You have choosen to hit the enemy");
                    System.out.println("The King Mudcrab is dead and the shield is yours.");
                    playerHealth--;
                    shield = true;
// Option C of entering an empty room randomizes the player's chances of dying instantly or gaining some health.
                }else if(option.equalsIgnoreCase("C")){
                    System.out.println("You have choose to run away.");
                    row = prevMove[0];
                    col = prevMove[1];
                }
                    
            }else if(dungeon[row][col] == ROOM_TYPE_SWORD){
// Entering the room with the shield.  
                System.out.println();
                System.out.println("The Dawnbreaker Sword is in this room!");
                System.out.println("A Draugr Overlord is guarding it.");
                System.out.println();
                playerActionsDisplay();
// Resetting Scanner 
                userScnr = new Scanner(System.in);
                String option = userScnr.nextLine();
// This loop assures a player enters a valid option through the validation method. 
                while(!validOption(option)){
                    System.out.println("Invalid Option, please enter a valid option");
                    option = userScnr.nextLine();

                }
// Option B of entering a room with the shield kills the monster and the player acquired the sword but loses one health.
  
                if(option.equalsIgnoreCase("A")){
                    System.out.println("You have choosen to do nothing.");
                    playerHealth--;

// Option B of entering a room with the shield kills the monster and the player aqquired the shiled but loses one health
                }else if(option.equalsIgnoreCase("B")){
                    System.out.println("You have choosen to hit the enemy");
                    System.out.println("The Draugr Overlord is dead and the sword is yours.");
                    playerHealth--;
                    sword = true;

// Option C of entering a room with the sword places you back to your previous position. 
                }else if(option.equalsIgnoreCase("C")){
                    System.out.println("You have choose to run away.");
                    row = prevMove[0];
                    col = prevMove[1];
                }
                    
            }else if(dungeon[row][col] == ROOM_TYPE_DRAGON){
                System.out.println();
                System.out.println("The Dragon is in this room!");
                playerActionsDisplay();

                userScnr = new Scanner(System.in);
                String option = userScnr.nextLine();

// This loop assures a player enters a valid option through the validation method.
                while(!validOption(option)){
                    System.out.println("Invalid Option, please enter a valid option");
                    option = userScnr.nextLine();
                }
// Option A of entering a room with the dragon will kill the player instantly if they have not acquired both the shield and sword otherwise they will lose on health                   
                if(option.equalsIgnoreCase("A")){
                    System.out.println("You have choosen to do nothing.");
                        if(shield && sword){
                            playerHealth--;
                        }else{
                            System.out.println("You Died. The Dragon continues to eat the sheep.");
                            notEndGame = false;
                        }
// Option B of entering a room with the dragon will kill the dragon if they have both shield and sword otherwise they will die instantly 
                }else if(option.equalsIgnoreCase("B")){
                    System.out.println("You have choosen to hit the enemy");
                        if(shield && sword){
                            System.out.println("You have defeated the Dragon! Thank you!");
                            notEndGame = false;
                        }else{
                            System.out.println("You Died. The Dragon continues to eat the sheep.");
                            notEndGame = false;
                        }
// Option C of entering a room will return the player to their previous location

                }else if(option.equalsIgnoreCase("C")){
                    System.out.println("You have choose to run away.");
                    row = prevMove[0];
                    col = prevMove[1];
                }
            }
        }
    }
// Your code ENDS HERE
}

// Your methods START HERE

// Method displays where the player is, player health points, the status of sword and shield, and what will be their next move.
    public static void mainActionsDisplay(int row, int col, int playerHealth, boolean shield, boolean sword){
    	System.out.println("You are in cell " + row + "," + col);
    	System.out.println("Health Points: " + playerHealth);
    	System.out.println("Shield aqquired: " + shield);
    	System.out.println("Sword aqquired: " + sword);
    	System.out.println();
    	System.out.println("Where would you like to move now?");
    	System.out.println("Use L to move left");
    	System.out.println("Use R to move right");
    	System.out.println("Use U to move up");
    	System.out.println("Use D to move down");
    	System.out.println();  
    }
// Method allows the player to move around the board but never outside it.
  	public static int [] playerMove(int [][] dungeon, int row, int col){
    	Scanner userScnr = new Scanner(System.in);
        int [] cell = new int [2];
    	String move = userScnr.nextLine();
// If the players move is valid the player can move 
	   	if(validMove(move)){
	    	if(move.equalsIgnoreCase("L")){
// Prevents players from going out of bounds and their location to being negative 
                col--;
	    		if(checkOutOfBounds(dungeon,row,col)){
	    			col++;
	    			return playerMove(dungeon,row,col);
	    		}else{
	    			cell[1] = col;
	    			cell[0] = row;
	    		}
	    	}else if(move.equalsIgnoreCase("R")){
// Prevents player from going out of bounds  
	    		col++;
	    		if(checkOutOfBounds(dungeon,row,col)){
	    			col--;
	    			return playerMove(dungeon,row,col);
	    		}else{
	    			cell[1] = col;
	    			cell[0] = row;
	    		}
	    	}else if(move.equalsIgnoreCase("U")){
// Prevents players from going out of bounds and their location to being negative 
	    		row--;
	    		if(checkOutOfBounds(dungeon,row,col)){
	    			row++;
	    			return playerMove(dungeon,row,col);
	    		}else{
	    			cell[1] = col;
	    			cell[0] = row;
	    		}
	    	}else if(move.equalsIgnoreCase("D")){
// Prevents player from going out of bounds 
	    		row++;
	    		if(checkOutOfBounds(dungeon,row,col)){
	    			row--;
	    			return playerMove(dungeon,row,col);
	    		}else{
	    			cell[1] = col;
	    			cell[0] = row;
	    		}
	    	}

	    }else{
// Otherwise if the option is invalid ask the user for a valid option
	    	System.out.println("Invalid Option, please enter a valid option.");
	    		return playerMove(dungeon,row,col);
	    }

    	System.out.println();
    	return cell;
    }

// Method validates the option a player takes when moving 
    public static boolean validMove(String move){
    	if(move.equalsIgnoreCase("U")){
    		return true;
    	}else if(move.equalsIgnoreCase("D")){
    		return true;
    	}else if(move.equalsIgnoreCase("L")){
    		return true;
    	}else if(move.equalsIgnoreCase("R")){
    		return true;
    	}else{
    		return false;
    	}
    }
// Method validates the option a makes when encountering a monster
    public static boolean validOption(String option){
    	if(option.equalsIgnoreCase("A")){
    		return true;
    	}else if(option.equalsIgnoreCase("B")){
    		return true;
    	}else if(option.equalsIgnoreCase("C")){
    		return true;
    	}else{
    		return false;
    	}
    }
// Method does not allow player to exit outside of the board while making fun of them 
    public static boolean checkOutOfBounds(int [][] dungeon, int row, int col){
    	if(row < 0 || row >= dungeon.length || col < 0 || col >= dungeon.length){
    		System.out.println("Good Job! You bumbed your head into a wall. Invalid Option.");
    		return true;
    	}else{
    		return false;
    	}
    }
// Method displayes the actions a player can take when approched by a monster
    public static void playerActionsDisplay(){
        System.out.println();
    	System.out.println("What would you like to do:");
    	System.out.println("a) Do nothing");
    	System.out.println("b) Hit the enemy");
    	System.out.println("c) Run away");
        System.out.println();
    }
// EXTRA CREDIT Method to display player actions in an empty room
    public static void playerInEmptyRoom(){
        System.out.println();
        System.out.println("What would you like to do:");
        System.out.println("a) Look around the rubble");
        System.out.println("b) Carry On");
        System.out.println("c) Test your luck");
        System.out.println();
    }

    public static void displayBoard(int [][] dungeon){
        System.out.println();
        // for every row
        for(int k = 0; k < dungeon.length; k++){
            // for every element in row
            for(int j = 0; j < dungeon[k].length; j++){
                System.out.print(dungeon[k][j] + " ");
            }
            System.out.println();
        }
    }

    // Your methods END HERE
    // DO NOT change anything below this comment.
    /*
     * Create a random dungeon represented by a 2D int array (int[][]).
     * In the resulting 2D array:
     *   0 represents an empty room.
     *   1 represents a room with the shield.
     *   2 represents a room with the sword.
     *   3 represents a room with the dragon.
     */
    public static int[][] generateRandomDungeon() {
        // Create dungeon
        int[][] dungeon = new int[NUM_ROWS][NUM_COLUMNS];

        // Get random coordinates
        int[][] locations = getRandomCoordinates();

        // Set shield location
        int[] shieldLocation = locations[0];
        dungeon[shieldLocation[0]][shieldLocation[1]] = ROOM_TYPE_SHIELD;

        // Set sword location
        int[] swordLocation = locations[1];
        dungeon[swordLocation[0]][swordLocation[1]] = ROOM_TYPE_SWORD;

        // Set dragon location
        int[] dragonLocation = locations[2];
        dungeon[dragonLocation[0]][dragonLocation[1]] = ROOM_TYPE_DRAGON;

        return dungeon;
    }
    /*
     * The following method returns a an array of random coordinates.
     * Each coordinate is represented by an array of length 2.
     *
     * In the resulting array:
     *   The first coordinate is the location of the the shield.
     *   The second coordinate is the location of the the sword.
     *   The third coordinate is the location of the dragon.
     * 
     * NOTE: This method requires that the minimum size for the dungeon must be 4x4.
     */
    private static int[][] getRandomCoordinates() {
        int numToolsToObtain = 2;
        int numDragons = 1;

        if (NUM_COLUMNS < 4 || NUM_ROWS < 4) {
            System.out.println("Minimum size for the dungeon must be 4x4");
            return null;
        }

        int[][] monstersLocation = new int[numToolsToObtain + numDragons][2];
        Random rand = new Random();

        for (int i = 0; i < numToolsToObtain + numDragons; i++) {
            int row = rand.nextInt(NUM_ROWS);
            int column = rand.nextInt(NUM_COLUMNS);
            if ((row == 0 && column == 0) || (i != 0 && isCoordinateDuplicate(i + 1, row, column, monstersLocation))) {
                int columnDuplicatedValue = column;
                while (column == columnDuplicatedValue || (i != 0 && isCoordinateDuplicate(i + 1, row, column, monstersLocation))) {
                    column = rand.nextInt(NUM_COLUMNS);
                }
            }
            monstersLocation[i][0] = row;
            monstersLocation[i][1] = column;
        }
        return monstersLocation;
    }
    /*
     * Returns true if a monster is already placed in the current cell (row, column), and false otherwise.
     */
    private static boolean isCoordinateDuplicate(int monsters, int row, int column, int[][] monstersLocation) {
        for (int i = 0; i < monsters; i++) {
            if (monstersLocation[i][0] == row && monstersLocation[i][1] == column) {
                return true;
            }
        }
        return false;
    }
}