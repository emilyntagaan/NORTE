package com.simulation;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder; // ✅ Correct JNA import
import org.jline.utils.InfoCmp;

import com.sun.jna.Native;
import com.sun.jna.win32.StdCallLibrary;

public class App {
    private static final String[] menuOptions = {"START", "HOW TO PLAY", "OPTIONS", "CREDITS", "EXIT"};
    private static int selected = 0;
    private static Terminal terminal;
    private static boolean running = true;

    // ✅ Interface for Windows key detection
    public interface User32 extends StdCallLibrary {
        User32 INSTANCE = Native.load("user32", User32.class);
        short GetAsyncKeyState(int key);
    }

    public static void main(String[] args) throws Exception {
        terminal = TerminalBuilder.builder().system(true).build();
        // BackgroundMusic.playMusic("background.mp3");
        // startKeyListener();
        displayMenu();
    }

    // ✅ Displays the menu, updates only when a key is pressed
    public static void displayMenu() throws Exception {
        BackgroundMusic.stopMusic(); // ✅ Stop any previous music
        BackgroundMusic.playMusic("background.mp3"); // ✅ Play menu music
        startKeyListener();
        running = true; // ✅ Reset running flag so menu can restart
    
        while (running) {
            BackgroundMusic.stopMusic(); // ✅ Stop any previous music
            terminal.puts(InfoCmp.Capability.clear_screen);
            printTitle();
            printMenu();
            terminal.flush();
            sleep(100);
        }
    }    
    
    

    // ✅ Uses JNA's GetAsyncKeyState to detect key presses
    public static void startKeyListener() {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.submit(() -> {
            while (running) {
                if (isKeyPressed(0x57) && selected > 0) { // W key (Move Up)
                    selected--;
                    SoundEffects.playSound("navigate.wav");
                } else if (isKeyPressed(0x53) && selected < menuOptions.length - 1) { // S key (Move Down)
                    selected++;
                    SoundEffects.playSound("navigate.wav");
                } else if (isKeyPressed(0x0D)) { // Enter key (Select Option)
                    running = false;
                    SoundEffects.playSound("select.wav");
                    handleSelection(menuOptions[selected]);
                    return; // ✅ Exit listener to prevent looping issue
                } else if (isKeyPressed(0x51)) { // Q key (Back to menu)
                    SoundEffects.playSound("select.wav");
                    waitForReturnToMenu();
                    return;
                }
                sleep(100); // ✅ Prevents excessive CPU usage
            }
        });
    }
    

    private static void waitForReturnToMenu() {
        while (true) {
            if (isKeyPressed(0x51)) { // 'Q' key
                running = true; // ✅ Allow interaction in the main menu
                sleep(100); // ✅ Prevent CPU overuse

    
                try {
                    BackgroundMusic.stopMusic(); // ✅ Stop current screen music
                    terminal.puts(InfoCmp.Capability.clear_screen);
                    startKeyListener(); // ✅ Restart key input listener
                    displayMenu(); // ✅ Directly call the main menu
                } catch (Exception e) {
                    e.printStackTrace();
                }
    
                return; // ✅ Exit function properly
            }
            sleep(100); // ✅ Prevent CPU overuse
        }
    }
    

    // ✅ Checks if a key is currently pressed
    public static boolean isKeyPressed(int keyCode) {
        return (User32.INSTANCE.GetAsyncKeyState(keyCode) & 0x8000) != 0;
    }

    // ✅ Prints the title and box
    private static void printTitle() {
        String[] title = {
            "██████████████████████████████████████████████████████████████████████████████████████████████████████████████████████████████████████████████████████████████████████████████████",
            "██                                                                                                                                                                              ██",
            "██                                                                                                                                                                              ██",
            "██                                                                                                                                                                              ██",
            "██                                                                                                                                                                              ██",
            "██                                                                                                                                                                              ██",
            "██                                                                                                                                                                              ██",
            "██                                                                                                                                                                              ██",
            "██                                                                                                                                                                              ██",
            "██                                                                                                                                                                              ██",
            "██                                                                                                                                                                              ██",
            "██                                                                                                                                                                              ██",
            "██                                     █████     ███         █████████         ███████████         █████████████       █████████████                                            ██",
            "██                                     ██████    ███       ███       ███       ███       ███            ███            ███                                                      ██",
            "██                                     ███ ███   ███       ███       ███       ███       ███            ███            █████████                                                ██",
            "██                                     ███  ███  ███       ███       ███       ███       ███            ███            ███                                                      ██",
            "██                                     ███   ███ ███       ███       ███       ██████████               ███            ███                                                      ██",
            "██                                     ███    ██████  ███  ███       ███  ███  ███       ███  ███       ███       ███  █████████████  ███                                       ██",
            "██                                     ███     █████  ███    █████████    ███  ███       ███  ███       ███       ███  █████████████  ███                                       ██",
            "██                                                                                                                                                                              ██",
            "██                                                                                                                                                                              ██",
            "██                                                                                                                                                                              ██",
            "██                                                   Navigating Our Remarkable Tourist Attractions and Exploring History                                                        ██",
            "██                                                                                                                                                                              ██",
            "██                                                                                                                                                                              ██",
            "██                                                                                                                                                                              ██",
            "██                                                                                                                                                                              ██",
            "██                                                                                                                                                                              ██",
            "██                                                                                                                                                                              ██",
            "██                                                                                                                                                                              ██",
            "██                                                                                                                                                                              ██",
            "██                                                                                                                                                                              ██",
            "██                                                                                                                                                                              ██",
            "██                                                                                                                                                                              ██",
            "██                                                                                                                                                                              ██",
        };
        for (String line : title) {
            terminal.writer().println(line);
        }
    }

    // ✅ Prints the menu centered within the box
    private static void printMenu() {
        int width = 166; // Total width of the menu box
        int borderPadding = 8; // Ensures total width consistency
    
        try { 
            for (int i = 0; i < menuOptions.length; i++) {
                String option = menuOptions[i];
                int extraChars = (i == selected) ? 4 : 2; // " > " and " < " add extra width
                int padding = (width - option.length() - extraChars) / 2; // Ensures equal padding
    
                // ✅ Keep both borders fixed and properly aligned
                String formattedOption = String.format("██%-" + (width + borderPadding) + "s██", 
                                            " ".repeat(padding) + 
                                            (i == selected ? "> " : "  ") + option + 
                                            (i == selected ? " <" : ""));
    
                // ✅ Print the formatted option
                terminal.writer().println(formattedOption);
    
                // ✅ Add 2 empty lines after each option (with borders)
                String emptyLine = "██" + " ".repeat(width + borderPadding) + "██";
                terminal.writer().println(emptyLine);
                terminal.writer().println(emptyLine);
            }
            terminal.writer().println("██████████████████████████████████████████████████████████████████████████████████████████████████████████████████████████████████████████████████████████████████████████████████");
    
            terminal.flush(); // ✅ Force immediate output refresh
    
        } catch (Exception e) {
            terminal.writer().println("⚠ Error Rendering Menu: " + e.getMessage());
            terminal.flush();
        }
    }
    

    public static void startGameScreen() throws Exception {
        try {
            Scratch.main(new String[]{}); // Pass an empty String array
        } catch (IOException e) {
            e.printStackTrace(); // Handle any IOExceptions
        }
    }

    public static void howToPlayScreen() throws Exception{
        terminal.writer().println("████████████████████████████████████████████████████████████████████████████████████████████████████████████████████████████████████████████████████████████████████████████████████████████████████████");
        terminal.writer().println("██                                                                                                                                                                                                    ██");
        terminal.writer().println("██                                                                                                                                                                                                    ██");
        terminal.writer().println("██                                                                                                                                                                                                    ██");
        terminal.writer().println("██                           ████     ████   █████████   ████         ████        █████████████   ████████          ███████████   ████            █████████   ████     ████                           ██");
        terminal.writer().println("██                           ████     ████ █████████████ ████   ████  ████        █████████████ █████████████       █████████████ ████          █████████████ ████     ████                           ██");
        terminal.writer().println("██                           █████████████ ████      ███ ████   ████  ████             ████     ████     ████       ████     ████ ████          ███       ███ ██████ ██████                           ██");
        terminal.writer().println("██                           █████████████ ████      ███ ████   ████  ████             ████     ████     ████       █████████████ ████          █████████████   █████████                             ██");
        terminal.writer().println("██                           ████     ████ ████      ███ ████   ████  ████             ████     ████     ████       ███████████   ████          █████████████      ███                                ██");
        terminal.writer().println("██                           ████     ████ █████████████ █████████████████             ████     █████████████       ████          █████████████ █████████████      ███                                ██");
        terminal.writer().println("██                           ████     ████   █████████     █████████████               ████       ████████          ████            ███████████ ███       ███      ███                                ██");
        terminal.writer().println("██                                                                                                                                                                                                    ██");
        terminal.writer().println("██                                                                                                                                                                                                    ██");
        terminal.writer().println("██                           1. Press \"START\" to start the simulation.                                                                                                                                ██");
        terminal.writer().println("██                           2. After selecting \"START\", you will be assigned a random starting point called Stop-over wherein you can buy native products of Camarines Norte.                        ██");
        terminal.writer().println("██                           3. Select your destination by using the left arrow key and right arrow key.                                                                                              ██");
        terminal.writer().println("██                           4. After selecting your destination, select the route you want to take by using the left arrow key and right arrow key.                                                  ██");
        terminal.writer().println("██                           5. After selecting your destination, select the type of vehicle you want to use by using the left arrow key and right arrow key.                                         ██");
        terminal.writer().println("██                           6. If you encounter a tourist destination along the way, you can choose to proceed with your travels or stop by the destination.                                         ██");
        terminal.writer().println("██                           7. After stopping by or reaching your destination, the historical facts of the destination will be displayed and you can enter “CONTINUE” to proceed.                    ██");
        terminal.writer().println("██                           8. Upon reaching the end of your travel, the summary will be displayed including the following:                                                                          ██");
        terminal.writer().println("██                                 a. Total number of tourist destinations travelled                                                                                                                  ██");
        terminal.writer().println("██                                 b. Total number of distance travelled                                                                                                                              ██");
        terminal.writer().println("██                                 c. Mode/s of transportation used                                                                                                                                   ██");
        terminal.writer().println("██                                 d. Products bought                                                                                                                                                 ██");
        terminal.writer().println("██                                 e. Total expenses                                                                                                                                                  ██");
        terminal.writer().println("██                                                                                                                                                                                                    ██");
        terminal.writer().println("██                                                                                                                                                                                                    ██");
        terminal.writer().println("██                                                                                                                                                                                                    ██");
        terminal.writer().println("██                                                                                                                                                                                                    ██");
        terminal.writer().println("██                                                                                                                                                                                                    ██");
        terminal.writer().println("██                                                                                                                                                                                                    ██");
        terminal.writer().println("██                                                                                                                                                                                                    ██");
        terminal.writer().println("██                                                                                       [Press 'Q' to go BACK]                                                                                       ██");
        terminal.writer().println("██                                                                                                                                                                                                    ██");
        terminal.writer().println("██                                                                                                                                                                                                    ██");
        terminal.writer().println("██                                                                                                                                                                                                    ██");
        terminal.writer().println("██                                                                                                                                                                                                    ██");
        terminal.writer().println("██                                                                                                                                                                                                    ██");
        terminal.writer().println("██                                                                                                                                                                                                    ██");
        terminal.writer().println("██                                                                                                                                                                                                    ██");
        terminal.writer().println("████████████████████████████████████████████████████████████████████████████████████████████████████████████████████████████████████████████████████████████████████████████████████████████████████████");

        waitForReturnToMenu();
    }

    public static void optionScreen(){
        terminal.writer().println("Options go here: ");
    }

    public static void creditsScreen() throws Exception{
        terminal.writer().println("████████████████████████████████████████████████████████████████████████████████████████████████████████████████████████████████████████████████████████████████████████████████████████████████████████");
        terminal.writer().println("██                                                                                                                                                                                                    ██");
        terminal.writer().println("██                                                                                                                                                                                                    ██");
        terminal.writer().println("██                                                                                                                                                                                                    ██");
        terminal.writer().println("██                                                 ████████    ██████████     ████████   ███       ███ █████████████        ███      ███    ██████████                                                ██");
        terminal.writer().println("██                                               ████████████  ███████████  ████████████ ███       ███ █████████████        ███      ███  ████████████                                                ██");
        terminal.writer().println("██                                               ███      ███  ███      ███ ███      ███ ███       ███      ███             ███      ███  ███                                                         ██");
        terminal.writer().println("██                                               ███      ███  ██████████   ███      ███ ███       ███      ███             ███      ███    ████████                                                  ██");
        terminal.writer().println("██                                               ████████████  ███      ███ ███      ███ ███       ███      ███             ███      ███           ███                                                ██");
        terminal.writer().println("██                                               ████████████  ███████████   ██████████   ███████████       ███              ██████████   ███████████                                                 ██");
        terminal.writer().println("██                                               ███      ███  ██████████     ████████      ███████         ███               ████████    ██████████                                                  ██");
        terminal.writer().println("██                                                                                                                                                                                                    ██");
        terminal.writer().println("██                                                                                                                                                                                                    ██");
        terminal.writer().println("██                                                                                                                                                                                                    ██");
        terminal.writer().println("██                                                                                          TEAM DEBUGGABLES                                                                                          ██");
        terminal.writer().println("██                                                                                                                                                                                                    ██");
        terminal.writer().println("██                                                                                       Interface and Designer                                                                                       ██");
        terminal.writer().println("██                                                                                                                                                                                                    ██");
        terminal.writer().println("██                                                                                               Manuel                                                                                               ██");
        terminal.writer().println("██                                                                                               Niecel                                                                                               ██");
        terminal.writer().println("██                                                                                                                                                                                                    ██");
        terminal.writer().println("██                                                                                Functionality and Audio Implementors                                                                                ██");
        terminal.writer().println("██                                                                                                                                                                                                    ██");
        terminal.writer().println("██                                                                                                Dion                                                                                                ██");
        terminal.writer().println("██                                                                                               Emilyn                                                                                               ██");
        terminal.writer().println("██                                                                                                                                                                                                    ██");
        terminal.writer().println("██                                                                                                                                                                                                    ██");
        terminal.writer().println("██                                                                                                                                                                                                    ██");
        terminal.writer().println("██                                                                                                                                                                                                    ██");
        terminal.writer().println("██                                                                                                                                                                                                    ██");
        terminal.writer().println("██                                                                                                                                                                                                    ██");
        terminal.writer().println("██                                                                                                                                                                                                    ██");
        terminal.writer().println("██                                                                                                                                                                                                    ██");
        terminal.writer().println("██                                                                                       [Press 'Q' to go BACK]                                                                                       ██");
        terminal.writer().println("██                                                                                                                                                                                                    ██");
        terminal.writer().println("██                                                                                                                                                                                                    ██");
        terminal.writer().println("██                                                                                                                                                                                                    ██");
        terminal.writer().println("██                                                                                                                                                                                                    ██");
        terminal.writer().println("██                                                                                                                                                                                                    ██");
        terminal.writer().println("██                                                                                                                                                                                                    ██");
        terminal.writer().println("██                                                                                                                                                                                                    ██");
        terminal.writer().println("████████████████████████████████████████████████████████████████████████████████████████████████████████████████████████████████████████████████████████████████████████████████████████████████████████");

        waitForReturnToMenu();
    }

    private static void handleSelection(String option) {
        try {
            terminal.puts(InfoCmp.Capability.clear_screen);
            BackgroundMusic.stopMusic(); // ✅ Stop menu music before entering new screen
    
            switch (option) {
                case "START":
                    startGameScreen();
                    break;
                case "HOW TO PLAY":
                    howToPlayScreen();
                    break;
                case "OPTIONS":
                    optionScreen();
                    break;
                case "CREDITS":
                    creditsScreen();
                    break;
                case "EXIT":
                    System.exit(0);
                    break;
            }
    
            running = true; // ✅ Ensure the menu is interactable again
    
        } catch (Exception ignored) {}
    }
    
    
    // ✅ Small sleep function to avoid CPU overuse
    public static void sleep(int ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException ignored) {}
    }
}
