# 🐍 Snake Game – Java 2 Players Edition

A 2D Snake game developed in Java using Swing.  
This project is a classic Snake game extended into a **multiplayer experience (2 players)** with obstacles, scoring, and custom game rules.

The game demonstrates object-oriented programming, real-time game loops, collision detection, and graphical rendering using Java AWT/Swing.

---

## ✨ Features

- 🎮 Two-player Snake gameplay (local multiplayer)
- 🍓 Food system with random spawning (fruits)
- 🧱 Obstacles that affect game outcome
- 📈 Score system (first to 3 wins)
- ⏱️ Real-time game loop using Java `Timer`
- 🧠 Custom growth rule based on number of turns
- ⏸️ Pause and restart system
- 💥 Collision detection (self, opponent, walls, obstacles)
- 🎨 Grid-based graphical rendering (Swing GUI)

---

## 🛠️ Tech Stack

- Java
- Java Swing (GUI)
- AWT Graphics
- Object-Oriented Programming (OOP)

---

## 🎮 Controls

### Player 1 (Green Snake)
- Z → Up
- Q → Left
- S → Down
- D → Right

### Player 2 (Blue Snake)
- ↑ Move Up  
- ↓ Move Down  
- ← Move Left  
- → Move Right  

### Global Controls
- SPACE → Pause / Resume
- R → Restart game

---

## ⚙️ Game Rules

- Each player controls a snake on a grid
- Eating a fruit affects snake growth based on a custom rule (N turns)
- Obstacles cause immediate round resolution
- Leaving the map results in defeat or draw
- First player to reach **3 points wins the game**

---

## 📦 Project Structure

- `Test.java` → Initializes the game window (JFrame)
- `JeuSnake.java` → Core game logic:
  - Rendering
  - Game loop
  - Input handling
  - Collision system
  - Score management

---

## 🧠 Key Concepts Demonstrated

- Object-Oriented Programming (OOP)
- Game loop with `Timer`
- Event-driven programming (KeyListener)
- Collision detection algorithms
- 2D grid-based movement system
- GUI rendering with Swing

---

## 🚀 How to Run

Run the application from:

```bash
Test.java
```
