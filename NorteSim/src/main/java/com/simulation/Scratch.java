package com.simulation;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;

import com.sun.jna.Native;
import com.sun.jna.win32.StdCallLibrary;

public class Scratch {
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

    private static final Random random = new Random();
    private static final String[] stopovers = {"Daet", "Labo1", "Labo2", "Paracale", "Capalonga", "Santa Elena", "Vinzons"};
    private static final String[] destinations = {
        "Bagasbas Beach", "Bantayog", "Mt. Labo", "Tan-awang Bato", "Mangcamagong Beach",
        "Mercedes Fish Port", "Capalonga Lighthouse", "Gateway to Bicolandia", "St. Peter the Apostle Church",
        "St. Francis Asisi Church", "Mananap Falls", "Adelaida Native Farm", "Turayog", "Parroquia de Nuestra Señora de Candelaria"
    };
    private static final String[] vehicles = {"Car", "Motorcycle", "Van", "Bus", "Tricycle", "Jeepney"};
    private static String selectedVehicle;  // Store the selected vehicle

    private static List<String[]> routes = new ArrayList<>();

    static {
        // Daet Spawn
        routes.add(new String[]{"Daet", "Adelaida Native Farm", "Daet -> Adelaida Native Farm", String.valueOf(randomDistance())});
        routes.add(new String[]{"Daet", "Bagasbas Beach", "Daet -> Bagasbas", String.valueOf(randomDistance())});
        routes.add(new String[]{"Daet", "Bantayog", "Daet -> Bantayog", String.valueOf(randomDistance())});
        routes.add(new String[]{"Daet", "Capalonga Lighthouse", "Daet -> Vinzons -> Labo1 -> Labo2 -> Capalonga -> Capalonga Lighthouse", String.valueOf(randomDistance())});
        routes.add(new String[]{"Daet", "Capalonga Lighthouse", "Daet -> Vinzons -> Labo1 -> Paracale -> Labo2 -> Capalonga -> Capalonga Lighthouse", String.valueOf(randomDistance())});
        routes.add(new String[]{"Daet", "Gateway to Bicolandia", "Daet -> Vinzons -> Labo1 -> Labo2 -> Santa Elena -> Gateway to Bicolandia", String.valueOf(randomDistance())});
        routes.add(new String[]{"Daet", "Gateway to Bicolandia", "Daet -> Vinzons -> Labo1 -> Paracale -> Labo2 -> Santa Elena -> Gateway to Bicolandia", String.valueOf(randomDistance())});
        routes.add(new String[]{"Daet", "Gateway to Bicolandia", "Daet -> Vinzons -> Labo1 -> Labo2 -> Capalonga -> Santa Elena -> Gateway to Bicolandia", String.valueOf(randomDistance())});
        routes.add(new String[]{"Daet", "Gateway to Bicolandia", "Daet -> Vinzons -> Labo1 -> Paracale -> Labo2 -> Capalonga -> Santa Elena -> Gateway to Bicolandia", String.valueOf(randomDistance())});
        routes.add(new String[]{"Daet", "Mananap Falls", "Daet -> Mananap Falls", String.valueOf(randomDistance())});
        routes.add(new String[]{"Daet", "Mangcamagong Beach", "Daet -> Mangcamagong Beach", String.valueOf(randomDistance())});
        routes.add(new String[]{"Daet", "Mt. Labo", "Daet -> Vinzons -> Labo1 -> Mt. Labo", String.valueOf(randomDistance())});
        routes.add(new String[]{"Daet", "Tan-awang Bato", "Daet -> Vinzons -> Labo1 -> Tan-awang Bato", String.valueOf(randomDistance())});
        routes.add(new String[]{"Daet", "Mercedes Fish Port", "Daet -> Mercedes Fish Port", String.valueOf(randomDistance())});
        routes.add(new String[]{"Daet", "Parroquia de Nuestra Señora de Candelaria", "Daet -> Vinzons -> Labo1 -> Paracale -> Parroquia de Nuestra Señora de Candelaria", String.valueOf(randomDistance())});
        routes.add(new String[]{"Daet", "Gumaus Beach", "Daet -> Vinzons -> Labo1 -> Paracale -> Gumaus Beach", String.valueOf(randomDistance())});
        routes.add(new String[]{"Daet", "St. Francis of Assisi Parish Church", "Daet -> St. Francis of Assisi Parish Church", String.valueOf(randomDistance())});
        routes.add(new String[]{"Daet", "St. Peter the Apostle Parish Church", "Daet -> Vinzons -> St. Peter the Apostle Parish Church", String.valueOf(randomDistance())});
        routes.add(new String[]{"Daet", "Turayog", "Daet -> Vinzons -> Labo1 -> Paracale -> Turayog", String.valueOf(randomDistance())});
        routes.add(new String[]{"Daet", "Turayog", "Daet -> Vinzons -> Labo1 -> Turayog", String.valueOf(randomDistance())});        
        // Labo2 Spawn
        routes.add(new String[]{"Labo2", "Adelaida Native Farm", "Labo2 -> Labo1 -> Vinzons -> Daet -> Adelaida Native Farm", String.valueOf(randomDistance())});
        routes.add(new String[]{"Labo2", "Adelaida Native Farm", "Labo2 -> Paracale -> Labo1 -> Vinzons -> Daet -> Adelaida Native Farm", String.valueOf(randomDistance())});
        routes.add(new String[]{"Labo2", "Bagasbas Beach", "Labo2 -> Labo1 -> Vinzons -> Daet -> Bagasbas", String.valueOf(randomDistance())});
        routes.add(new String[]{"Labo2", "Bagasbas Beach", "Labo2 -> Paracale -> Labo1 -> Vinzons -> Daet -> Bagasbas", String.valueOf(randomDistance())});
        routes.add(new String[]{"Labo2", "Bantayog", "Labo2 -> Labo1 -> Vinzons -> Daet -> Bantayog", String.valueOf(randomDistance())});
        routes.add(new String[]{"Labo2", "Bantayog", "Labo2 -> Paracale -> Labo1 -> Vinzons -> Daet -> Bantayog", String.valueOf(randomDistance())});
        routes.add(new String[]{"Labo2", "Capalonga Lighthouse", "Labo2 -> Capalonga -> Capalonga Lighthouse", String.valueOf(randomDistance())});
        routes.add(new String[]{"Labo2", "Gateway to Bicolandia", "Labo2 -> Santa Elena -> Gateway to Bicolandia", String.valueOf(randomDistance())});
        routes.add(new String[]{"Labo2", "Gateway to Bicolandia", "Labo2 -> Capalonga -> Santa Elena -> Gateway to Bicolandia", String.valueOf(randomDistance())});
        routes.add(new String[]{"Labo2", "Mananap Falls", "Labo2 -> Labo1 -> Vinzons -> Mananap Falls", String.valueOf(randomDistance())});
        routes.add(new String[]{"Labo2", "Mananap Falls", "Labo2 -> Paracale -> Labo1 -> Vinzons -> Mananap Falls", String.valueOf(randomDistance())});
        routes.add(new String[]{"Labo2", "Mangcamagong Beach", "Labo2 -> Labo1 -> Vinzons -> Daet -> Mangcamagong Beach", String.valueOf(randomDistance())});
        routes.add(new String[]{"Labo2", "Mangcamagong Beach", "Labo2 -> Paracale -> Labo1 -> Vinzons -> Daet -> Mangcamagong Beach", String.valueOf(randomDistance())});
        routes.add(new String[]{"Labo2", "Mt. Labo", "Labo2 -> Mt. Labo", String.valueOf(randomDistance())});
        routes.add(new String[]{"Labo2", "Tan-awang Bato", "Labo2 -> Tan-awang Bato", String.valueOf(randomDistance())});
        routes.add(new String[]{"Labo2", "Mercedes Fish Port", "Labo2 -> Labo1 -> Vinzons -> Daet -> Mercedes Fish Port", String.valueOf(randomDistance())});
        routes.add(new String[]{"Labo2", "Mercedes Fish Port", "Labo2 -> Paracale -> Labo1 -> Vinzons -> Daet -> Mercedes Fish Port", String.valueOf(randomDistance())});
        routes.add(new String[]{"Labo2", "Parroquia de Nuestra Señora de Candelaria", "Labo2 -> Paracale -> Parroquia de Nuestra Señora de Candelaria", String.valueOf(randomDistance())});
        routes.add(new String[]{"Labo2", "Parroquia de Nuestra Señora de Candelaria", "Labo2 -> Labo1 -> Paracale -> Parroquia de Nuestra Señora de Candelaria", String.valueOf(randomDistance())});
        routes.add(new String[]{"Labo2", "St. Francis of Assisi Parish Church", "Labo2 -> Labo1 -> Vinzons -> St. Francis of Assisi Parish Church", String.valueOf(randomDistance())});
        routes.add(new String[]{"Labo2", "St. Francis of Assisi Parish Church", "Labo2 -> Paracale -> Labo1 -> Vinzons -> St. Francis of Assisi Parish Church", String.valueOf(randomDistance())});
        routes.add(new String[]{"Labo2", "St. Peter the Apostle Parish Church", "Labo2 -> Labo1 -> St. Peter the Apostle Parish Church", String.valueOf(randomDistance())});
        routes.add(new String[]{"Labo2", "St. Peter the Apostle Parish Church", "Labo2 -> Paracale -> Labo1 -> St. Peter the Apostle Parish Church", String.valueOf(randomDistance())});
        routes.add(new String[]{"Labo2", "Turayog", "Labo2 -> Turayog", String.valueOf(randomDistance())});        
        // Santa Elena Spawn
        routes.add(new String[]{"Santa Elena", "Adelaida Native Farm", "Santa Elena -> Labo2 -> Labo1 -> Vinzons -> Talisay -> Daet -> Adelaida Native Farm", String.valueOf(randomDistance())});
        routes.add(new String[]{"Santa Elena", "Adelaida Native Farm", "Santa Elena -> Capalonga -> Labo2 -> Labo1 -> Vinzons -> Daet -> Adelaida Native Farm", String.valueOf(randomDistance())});
        routes.add(new String[]{"Santa Elena", "Bagasbas Beach", "Santa Elena -> Labo2 -> Labo1 -> Vinzons -> Daet -> Bagasbas Beach", String.valueOf(randomDistance())});
        routes.add(new String[]{"Santa Elena", "Bagasbas Beach", "Santa Elena -> Capalonga -> Labo2 -> Labo1 -> Vinzons -> Daet -> Bagasbas Beach", String.valueOf(randomDistance())});
        routes.add(new String[]{"Santa Elena", "Bantayog", "Santa Elena -> Labo2 -> Labo1 -> Vinzons -> Daet -> Bantayog", String.valueOf(randomDistance())});
        routes.add(new String[]{"Santa Elena", "Bantayog", "Santa Elena -> Capalonga -> Labo2 -> Labo1 -> Vinzons -> Daet -> Bantayog", String.valueOf(randomDistance())});
        routes.add(new String[]{"Santa Elena", "Capalonga Lighthouse", "Santa Elena -> Capalonga Lighthouse", String.valueOf(randomDistance())});
        routes.add(new String[]{"Santa Elena", "Capalonga Lighthouse", "Santa Elena -> Labo2 -> Capalonga Lighthouse", String.valueOf(randomDistance())});
        routes.add(new String[]{"Santa Elena", "Gateway to Bicolandia", "Santa Elena -> Gateway to Bicolandia", String.valueOf(randomDistance())});
        routes.add(new String[]{"Santa Elena", "Mananap Falls", "Santa Elena -> Labo2 -> Labo1 -> Vinzons -> Mananap Falls", String.valueOf(randomDistance())});
        routes.add(new String[]{"Santa Elena", "Mananap Falls", "Santa Elena -> Capalonga -> Labo2 -> Labo1 -> Vinzons -> Mananap Falls", String.valueOf(randomDistance())});
        routes.add(new String[]{"Santa Elena", "Mangcamagong Beach", "Santa Elena -> Labo2 -> Labo1 -> Vinzons -> Daet -> Mangcamagong Beach", String.valueOf(randomDistance())});
        routes.add(new String[]{"Santa Elena", "Mangcamagong Beach", "Santa Elena -> Capalonga -> Labo2 -> Labo1 -> Vinzons -> Daet -> Mangcamagong Beach", String.valueOf(randomDistance())});
        routes.add(new String[]{"Santa Elena", "Mt. Labo", "Santa Elena -> Labo2 -> Mt. Labo", String.valueOf(randomDistance())});
        routes.add(new String[]{"Santa Elena", "Mt. Labo", "Santa Elena -> Capalonga -> Labo2 -> Mt. Labo", String.valueOf(randomDistance())});
        routes.add(new String[]{"Santa Elena", "Tan-awang Bato", "Santa Elena -> Labo2 -> Tan-awang Bato", String.valueOf(randomDistance())});
        routes.add(new String[]{"Santa Elena", "Tan-awang Bato", "Santa Elena -> Capalonga -> Labo2 -> Tan-awang Bato", String.valueOf(randomDistance())});
        routes.add(new String[]{"Santa Elena", "Mercedes Fish Port", "Santa Elena -> Labo2 -> Labo1 -> Vinzons -> Daet -> Mercedes Fish Port", String.valueOf(randomDistance())});
        routes.add(new String[]{"Santa Elena", "Mercedes Fish Port", "Santa Elena -> Capalonga -> Labo2 -> Labo1 -> Vinzons -> Daet -> Mercedes Fish Port", String.valueOf(randomDistance())});
        routes.add(new String[]{"Santa Elena", "Parroquia de Nuestra Señora de Candelaria", "Santa Elena -> Labo2 -> Labo1 -> Paracale -> Parroquia de Nuestra Señora de Candelaria", String.valueOf(randomDistance())});
        routes.add(new String[]{"Santa Elena", "Parroquia de Nuestra Señora de Candelaria", "Santa Elena -> Capalonga -> Labo2 -> Labo1 -> Paracale -> Parroquia de Nuestra Señora de Candelaria", String.valueOf(randomDistance())});
        routes.add(new String[]{"Santa Elena", "St. Francis of Assisi Parish Church", "Santa Elena -> Labo2 -> Labo1 -> Vinzons -> St. Francis of Assisi Parish Church", String.valueOf(randomDistance())});
        routes.add(new String[]{"Santa Elena", "St. Francis of Assisi Parish Church", "Santa Elena -> Capalonga -> Labo2 -> Labo1 -> Vinzons -> St. Francis of Assisi Parish Church", String.valueOf(randomDistance())});
        routes.add(new String[]{"Santa Elena", "St. Peter the Apostle Parish Church", "Santa Elena -> Labo2 -> Labo1 -> Vinzons -> St. Peter the Apostle Parish Church", String.valueOf(randomDistance())});
        routes.add(new String[]{"Santa Elena", "St. Peter the Apostle Parish Church", "Santa Elena -> Capalonga -> Labo2 -> Labo1 -> Vinzons -> St. Peter the Apostle Parish Church", String.valueOf(randomDistance())});
        routes.add(new String[]{"Santa Elena", "Turayog", "Santa Elena -> Labo2 -> Labo1 -> Paracale -> Turayog", String.valueOf(randomDistance())});
        routes.add(new String[]{"Santa Elena", "Turayog", "Santa Elena -> Capalonga -> Labo2 -> Labo1 -> Paracale -> Turayog", String.valueOf(randomDistance())});
        // Vinzons Spawn
        routes.add(new String[]{"Vinzons", "Adelaida Native Farm", "Vinzons -> Daet -> Adelaida Native Farm", String.valueOf(randomDistance())});
        routes.add(new String[]{"Vinzons", "Bagasbas Beach", "Vinzons -> Daet -> Bagasbas", String.valueOf(randomDistance())});
        routes.add(new String[]{"Vinzons", "Bantayog", "Vinzons -> Daet -> Bantayog", String.valueOf(randomDistance())});
        routes.add(new String[]{"Vinzons", "Capalonga Lighthouse", "Vinzons -> Labo1 -> Labo2 -> Capalonga -> Capalonga Lighthouse", String.valueOf(randomDistance())});
        routes.add(new String[]{"Vinzons", "Capalonga Lighthouse", "Vinzons -> Labo1 -> Paracale -> Labo2 -> Capalonga -> Capalonga Lighthouse", String.valueOf(randomDistance())});
        routes.add(new String[]{"Vinzons", "Gateway to Bicolandia", "Vinzons -> Labo1 -> Labo2 -> Santa Elena -> Gateway to Bicolandia", String.valueOf(randomDistance())});
        routes.add(new String[]{"Vinzons", "Gateway to Bicolandia", "Vinzons -> Labo1 -> Paracale -> Labo2 -> Santa Elena -> Gateway to Bicolandia", String.valueOf(randomDistance())});
        routes.add(new String[]{"Vinzons", "Gateway to Bicolandia", "Vinzons -> Labo1 -> Labo2 -> Capalonga -> Santa Elena -> Gateway to Bicolandia", String.valueOf(randomDistance())});
        routes.add(new String[]{"Vinzons", "Gateway to Bicolandia", "Vinzons -> Labo1 -> Paracale -> Labo2 -> Capalonga -> Santa Elena -> Gateway to Bicolandia", String.valueOf(randomDistance())});
        routes.add(new String[]{"Vinzons", "Mananap Falls", "Vinzons -> Mananap Falls", String.valueOf(randomDistance())});
        routes.add(new String[]{"Vinzons", "Mangcamagong Beach", "Vinzons -> Daet -> Mangcamagong Beach", String.valueOf(randomDistance())});
        routes.add(new String[]{"Vinzons", "Mt. Labo", "Vinzons -> Labo1 -> Mt. Labo", String.valueOf(randomDistance())});
        routes.add(new String[]{"Vinzons", "Tan-awang Bato", "Vinzons -> Labo1 -> Tan-awang Bato", String.valueOf(randomDistance())});
        routes.add(new String[]{"Vinzons", "Mercedes Fish Port", "Vinzons -> Daet -> Mercedes Fish Port", String.valueOf(randomDistance())});
        routes.add(new String[]{"Vinzons", "Parroquia de Nuestra Señora de Candelaria", "Vinzons -> Labo1 -> Paracale -> Parroquia de Nuestra Señora de Candelaria", String.valueOf(randomDistance())});
        routes.add(new String[]{"Vinzons", "St. Francis of Assisi Parish Church", "Vinzons -> St. Francis of Assisi Parish Church", String.valueOf(randomDistance())});
        routes.add(new String[]{"Vinzons", "St. Peter the Apostle Parish Church", "Vinzons -> St. Peter the Apostle Parish Church", String.valueOf(randomDistance())});
        routes.add(new String[]{"Vinzons", "Turayog", "Vinzons -> Labo1 -> Turayog", String.valueOf(randomDistance())});
        routes.add(new String[]{"Vinzons", "Turayog", "Vinzons -> Labo1 -> Paracale -> Turayog", String.valueOf(randomDistance())});        
        // Capalonga Spawn
        routes.add(new String[]{"Capalonga", "Adelaida Native Farm", "Capalonga -> Labo2 -> Labo1 -> Vinzons -> Daet -> Adelaida Native Farm", String.valueOf(randomDistance())});
        routes.add(new String[]{"Capalonga", "Adelaida Native Farm", "Capalonga -> Santa Elena -> Labo2 -> Labo1 -> Vinzons -> Daet -> Adelaida Native Farm", String.valueOf(randomDistance())});
        routes.add(new String[]{"Capalonga", "Bagasbas Beach", "Capalonga -> Labo2 -> Labo1 -> Vinzons -> Daet -> Bagasbas Beach", String.valueOf(randomDistance())});
        routes.add(new String[]{"Capalonga", "Bagasbas Beach", "Capalonga -> Santa Elena -> Labo2 -> Labo1 -> Vinzons -> Daet -> Bagasbas Beach", String.valueOf(randomDistance())});
        routes.add(new String[]{"Capalonga", "Bantayog", "Capalonga -> Labo2 -> Labo1 -> Vinzons -> Daet -> Bantayog", String.valueOf(randomDistance())});
        routes.add(new String[]{"Capalonga", "Bantayog", "Capalonga -> Santa Elena -> Labo2 -> Labo1 -> Vinzons -> Daet -> Bantayog", String.valueOf(randomDistance())});
        routes.add(new String[]{"Capalonga", "Capalonga Lighthouse", "Capalonga -> Capalonga Lighthouse", String.valueOf(randomDistance())});
        routes.add(new String[]{"Capalonga", "Gateway to Bicolandia", "Capalonga -> Santa Elena -> Gateway to Bicolandia", String.valueOf(randomDistance())});
        routes.add(new String[]{"Capalonga", "Gateway to Bicolandia", "Capalonga -> Labo2 -> Santa Elena -> Gateway to Bicolandia", String.valueOf(randomDistance())});
        routes.add(new String[]{"Capalonga", "Mananap Falls", "Capalonga -> Labo2 -> Labo1 -> Vinzons -> Mananap Falls", String.valueOf(randomDistance())});
        routes.add(new String[]{"Capalonga", "Mananap Falls", "Capalonga -> Santa Elena -> Labo2 -> Labo1 -> Vinzons -> Mananap Falls", String.valueOf(randomDistance())});
        routes.add(new String[]{"Capalonga", "Mangcamagong Beach", "Capalonga -> Labo2 -> Labo1 -> Vinzons -> Daet -> Mangcamagong Beach", String.valueOf(randomDistance())});
        routes.add(new String[]{"Capalonga", "Mangcamagong Beach", "Capalonga -> Santa Elena -> Labo2 -> Labo1 -> Vinzons -> Daet -> Mangcamagong Beach", String.valueOf(randomDistance())});
        routes.add(new String[]{"Capalonga", "Mt. Labo", "Capalonga -> Labo2 -> Labo1 -> Mt. Labo", String.valueOf(randomDistance())});
        routes.add(new String[]{"Capalonga", "Mt. Labo", "Capalonga -> Santa Elena -> Labo2 -> Labo1 -> Mt. Labo", String.valueOf(randomDistance())});
        routes.add(new String[]{"Capalonga", "Tan-awang Bato", "Capalonga -> Labo2 -> Labo1 -> Tan-awang Bato", String.valueOf(randomDistance())});
        routes.add(new String[]{"Capalonga", "Tan-awang Bato", "Capalonga -> Santa Elena -> Labo2 -> Labo1 -> Tan-awang Bato", String.valueOf(randomDistance())});
        routes.add(new String[]{"Capalonga", "Mercedes Fish Port", "Capalonga -> Labo2 -> Labo1 -> Vinzons -> Daet -> Mercedes Fish Port", String.valueOf(randomDistance())});
        routes.add(new String[]{"Capalonga", "Mercedes Fish Port", "Capalonga -> Santa Elena -> Labo2 -> Labo1 -> Vinzons -> Daet -> Mercedes Fish Port", String.valueOf(randomDistance())});
        routes.add(new String[]{"Capalonga", "Parroquia de Nuestra Señora de Candelaria", "Capalonga -> Labo2 -> Labo1 -> Paracale -> Parroquia de Nuestra Señora de Candelaria", String.valueOf(randomDistance())});
        routes.add(new String[]{"Capalonga", "Parroquia de Nuestra Señora de Candelaria", "Capalonga -> Santa Elena -> Labo2 -> Labo1 -> Paracale -> Parroquia de Nuestra Señora de Candelaria", String.valueOf(randomDistance())});
        routes.add(new String[]{"Capalonga", "St. Francis of Assisi Parish Church", "Capalonga -> Labo2 -> Labo1 -> Vinzons -> St. Francis of Assisi Parish Church", String.valueOf(randomDistance())});
        routes.add(new String[]{"Capalonga", "St. Francis of Assisi Parish Church", "Capalonga -> Santa Elena -> Labo2 -> Labo1 -> Vinzons -> St. Francis of Assisi Parish Church", String.valueOf(randomDistance())});
        routes.add(new String[]{"Capalonga", "St. Peter the Apostle Parish Church", "Capalonga -> Labo2 -> Labo1 -> Vinzons -> St. Peter the Apostle Parish Church", String.valueOf(randomDistance())});
        routes.add(new String[]{"Capalonga", "St. Peter the Apostle Parish Church", "Capalonga -> Santa Elena -> Labo2 -> Labo1 -> Vinzons -> St. Peter the Apostle Parish Church", String.valueOf(randomDistance())});
        routes.add(new String[]{"Capalonga", "Turayog", "Capalonga -> Labo2 -> Turayog", String.valueOf(randomDistance())});
        routes.add(new String[]{"Capalonga", "Turayog", "Capalonga -> Labo2 -> Labo1 -> Paracale -> Turayog", String.valueOf(randomDistance())});
        routes.add(new String[]{"Capalonga", "Turayog", "Capalonga -> Santa Elena -> Labo2 -> Labo1 -> Paracale -> Turayog", String.valueOf(randomDistance())});        
        // Paracale Spawn
        routes.add(new String[]{"Paracale", "Adelaida Native Farm", "Paracale -> Labo1 -> Vinzons -> Daet -> Adelaida Native Farm", String.valueOf(randomDistance())});
        routes.add(new String[]{"Paracale", "Bagasbas Beach", "Paracale -> Labo1 -> Vinzons -> Daet -> Bagasbas Beach", String.valueOf(randomDistance())});
        routes.add(new String[]{"Paracale", "Bantayog", "Paracale -> Labo1 -> Vinzons -> Daet -> Bantayog", String.valueOf(randomDistance())});
        routes.add(new String[]{"Paracale", "Capalonga Lighthouse", "Paracale -> Labo1 -> Labo2 -> Capalonga -> Capalonga Lighthouse", String.valueOf(randomDistance())});
        routes.add(new String[]{"Paracale", "Capalonga Lighthouse", "Paracale -> Labo1 -> Labo2 -> Santa Elena -> Capalonga -> Capalonga Lighthouse", String.valueOf(randomDistance())});
        routes.add(new String[]{"Paracale", "Gateway to Bicolandia", "Paracale -> Labo1 -> Labo2 -> Santa Elena -> Gateway to Bicolandia", String.valueOf(randomDistance())});
        routes.add(new String[]{"Paracale", "Gateway to Bicolandia", "Paracale -> Labo1 -> Labo2 -> Capalonga -> Santa Elena -> Gateway to Bicolandia", String.valueOf(randomDistance())});
        routes.add(new String[]{"Paracale", "Mananap Falls", "Paracale -> Labo1 -> Vinzons -> Mananap Falls", String.valueOf(randomDistance())});
        routes.add(new String[]{"Paracale", "Mangcamagong Beach", "Paracale -> Labo1 -> Vinzons -> Daet -> Mangcamagong Beach", String.valueOf(randomDistance())});
        routes.add(new String[]{"Paracale", "Mt. Labo", "Paracale -> Labo1 -> Mt. Labo", String.valueOf(randomDistance())});
        routes.add(new String[]{"Paracale", "Tan-awang Bato", "Paracale -> Labo1 -> Tan-awang Bato", String.valueOf(randomDistance())});
        routes.add(new String[]{"Paracale", "Mercedes Fish Port", "Paracale -> Labo1 -> Vinzons -> Daet -> Mercedes Fish Port", String.valueOf(randomDistance())});
        routes.add(new String[]{"Paracale", "Parroquia de Nuestra Señora de Candelaria", "Paracale -> Parroquia de Nuestra Señora de Candelaria", String.valueOf(randomDistance())});
        routes.add(new String[]{"Paracale", "St. Francis of Assisi Parish Church", "Paracale -> Labo1 -> Vinzons -> St. Francis of Assisi Parish Church", String.valueOf(randomDistance())});
        routes.add(new String[]{"Paracale", "St. Peter the Apostle Parish Church", "Paracale -> Labo1 -> Vinzons -> St. Peter the Apostle Parish Church", String.valueOf(randomDistance())});
        routes.add(new String[]{"Paracale", "Turayog", "Paracale -> Turayog", String.valueOf(randomDistance())});
        routes.add(new String[]{"Paracale", "Turayog", "Paracale -> Labo1 -> Turayog", String.valueOf(randomDistance())});        
        // Labo1 Spawn
        routes.add(new String[]{"Labo1", "Adelaida Native Farm", "Labo1 -> Vinzons -> Daet -> Adelaida Native Farm", String.valueOf(randomDistance())});
        routes.add(new String[]{"Labo1", "Bagasbas Beach", "Labo1 -> Vinzons -> Daet -> Bagasbas Beach", String.valueOf(randomDistance())});
        routes.add(new String[]{"Labo1", "Bantayog", "Labo1 -> Vinzons -> Daet -> Bantayog", String.valueOf(randomDistance())});
        routes.add(new String[]{"Labo1", "Capalonga Lighthouse", "Labo1 -> Labo2 -> Capalonga -> Capalonga Lighthouse", String.valueOf(randomDistance())});
        routes.add(new String[]{"Labo1", "Capalonga Lighthouse", "Labo1 -> Labo2 -> Santa Elena -> Capalonga -> Capalonga Lighthouse", String.valueOf(randomDistance())});
        routes.add(new String[]{"Labo1", "Gateway to Bicolandia", "Labo1 -> Labo2 -> Santa Elena -> Gateway to Bicolandia", String.valueOf(randomDistance())});
        routes.add(new String[]{"Labo1", "Gateway to Bicolandia", "Labo1 -> Labo2 -> Capalonga -> Santa Elena -> Gateway to Bicolandia", String.valueOf(randomDistance())});
        routes.add(new String[]{"Labo1", "Mananap Falls", "Labo1 -> Vinzons -> Mananap Falls", String.valueOf(randomDistance())});
        routes.add(new String[]{"Labo1", "Mangcamagong Beach", "Labo1 -> Vinzons -> Daet -> Mangcamagong Beach", String.valueOf(randomDistance())});
        routes.add(new String[]{"Labo1", "Mt. Labo", "Labo1 -> Mt. Labo", String.valueOf(randomDistance())});
        routes.add(new String[]{"Labo1", "Tan-awang Bato", "Labo1 -> Tan-awang Bato", String.valueOf(randomDistance())});
        routes.add(new String[]{"Labo1", "Mercedes Fish Port", "Labo1 -> Vinzons -> Daet -> Mercedes Fish Port", String.valueOf(randomDistance())});
        routes.add(new String[]{"Labo1", "Parroquia de Nuestra Señora de Candelaria", "Labo1 -> Paracale -> Parroquia de Nuestra Señora de Candelaria", String.valueOf(randomDistance())});
        routes.add(new String[]{"Labo1", "St. Francis of Assisi Parish Church", "Labo1 -> Vinzons -> St. Francis of Assisi Parish Church", String.valueOf(randomDistance())});
        routes.add(new String[]{"Labo1", "St. Peter the Apostle Parish Church", "Labo1 -> Vinzons -> St. Peter the Apostle Parish Church", String.valueOf(randomDistance())});
        routes.add(new String[]{"Labo1", "Turayog", "Labo1 -> Turayog", String.valueOf(randomDistance())});
        routes.add(new String[]{"Labo1", "Turayog", "Labo1 -> Paracale -> Turayog", String.valueOf(randomDistance())});        
    }
    
    private static String userSpawn;
    private static int selectedIndex = 0;
    private static int selectedVehicleIndex = 0;
    private static Terminal terminal;

    private static String selectedDestination;
    private static String selectedRoute;
    private static int selectedDistance;

    
    public interface User32 extends StdCallLibrary {
        User32 INSTANCE = Native.load("user32", User32.class);
        short GetAsyncKeyState(int vKey);
    }

       public static void main(String[] args) throws Exception {
        terminal = TerminalBuilder.builder().system(true).build();
        userSpawn = stopovers[random.nextInt(stopovers.length)]; // Random spawn
        while (true) {
            clearScreen();
            drawMap();
            displayDestinationSelection();
            handleDestinationInput();
        }
    }

    private static void drawMap() throws IOException {
		terminal.writer().println("▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄");
		terminal.writer().println("██                                                                                                                                                                                                     ");
		terminal.writer().println("██      < BACK >                                                                                                                                                                                       ");
		
		terminal.writer().print("██                                     ");
		terminal.writer().print(ANSI_RED + "█████████♥█           " + ANSI_RESET);
		terminal.writer().print(ANSI_GREEN + "████████                                                " + ANSI_RESET);
		terminal.writer().println("▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄              ");
		
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

    private static void handleDestinationInput() throws Exception {
        boolean updated = true; // Ensure initial rendering
    
        // 🔥 Ensure the map is printed once at the start
        clearScreen();  
        drawMap();  
    
        while (true) {
            if ((User32.INSTANCE.GetAsyncKeyState(0x26) & 0x8000) != 0) { // Up arrow
                selectedIndex = (selectedIndex - 1 + destinations.length) % destinations.length;
                updated = true;
            }
            if ((User32.INSTANCE.GetAsyncKeyState(0x28) & 0x8000) != 0) { // Down arrow
                selectedIndex = (selectedIndex + 1) % destinations.length;
                updated = true;
            }
            if ((User32.INSTANCE.GetAsyncKeyState(0x0D) & 0x8000) != 0) { // Enter key
                sleep(200);  
                selectedDestination = destinations[selectedIndex];
    
                findRoutes(selectedDestination);
                return; // Exit function
            }
    
            // ✅ Only update the menu without erasing the map
            if (updated) {
                moveCursorRight();  // 🔥 Move cursor instead of clearing
                displayDestinationSelection();
                updated = false; 
            }
    
            sleep(100);
        }
    }
    
    

    private static void moveCursorRight() {
        System.out.print("\033[15;50H");  // Moves cursor to row 30, column 50 (adjust as needed)
        terminal.flush();
    }
    
    
    

    private static void displayDestinationSelection() {
        moveCursorRight();  // Move cursor instead of clearing
    
        // ✅ Print the "You spawned at: " message at a fixed position
        terminal.writer().printf("\033[14;130HYou spawned at: %s", userSpawn);    
        // ✅ Print the title without overwriting the previous message
        terminal.writer().printf("\033[15;130H---- Select a Tourist Destination ----\n");
    
        // ✅ Print destination options
        for (int i = 0; i < destinations.length; i++) {
            terminal.writer().printf("\033[%d;%dH%s %s", 18 + i, 135, (i == selectedIndex ? "▶" : " "), destinations[i]);
        }
    
        terminal.flush();
    }
    
    
    

    
    private static void findRoutes(String destination) throws Exception {
        List<String[]> availableRoutes = new ArrayList<>();
        for (String[] route : routes) {
            if (route[0].equals(userSpawn) && route[1].equals(destination)) {
                availableRoutes.add(route);
            }
        }
        if (availableRoutes.isEmpty()) {
            terminal.writer().println("\nNo available routes found!");
            terminal.flush();
            sleep(2000);
            return;
        }
        displayRouteAndVehicleSelection(destination, availableRoutes);
    }

    private static void displayRouteAndVehicleSelection(String destination, List<String[]> availableRoutes) throws Exception {
        selectedIndex = 0;  // Start at the first route
        int selectedVehicleIndex = 0;
    
        while (true) {
            clearScreen();
            terminal.writer().printf("Routes to %s:\n", destination);
            printTable("Available Routes", availableRoutes);  // Display available routes
            printVehicleCostTable(selectedVehicleIndex);  // Display vehicle costs
    
            if (handleRouteNavigation(availableRoutes)) {  // Pass the full route list
                selectedRoute = availableRoutes.get(selectedIndex)[2];  
                selectedDistance = randomDistance();
                displayVehicleSelection(selectedRoute);  // Proceed to vehicle selection
                break;
            }
        }
    }
    

    private static void displayRouteSelection(String destination, List<String[]> availableRoutes) throws Exception {
        int selectedIndex = 0;
    
        while (true) {
            clearScreen();
            terminal.writer().printf("Routes to %s:\n", destination);
    
            if (availableRoutes == null || availableRoutes.isEmpty()) {
                terminal.writer().println("No available routes!");
                return;
            }
    
            printTable("Available Routes", availableRoutes); // Now matches printTable's parameter type
    
            if (handleNavigation(availableRoutes.size())) {
                selectedIndex = Math.max(0, Math.min(selectedIndex, availableRoutes.size() - 1));
                String selectedRoute = availableRoutes.get(selectedIndex)[2]; // Access the correct part of the array
                displayVehicleSelection(selectedRoute);
                break;
            }
        }
    }

    private static void displayVehicleSelection(String selectedRoute) throws Exception {
        selectedVehicleIndex = 0;  // Ensure cursor starts at the first vehicle
    
        while (true) {
            clearScreen();
            terminal.writer().printf("Selecting vehicle for route: %s\n", selectedRoute);
            printVehicleCostTable(selectedVehicleIndex); // Pass selected index to update cursor
    
            if (handleNavigation(vehicles.length)) {
                break; // Exit loop when Enter is pressed
            }
        }
    
        // Store the selected vehicle
        selectedVehicle = vehicles[selectedVehicleIndex];
    
        // Show confirmation
        terminal.writer().printf("\nYou selected: %s (Cost: ₱%.2f)\n", 
            selectedVehicle, calculateCost(selectedVehicleIndex, selectedDistance / 1000.0));
    
        // Call loading screen
        TravelFlowManager.startJourney(selectedVehicle, selectedDestination);
    }

    private static boolean handleRouteNavigation(List<String[]> routes) throws Exception {
        boolean updated = true; // Ensure initial rendering
    
        while (true) {
            if ((User32.INSTANCE.GetAsyncKeyState(0x26) & 0x8000) != 0) { // Up arrow
                selectedIndex = (selectedIndex - 1 + routes.size()) % routes.size();
                updated = true;
            }
            if ((User32.INSTANCE.GetAsyncKeyState(0x28) & 0x8000) != 0) { // Down arrow
                selectedIndex = (selectedIndex + 1) % routes.size();
                updated = true;
            }
            if ((User32.INSTANCE.GetAsyncKeyState(0x0D) & 0x8000) != 0) { // Enter key
                sleep(200);
                return true; // Confirm selection
            }
    
            // 🔥 Add 'F' Key Handling for Products.java
            if ((User32.INSTANCE.GetAsyncKeyState(0x46) & 0x8000) != 0) { // 'F' key
                sleep(200); // Prevent multiple presses
                System.out.println("Opening Products.java...");
                Products.sell(); // Call the method from Products.java
                updated = true; // Force re-render after returning
            }
    
            // ✅ Only redraw when navigation changes
            if (updated) {
                clearScreen();
                drawMap();                                 // ✅ Keep map at the top
                printTable("Available Routes", routes);   // 🔥 Corrected function call
                updated = false;
            }
    
            sleep(100);
        }
    }
    
    
    
    
    private static boolean handleNavigation(int vehicleCount) throws IOException {
        boolean updated = true; // Ensure initial rendering
    
        while (true) {
            if ((User32.INSTANCE.GetAsyncKeyState(0x26) & 0x8000) != 0) { // Up arrow
                selectedVehicleIndex = (selectedVehicleIndex - 1 + vehicleCount) % vehicleCount;
                updated = true;
            }
            if ((User32.INSTANCE.GetAsyncKeyState(0x28) & 0x8000) != 0) { // Down arrow
                selectedVehicleIndex = (selectedVehicleIndex + 1) % vehicleCount;
                updated = true;
            }
            if ((User32.INSTANCE.GetAsyncKeyState(0x0D) & 0x8000) != 0) { // Enter key
                sleep(150);
                return true; // Confirm selection
            }
    
            // ✅ Only update when selection changes
            if (updated) {
                clearScreen();
                drawMap();                               // ✅ Keep map at the top
                printVehicleCostTable(selectedVehicleIndex); // 🔥 Corrected function call
                updated = false;
            }
    
            sleep(100);
        }
    }
    
    
    private static void printTable(String title, List<String[]> items) {
        int columnWidth = 50;  
        int baseRow = 15; // Adjust this to place the table at the correct starting row
        int baseCol = 130; // Adjust this to the correct column
    
        // Save cursor position only ONCE before modifying the screen
        terminal.writer().printf("\033[s");
    
        // Print table header
        terminal.writer().printf("\033[%d;%dH+" + "-".repeat(columnWidth + 4) + "+", baseRow, baseCol);
        terminal.writer().printf("\033[%d;%dH| %-"+(columnWidth + 2)+"s |", baseRow + 1, baseCol, title);
        terminal.writer().printf("\033[%d;%dH+" + "-".repeat(columnWidth + 4) + "+", baseRow + 2, baseCol);
    
        int shortestIndex = 0;
        int shortestDistance = Integer.MAX_VALUE;
    
        // Print table contents
        for (int i = 0; i < items.size(); i++) {
            String cursor = (i == selectedIndex) ? "▶" : " ";
            String route = items.get(i)[2];
            int distance = Integer.parseInt(items.get(i)[3]);
    
            if (distance < shortestDistance) {
                shortestDistance = distance;
                shortestIndex = i;
            }
    
            List<String> wrappedLines = wrapText(route, columnWidth - 10);
            for (int j = 0; j < wrappedLines.size(); j++) {
                if (j == 0) {
                    terminal.writer().printf("\033[%d;%dH| %s %-"+(columnWidth - 9)+"s [%3dm] |", baseRow + 3 + i, baseCol, cursor, wrappedLines.get(j), distance);
                } else {
                    terminal.writer().printf("\033[%d;%dH|   %-"+(columnWidth - 9)+"s       |", baseRow + 3 + i, baseCol, wrappedLines.get(j));
                }
            }
        }
    
        // Print table footer
        int footerRow = baseRow + 3 + items.size();
        terminal.writer().printf("\033[%d;%dH+" + "-".repeat(columnWidth + 4) + "+", footerRow, baseCol);
        
        // ✅ Print the recommended route based on the shortest distance
        terminal.writer().printf("\033[%d;%dH| Recommended Route: %-"+(columnWidth - 3)+"s [%3dm] |", footerRow + 1, baseCol, items.get(shortestIndex)[2], shortestDistance);
        terminal.writer().printf("\033[%d;%dH+" + "-".repeat(columnWidth + 4) + "+", footerRow + 2, baseCol);
    
        // ✅ Move the cursor to a safe position instead of restoring it
        terminal.writer().printf("\033[%d;0H", footerRow + 3); // Moves cursor below the table
    
        terminal.flush();
    }
        
    
    
    // Helper function to wrap text within the column width
    private static List<String> wrapText(String text, int width) {
        List<String> lines = new ArrayList<>();
        while (text.length() > width) {
            int splitAt = text.lastIndexOf(" ", width);
            if (splitAt == -1) splitAt = width;
            lines.add(text.substring(0, splitAt));
            text = text.substring(splitAt).trim();
        }
        lines.add(text);
        return lines;
    }
    

    private static void printVehicleCostTable(int selectedIndex) {
        terminal.writer().printf("\033[s\033[15;135H+--------------------------------------+\033[u\n");
        terminal.writer().printf("\033[s\033[16;135H|           Vehicle Costs              |\033[u\n");
        terminal.writer().printf("\033[s\033[17;135H+--------------------------------------+\033[u\n");
    
        for (int i = 0; i < vehicles.length; i++) {
            String cursor = (i == selectedIndex) ? "▶" : " ";
            terminal.writer().printf("\033[s\033[%d;135H| %s %-12s ₱ %7.2f             |\033[u\n", 18 + i, cursor, vehicles[i], calculateCost(i, selectedDistance / 1000.0));
        }
    
        terminal.writer().printf("\033[s\033[%d;135H+--------------------------------------+\033[u\n", 18 + vehicles.length);
        terminal.flush();
    }
      
    

        private static double calculateCost(int vehicleIndex, double distanceKm) {
            switch (vehicleIndex) {
                case 0:
                    return 40 + (distanceKm * 15) + (distanceKm * 2); // Car
                case 1:
                    return 20 + (distanceKm * 12); // Motorcycle
                case 2:
                    return 20 + (distanceKm * 10); // Van
                case 3:
                    return 12 + (distanceKm * 5); // Bus
                case 4:
                    return 10 + (distanceKm * 8); // Tricycle
                case 5:
                    return 12 + (Math.max(0, distanceKm - 4) * 1.5); // Jeepney
                default:
                    return 0;
            }
        }

    private static int randomDistance() {
        return random.nextInt(91) + 10;
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
            System.out.println("Error clearing screen: " + e.getMessage());
        }
    }
    

    private static void sleep(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
