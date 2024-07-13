package hammurabi.docs.matuszek;
import java.util.Random;         // imports go here
import java.util.Scanner;

public class Hammurabi {         // must save in a file named Hammurabi.java
    Random rand = new Random();  // this is an instance variable
    Scanner scanner = new Scanner(System.in);

    int population = 100;
    int bushels = 2800;
    int acresOwned = 1000;
    int landValue = 19;
    public boolean gameon;


    public static void main(String[] args) { // required in every Java program
        new Hammurabi().playGame();
    }

    void playGame() {
        Scanner scanner = new Scanner(System.in);

        // declare local variables here: grain, population, etc.
        // statements go after the declations
        while(gameon) {
            askHowManyAcresToBuy(19, bushels);
            sellHowManyAcres(acresOwned);
            askHowMuchGrainToFeedPeople(bushels);
            askHowManyAcresToPlant(acresOwned, population, bushels);
            plagueDeaths(population);
            starvationDeaths(population, bushels);
            uprising(population, 10);
        }
        scanner.close();
    }

    public int askHowManyAcresToBuy(int price, int bushels){
        System.out.print("How many acres of land do you wish to buy?");
        int userInput = scanner.nextInt();
        price = userInput * 19;
        if (bushels >= price){
            this.bushels -= price;
            this.acresOwned += userInput;
        } else {
            System.out.println("You dont have enough bushels to purchase.");
        }
        System.out.println("You have " + bushels + " bushels left, and " + acresOwned + " acres left.");
        return userInput;
    }

    public int sellHowManyAcres(int acresOwned){
        System.out.println("How many acres of land do you wish to sell?");
        int userInput = scanner.nextInt();
        if (userInput <= acresOwned){
            this.acresOwned -= userInput;
            this.bushels += landValue * userInput;
        } else {
            System.out.println("You don't have enough acres");
        }
        System.out.println(acresOwned + " acres left.");
        return userInput;
    }

    public int askHowMuchGrainToFeedPeople(int bushels){
        System.out.println("How many bushels would you like to feed the people?");
        System.out.println("You have currently have " + bushels + " bushels.");
        int userInput = scanner.nextInt();
        if (userInput < bushels){
            this.bushels -= userInput;
        } else {
            System.out.println("You don't have enough bushels");
        }
        System.out.println("You now have " + bushels + " bushels left.");
        return userInput;
    }

    public int askHowManyAcresToPlant(int acresOwned, int population, int bushels){
//        int peoplePerAcres = population / 10;
        while(true) {
            System.out.println("How many acres would you like to plant?");
            int userInput = scanner.nextInt();
            int amountOfBushelsNeeded = userInput * 2;
            int amountOfPeopleNeeded = userInput / 10;
            if (acresOwned >= userInput && bushels >= amountOfBushelsNeeded && this.population >= amountOfPeopleNeeded) {
                System.out.println("You're now planting " + userInput + " acres.");
                this.bushels -= amountOfBushelsNeeded;
                System.out.println("You now have " + bushels + " left.");
                return userInput;
            } else {
                System.out.println("You lack the resources to plant this amount of acres.");
            }
        }
        //have enough acres, bushels, and enough people to work the acres
    }

    public int plagueDeaths(int i) {
        Random random = new Random();
        if (random.nextInt(101) < 15) {
            System.out.println("You got hit by the black plague!");
            this.population -= this.population / 2;
            System.out.println("Your previous population of " + this.population*2 + " has now been reduced to " + this.population + ".");
            return this.population;
        }
        return 0;
    }

    public int starvationDeaths(int ppl, int bushelsFed) {
        int amountOfBushelsNeeded = ppl * 20;
        if (bushelsFed >= amountOfBushelsNeeded){
            this.bushels -= amountOfBushelsNeeded;
            immigrants(population,this.acresOwned,this.bushels);
            return 0;
        }

        int bushelsShort = amountOfBushelsNeeded - bushelsFed;
        double amountOfPplStarved = (double)bushelsShort / 20;
        double numberCeil = Math.ceil(amountOfPplStarved);
        this.population -= (int) numberCeil;
        return (int) numberCeil;
//        return 0;
    }

    public boolean uprising(int population, int howManyPeopleStarved) {
        double populationNumber = (int)population * 0.45;
        if (howManyPeopleStarved > populationNumber){
            return true;
        } else {
            return false;
        }
    }

    public int immigrants(int population, int acresOwned, int grainInStorage) {
        if(starvationDeaths(population, grainInStorage) == 0){
            int results = (20 * acresOwned + grainInStorage) / (100 * population) +1;
            this.population += results;
            return results;
        }
        return 0;
    }

    public int harvest(int acres) {
        Random random = new Random();
        int randoNumber = random.nextInt(6) + 1;
        this.bushels += acres * randoNumber;
        return acres * randoNumber;
    }

    public int grainEatenByRats(int i) {
        Random random = new Random();
        if (random.nextInt(101) < 40) {
            Random random2 = new Random();
            double daNumber = (random2.nextInt(21)+10);
            return (int)(daNumber * i )/ 100;
        }
        return 0;
    }

    public int newCostOfLand() {
        Random random = new Random();
        int price = (random.nextInt(7) + 17);
        landValue = price;
        return price;
    }

    //other methods go here
}