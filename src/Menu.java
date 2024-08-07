import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.regex.Pattern;


public class Menu {

    private BufferedReader in = new BufferedReader(new InputStreamReader(System.in));


    public int selectOption() {
        System.out.println();
        System.out.println("1. Start of the game " + "\uD83C\uDFAE");
        System.out.println("2. Rules of the game " + "\uD83D\uDCDC");
        System.out.println();
        String input = "";
        do {
            System.out.print("What do you wish to do? ");
            try {
                input = in.readLine();
            } catch (java.io.IOException e) {
                System.out.println("An error occured : " + e);
            }
        } while (!Pattern.matches("[012]", input));

        return Integer.valueOf(input);
    }


    public void showMenu() {
        System.out.println("WELCOME TO BATTLESHIP GAME");
    }

    public void showRules() {
        ConsoleHelper.eraseConsole();
        System.out.println("########################################");
        System.out.println("### 🛳️ Welcome to the Battleship Game! 🛳️ ###");
        System.out.println("########################################\n");

        // Rule 1: Setup
        System.out.println("╔══════════════════════╗");
        System.out.println("📜 Rule 1:Setup");
        System.out.println("╚══════════════════════╝");
        System.out.println("🛳️ Arrange your fleet on your grid secretly from your opponent.");
        System.out.println("⚓ Fleet includes:");
        System.out.println("   - 1x Carrier (5 spaces)");
        System.out.println("   - 1x Battleship (4 spaces)");
        System.out.println("   - 1x Cruiser (3 spaces)");
        System.out.println("   - 1x Submarine (3 spaces)");
        System.out.println("   - 1x Destroyer (2 spaces)\n");

        // Rule 2: Turn Order
        System.out.println("╔══════════════════════╗");
        System.out.println("🎲 Rule 2:Turn Order");
        System.out.println("╚══════════════════════╝");
        System.out.println("⏳ Players take turns calling out coordinates to attack.");
        System.out.println("🗺️ Example: For a coordinate (row, column, direction), say \"2 3 0\" or \"5 7 1\" where:");
        System.out.println("   - First number is the row");
        System.out.println("   - Second number is the column");
        System.out.println("   - Third number is the direction (0 for horizontal, 1 for vertical)\n");


        // Rule 3: Objective
        System.out.println("╔══════════════════════╗");
        System.out.println("🎯 Rule 3:Objective");
        System.out.println("╚══════════════════════╝");
        System.out.println("🎯 Sink all of your opponent's ships by correctly guessing their locations.");
        System.out.println("🔴 A hit is marked with(\uD83D\uDCA5).");
        System.out.println("⬛ A miss is marked with a black square (⬛).");
        System.out.println("⭐ A sink is marked with a white star ( ✭ ).\n");

        // Rule 4: Prohibited Moves
        System.out.println("╔══════════════════════╗");
        System.out.println("🚫 Rule 4:Invalid Moves");
        System.out.println("╚══════════════════════╝");
        System.out.println("❌ No calling the same coordinate more than once.");
        System.out.println("⛔ Do not move your ships once the game has started.\n");

        // Rule 5: Winning the Game
        System.out.println("╔══════════════════════╗");
        System.out.println("🏆 Rule 5:Winning Game");
        System.out.println("╚══════════════════════╝");
        System.out.println("🎉 The first player to sink all of their opponent's ships wins.");
        System.out.println("👑 Declare victory by saying \"You sank my battleship!\" for the final ship.\n");

        // Rule 6: Communication
        System.out.println("╔══════════════════════╗");
        System.out.println("💬 Rule 6:Communication");
        System.out.println("╚══════════════════════╝");
        System.out.println("🗣️ Announce \"hit\" or \"miss\" after each attack.");
        System.out.println("🤐 Do not give away the positions of your ships.\n");

        // Rule 7: Fair Play
        System.out.println("╔══════════════════════╗");
        System.out.println("📏 Rule 7: Fair Play");
        System.out.println("╚══════════════════════╝");
        System.out.println("🕵️ Play honestly and keep track of hits and misses accurately.");
        System.out.println("🤝 Respect your opponent and enjoy the game.");
    }
}