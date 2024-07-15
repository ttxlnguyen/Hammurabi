package hammurabi.docs.matuszek;
import com.sun.security.jgss.GSSUtil;

import java.util.Random;         // imports go here
import java.util.Scanner;

public class Hammurabi {         // must save in a file named Hammurabi.java
    Random rand = new Random();
    Scanner scanner = new Scanner(System.in);

    // Starting values
    int population = 100;
    int bushels = 2800;
    int acresOwned = 1000;
    int landValue = 19;
    public boolean runGame;

    // Post Game Summary Variables
    int years = 0;
    int starved;
    int bushelsFed;
    int bushelsEatenByRats;
    int newImmigrants;
    int totalBushels;
    int totalAcres;
    int totalStaved;

    public static void main(String[] args) { // required in every Java program
        new Hammurabi().playGame();
    }

    void playGame() {
        Scanner scanner = new Scanner(System.in);
        runGame = true;

        // declare local variables here: grain, population, etc.
        // statements go after the declations
        for (int i = 0; i<= 10; i++) {
            while(runGame) {
                i++;
                years++;
                totalAcres += acresOwned;
                totalBushels += bushels;

                if (years< 10){
                    System.out.println("===========================================================================");
                    System.out.println("You are in year " + years + " of you reign.");
                    System.out.println("In the previous year, " + starved + " people starved, and " + newImmigrants + " people came to the city.");
                    System.out.println("You total population is currently " + population + " people strong.");
                    System.out.println("Rats ate a total of " + bushelsEatenByRats + " bushels, leaving us a total of " + bushels);
                    System.out.println("The city currently owns " + acresOwned + " acres of land with a land value of " + landValue + " per acre.");
                    System.out.println("===========================================================================");

                    acresToBuy(landValue, bushels);
                    acresToSell(acresOwned);
                    feedThePeople(bushels);
                    acresToPlant(acresOwned, population, bushels);
                    plagueDeaths(population);
                    starvationDeaths(population, bushels);
                    harvest(acresOwned);
                    uprising(population, starved);
                    immigrants(population, acresOwned, bushels);
                    grainEatenByRats(bushels);
                    newCostOfLand();
                }
                if (years == 10) {
                    runGame = false;
                    break;
                }
            }
            if (years == 10) {
                finalSummary();
            }
        }
        scanner.close();
    }

    public int acresToBuy(int acrePrice, int bushels){
        while (true) {
            System.out.print("\nHow many acres of land do you wish to buy? ");
            int userInput = scanner.nextInt();
            acrePrice = userInput * 19;
            if (bushels >= acrePrice){
                this.bushels -= acrePrice;
                this.acresOwned += userInput;
                return userInput;
            } else {
                System.out.println("You don't have enough bushels to purchase.");
                System.out.println("You have " + this.bushels + " bushels left, and " + this.acresOwned + " acres left.");
            }
        }
    }

    public int acresToSell(int acresOwned){
        while (true) {
            System.out.print("How many acres of land do you wish to sell? ");
            int userInput = scanner.nextInt();
            if (userInput <= acresOwned){
                this.acresOwned -= userInput;
                this.bushels += landValue * userInput;
                System.out.println("You have " + this.acresOwned + " acres left.\n");
                return userInput;
            } else {
                System.out.println("You don't have enough acres to sell, you currently own: " + this.acresOwned);
            }
        }
    }

    public int feedThePeople(int bushels){
        while (true) {
            System.out.println("How many bushels would you like to feed the people?");
            System.out.print("You currently have " + bushels + " bushels. ");
            int userInput = scanner.nextInt();
            if (userInput < bushels){
                this.bushels -= userInput;
                System.out.println("Such generosity my lord, thank you for feeding the people " + userInput + " bushels.");
                System.out.println("You now have " + this.bushels + " bushels left.\n");
                return userInput;
            } else {
                System.out.println("You don't have enough bushels to feed the people. You currently have: " + this.bushels);
            }
        }
    }

    public int acresToPlant(int acresOwned, int population, int bushels){
        while(true) {
            System.out.print("How many acres would you like to plant? ");
            int userInput = scanner.nextInt();
            int amountOfBushelsNeeded = userInput * 2;
            int amountOfPeopleNeeded = userInput / 10;
            if (acresOwned >= userInput && bushels >= amountOfBushelsNeeded && this.population >= amountOfPeopleNeeded) {
                System.out.println("You're now planting " + userInput + " acres of land.");
                this.bushels -= amountOfBushelsNeeded;
                System.out.println("You now have " + this.bushels + " bushels left.\n");
                return userInput;
            } else {
                System.out.println("You lack the resources to plant this many acres of land.");
            }
        }
    }

    public int plagueDeaths(int i) {
        Random random = new Random();
        if (random.nextInt(101) <= 15) {
            System.out.println("You got hit by the black plague! Half of your population saw the light.");
            this.population -= this.population / 2;
            System.out.println("Your previous population of " + this.population*2 + " has now been reduced to " + this.population + ".");
            return this.population;
        } else {
            return 0;
        }
    }

    public int starvationDeaths(int people, int bushelsFed) {
        int amountOfBushelsNeeded = people * 20;
        if (bushelsFed >= amountOfBushelsNeeded){
            this.bushels -= amountOfBushelsNeeded;
            this.starved = 0;
            return 0;
        } else {
            int bushelsLacked = amountOfBushelsNeeded - bushelsFed;
            double amountOfPeopleStarved = (double) bushelsLacked / 20;
            int numberCeil = (int) Math.ceil(amountOfPeopleStarved);
            this.population -= numberCeil;
            this.starved = numberCeil;
            totalStaved += numberCeil;
            return numberCeil;
        }
    }

    public boolean uprising(int population, int howManyPeopleStarved) {
        double populationNumber = (int) population * 0.45;
        if (howManyPeopleStarved > populationNumber){
            System.out.println("You let too many people starve. Game over.");
            runGame = false;
            return true;
        } else {
            return false;
        }
    }

    public int immigrants(int population, int acresOwned, int grainInStorage) {
        if(this.starved == 0) {
            int results = (20 * acresOwned + grainInStorage) / (100 * population) + 1;
            this.population += results;
            newImmigrants = results;
            return results;
        }
        System.out.println("The people are scared of starvation. No immigrants this year.");
        newImmigrants = 0;
        return 0;
    }

    public int harvest(int acres) {
        Random random = new Random();
        int randomNumber = random.nextInt(6) + 1; // Sets bounds 1-5
        this.bushels += acres * randomNumber;
        return acres * randomNumber;
    }

    public int grainEatenByRats(int i) {
        Random random = new Random();
        while (years < 10) {
            if (random.nextInt(101) < 40) {
                Random random2 = new Random();
                double randomNumber2 = (random2.nextInt(21)+10); // Sets bounds 10-30
                bushelsEatenByRats = (int) (randomNumber2 * bushels) / 100;
                this.bushels -= bushelsEatenByRats;
                System.out.println("You kingdom has is experiencing a rat infestation.");
                return bushelsEatenByRats;
            } else {
                return 0;
            }
        }
        return 0;
    }

    public int newCostOfLand() {
        Random random = new Random();
        int price = (random.nextInt(7) + 17); // Sets bound 17-23
        landValue = price;
        return price;
    }

    public void finalSummary() {
        System.out.println("\nCongratulations my lord, you've eclipsed a ten year tenure.");
        System.out.println("In ten years you've...");
        System.out.println("Gained " + totalBushels + " total bushels.");
        System.out.println("Let " + totalStaved + " people starve.");
        System.out.println("Acquired " + totalAcres + " total acres of land.");
    }
}