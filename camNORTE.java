package com.simulation;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;
import org.jline.utils.InfoCmp;

import com.simulation.App.User32;

public class camNORTE {
    private static Terminal terminal;

	public static final String ANSI_RESET = "\u001B[0m";
	public static final String ANSI_RED = "\u001B[31m";
	public static final String ANSI_GREEN = "\u001B[32m";
	public static final String ANSI_YELLOW = "\u001B[33m";
	public static final String ANSI_BLUE = "\u001B[34m";
	public static final String ANSI_MAGENTA = "\u001B[35m";
	public static final String ANSI_CYAN = "\u001B[36m";
	public static final String ANSI_bRED = "\u001B[91m";
	public static final String ANSI_bGREEN = "\u001B[92m";
	public static final String ANSI_bYELLOW = "\u001B[93m";
	public static final String ANSI_bBLUE = "\u001B[94m";
	public static final String ANSI_bMAGENTA = "\u001B[95m";
	public static final String ANSI_bCYAN = "\u001B[96m";

    private static boolean running = true;

    static Random random = new Random();
    private static int selectedIndex = 0;

	private static final String[] destinations = {
        "Bagasbas Beach", "Bantayog", "Mt. Labo", "Tan-awang Bato", "Mangcamagong Beach",
		 "Mercedes Fish Port", "Capalonga Lighthouse", "Gateway to Bicolandia", "St. Peter the Apostle Church", 
		 "St. Francis Asisi Church", "Mananap Falls", "Adelaida Native Farm", "Turayog"," Parroquia de Nuestra Señora de Candelaria"
    };

	private static final String[] routes = {
        "Daet-Bagasbas",randomDistance(),
        "Daet-Bantayog",randomDistance(),
        "Daet-MercedesFishPort",randomDistance(), 
        "Daet-MangcamagongBeach",randomDistance(), 
        "Daet-StFrancisAsisiParishChurch",randomDistance(), 
        "Daet-MananapFalls",randomDistance(),
        "Daet-AdelaidaNativeFarm",randomDistance(), 
        "Labo1-MtLabo",randomDistance(),
        "Labo1-TanawangBato",randomDistance(), 
        "Labo1-StFrancisAsisiParishChurch",randomDistance(),
        "Labo1-StPetertheApostleParishChurch",randomDistance(),
		"Labo1-Vinzons",randomDistance(), 
        "Labo1-Turayog",randomDistance(), 
        "Labo1-Paracale",randomDistance(), 
        "Labo2-MtLabo",randomDistance(), 
        "Labo2-TanawangBato",randomDistance(), 
        "Labo2-Capalonga",randomDistance(),
        "Labo2-SantaElena",randomDistance(), 
        "Labo1-Labo2",randomDistance(), 
        "Capalonga-CapalongaLighthouse", randomDistance(),
        "SantaElena-GatewaytoBicolandia",randomDistance(), 
        "SantaElena-Capalonga",randomDistance(), 
        "SantaElena-Labo2",randomDistance(), 
        "Capalonga-SantaElena",randomDistance(), 
        "Capalonga-Labo2",randomDistance(), 
        "Paracale-ParroquiadeNuestraSeñoradeCandelaria",randomDistance(), 
        "Paracale-Turayog",randomDistance(), 
		"Paracale-Labo1",randomDistance(), 
        "Vinzons-StPetertheApostleParishChurch",randomDistance(), 
        "Vinzons-StFrancisofAssisiParishChurch",randomDistance(), 
        "Vinzons-Labo1",randomDistance()
	};


	private static final String[] vehicles = {"Car", "Motorcycle", "Van", "Bus", "Tricycle", "Jeepney"};

    public static void tourSim() throws Exception {
        try {
            // terminal = TerminalBuilder.builder().system(true).build();
			// terminal = TerminalBuilder.builder().dumb(true).build();

			try {
				terminal = TerminalBuilder.builder().system(true).build();
			} catch (IOException e) {
				System.out.println("⚠️ Warning: Unable to create a system terminal. Switching to fallback.");
				try {
					terminal = TerminalBuilder.builder().dumb(true).build(); // ✅ Fallback to dumb terminal
				} catch (IOException ex) {
					ex.printStackTrace();
					return; // 🔴 Exit if terminal initialization completely fails
				}
			}
			

			while(true){
				clearScreen();
				// Display the map and dialogue box
				drawMapWithDialogue();
				// Start listening for 'F' key press
				startKeyListener();
			}

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

	private static void displayDestinationSelection() {
        System.out.println("Select a Tourist Destination:");
        for (int i = 0; i < destinations.length; i++) {
            if (i == selectedIndex) {
                System.out.println("▶ " + destinations[i]);
            } else {
                System.out.println("  " + destinations[i]);
            }
        }
    }

    private static void handleUserInput() throws Exception {
        while (true) {
            if ((User32.INSTANCE.GetAsyncKeyState(0x26) & 0x8000) != 0) { // Up arrow key
                selectedIndex = (selectedIndex - 1 + destinations.length) % destinations.length;
                break;
            }
            if ((User32.INSTANCE.GetAsyncKeyState(0x28) & 0x8000) != 0) { // Down arrow key
                selectedIndex = (selectedIndex + 1) % destinations.length;
                break;
            }
            if ((User32.INSTANCE.GetAsyncKeyState(0x0D) & 0x8000) != 0) { // Enter key
                if (selectedIndex == destinations.length - 1) {
                    System.out.println("Returning to Main Menu...");
                    return;
                } else {
                    selectRoute(destinations[selectedIndex]);
                }
                break;
            }
            Thread.sleep(100); // Prevents excessive CPU usage
        }
    }

	private static void selectRoute(String destination) {
        System.out.println("\nFinding routes to: " + destination);
        for (int i = 0; i < routes.length; i += 2) {
            if (routes[i].contains(destination)) {
                double distanceMeters = Double.parseDouble(routes[i + 1]);
                double distanceKm = distanceMeters / 1000.0;
                System.out.printf("Route: %s | Distance: %.0f meters\n", routes[i], distanceMeters);
                displayVehicleCosts(distanceKm);
            }
        }
    }

	private static void displayVehicleCosts(double distanceKm) {
        System.out.println("\nSelect a vehicle:");
        for (int i = 0; i < vehicles.length; i++) {
            double cost = calculateCost(i, distanceKm);
            System.out.printf("%d. %s - ₱%.2f\n", i + 1, vehicles[i], cost);
        }
    }

	private static double calculateCost(int vehicleIndex, double distanceKm) {
        switch (vehicleIndex) {
            case 0: // Car
                return 40 + (distanceKm * 15) + (distanceKm * 2);
            case 1: // Motorcycle
                return 20 + (distanceKm * 12);
            case 2: // Van
                return 20 + (distanceKm * 10);
            case 3: // Bus
                return 12 + (distanceKm * 5);
            case 4: // Tricycle
                return 10 + (distanceKm * 8);
            case 5: // Jeepney
                return 12 + (Math.max(0, distanceKm - 4) * 1.5);
            default:
                return 0;
        }
    

	private static List<String[]> getRoutesFrom(String location) {
        List<String[]> matchedRoutes = new ArrayList<>();
        for (int i = 0; i < routes.length; i += 7) {
            if (routes[i].startsWith(location)) {
                matchedRoutes.add(Arrays.copyOfRange(routes, i, i + 7));
            }
        }
        return matchedRoutes;
    }

	// This will be changed
	private static int selectRoute(List<String[]> routes) {
        System.out.println("\nAvailable Routes:");
        for (int i = 0; i < routes.size(); i++) {
            System.out.printf("%d. %s\n", i + 1, routes.get(i)[0]);
        } // This will be replaced with a box where the user selects the tourist destination
        return Integer.parseInt(reader.readLine("Select a route: ")) - 1;
    }

	// This will be removed and replaced with the table version
	private static int selectVehicle() {
        System.out.println("\nSelect a vehicle:");
        for (int i = 0; i < vehicles.length; i++) {
            System.out.printf("%d. %s\n", i + 1, vehicles[i]);
        }
        return Integer.parseInt(reader.readLine("Enter your choice: ")) - 1;
    }

	private static String getRandomStopover() {
        String[] stopovers = {"Daet", "Labo1", "Labo2", "Paracale", "Capalonga", "Santa Elena", "Vinzons"};
        return stopovers[random.nextInt(stopovers.length)];
    }

	private static void clearScreen() {
		try {
			if (terminal != null) {
				terminal.puts(InfoCmp.Capability.clear_screen);
				terminal.flush();
			} else {
				System.out.print("\033[H\033[2J");
				System.out.flush();
			}
		} catch (Exception e) { // 🔹 Catching a general Exception instead of IOException
			System.out.println("Failed clear screen. Proceeding...");
		}
	}

	
    private static void drawMapWithDialogue() throws IOException {
		terminal.writer().println("▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄");
		terminal.writer().println("██                                                                                                                                                                                                     ");
		terminal.writer().println("██      < BACK >                                                                                                                                                                                       ");
		
		terminal.writer().print("██                                     ");
		terminal.writer().print(ANSI_RED + "█████████♥█           " + ANSI_RESET);
		terminal.writer().print(ANSI_GREEN + "████████                                                " + ANSI_RESET);
		terminal.writer().println("▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄             ");
		
		terminal.writer().print("██                                    ");
		terminal.writer().print(ANSI_RED + "███████─┐██            " + ANSI_RESET);
		terminal.writer().print(ANSI_GREEN + "███──┐███" + ANSI_RESET);
		terminal.writer().print(ANSI_MAGENTA + "┌──┐█                                          " + ANSI_RESET);
		terminal.writer().println("█                                                                   █             ");
		
		terminal.writer().print("██                                    ");
		terminal.writer().print(ANSI_RED + "█┌─┐████│██" + ANSI_RESET);
		terminal.writer().print(ANSI_GREEN + "███████     ██♥██│███" + ANSI_RESET);
		terminal.writer().print(ANSI_MAGENTA + "│██│█                                          " + ANSI_RESET);
		terminal.writer().println("█                                                                   █             ");
		
		terminal.writer().print("██                               " + ANSI_RESET);
		terminal.writer().print(ANSI_RED + "█████┌┘█└───O┘██" + ANSI_RESET);
		terminal.writer().print(ANSI_GREEN + "███████     ┌──┐█└┐██" + ANSI_RESET);
		terminal.writer().print(ANSI_MAGENTA + "└┐█└─┐█                                        " + ANSI_RESET);
		terminal.writer().println("█                     Will you buy native products?                 █             ");
		
		terminal.writer().print("██                               ");
		terminal.writer().print(ANSI_RED + "████┌┘██████│███" + ANSI_RESET);
		terminal.writer().print(ANSI_GREEN + "████████████│██│██│███" + ANSI_RESET);
		terminal.writer().print(ANSI_MAGENTA + "│███│█                                        " + ANSI_RESET);
		terminal.writer().println("█                                                                   █             ");
		
		terminal.writer().print("██                               ");
		terminal.writer().print(ANSI_RED + "████│███████│███" + ANSI_RESET);
		terminal.writer().print(ANSI_GREEN + "█████┌──────┘██└┐█└┐██" + ANSI_RESET);
		terminal.writer().print(ANSI_MAGENTA + "└─┐█└┐♥                                       " + ANSI_RESET);
		terminal.writer().println("█                     [Press 'F' to View Products]                  █             ");
		
		terminal.writer().print("██                           ");
		terminal.writer().print(ANSI_RED + "██████┌─┘███████│███" + ANSI_RESET);
		terminal.writer().print(ANSI_GREEN + "█████│██████████│██└─┐" + ANSI_RESET);
		terminal.writer().print(ANSI_MAGENTA + "██│██└─┐██████" + ANSI_RESET);
		terminal.writer().println("                                █                                                                   █             ");
		
		terminal.writer().print("██                           ");
		terminal.writer().print(ANSI_RED + "██████│█████████└┐███" + ANSI_RESET);
		terminal.writer().print(ANSI_GREEN + "████└┐█████████└┐███└" + ANSI_RESET);
		terminal.writer().print(ANSI_MAGENTA + "┐█│████│██┌─██                               " + ANSI_RESET);
		terminal.writer().println(" █                                                                   █             ");
		
		terminal.writer().print("██                         ");
		terminal.writer().print(ANSI_YELLOW + "██" + ANSI_RESET);
		terminal.writer().print(ANSI_RED + "██████│██████████│███" + ANSI_RESET);
		terminal.writer().print(ANSI_GREEN + "█████└──────┐███└┐█" + ANSI_RESET);
		terminal.writer().print(ANSI_MAGENTA + "██└─┴┐███├──┘██" + ANSI_RESET);
		terminal.writer().print(ANSI_bYELLOW + "█                               " + ANSI_RESET);
		terminal.writer().println(" █████████████████████████████████████████████████████████████████████             ");
		
		terminal.writer().print("██                          ");
		terminal.writer().print(ANSI_YELLOW + "█" + ANSI_RESET);
		terminal.writer().print(ANSI_RED + "█████" + ANSI_RESET);
		terminal.writer().print(ANSI_YELLOW + "█" + ANSI_RESET);
		terminal.writer().print(ANSI_RED + "│██████████│███" + ANSI_RESET);
		terminal.writer().print(ANSI_GREEN + "████████████│████└┐" + ANSI_RESET);
		terminal.writer().print(ANSI_MAGENTA + "█████└┐██│█████" + ANSI_RESET);
		terminal.writer().print(ANSI_bYELLOW + "████                                                                                                               " + ANSI_RESET);
		terminal.writer().println(" ");
		
		terminal.writer().print("██                          ");
		terminal.writer().print(ANSI_YELLOW + "███████│" + ANSI_RESET);
		terminal.writer().print(ANSI_RED + "██████████└┐██" + ANSI_RESET);
		terminal.writer().print(ANSI_GREEN + "████████████└─┐███└─" + ANSI_RESET);
		terminal.writer().print(ANSI_MAGENTA + "─┐███├──┘█████" + ANSI_RESET);
		terminal.writer().print(ANSI_bYELLOW + "████                                                                                                               " + ANSI_RESET);
		terminal.writer().println(" ");
		
		terminal.writer().print("██                       ");
		terminal.writer().print(ANSI_YELLOW + "██████████│█████" + ANSI_RESET);
		terminal.writer().print(ANSI_RED + "████" + ANSI_RESET);
		terminal.writer().print(ANSI_BLUE + "██│██████" + ANSI_RESET);
		terminal.writer().print(ANSI_GREEN + "██████████│█████" + ANSI_RESET);
		terminal.writer().print(ANSI_MAGENTA + "█└───O████████" + ANSI_RESET);
		terminal.writer().print(ANSI_bYELLOW + "███████                                                                                                            " + ANSI_RESET);
		terminal.writer().println(" ");
		
		terminal.writer().print("██                       ");
		terminal.writer().print(ANSI_YELLOW + "██████████│█████" + ANSI_RESET);
		terminal.writer().print(ANSI_BLUE + "██████└──┐██████████" + ANSI_RESET);
		terminal.writer().print(ANSI_GREEN + "███│█████" + ANSI_RESET);
		terminal.writer().print(ANSI_MAGENTA + "█████│████████" + ANSI_RESET);
		terminal.writer().print(ANSI_bYELLOW + "█████████                                                                                                          " + ANSI_RESET);
		terminal.writer().println(" ");
		
		terminal.writer().print("██                      ");
		terminal.writer().print(ANSI_YELLOW + "███████████│███" + ANSI_RESET);
		terminal.writer().print(ANSI_BLUE + "███████████└──┐███████" + ANSI_RESET);
		terminal.writer().print(ANSI_GREEN + "███│█" + ANSI_RESET);
		terminal.writer().print(ANSI_BLUE + "███████" + ANSI_RESET);
		terminal.writer().print(ANSI_MAGENTA + "██│████");
		terminal.writer().print(ANSI_BLUE + "██████" + ANSI_RESET);
		terminal.writer().print(ANSI_bYELLOW + "███████                                                                                                          " + ANSI_RESET);
		terminal.writer().println(" ");
		
		terminal.writer().print("██                       ");
		terminal.writer().print(ANSI_YELLOW + "███████┌──O───" + ANSI_RESET);
		terminal.writer().print(ANSI_BLUE + "─────┐████████└─┐████████└┐█████████│████┌───" + ANSI_RESET);
		terminal.writer().print(ANSI_bYELLOW + "─────┐███");
		terminal.writer().print(ANSI_bRED + "█                                                                                                         " + ANSI_RESET);
		terminal.writer().println(" ");
		
		terminal.writer().print("██                      ");
		terminal.writer().print(ANSI_YELLOW + "████████│██████" + ANSI_RESET);
		terminal.writer().print(ANSI_BLUE + "█████└┐█████████│█████████│██████┌──O────┴┐██" + ANSI_RESET);
		terminal.writer().print(ANSI_bYELLOW + "███♥┌┘██" + ANSI_RESET);
		terminal.writer().print(ANSI_bRED + "████                                                                                                       " + ANSI_RESET);
		terminal.writer().println(" ");
		
		terminal.writer().print("██                       ");
		terminal.writer().print(ANSI_YELLOW + "██████┌┘█████" + ANSI_RESET);
		terminal.writer().print(ANSI_BLUE + "███████└─────────O┐████┌───┴────┬─┘████████└┐█" + ANSI_RESET);
		terminal.writer().print(ANSI_bYELLOW + "████│█" + ANSI_RESET);
		terminal.writer().print(ANSI_bRED + "███████                                                                                                      " + ANSI_RESET);
		terminal.writer().println(" ");
		
		terminal.writer().print("██                       ");
		terminal.writer().print(ANSI_YELLOW + "██████│██████" + ANSI_RESET);
		terminal.writer().print(ANSI_BLUE + "██████████████████└────┘████████│███████████└─" + ANSI_RESET);
		terminal.writer().print(ANSI_bYELLOW + "────O" + ANSI_RESET);
		terminal.writer().print(ANSI_bRED + "█████████                                                                                                     " + ANSI_RESET);
		terminal.writer().println(" ");
		
		terminal.writer().print("██                        ");
		terminal.writer().print(ANSI_YELLOW + "████┌┘██████    " + ANSI_RESET);
		terminal.writer().print(ANSI_BLUE + "████████████████████████████│♥████████████" + ANSI_RESET);
		terminal.writer().print(ANSI_bYELLOW + "██" + ANSI_RESET);
		terminal.writer().print(ANSI_bRED + "██└┐████████                                                                                                     " + ANSI_RESET);
		terminal.writer().println(" ");
		
		terminal.writer().print("██                     ");
		terminal.writer().print(ANSI_YELLOW + "███████│████              " + ANSI_RESET);
		terminal.writer().print(ANSI_BLUE + "████████████████████┌┘███████████" + ANSI_RESET);
		terminal.writer().print(ANSI_bGREEN + "██████" + ANSI_RESET);
		terminal.writer().print(ANSI_bRED + "♥└┐█" + ANSI_RESET);
		terminal.writer().print(ANSI_bBLUE + "████████" + ANSI_RESET);
		terminal.writer().print(ANSI_bCYAN + "♥██                                                                                                " + ANSI_RESET);
		terminal.writer().println(" ");
		
		terminal.writer().print("██                    ");
		terminal.writer().print(ANSI_YELLOW + "████████│████              " + ANSI_RESET);
		terminal.writer().print(ANSI_BLUE + "████████████████████│██████████" + ANSI_RESET);
		terminal.writer().print(ANSI_bGREEN + "████████" + ANSI_RESET);
		terminal.writer().print(ANSI_bRED + "█" + ANSI_RESET);
		terminal.writer().print(ANSI_bBLUE + "█└┬──┐█████" + ANSI_RESET);
		terminal.writer().print(ANSI_bCYAN + "████                                                                                               " + ANSI_RESET);
		terminal.writer().println(" ");
		
		terminal.writer().print("██                   ");
		terminal.writer().print(ANSI_YELLOW + "████████┌┘███                   " + ANSI_RESET);
		terminal.writer().print(ANSI_BLUE + "████████████████│██████████" + ANSI_RESET);
		terminal.writer().print(ANSI_bGREEN + "████████" + ANSI_RESET);
		terminal.writer().print(ANSI_bBLUE + "███│██O────┬" + ANSI_RESET);
		terminal.writer().print(ANSI_bCYAN + "──┐█                                                                                               " + ANSI_RESET);
		terminal.writer().println(" ");
		
		terminal.writer().print("██                   ");
		terminal.writer().print(ANSI_YELLOW + "████████♥████                   " + ANSI_RESET);
		terminal.writer().print(ANSI_BLUE + "███████████████♥█████████");
		terminal.writer().print(ANSI_bGREEN + "██┌───────" + ANSI_RESET);
		terminal.writer().print(ANSI_bBLUE + "───┘♥█│██" + ANSI_RESET);
		terminal.writer().print(ANSI_CYAN + "██│♥█" + ANSI_RESET);
		terminal.writer().print(ANSI_bCYAN + "│██                                                                                              " + ANSI_RESET);
		terminal.writer().println(" ");
		
		terminal.writer().print("██                   ");
		terminal.writer().print(ANSI_YELLOW + "███████████                         " + ANSI_RESET);
		terminal.writer().print(ANSI_BLUE + "█████████████████████" + ANSI_RESET);
		terminal.writer().print(ANSI_bGREEN + "█┌┘█████" + ANSI_RESET);
		terminal.writer().print(ANSI_bMAGENTA + "██████" + ANSI_RESET);
		terminal.writer().print(ANSI_bBLUE + "┌─┘█" + ANSI_RESET);
		terminal.writer().print(ANSI_CYAN + "███│██│██                                                                                              " + ANSI_RESET);
		terminal.writer().println(" ");
		
		terminal.writer().print("██                                                            ");
		terminal.writer().print(ANSI_BLUE + "███████████" + ANSI_RESET);
		terminal.writer().print(ANSI_bGREEN + "██────┘██" + ANSI_RESET);
		terminal.writer().print(ANSI_bMAGENTA + "██████┌───┘█" + ANSI_RESET);
		terminal.writer().print(ANSI_CYAN + "█████│██│█                                                                                               " + ANSI_RESET);
		terminal.writer().println(" ");
		
		terminal.writer().print("██                                                                 ");
		terminal.writer().print(ANSI_BLUE + "██████" + ANSI_RESET);
		terminal.writer().print(ANSI_bGREEN + "█♥█" + ANSI_RESET);
		terminal.writer().print(ANSI_bMAGENTA + "█████████┌──┘███" + ANSI_RESET);
		terminal.writer().print(ANSI_CYAN + "████┌──┘██│                                                                                                " + ANSI_RESET);
		terminal.writer().println(" ");
		
		terminal.writer().print("██                                                                 ");
		terminal.writer().print(ANSI_BLUE + "█████" + ANSI_RESET);
		terminal.writer().print(ANSI_bGREEN + "██" + ANSI_RESET);
		terminal.writer().print(ANSI_bMAGENTA + "███████┌───┘██" + ANSI_RESET);
		terminal.writer().print(ANSI_CYAN + "███████┌┘████" + ANSI_RESET);
		terminal.writer().print(ANSI_bCYAN + "█│                                                                                                " + ANSI_RESET);
		terminal.writer().println(" ");
		
		terminal.writer().print("██                                                                      ");
		terminal.writer().print(ANSI_bMAGENTA + "████████─┘████" + ANSI_RESET);
		terminal.writer().print(ANSI_CYAN + "█████████│█████" + ANSI_RESET);
		terminal.writer().print(ANSI_bCYAN + "♥│                                                                                                " + ANSI_RESET);
		terminal.writer().println(" ");
		
		terminal.writer().print("██                                                                      ");
		terminal.writer().print(ANSI_bMAGENTA + "██████████♥█" + ANSI_RESET);
		terminal.writer().print(ANSI_CYAN + "███████████│█████" + ANSI_RESET);
		terminal.writer().print(ANSI_bCYAN + "█│███                                                                                             " + ANSI_RESET);
		terminal.writer().println(" ");
		
		terminal.writer().print("██          ");
		terminal.writer().print("<<      LEGEND      >>                                           ");
		terminal.writer().print(ANSI_bMAGENTA + "█████████" + ANSI_RESET);
		terminal.writer().print(ANSI_CYAN + "█████████│██████└─" + ANSI_RESET);
		terminal.writer().print(ANSI_bCYAN + "┐███                                                                                           " + ANSI_RESET);
		terminal.writer().println(" ");
		
		terminal.writer().print("██          ");
		terminal.writer().print(ANSI_RED + "██  " + ANSI_RESET);
		terminal.writer().print("-  CAPALONGA                                                     ");
		terminal.writer().print(ANSI_bMAGENTA + "███████" + ANSI_RESET);
		terminal.writer().print(ANSI_CYAN + "███████│████████" + ANSI_RESET);
		terminal.writer().print(ANSI_bCYAN + "└┐██                                                                                           " + ANSI_RESET);
		terminal.writer().println(" ");
		
		terminal.writer().print("██          ");
		terminal.writer().print(ANSI_GREEN + "██  " + ANSI_RESET);
		terminal.writer().print("-  JOSE PANGANIBAN                                                 ");
		terminal.writer().print(ANSI_bMAGENTA + "███████" + ANSI_RESET);
		terminal.writer().print(ANSI_CYAN + "█████│██████" + ANSI_RESET);
		terminal.writer().print(ANSI_bCYAN + "███└┐██                                                                                          " + ANSI_RESET);
		terminal.writer().println(" ");
		
		terminal.writer().print("██          ");
		terminal.writer().print(ANSI_YELLOW + "██  " + ANSI_RESET);
		terminal.writer().print("-  STA. ELENA                                                       ");
		terminal.writer().print(ANSI_bMAGENTA + "██████" + ANSI_RESET);
		terminal.writer().print(ANSI_CYAN + "████████████" + ANSI_RESET);
		terminal.writer().print(ANSI_bCYAN + "████│██                                                                                          " + ANSI_RESET);
		terminal.writer().println(" ");
		
		terminal.writer().print("██          ");
		terminal.writer().print(ANSI_BLUE + "██  " + ANSI_RESET);
		terminal.writer().print("-  LABO                                                                ");
		terminal.writer().print(ANSI_bMAGENTA + "███" + ANSI_RESET);
		terminal.writer().print(ANSI_CYAN + "██████████████" + ANSI_RESET);
		terminal.writer().print(ANSI_bCYAN + "██└┐█                                                                                          " + ANSI_RESET);
		terminal.writer().println(" ");
		
		terminal.writer().print("██          ");
		terminal.writer().print(ANSI_MAGENTA + "██  " + ANSI_RESET);
		terminal.writer().print("-  PARACALE                                                               ");
		terminal.writer().print(ANSI_CYAN + "██████████████" + ANSI_RESET);
		terminal.writer().print(ANSI_bCYAN + "███│█                                                                                          " + ANSI_RESET);
		terminal.writer().println(" ");
		
		terminal.writer().print("██          ");
		terminal.writer().print(ANSI_CYAN + "██  " + ANSI_RESET);
		terminal.writer().print("-  BASUD                                                                     ");
		terminal.writer().print(ANSI_CYAN + "████████████" + ANSI_RESET);
		terminal.writer().print(ANSI_bCYAN + "█████                                                                                         " + ANSI_RESET);
		terminal.writer().println(" ");
		
		terminal.writer().print("██          ");
		terminal.writer().print(ANSI_bRED + "██  " + ANSI_RESET);
		terminal.writer().print("-  TALISAY                                                                   ");
		terminal.writer().print(ANSI_CYAN + "██████████" + ANSI_RESET);
		terminal.writer().print(ANSI_bCYAN + "███████                                                                                         " + ANSI_RESET);
		terminal.writer().println(" ");
		
		terminal.writer().print("██          ");
		terminal.writer().print(ANSI_bGREEN + "██  " + ANSI_RESET);
		terminal.writer().print("-  SAN VICENTE                                                                  ");
		terminal.writer().print(ANSI_bCYAN + "██████████████                                                                                         " + ANSI_RESET);
		terminal.writer().println(" ");
		
		terminal.writer().print("██          ");
		terminal.writer().print(ANSI_bYELLOW + "██  " + ANSI_RESET);
		terminal.writer().print("-  VINSONZ                                                                        ");
		terminal.writer().print(ANSI_bCYAN + "███████████                                                                                          " + ANSI_RESET);
		terminal.writer().println(" ");
		
		terminal.writer().print("██          ");
		terminal.writer().print(ANSI_bBLUE + "██  " + ANSI_RESET);
		terminal.writer().print("-  DAET                                                                           ");
		terminal.writer().print(ANSI_bCYAN + "███████████                                                                                          " + ANSI_RESET);
		terminal.writer().println(" ");
		
		terminal.writer().print("██          ");
		terminal.writer().print(ANSI_bMAGENTA + "██  " + ANSI_RESET);
		terminal.writer().print("-  SAN LORENZO                                                                       ");
		terminal.writer().print(ANSI_bCYAN + "██████                                                                                            " + ANSI_RESET);
		terminal.writer().println(" ");
		
		terminal.writer().print("██          ");
		terminal.writer().print(ANSI_bCYAN + "██  " + ANSI_RESET);
		terminal.writer().print("-  MERCEDES                                                                           ");
		terminal.writer().print(ANSI_bCYAN + "█████                                                                                            " + ANSI_RESET);
		terminal.writer().println(" ");
		
		terminal.writer().println("██                                                                                                                                                                                                     ");
		terminal.writer().println("██                                                                         ██    ═══════════════════════════════════════    ██                                                                         ");
		terminal.writer().println("██                                                                       ███                TOURIST ATTRACTION               ███                                                                       ");
		terminal.writer().println("██                                                                         ██    ═══════════════════════════════════════    ██                                                                         ");
		terminal.writer().println("██                                                                                                                                                                                                     ");
		terminal.writer().println("█████████████████████████████████████████████████████████████████████████████████████████████████████████████████████████████████████████████████████████████████████████████████████████████████████████");

        terminal.flush();
    }

   public static void startKeyListener() {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.submit(() -> {
            while (running) {
               if ((User32.INSTANCE.GetAsyncKeyState(0x46) & 0x8000) != 0) { // 'F' Key (View Products)
                    SoundEffects.playSound("select.wav");
                    openProductView();
                    return; // ✅ Prevent looping issue
                } 
                sleep(100); // ✅ Prevents excessive CPU usage
            }
        });
    }

	private static void openProductView() {
		try {
			clearScreen();
			terminal.writer().println("\n[Product List Interface Here]");
			terminal.flush();
		} catch (Exception e) { // 🔹 Changed IOException to Exception
			e.printStackTrace();
		}
	}
	
    private static boolean isKeyPressed(int keyCode) {
        try {
            if (System.in.available() > 0) {
                int key = System.in.read();
                return key == keyCode;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static String randomDistance() {
        return String.valueOf(random.nextInt(91) + 10); // ✅ Generates 10-100 meters
    }

    public static double calculateTime(double distance) {
        double speed = 0.1; // ✅ Speed in meters per minute
        double timeInMinutes = distance / speed; // ✅ Time in minutes
        double timeInHours = timeInMinutes / 60; // ✅ Convert to hours
        return timeInHours;
    }

    // public static void calculateRouteCost(String[] selectedRoute) {
    //     double[] totalCost = new double[6]; // Stores total cost for each vehicle

    //     for (int i = 0; i < selectedRoute.length - 1; i++) {
    //         String routeKey = selectedRoute[i] + "-" + selectedRoute[i + 1]; // Format: "Start-End"

    //         // Search for the route in the 1D array
    //         for (int j = 0; j < routes.length; j++) {
    //             if (routes[j].equals(routeKey)) {
    //                 // Costs are stored after the route name, so read the next 6 values
    //                 for (int k = 0; k < 6; k++) {
    //                     totalCost[k] += Double.parseDouble(routes[j + 1 + k]);
    //                 }
    //                 break; // Exit loop once found
    //             }
    //         }
    //     }
    // }
    

    private static void sleep(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
