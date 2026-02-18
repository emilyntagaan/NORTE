package com.simulation;

import java.util.ArrayList;
import java.util.List;

import org.jline.reader.LineReader;
import org.jline.reader.LineReaderBuilder;
import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;

import com.simulation.screens.destination.WMBagasbasBeach;
import com.simulation.screens.destination.WMCapalongaLighthouse;
import com.simulation.screens.destination.WMGatewayToBicolandia;
import com.simulation.screens.destination.WMMananapFalls;
import com.simulation.screens.destination.WMMangcamagongBeach;
import com.simulation.screens.destination.WMMercedesFishPort;
import com.simulation.screens.destination.WMMountLabo;
import com.simulation.screens.destination.WMSenoraDeCandelaria;
import com.simulation.screens.destination.WMStFrancis;
import com.simulation.screens.destination.WMStPeter;
import com.simulation.screens.destination.WMTanAwangBato;
import com.simulation.screens.destination.WMTurayog;
import com.simulation.screens.loading.LSBus;
import com.simulation.screens.loading.LSCar;
import com.simulation.screens.loading.LSJeepney;
import com.simulation.screens.loading.LSMotorcycle;
import com.simulation.screens.loading.LSTricycle;
import com.simulation.screens.loading.LSVan;
import com.simulation.screens.narratives.AdelaidaNativeFarm;
import com.simulation.screens.narratives.BagasbasBeach;
import com.simulation.screens.narratives.CapalongaLighthouse;
import com.simulation.screens.narratives.GatewayToBicolandia;
import com.simulation.screens.narratives.MananapFalls;
import com.simulation.screens.narratives.MangcamagongBeach;
import com.simulation.screens.narratives.MercedesFishPort;
import com.simulation.screens.narratives.MountLabo;
import com.simulation.screens.narratives.SenoraDeCandelaria;
import com.simulation.screens.narratives.StFrancis;
import com.simulation.screens.narratives.StPeter;
import com.simulation.screens.narratives.TanAwangBato;
import com.simulation.screens.narratives.Turayog;
import com.sun.jna.Native;
import com.sun.jna.win32.StdCallLibrary;

public class TravelFlowManager {
    private static Terminal terminal = null;
    private static LineReader reader = null;
    private static List<String> visitedLocations = new ArrayList<>();
    private static List<String> usedVehicles = new ArrayList<>();

    public interface User32 extends StdCallLibrary {
        User32 INSTANCE = Native.load("user32", User32.class);
        short GetAsyncKeyState(int vKey);
    }

    static {
        try {
            terminal = TerminalBuilder.builder().system(true).build();
            reader = LineReaderBuilder.builder().terminal(terminal).build();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void startJourney(String vehicle, String destination) throws Exception {
        clearScreen();
        showLoadingScreen(vehicle);
        
        clearScreen();
        showDestinationTitle(destination);  // ✅ Only call this ONCE

        showNarrative(destination);  // ✅ Directly show the narrative
    
        visitedLocations.add(destination);
        usedVehicles.add(vehicle);

        listenForKeyPress();
    }   
    
    private static void clearScreen() {
        try {
            if (System.getProperty("os.name").contains("Windows")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                System.out.print("\033[H\033[2J");  
                System.out.flush();
            }
        } catch (Exception e) {
            terminal.writer().println("Error clearing screen.");
        }
    }


    // 1. Show the correct loading screen based on the selected vehicle
    public static void showLoadingScreen(String vehicle) {
        switch (vehicle.toLowerCase()) {
            case "car":
                new LSCar().display();
                break;
            case "motorcycle":
                new LSMotorcycle().display();
                break;
            case "van":
                new LSVan().display();
                break;
            case "bus":
                new LSBus().display();
                break;
            case "tricycle":
                new LSTricycle().display();
                break;
            case "jeepney":
                new LSJeepney().display();
                break;
            default:
                terminal.writer().println("Unknown vehicle selection.");
        }
    }

    // 2. Show the correct title screen for the selected destination
    public static void showDestinationTitle(String destination) {
        switch (destination.toLowerCase()) {
            case "mangcamagong beach":
                new WMMangcamagongBeach().display();
                break;
            case "bagasbas beach":
                new WMBagasbasBeach().display();
                break;
                case "mount labo" :
                new WMMountLabo().display();
                break;
                case "tan-awang bato" :
                new WMTanAwangBato().display();
                break;
                case "mercedes fish port" :
                new WMMercedesFishPort().display();
                break;
                case "capalonga lighthouse" :
                new WMCapalongaLighthouse().display();
                break ;
                case "gateway to bicolandia" :
                new WMGatewayToBicolandia().display();
                break;
                case "st. peter the apostle church" :
                new WMStPeter().display();
                break;
                case "st. francis asisi church" :
                new WMStFrancis().display();
                break;
                case "mananap Falls" :
                new WMMananapFalls().display();
                break;
                case "adelaida native farm" :
                new AdelaidaNativeFarm().display();
                break;
                case "taruyog" :
                new WMTurayog().display();
                break;
                case "parroquia nuestra señora de Candelaria" :
                new WMSenoraDeCandelaria().display();
                break;
            default:
                terminal.writer().println("Unknown destination.");
        }
        
        // Wait for user to press ENTER to continue to the narrative
        reader.readLine("Press ENTER to continue...");
    }

    // 3. Show the narrative information for the selected destination
    public static void showNarrative(String destination) {
        switch (destination.toLowerCase()) {
            case "mangcamagong beach":
                clearScreen();
                new MangcamagongBeach().display();
                break;
            case "bagasbas beach":
                clearScreen();
                new BagasbasBeach().display();
                break;
                case "mt. labo" :
                clearScreen();
                new MountLabo().display();
                break;
                case "tan-awang bato" :
                clearScreen();
                new TanAwangBato().display();
                break;
                case "mercedes fish port" :
                clearScreen();
                new MercedesFishPort().display();
                break;
                case "capalonga lighthouse" :
                clearScreen();
                new CapalongaLighthouse().display();
                break ;
                case "gateway to Bicolandia" :
                clearScreen();
                new GatewayToBicolandia().display();
                break;
                case "st. peter the apostle church" :
                clearScreen();
                new StPeter().display();
                break;
                case "st. francis asisi church" :
                clearScreen();
                new StFrancis().display();
                break;
                case "mananap Falls" :
                clearScreen();
                new MananapFalls().display();
                break;
                case "adelaida native farm" :
                clearScreen();
                new AdelaidaNativeFarm().display();
                break;
                case "taruyog" :
                clearScreen();
                new Turayog().display();
                break;
                case "parroquia nuestra señora de Candelaria" :
                clearScreen();
                new SenoraDeCandelaria().display();
                break;
            default:
                terminal.writer().println("Unknown narrative.");
        }
    }

    // 4. Give user options after reading the narrative
    private static void listenForKeyPress() throws Exception {

        while (true) {
            if ((User32.INSTANCE.GetAsyncKeyState(0x52) & 0x8000) != 0) { // Key 'R'
                Scratch.main(new String[]{}); // Respawn logic
                break;
            } 
            if ((User32.INSTANCE.GetAsyncKeyState(0x4D) & 0x8000) != 0) { // Key 'M'
                displaySummaryReport(); // ✅ Show summary before going back
                sleep(3000); // Pause to let the user read the summary
                App.displayMenu(); // Return to main menu
                break;
            }
            sleep(150); // Prevent excessive CPU usage
        }
    }

    private static void displaySummaryReport() {
        clearScreen();
        terminal.writer().println("+--------------------------------------+");
        terminal.writer().println("|         EXPLORATION SUMMARY          |");
        terminal.writer().println("+--------------------------------------+");

        terminal.writer().println("Locations Explored:");
        for (String location : visitedLocations) {
            terminal.writer().println("  - " + location);
        }

        terminal.writer().println("\nVehicles Used:");
        for (String vehicle : usedVehicles) {
            terminal.writer().println("  - " + vehicle);
        }
        
        terminal.writer().println("+--------------------------------------+");
        terminal.flush();
    }


    private static void sleep(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
        
}


