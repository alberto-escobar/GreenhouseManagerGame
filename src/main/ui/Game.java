package ui;

import java.util.Scanner;
import model.Greenhouse;
import model.Plant;

// Greenhouse manager game
public class Game {
    private Scanner input;
    int time;
    Greenhouse greenhouse;

    // REQUIRES: nothing
    // MODIFIES: this
    //   EFFECT: creates Game object, initializes scanner for keyboard commands, and opens the welcome prompt.
    public Game() {
        this.input = new Scanner(System.in);
        input.useDelimiter("\n");
        System.out.println("Welcome to Greenhouse Manger 2023! Press s to start!");
        if (input.next().equals("s")) {
            greenhouse = new Greenhouse();
            run();
        } else {
            System.out.println("good bye!");
        }
    }

    // REQUIRES: nothing
    // MODIFIES: this
    //   EFFECT: runs main game and waits for user input to process command. If user enters "q", game quits.
    public void run() {
        boolean runGame = true;
        String command = null;

        while (runGame) {
            updateTime();
            printGreenhouseCommand();
            command = input.next();
            if (command.equals("q")) {
                runGame = false;
            } else {
                this.processCommand(command);
            }
        }
        System.out.println("good bye!");
    }

    // REQUIRES: valid string command
    // MODIFIES: this
    //   EFFECT: Processes user command.
    public void processCommand(String command) {
        if (command.equals("")) {
            this.updateTime();
        } else if (command.equals("p")) {
            plantSeedCommand();
        } else if (command.equals("b")) {
            buySeedCommand();
        } else if (command.equals("s")) {
            sellPlantCommand();
        } else if (command.equals("w")) {
            waterPlantCommand();
        } else if (command.equals("h")) {
            System.out.println(
                    "press enter to update game, b = buy seeds, p = plant seeds, s = sell plant, w = water plant");
        } else {
            System.out.println("invalid command, enter h for help");
        }
    }

    // REQUIRES: nothing
    // MODIFIES: this
    //   EFFECT: updates current game time and updates plants in Greenhouse to current game time
    public void updateTime() {
        long currentTime = System.currentTimeMillis();
        this.time = (int) currentTime;
        this.time = this.time / 1000;
        greenhouse.updatePlants(this.time);
    }

    // REQUIRES: nothing
    // MODIFIES: this
    //   EFFECT: outputs wallet amount, seed amount, and list of plants along with their age and hydration level to
    //           terminal.
    public void printGreenhouseCommand() {
        String stats = "Wallet: $" + greenhouse.getWallet() + " Seed: " + greenhouse.getSeeds();
        System.out.println(stats);
        String plants = "";
        if (greenhouse.getPlants().isEmpty()) {
            plants = "No plants";
        }
        for (Plant plant : greenhouse.getPlants()) {
            plants = plants + plant.getName()
                    + "(Age: " + plant.getAge() + " Hydration: " +  plant.getHydration() + ")   ";
        }
        System.out.println(plants);

    }

    // REQUIRES: nothing
    // MODIFIES: this
    //   EFFECT: Asks user for new plant name and plants seed in Greenhouse.
    public void plantSeedCommand() {
        System.out.println("Enter name for new plant:");
        String name = input.next();
        if (!greenhouse.plantSeed(name, this.time)) {
            System.out.println("Error, either not enough seeds or duplicate name");
        }
    }

    // REQUIRES: nothing
    // MODIFIES: this
    //   EFFECT: Buys seed in Greenhouse.
    public void buySeedCommand() {
        if (!greenhouse.buySeed()) {
            System.out.print("Not enough money");
        }
    }

    // REQUIRES: nothing
    // MODIFIES: this
    //   EFFECT: asks user for name of plant to sell and sells plant.
    public void sellPlantCommand() {
        System.out.println("Which plant would you like to sell?");
        String name = input.next();
        if (!greenhouse.sellPlant(name)) {
            System.out.println("No plant with that name");
        }
    }

    // REQUIRES: nothing
    // MODIFIES: this
    //   EFFECT: asks user for plant name to water and waters plant
    public void waterPlantCommand() {
        System.out.println("Enter name of plant to water:");
        String name = input.next();
        if (!greenhouse.waterPlant(name, this.time)) {
            System.out.println("Plant does not exist");
        }
    }
}