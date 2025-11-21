# DFA Simulator in C


## Description
DFA simulator implemented in C It allows users to define an automaton by specifying:

- Alphabet (letters)
- States
- Initial states
- Accepting states
- Transition matrix

The program then displays the automaton and tests whether a **given input letter** is accepted by the automaton.  

This project demonstrates understanding of:

- C structures and arrays
- User input handling
- Finite automata concepts

It can be extended to support **full word testing**, non-deterministic automata (NFA), and dynamic memory handling for larger automata.

## Features
- Input automaton details interactively
- Display states, initial and accepting states, and transition matrix
- Test a letter against the automaton
-Currently supports single-letter testing only
## Usage
1. Compile the code:
   ```bash
   gcc dfa_simulator.c -o dfa_simulator
