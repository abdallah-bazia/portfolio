# Sudoku Solver

A Python Sudoku game with GUI that generates and solves puzzles using backtracking.

## Features

- Generate random Sudoku puzzles
- Solve puzzles instantly or view step-by-step solution
- Clean Tkinter GUI with input validation
- Visual distinction between original and solved numbers

## Installation

```bash
git clone https://github.com/Abdallah/sudoku-solver.git
cd sudoku-solver
python sudoku_solver.py
```

**Requirements**: Python 3.6+ (Tkinter included)

## Usage

- **Solve**: Complete the puzzle instantly
- **Show Steps**: View the backtracking process in terminal
- **New Game**: Generate a new puzzle

## How It Works

Uses recursive backtracking to solve puzzles. Generates puzzles by filling diagonal boxes randomly, solving the grid, then removing 45-55 numbers.

## Customization

Adjust difficulty in `generate_puzzle()`:

```python
for _ in range(random.randint(30, 40)):  # Easy
for _ in range(random.randint(45, 55)):  # Medium (default)
for _ in range(random.randint(55, 65)):  # Hard
```

## License

MIT License