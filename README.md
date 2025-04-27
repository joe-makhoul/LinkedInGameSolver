## LinkedIn Puzzle Solver

This project is a JavaFX desktop application that provides interactive solvers for two logic puzzles: **LinkedIn Queens** and **LinkedIn Tango**. It features graphical user interfaces for both puzzles, allowing users to set up boards, customize parameters, and visualize solutions.

---

### Features

- **Queens Solver:**  
  - Interactive N-Queens variant with customizable board size (5–11).
  - Color-coded board setup using a color picker.
  - Visual placement and solution display for queens.
  - Board clearing and resizing functionality.

- **Tango Solver:**  
  - Interactive grid for the LinkedIn Tango puzzle (default size 6x6).
  - Place Sun and Moon icons and set edge types (normal, equals, cross).
  - Visual feedback for cell and edge states.
  - Grid clearing and solving actions.

- **Unified Application:**  
  - Both solvers are accessible from a single application window.
  - Switch between puzzles using keyboard shortcuts (`Q` for Queens, `T` for Tango).
  - Modern JavaFX interface with responsive controls.

---

### Getting Started

#### **Prerequisites**

- Java 17 or higher (JavaFX required)

#### **Usage**

- **Switch puzzles:**  
  - Press `Q` to bring the Queens Solver to the front.
  - Press `T` to bring the Tango Solver to the front.

- **Queens Solver:**  
  - Select board size and confirm.
  - Use the color picker to assign colors to a cell by clicking on it.
  - Click "Solve grid" to display a solution.
  - Click "Clear grid" to reset the board.
  - Click "Change size" to select a new board size.

- **Tango Solver:**  
  - Click cells to cycle through Sun, Moon, and empty.
  - Click edges to cycle through =, x and normal edge.
  - Click "Solve grid" to validate or solve.
  - Click "Clear grid" to reset the grid.

---

### Project Structure

```
solver_app/
  gui/         # JavaFX UI classes (TangoUI, QueensUI, SolverApp)
  queens/      # Logic for Queens puzzle (QueensBoard)
  tango/       # Logic for Tango puzzle (TangoGrid, CellType, EdgeType, EdgeOrientation)
  Solver.java  # Puzzle solving logic (not shown)
resources/
  solver-style.css  # JavaFX styles
```

---

### Key Classes
* SolverApp.java: Main application entry point and handles switching between the Queens and Tango solvers.
* QueensUI.java: Manages the graphical user interface for the Queens puzzle, including board setup, color selection, and solution visualization.
* QueensBoard.java: Contains the logic for the Queens puzzle board, including queen placement, color assignment, and board validation.
* TangoUI.java: Manages the graphical user interface for the Tango puzzle, including cell and edge interaction, and solution visualization.
* TangoGrid.java: Contains the logic for the Tango puzzle grid, including cell and edge state management, and grid validation.
* CellType.java: Enum defining cell types (EMPTY, SUN, MOON) for the Tango puzzle, each with a graphical symbol.
* EdgeType.java: Enum defining edge types (NORMAL, EQUALS, CROSS) for the Tango puzzle, each with a display symbol.
* EdgeOrientation.java: Enum specifying edge orientation (HORIZONTAL, VERTICAL) for the Tango puzzle.



--- 

**Note:**  
If you encounter any issues or have suggestions, please open an issue or submit a pull request.

---
