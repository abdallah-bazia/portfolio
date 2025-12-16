# ♟️ JADE Connect Four Multi-Agent System (MAS)

A Multi-Agent System (MAS) project implementing the game of Connect Four, where two autonomous, intelligent agents compete using a Minimax strategy.

## 🌟 Project Overview

This project is a complete implementation of the Connect Four game in Java. It utilizes the **JADE (Java Agent Development Framework)** to model the game as a Multi-Agent System (MAS).

Two autonomous player agents (`PlayerAgent`) communicate with a coordinator agent (`GameCoordinator`) to negotiate and execute their moves.

## 🎯 Key Features

* **Multi-Agent Architecture (JADE):**
    * **`GameCoordinator`:** Manages the game flow, maintains the board state (`GameBoard`), and coordinates turns between the two player agents using ACL (Agent Communication Language) messages.
    * **`PlayerAgent`:** Represents an intelligent player. It receives the request to play and responds with its calculated move.
* **Artificial Intelligence (AI):**
    * Implementation of the **Minimax algorithm** with **Alpha-Beta Pruning** (`AIStrategy.java`) to determine the optimal move.
    * Heuristic evaluation function to score intermediate board positions, including prioritizing center control and evaluating "windows" of four cells.
* **Graphical User Interface (GUI):**
    * A GUI built using **Java Swing** (`GameGUI.java`) provides a real-time visualization of the game board, the current turn status, and the final result.
* **Language:** The core logic is implemented in Java, with French comments and interface labels (which can be noted as a language skill).

## 🛠️ Technologies Used

* **Language:** Java
* **MAS Framework:** JADE (Java Agent Development Framework)
* **GUI:** Java Swing/AWT
* **Algorithm:** Minimax with Alpha-Beta Pruning

## 📁 Project Structure

The project follows the standard Java convention, organizing classes by package:## 🚀 How to Run the Project

This project requires the JADE library to run.

### Prerequisites

* JDK (Java Development Kit) 8 or newer.
* The `jade.jar` file (must be included in the project's dependencies).

### Steps to Compile and Run


  **Configuration (IDE):**
    * Add `jade.jar` as an external dependency in your IDE (IntelliJ, Eclipse, VS Code).
  **Launch the Main Container:**
    * Run the `main` method in the `projetpuissance4.MainContainer` class.
  **JADE Startup:**
    * The program will start the JADE platform, and the RMA (Remote Management Agent) GUI will open, followed by the Connect Four GUI.
    * The three agents (`Player1Agent`, `Player2Agent`, `GameCoordinator`) will be launched and registered..  **Start Game:**
    * Click the **"Démarrer une nouvelle partie"** (Start a new game) button in the GUI to initiate the match between the two AI agents.

---
## 📝 Author

**[Bazia Abdallah]**

* [(https://github.com/abdallah-bazia/portfolio)]
