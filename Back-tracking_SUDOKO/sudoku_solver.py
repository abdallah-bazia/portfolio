import tkinter as tk
from tkinter import messagebox
import copy
import random

def condition(mat, r, c, n):
    for j in range(9):
        if mat[r][j] == n: return False
    for i in range(9):
        if mat[i][c] == n: return False
    sr, sc = (r//3)*3, (c//3)*3
    for i in range(sr, sr+3):
        for j in range(sc, sc+3):
            if mat[i][j] == n: return False
    return True

def solution(mat, show=False):
    for i in range(9):
        for j in range(9):
            if mat[i][j] == 0:
                for n in range(1, 10):
                    if show:
                        print(f"tester {n} en position({i},{j}):")
                    if condition(mat, i, j, n):
                        mat[i][j] = n
                        if show:
                            print(f"{n} convient pour({i},{j}):")
                            for row in mat:
                                print(row)
                            print("_"*35)
                        if solution(mat, show):
                            return True
                        mat[i][j] = 0
                    else:
                        if show:
                            print(f"{n} n'est pas un solution en position({i},{j}):")
                return False
    return True

def generate_puzzle():
    mat = [[0]*9 for _ in range(9)]
    for box in range(0, 9, 3):
        nums = list(range(1, 10))
        random.shuffle(nums)
        for i in range(3):
            for j in range(3):
                mat[box+i][box+j] = nums[i*3+j]
    solution(mat)
    puzzle = copy.deepcopy(mat)
    for _ in range(random.randint(45, 55)):
        i, j = random.randint(0, 8), random.randint(0, 8)
        puzzle[i][j] = 0
    return puzzle

class SudokuGUI:
    def __init__(self, root):
        self.root = root
        self.root.title("Sudoku Solver")
        self.root.resizable(False, False)
        self.puzzle = generate_puzzle()
        self.count = 1
        self.cells = []
        self.create_grid()
        self.create_buttons()
        self.load_puzzle()
        
    def create_grid(self):
        frame = tk.Frame(self.root, bg='black', padx=2, pady=2)
        frame.pack(padx=10, pady=10)
        for i in range(9):
            row = []
            for j in range(9):
                cell = tk.Entry(tk.Frame(frame, bg='black'), width=3, 
                              font=('Arial', 18, 'bold'), justify='center', bd=0)
                cell.pack()
                cell.master.grid(row=i, column=j, 
                               padx=(2 if j%3==0 else 1, 2 if j%3==2 else 1),
                               pady=(2 if i%3==0 else 1, 2 if i%3==2 else 1))
                vcmd = (self.root.register(lambda p: p=="" or (p.isdigit() and 1<=int(p)<=9)), '%P')
                cell.config(validate='key', validatecommand=vcmd)
                row.append(cell)
            self.cells.append(row)
    
    def create_buttons(self):
        frame = tk.Frame(self.root)
        frame.pack(pady=10)
        tk.Button(frame, text="Solve", font=('Arial', 12, 'bold'), bg='#4CAF50', 
                 fg='white', padx=20, pady=5, command=self.solve).grid(row=0, column=0, padx=5)
        tk.Button(frame, text="Show Steps", font=('Arial', 12, 'bold'), bg='#9C27B0',
                 fg='white', padx=20, pady=5, command=self.solve_steps).grid(row=0, column=1, padx=5)
        tk.Button(frame, text="New Game", font=('Arial', 12, 'bold'), bg='#FF9800',
                 fg='white', padx=20, pady=5, command=self.new_game).grid(row=0, column=2, padx=5)
    
    def load_puzzle(self):
        for i in range(9):
            for j in range(9):
                self.cells[i][j].delete(0, tk.END)
                if self.puzzle[i][j] != 0:
                    self.cells[i][j].insert(0, str(self.puzzle[i][j]))
                    self.cells[i][j].config(fg='black')
    
    def get_grid(self):
        return [[int(self.cells[i][j].get() or 0) for j in range(9)] for i in range(9)]
    
    def display(self, mat):
        for i in range(9):
            for j in range(9):
                if not self.cells[i][j].get():
                    self.cells[i][j].insert(0, str(mat[i][j]))
                    self.cells[i][j].config(fg='green')
    
    def solve(self):
        mat = copy.deepcopy(self.get_grid())
        if solution(mat):
            self.display(mat)
            messagebox.showinfo("Success", "Solved!")
        else:
            messagebox.showerror("Error", "No solution!")
    
    def solve_steps(self):
        mat = copy.deepcopy(self.get_grid())
        print("\n" + "="*40 + "\nSOLVING PROCESS:\n" + "="*40)
        if solution(mat, show=True):
            print("="*40 + "\nSOLUTION COMPLETE!\n" + "="*40 + "\n")
            self.display(mat)
            messagebox.showinfo("Success", "Solved! Check terminal.")
        else:
            messagebox.showerror("Error", "No solution!")
    
    def new_game(self):
        self.puzzle = generate_puzzle()
        self.count += 1
        self.load_puzzle()
        messagebox.showinfo("New Game", f"Puzzle #{self.count}")

def main():
    root = tk.Tk()
    SudokuGUI(root)
    root.mainloop()

if __name__ == "__main__":
    main()