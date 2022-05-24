import java.util.Scanner;


//start of class Main where the game is executed.
public class Game {
    public static void main(String[] args) {
        //instantiating variables that are required for status checks throughout the game. Scanner input = new Scanner(System.in);
        Player player = new Player(30);
        Monster[] monsters = {new Monster(), new Monster("Cyclops", 300, 20), new Vampire(), new Basilisk()};
        int turn = 1;
        Scanner input = new Scanner(System.in);
        String proceed;
        int command;
        boolean healed = false;
        //beginning of the for loop to run the game.
        for(int i = 0; i < monsters.length; i++) {
            //if statement to check that the player is still alive.
            if(player.getHealth() > 0) {
                //The enemy is selected from the Monster[array]
                Monster enemy = monsters[i];
                System.out.println("\t- A " + enemy.getName() + " Has Appeared! -\n");
                        System.out.println("\tEnter to continue");
                proceed = input.nextLine();
                proceed = input.nextLine();
                //This while loop will exit when the player dies or the monster dies.
                while(enemy.getHealth() > 0 && player.getHealth() > 0) {
                    //check to see if the amount of health sacrificed is more than 200, and if so the monster is defeated.
                    if(player.getBonusDamage() >= 200) {
                        enemy.takeDamage(9999999);
                        System.out.println("\t~ You performed a ritual that defeated the " + enemy.getName() +
                                ". It dropped " + player.addPotions() + " potions ~\n\n\tEnter to continue\n");
                        proceed = input.nextLine();
                        proceed = input.nextLine();
                        player.resetBonusDamage();
                        System.out.println("\tYou may choose a special move! (You may only have one special at a time)");
                        System.out.println("\t" + "1. Parry");
                        System.out.println("\t" + "2. Lifesteal");
                        System.out.println("\t" + "3. Hypnosis");
                        command = input.nextInt();
                        player.setSpecial(command);
                        turn++;
                        continue;
                    }
                    //check to see if the player healed in the last turn (this is to control for poison counter decrement later found later)
                    healed = false;
                    if(player.getHypno() > 0) {
                        player.decrementHypno();
                    }
                    //prints out text to ask show the actions available for theplayer.
                            System.out.println("\t# # # # # # Turn " + turn + " # # # # # #");
                                    System.out.print("\n\t+ " + enemy.getName() + " Health: " +
                                            enemy.getHealth()+ " +");
                    if(player.getHypno() > 0) {
                        if(player.getHypno() == 1) {
                            System.out.print(" (Hypnotized for 1 turn)");
                        } else {
                            System.out.print(" (Hypnotized for " + player.getHypno() + " turns)");
                        }
                    }
                    System.out.print("\n\t+ " + "Your Health: " + player.getHealth() + " +");
                    if(enemy.getPoison()>0) {
                        if(enemy.getPoison() == 1) {
                            System.out.println(" (You have 1 poison counter)");
                        } else {
                            System.out.println(" (You have " + enemy.getPoison() + " poison counters)");
                        }
                    }
                    System.out.println("\n\n\t" + "What will you do? (Enter a number)");
                    if(player.getSpecial() != "") {
                        System.out.println("\t0. Special Move (" + player.getSpecial() + ")");
                    }
                    System.out.println("\t" + "1. Attack");
                    System.out.println("\t" + "2. Heal (You have " + player.getPotions() + " potions left)");
                    System.out.println("\t" + "3. Ritual (" + player.getBonusDamage() + " health sacrificed)");
                    System.out.println("\t" + "4. Enemy Info");
                    System.out.println("\t" + "5. How to Play");
                    //input for the player to choose a move
                    command = input.nextInt();
                    //control flow structure that will do an action based on what number the player picks
                    if(command == 1) {
                        int damage = player.attack();
                        enemy.takeDamage(damage);
                        System.out.println("\t" + "You deal " + damage + " damage!");
                    } else if (command == 2) {
                        if(player.getPotions() > 0) {
                            player.heal();
                            if(enemy.getPoison() > 0) {
                                enemy.resetPoison();
                                healed = true;
                            }
                        } else {
                            System.out.println("\tYou do not have any potions left.\n");
                            System.out.println("\tEnter to continue\n");
                            proceed = input.nextLine();
                            proceed = input.nextLine();
                            continue;
                        }
                    } else if(command == 3) {
                        System.out.println("\t" + "Sacrifice How Much Health?");
                        int sac = input.nextInt();
                        System.out.println();
                        player.ritual(sac);
                        if(player.getHealth() < 1) {
                            System.out.println("\t~ You have died from sacrificing too much health! ~");
                            break;
                        }
                        continue;
                    } else if(command == 4) {
                        enemy.getInfo();
                        System.out.println("\tEnter to continue\n");
                        proceed = input.nextLine();
                        proceed = input.nextLine();
                        continue;
                    } else if(command == 5) {
                        System.out.println("\tDefeat all 4 enemies to win.\n\tEach turn you may attack, heal, do a " +
                                        "ritual, or use a special ability." + "\n\tAttacking will do random " +
                                        "amount of damage between 15 and 45 damage."
                                + "\n\tHealing will restore 40 health " +
                                        "and consume one potion. If you have any poison counters, healing will reset " +
                                        "them to 0." + "\n\tThe enemy and you will perform attacks and heals at the same time."
                                + "\n\tRituals are performed before " +
                                        "the turn ends (you will not be attacked and the turn stays the same)."
                                + "\n\tWhen doing a ritual, you will " +
                                        "sacrifice an amount of health which will be converted into extra " +
                                        "damage for your next attack." + "\n\tIf 200 or more health has been " +
                                        "sacrificed, you will instantly kill the monster without having to attack."
                                + "\n\tYou may do as many rituals as you like each turn however, if you sacrifice " +
                                        "too much health, you will die." + "\n\tWhenever you defeat a monster, it " +
                                        "will drop between 2-3 health potions."
                                + "\n\tAfter defeating an enemy, you will also be asked to learn a special move."
                                + "\n\tYou may only have one special move at a time."
                                + "\n\tThe special move \"Parry\" will dodge and reflect twice the damage dealt by the " +
                                        "enemy if you guess a random integer from 1 to 9 within 3 attempts."
                                + "\n\tThe special move \"Lifesteal\" will deal half of your normal attack damage and " +
                                        "heal you for that amount" + "\n\tThe special move \"Hypnosis\" make the enemy " +
                                "unable to perform actions for the next two turns if you win a game of Rock, Paper, and Scissors.");
                        System.out.println("\n\tEnter to continue\n");
                        proceed = input.nextLine();
                        proceed = input.nextLine();
                        continue;
                    } else if(command == 0) { //if the player chooses to use a special move, this control flow structure will execute the special that the player has equipped
                        int damage = player.special();
                        if(player.getSpecial().equals("")) {
                            System.out.println("\tEnter to continue\n");
                            proceed = input.nextLine();
                            proceed = input.nextLine();
                            continue;
                        }
                        if(player.getSpecial().equals("Parry")) {
                            if(damage > 0) {
                                damage = enemy.attack(true)*2 + player.getBonusDamage();
                                enemy.takeDamage(damage);
                                player.resetBonusDamage();
                                System.out.println("\tYou parry the "
                                        + enemy.getName() + "'s attack, avoiding all incoming damage, and reflect " + damage + " damage!\n\n");
                                System.out.println("\tEnter to continue\n");
                                proceed = input.nextLine();
                                proceed = input.nextLine();
                                turn++;
                                if(enemy.getHealth() < 1) {
                                    System.out.println("\t~ You have defeated the " + enemy.getName() +
                                            " and it dropped " + player.addPotions() + " potions ~\n\n\tEnter to continue\n");
                                    proceed = input.nextLine();
                                    System.out.println("\tYou may choose to learn a special move! (You may only have " +
                                            "one special at a time)"); System.out.println("\t" + "1. Parry");
                                    System.out.println("\t" + "2. Lifesteal");
                                    System.out.println("\t" + "3. Hypnosis");
                                    command = input.nextInt();
                                    player.setSpecial(command);
                                }
                                continue;
                            }
                        } else if(player.getSpecial().equals("Lifesteal")) {
                            player.addHealth((int)(damage/2));
                            player.addHealth(player.getBonusDamage());
                            damage += player.getBonusDamage();
                            enemy.takeDamage(damage/2);
                            System.out.println("\tYou deal " + damage/2
                                    + " damage and lifesteal " + ((int)(damage/2) + player.getBonusDamage()) + " health!\n"); player.resetBonusDamage();
                        } else if(player.getSpecial().equals("Hypnosis")) {
                            if(damage > 0) {
                                player.addHypno();
                            } else {
                                System.out.println("\tYou failed to hypnotise the enemy.");
                            }
                        }
                    } else {
                        System.out.println("\tYou do nothing.");
                    }
                    //check to see if the player has healed this round. Will decrement poison if player was healed.
                    if(healed && player.getHypno() < 1) {
                        enemy.decrementPoison();
                    }
                    //check to see if the enemy is hypnotized. If no, then the enemy attacks.
                    if(player.getHypno() == 0) {
                        int incoming = enemy.attack(false);
                        player.takeDamage(incoming);
                        System.out.println("\tEnter to continue\n");
                        proceed = input.nextLine();
                        proceed = input.nextLine();
                    } else if (player.getHypno() == 3){
                        System.out.println("\n\tEnter to continue\n");
                        proceed = input.nextLine();
                        proceed = input.nextLine();
                    } else {
                        System.out.println("\tThe " + enemy.getName() + "is hypnotised and cannot attack.\n");
                        System.out.println("\tEnter to continue\n");
                        proceed = input.nextLine();
                        proceed = input.nextLine();
                    }
                    //increment the turn tracker
                    turn++;
                    //if the player's poison is 5 or greater, the player loses.
                    if(enemy.getPoison() >= 5) {
                        System.out.println("\t~ You have died from the Basilisk's poison! ~");
                        break;
                    }
                    //if the player's health is below 1, they lose
                    if(player.getHealth() < 1){
                        System.out.println("\t~ You have died! ~");
                        break;
                    }
                    //if the enemy's health is less than 1, then the enemy is defeated and the player can choose a special move
                    if(enemy.getHealth() < 1) {
                        System.out.println("\t~ You have defeated the " + enemy.getName() + " and it dropped " +
                                player.addPotions() + " potions ~\n\n\tEnter to continue\n");
                        proceed = input.nextLine();
                        System.out.println("\tYou may choose a special move! (You may only have one special at a time)");
                        System.out.println("\t" + "1. Parry");
                        System.out.println("\t" + "2. Lifesteal");
                        System.out.println("\t" + "3. Hypnosis");
                        command = input.nextInt();
                        player.setSpecial(command);
                    }
                }
                //if all the monsters have been defeated and the player's health is above 0, then the player wins the game.
                if(i == monsters.length-1 && enemy.getHealth() <= 0){
                    System.out.println("\t~ You have defeated all the monsters! ~");
                }
            }
        }
    }
} //end of the class Main

//beginning of the Player class
class Player {
    private int health;
    private int bonusDamage;
    private int baseDamage;
    private int potions;
    private int hypno;
    private String specialmove;
    Scanner input = new Scanner(System.in);
    //Player constructor
    public Player(int damage) {
        health = 100;
        bonusDamage = 0;
        baseDamage = damage;
        potions = 3;
        specialmove = "";
        hypno = 0;
    }
    //Player's attack() method that deals a random amount of damage from 15 to baseDamage + 15
    public int attack() {
        int damage = (int)(Math.random()*(baseDamage+1) + 15);
        damage += getBonusDamage();
        bonusDamage = 0;
        return damage;
    }
    //Player's method for healing (decrements potions)
    public void heal() {
        health += 40;
        System.out.println("\t" + "You heal 40 damage!");
        potions--;
    }
    //method to sacrifice heal for bonus attack damage
    public void ritual(int num) {
        health -= num;
        bonusDamage += num;
        System.out.println("\tYou sacrifice " + num + " health\n");
    }
    //method that calls the player's special ability
//will execute the special ability based on what word is assigned to the Player's specialmove instance variable
    public int special() {
        int damage = 0;
        if(specialmove.equals("Parry")) {
            int num = (int)(Math.random()*9 + 1);
            int tries = 0;
            while(tries < 3) {
                System.out.println("\tGuess an integer from 1 to 9. You have " +
                        (3-tries) + " attempts left.\n");
                int guess = input.nextInt();
                if(guess == num) {
                    System.out.println("\tYou correctly guessed the number and performed a parry!\n");
                    System.out.println("\tEnter to continue\n");
                    String proceed = input.nextLine();
                    proceed = input.nextLine();
                    return 1;
                }
                if(tries < 2 && guess < num) {
                    System.out.println("\tHigher!\n");
                } else if(tries < 2 && guess > num) {
                    System.out.println("\tLower!\n");
                }
                tries++;
            }
            System.out.println("\tYou failed to guessed the number and couldn't parry!\n");
            System.out.println("\tEnter to continue\n");
            String proceed = input.nextLine();
            proceed = input.nextLine();
            return -1;
        } else if(specialmove.equals("Lifesteal")) {
            damage = attack();
        } else if(specialmove.equals("Hypnosis")) {
            String proceed;
            boolean stop = true;
            while(stop) {
                int enemyGuess = (int)(Math.random()*3+1);
                System.out.println("\tWin a round of Rock, Paper, and Scissors to hypnotize the enemy for 2 turns. (Enter a number)");
                System.out.println("\t1. Rock");
                System.out.println("\t2. Paper");
                System.out.println("\t3. Scissors\n");
                int guess = input.nextInt();
                if(guess == enemyGuess) {
                    if(guess == 1) {
                        System.out.println("\tYou and the enemy both chose Rock and reached a stalemate. Please play again.\n");
                                System.out.println("\tEnter to Continue.");
                        proceed = input.nextLine();
                        proceed = input.nextLine();
                    } else if(guess == 2) {
                        System.out.println("\tYou and the enemy both chose Paper and reached a stalemate. Please play again.\n");
                                System.out.println("\tEnter to Continue.");
                        proceed = input.nextLine();
                        proceed = input.nextLine();
                    } else if(guess == 3) {
                        System.out.println("\tYou and the enemy both chose Scissors and reached a stalemate. Please play again.\n");
                                System.out.println("\tEnter to Continue.");
                        proceed = input.nextLine();
                        proceed = input.nextLine();
                    }
                } else if(guess == 1 && enemyGuess == 2) {
                    System.out.println("\tYou chose Rock and the enemy chose Paper. You Lose.\n");
                            System.out.println("\tEnter to Continue.");
                    proceed = input.nextLine();
                    proceed = input.nextLine();
                    return -1;
                } else if(guess == 1 && enemyGuess == 3) {
                    System.out.println("\tYou chose Rock and the enemy chose Scissors. You Win!\n");
                    System.out.println("\tEnter to Continue.");
                    proceed = input.nextLine();
                    proceed = input.nextLine();
                    return 1;
                } else if(guess == 2 && enemyGuess == 1) {
                    System.out.println("\tYou chose Paper and the enemy chose Rock. You Win!\n");
                    System.out.println("\tEnter to Continue.");
                    proceed = input.nextLine();
                    proceed = input.nextLine();
                    return 1;
                } else if(guess == 2 && enemyGuess == 3) {
                    System.out.println("\tYou chose Rock and the enemy chose Paper. You Lose.\n");
                            System.out.println("\tEnter to Continue.");
                    proceed = input.nextLine();
                    proceed = input.nextLine();
                    return -1;
                } else if(guess == 3 && enemyGuess == 1) {
                    System.out.println("\tYou chose Scissors and the enemy chose Rock. You Lose.\n");
                            System.out.println("\tEnter to Continue.");
                    proceed = input.nextLine();
                    proceed = input.nextLine();
                    return -1;
                } else if(guess == 3 && enemyGuess == 2) {
                    System.out.println("\tYou chose Scissors and the enemy chose Paper. You Win!\n");
                    System.out.println("\tEnter to Continue.");
                    proceed = input.nextLine();
                    proceed = input.nextLine();
                    return 1;
                } else if(guess <= 0 || guess > 3) {
                    System.out.println("\tInvalid choice, please play again.\n");
                    System.out.println("\tEnter to Continue.");
                    proceed = input.nextLine();
                    proceed = input.nextLine();
                }
            }
        } else {
            System.out.println("\tYou do not have a special move yet!\n");
        }
        return damage;
    }
    //method to set the player's special move
    public void setSpecial(int num) {
        String proceed;
        if(num==1) {
            specialmove = "Parry";
            System.out.println("\tYou have successfully learned Parry for the next battle!\n");
            System.out.println("\tEnter to continue\n");
            proceed = input.nextLine();
        } else if(num==2) {
            specialmove = "Lifesteal";
            System.out.println("\tYou have successfully learned Lifesteal for the next battle!\n");
            System.out.println("\tEnter to continue\n");
            proceed = input.nextLine();
        } else if(num==3) {
            specialmove = "Hypnosis";
            System.out.println("\tYou have successfully learned Hypnosis for the next battle!\n");
            System.out.println("\tEnter to continue\n");
            proceed = input.nextLine();
        } else {
            System.out.println("\tYou did not learn a new special move\n");
            System.out.println("\tEnter to continue\n");
            proceed = input.nextLine();
        }
    }
    //returns the player's specialmove
    public String getSpecial() {
        return specialmove;
    }
    //adds potions for the player to use
    public int addPotions() {
        int rand = (int)(Math.random()*2 + 2);
        potions += rand;
        return rand;
    }
    //method to make the player take damage
    public void takeDamage(int num) {
        health -= num;
    }
    //method to get player's health
    public int getHealth() {
        return health;
    }
    //method to add an amount to player's health
    public void addHealth(int num) {
        health += num;
    }
    //method that returns the player's current bonusDamage
    public int getBonusDamage() {
        return bonusDamage;
    }
    //method that sets bonusDamage to 0
    public void resetBonusDamage() {
        bonusDamage = 0;
    }
    //Method that returns how many potions the player has
    public int getPotions() {
        return potions;
    }
//helper method for the player's special() method. Used to hypnotize the enemy.
    public void addHypno() {
        hypno = 3;
        System.out.println("\tThe enemy has been hypnotized for the next two turns.");
    }
    //returns the amount of hypno the player has
    public int getHypno() {
        return hypno;
    }
    //used to decrementHypno every round if not 0.
    public void decrementHypno() {
        hypno--;
    }
} //end of the Player class
//beginning of the Monster super class
class Monster {
    private int health;
    private int maxDamage;
    private String name;
    private int poison;
    //default Monster constructor
    public Monster() {
        health = 100;
        maxDamage = 10;
        name = "Zombie";
    }
    //parameterized Monster constructor
    public Monster(String str, int h, int d) {
        health = h;
        maxDamage = d;
        name = str;
        poison = 0;
    }
    //Monster's attack() method
    public int attack(boolean parried) {
        int damage = (int)(Math.random()*maxDamage + 1);
        if(parried) {
            System.out.println("\tThe " + name + " attempts to attack for " + damage + " damage!\n");
            return damage;
        }
        System.out.println("\t" + "The " + name + " attacks for " + damage + " damage!\n");
        return damage;
    }
    //returns the Monster's health
    public int getHealth() {
        return health;
    }
    //adds an amount to Monster's health
    public void addHealth(int num) {
        health += num;
    }
    //method to get the Monster's name
    public String getName() {
        return name;
    }
    //Method for monster to take damage
    public void takeDamage(int num) {
        health -= num;
    }
    //Method to get super class getInfo method
    public void getInfo() {
        System.out.println("\tMonster Info:\n\tThis monster has no special moves and can only attack. \n\tIts attacks " +
                "randomly deal 1 to " + getDamage() + " damage. \n\tAttack the monster to kill it!\n");
    }
    //method to get the maximum amount of damage the monster will do
    public int getDamage() {
        return maxDamage;
    }
    //method to get the Monster's poison (The method is only called by the Basilisk child class)
    public int getPoison() {
        return poison;
    }
        //method to add poison (The method is only called by the Basilisk child class)
    public void addPoison() {
        poison++;
    }
    //method to set poison to 0 when the player heals
    public void resetPoison() {
        System.out.println("\tYou neutralised the Basilisk's poison and you now have 0 poison counters.\n");
        poison = 0;
    }
    //method to decrement poison whenever the player heals so that it shows the player has 0 counters on the turn they heal.
    public void decrementPoison() {
        poison--;
    }
} //end of the Monster super class


//beginning of Vampire class (child class of the Monster class)
class Vampire extends Monster{
    //Vampire constructor
    public Vampire() {
        super("Vampire", 150, 26);
    }
    //the Vampire classes special attack() method
    public int attack(boolean parried) {
        int damage = (int)(Math.random()*getDamage() + 1);
        if(parried) {
            System.out.println("\tThe Vampire attempts to attack for " + damage + " damage!\n");
            return damage;
        }
        System.out.println("\tThe Vampire siphons and steals " + damage + " health from you.\n");
        super.addHealth(damage);
        return damage;
    }
    //The Vampire class's getInfo() method
    public void getInfo() {
        System.out.println("\tMonster Info:\n\tWhenever the Vampire attacks, it heals the same amount as the damage it " +
                "deals. \n\tIts attacks randomly deal 1 to " + (getDamage()-1) + " damage. \n\tAttack the monster to kill it!\n");
    }
}//end of the Vampire class
//beginning of the Basilisk class (child class of the Monster super class
class Basilisk extends Monster{
    //Basilisk constructor
    public Basilisk() {
        super("Basilisk", 400, 36);
    }
    //The Basilisk class's special attack() method
    public int attack(boolean parried) {
        int damage = (int)(Math.random()*super.getDamage()+10);
        if(parried) {
            System.out.println("\tThe Basilisk attempts to attack for " + damage + " damage.\n");
            return damage;
        }
        super.addPoison();
        System.out.println("\tThe Basilisk sinks its teeth into your body for " + damage + " damage and injects you with poison.");
        if(super.getPoison() > 0 ) {
            if(super.getPoison() == 1) {
                System.out.println("\t(You have 1 poison counter)\n");
            } else {
                System.out.println("\t(You have " + super.getPoison() + " poison counters)\n");
            }
        }
        return damage;
    }
    //The Basilisk class's special getInfo() method
    public void getInfo() {
        System.out.println("\tMonster Info:\n\tWhenever the Basilisk attacks, it gives you one poison counter. If the " +
                "poison counter reaches 5, you instantly die from the poison." + "\n\tIf you have been poisoned, using" +
                " a healing potion will cure the poison and reset your poison counter to 0."
                + "\n\tIts attacks randomly deal 10 to " + (getDamage()+9) + " damage. \n\tAttack the monster to kill it!\n");
    }
}//end of the Basilisk class

