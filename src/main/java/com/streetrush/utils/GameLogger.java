package com.streetrush.utils;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Singleton Pattern - Logger centralisé pour tracer tous les événements du jeu
 * Conforme aux spécifications du projet Design Patterns
 */
public class GameLogger {
    private static GameLogger instance;
    private PrintWriter writer;
    private DateTimeFormatter formatter;
    private boolean consoleOutput = true; // Afficher aussi dans la console

    private GameLogger() {
        try {
            writer = new PrintWriter(new FileWriter("game.log", false)); // false = écrase le fichier
            formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            logInfo("=== Game Logger Initialized ===");
            logInfo("Street Rush - Design Patterns Project");
        } catch (IOException e) {
            System.err.println("ERROR: Could not initialize logger: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Singleton - Point d'accès global unique
     */
    public static GameLogger getInstance() {
        if (instance == null) {
            instance = new GameLogger();
        }
        return instance;
    }

    /**
     * Méthode privée de logging générique
     */
    private void log(String level, String category, String message) {
        String timestamp = LocalDateTime.now().format(formatter);
        String logMessage = String.format("[%s] [%s] [%s] %s", timestamp, level, category, message);

        // Afficher dans la console (optionnel)
        if (consoleOutput) {
            System.out.println(logMessage);
        }

        // Écrire dans le fichier
        if (writer != null) {
            writer.println(logMessage);
            writer.flush(); // Force l'écriture immédiate
        }
    }

    // ========== Méthodes de Logging Spécifiques ==========

    /**
     * Log général d'information
     */
    public void logInfo(String message) {
        log("INFO", "GENERAL", message);
    }

    /**
     * STATE PATTERN - Changements d'états du jeu et du joueur
     */
    public void logState(String message) {
        log("INFO", "STATE", message);
    }

    /**
     * DECORATOR PATTERN - Application/retrait de décorateurs
     */
    public void logDecorator(String message) {
        log("INFO", "DECORATOR", message);
    }

    /**
     * FACTORY PATTERN - Création d'objets
     */
    public void logFactory(String message) {
        log("INFO", "FACTORY", message);
    }

    /**
     * COMPOSITE PATTERN - Opérations sur les groupes
     */
    public void logComposite(String message) {
        log("INFO", "COMPOSITE", message);
    }

    /**
     * Événements de gameplay
     */
    public void logEvent(String message) {
        log("INFO", "EVENT", message);
    }

    /**
     * Collisions (pièces, obstacles, power-ups)
     */
    public void logCollision(String message) {
        log("INFO", "COLLISION", message);
    }

    /**
     * Erreurs
     */
    public void logError(String message) {
        log("ERROR", "GENERAL", message);
    }

    /**
     * Warnings
     */
    public void logWarning(String message) {
        log("WARNING", "GENERAL", message);
    }

    /**
     * Active/désactive l'affichage console
     */
    public void setConsoleOutput(boolean enabled) {
        this.consoleOutput = enabled;
    }

    /**
     * Ferme le logger proprement
     */
    public void close() {
        if (writer != null) {
            logInfo("=== Game Logger Closed ===");
            writer.close();
        }
    }

    /**
     * Ajout d'un séparateur visuel dans les logs
     */
    public void logSeparator() {
        if (writer != null) {
            writer.println("============================================================");
            writer.flush();
        }
        if (consoleOutput) {
            System.out.println("============================================================");
        }
    }
}