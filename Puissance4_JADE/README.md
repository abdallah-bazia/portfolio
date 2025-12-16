# 🤝 JADE Connect Four Peer-to-Peer (P2P)

A Multi-Agent System (MAS) project that implements the game of Connect Four, allowing **two human players** to compete against each other across separate graphical user interfaces (GUIs), with all moves transmitted via **JADE** agents.

## 🌟 Project Overview

This project demonstrates a Peer-to-Peer communication model within the JADE framework. Instead of a central coordinator, two identical `PlayerAgent` instances communicate directly using JADE's Agent Communication Language (ACL) messages.

Each agent manages its own window, allowing two people (or the same person acting as two players) to play a classic Connect Four game by distributing the interaction across the agent platform.

## 🎯 Key Features

* **P2P Multi-Agent Communication:** Direct communication between two identical agents (`Joueur1` and `Joueur2`) using JADE's ACL `INFORM` messages to transmit moves and synchronization commands.
* **Dual Graphical Interfaces:** Each `PlayerAgent` launches and controls its own dedicated `Puissance4GUI` window, representing a separate client in the game.
* **Decentralized Game Management:** Game logic (checking for wins, board state) is managed locally within each player's GUI, with moves synchronized through the JADE platform.
* **Standard Game Logic:** Full implementation of Connect Four rules (6 rows, 7 columns), win checks (horizontal, vertical, diagonal), and draw conditions.

## ⚙️ Architecture and Agent Roles

The system consists of two primary components running within the JADE platform:

1.  **`Puissance4Main.java` (JADE Container):**
    * Starts the main JADE container and the RMA (Remote Management Agent).
    * Launches two instances of the `PlayerAgent`: **"Joueur1" (RED)** and **"Joueur2" (YELLOW)**.
    * Crucially, it injects the opponent's name (`"Joueur2"` or `"Joueur1"`) into each agent's arguments to enable P2P messaging.

2.  **`PlayerAgent.java` (The Agent):**
    * The core communication hub.
    * Launches the player's dedicated GUI (`Puissance4GUI`).
    * **Receives:** Listens for incoming `MOVE:X` messages from the opponent agent and updates its local GUI accordingly.
    * **Sends:** When the local human player clicks, the agent sends an `INFORM` message with the `MOVE:X` content to the opponent's Agent ID.

## 🛠️ Technologies Used

* **Language:** Java
* **Framework MAS:** JADE (Java Agent Development Framework)
* **GUI:** Java Swing/AWT
* **Communication Model:** Peer-to-Peer (P2P)

## 🚀 How to Run the Project

### Prerequisites
* JDK (Java Development Kit) 8 or newer.
* The `jade.jar` library file (required for the JADE platform).

### Execution Steps
1.  **Setup Dependencies:** Ensure `jade.jar` is included in your project's classpath (usually in a `lib/` folder).
2.  **Launch the JADE Platform:** Run the `main` method in the **`Puissance4Main.java`** class.
3.  **Game Windows:** Two separate windows will appear, one for "Joueur ROUGE" and one for "Joueur JAUNE".


---
## 📝 Author

**[Bazia Abdallah]**

* [[Link to your](https://github.com/abdallah-bazia/Portfolio) ]
