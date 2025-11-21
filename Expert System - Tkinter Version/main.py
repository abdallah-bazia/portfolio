import tkinter as tk
from tkinter import messagebox, simpledialog
import re


rules = []
facts = set()


def parse_rule(rule_text, index, priority=0):
    pattern = r"Si\s+(.*?)\s+alors\s+(.*)"
    match = re.match(pattern, rule_text, re.IGNORECASE)
    if match:
        premises = [p.strip() for p in match.group(1).split("et")]
        conclusion = match.group(2).strip()
        return {
            "index": index,
            "premises": premises,
            "conclusion": conclusion,
            "priority": priority
        }
    return None


def forward_chaining(target=None):
    steps = []
    current_facts = facts.copy()
    triggered = set()
    iteration = 1
    steps.append("Faits initiaux: " + ", ".join(sorted(current_facts)))

    while True:
        fired = False
        applicable = []

        for rule in rules:
            if rule["index"] not in triggered and all(p in current_facts for p in rule["premises"]):
                applicable.append(rule)

        if not applicable:
            break

        
        applicable.sort(key=lambda r: (-r["priority"], r["index"]))
        rule = applicable[0]

        if rule["conclusion"] not in current_facts:
            current_facts.add(rule["conclusion"])
            triggered.add(rule["index"])
            fired = True

            steps.append(f"Itération {iteration}: R{rule['index']} déclenchée → {rule['conclusion']}")
            steps.append(f"Faits: {', '.join(sorted(current_facts))}")
            if target and target in current_facts:
                steps.append(f" But {target} atteint.")
                return True, steps
            iteration += 1
        else:
            triggered.add(rule["index"])

        if not fired:
            break

    if target:
        reached = target in current_facts
        steps.append(f"{'' if reached else ''} But {target} {'atteint' if reached else 'non atteint'}.")
        return reached, steps

    return True, steps


def backward_chaining(target):
    steps = []
    visited = set()

    def prove(goal, depth=0):
        indent = "  " * depth
        steps.append(f"{indent}Prouver {goal}")
        if goal in facts:
            steps.append(f"{indent}✓ {goal} est connu.")
            return True

        if goal in visited:
            steps.append(f"{indent}  {goal} déjà visité.")
            return False
        visited.add(goal)

        applicable = [r for r in rules if r["conclusion"] == goal]
        applicable.sort(key=lambda r: r["index"])

        for rule in applicable:
            steps.append(f"{indent}Essayer R{rule['index']}: {' et '.join(rule['premises'])} → {goal}")
            if all(prove(p, depth+1) for p in rule["premises"]):
                steps.append(f"{indent} R{rule['index']} prouvée.")
                return True
            else:
                steps.append(f"{indent} R{rule['index']} échouée.")
        visited.remove(goal)
        steps.append(f"{indent} {goal} non prouvé.")
        return False

    result = prove(target)
    return result, steps


def add_rule():
    rule_text = rule_entry.get().strip()
    try:
        priority = int(priority_entry.get().strip())
    except ValueError:
        messagebox.showerror("Erreur", "La priorité doit être un entier.")
        return
    if not rule_text:
        return messagebox.showwarning("Erreur", "Règle vide.")
    rule = parse_rule(rule_text, len(rules)+1, priority)
    if rule:
        rules.append(rule)
        rules_box.insert(tk.END, f"R{rule['index']} (P{priority}): {rule_text}")
        rule_entry.delete(0, tk.END)
        priority_entry.delete(0, tk.END)
    else:
        messagebox.showerror("Erreur", "Format: Si <a> et <b> alors <c>")


def add_fact():
    fact = fact_entry.get().strip()
    if fact:
        facts.add(fact)
        update_facts()

def update_facts():
    facts_box.delete(0, tk.END)
    for fact in sorted(facts):
        facts_box.insert(tk.END, fact)

# Forward chaining button
def run_forward():
    target = simpledialog.askstring("Chaînage avant", "But (laisser vide si aucun):")
    result, steps = forward_chaining(target)
    output_box.delete(1.0, tk.END)
    output_box.insert(tk.END, "\n".join(steps))

# Backward chaining button
def run_backward():
    target = simpledialog.askstring("Chaînage arrière", "Quel est le but ?")
    if target:
        result, steps = backward_chaining(target)
        output_box.delete(1.0, tk.END)
        output_box.insert(tk.END, "\n".join(steps))

# Clear everything
def reset_all():
    rules.clear()
    facts.clear()
    rules_box.delete(0, tk.END)
    facts_box.delete(0, tk.END)
    output_box.delete(1.0, tk.END)

# UI
root = tk.Tk()
root.title("Système Expert - Chaînage Avant/Arrière")

tk.Label(root, text="Fait:").grid(row=0, column=0)
fact_entry = tk.Entry(root, width=20)
fact_entry.grid(row=0, column=1)
tk.Button(root, text="Ajouter Fait", command=add_fact).grid(row=0, column=2)

tk.Label(root, text="Règle:").grid(row=1, column=0)
rule_entry = tk.Entry(root, width=40)
rule_entry.grid(row=1, column=1)
priority_entry = tk.Entry(root, width=5)
priority_entry.grid(row=1, column=2)
tk.Label(root, text="Priorité").grid(row=1, column=3)
tk.Button(root, text="Ajouter Règle", command=add_rule).grid(row=1, column=4)

tk.Label(root, text="Faits:").grid(row=2, column=0)
facts_box = tk.Listbox(root, height=6, width=30)
facts_box.grid(row=3, column=0, columnspan=2)

tk.Label(root, text="Règles:").grid(row=2, column=2)
rules_box = tk.Listbox(root, height=6, width=50)
rules_box.grid(row=3, column=2, columnspan=3)

tk.Button(root, text="Chaînage Avant", command=run_forward).grid(row=4, column=0)
tk.Button(root, text="Chaînage Arrière", command=run_backward).grid(row=4, column=1)
tk.Button(root, text="Réinitialiser", command=reset_all).grid(row=4, column=2)

output_box = tk.Text(root, height=20, width=80)
output_box.grid(row=5, column=0, columnspan=5)

root.mainloop()
