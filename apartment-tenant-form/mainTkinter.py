import tkinter as tk
from tkinter import messagebox
from tkinter import ttk
import mysql.connector
import re

# Global variable to track validation status
is_valid = False

def insert_data_to_db(nom, prenom, date_naissance, email, phone, nin, ville, commune, appartement):
    try:
        # Connect to the database
        conn = mysql.connector.connect(
            host="localhost",
            user="root",  # Replace with your MySQL username
            password="",  # Replace with your MySQL password
            database="apartment_rentals"
        )
        print("Database connection status:", conn.is_connected())  # Check connection status inside the function

        cursor = conn.cursor()

        # SQL query to insert data
        insert_query = """
            INSERT INTO tenants (nom, prenom, date_de_naissance, email, phone, nin, ville, commune, appartement_type)
            VALUES (%s, %s, %s, %s, %s, %s, %s, %s, %s)
        """
        data = (nom, prenom, date_naissance, email, phone, nin, ville, commune, appartement)
        cursor.execute(insert_query, data)
        conn.commit()

        # Verify if the data was inserted correctly by querying the database
        select_query = """
            SELECT * FROM tenants WHERE nom = %s AND prenom = %s AND date_de_naissance = %s AND email = %s
        """
        cursor.execute(select_query, (nom, prenom, date_naissance, email))
        result = cursor.fetchone()

        if result:
            messagebox.showinfo("Success", "Data successfully inserted into the database and verified!")
        else:
            messagebox.showwarning("Insertion Failed", "The data could not be verified in the database.")
            
    except mysql.connector.Error as err:
        messagebox.showerror("Database Error", f"Error: {err}")
    except Exception as e:
        messagebox.showerror("Unexpected Error", f"Unexpected error: {e}")
    finally:
        cursor.close()
        conn.close()



def validate_input():
    global is_valid  # Access the global variable

    # Get inputs
    date_input = date.get()
    email_input = email.get()
    nom_input = nom.get()
    prenom_input = prenom.get()
    phone_input = phone.get()
    nin_input = nin.get()
    ville_input = ville.get()
    commune_input = commune.get()
    appartement_input = appartement.get()

    # Reset validation status
    is_valid = False

    # Validate Nom (only alphabetic characters)
    if not nom_input.isalpha():
        messagebox.showwarning("Invalid Nom", "Please enter a valid nom (only alphabetic characters).")
        return

    # Validate Prenom (only alphabetic characters)
    if not prenom_input.isalpha():
        messagebox.showwarning("Invalid Prenom", "Please enter a valid prenom (only alphabetic characters).")
        return

    # Validate Date (format jj/mm/aaaa)
    if not re.match(r'^\d{2}/\d{2}/\d{4}$', date_input):
        messagebox.showwarning("Invalid Date", "Please enter a valid date in the format jj/mm/aaaa.")
        return

    # Validate Email (basic email format check)
    if not re.match(r'^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\.[a-zA-Z0-9-.]+$', email_input):
        messagebox.showwarning("Invalid Email", "Please enter a valid email address (e.g., example@domain.com).")
        return

    # Validate Phone Number (example format: 05 55 55 55 55)
    if not re.match(r'^\d{2} \d{2} \d{2} \d{2} \d{2}$', phone_input):
        messagebox.showwarning("Invalid Phone Number", "Please enter a valid phone number in the format 05 55 55 55 55.")
        return

    # Validate NIN (assumed to be only numbers, length 16)
    if not re.match(r'^\d{16}$', nin_input):
        messagebox.showwarning("Invalid NIN", "Please enter a valid NIN (16 digits).")
        return

    # Validate Ville (should not be empty)
    if not ville_input:
        messagebox.showwarning("Invalid Ville", "Please select a ville.")
        return

    # Validate Commune (only alphabetic characters)
    if not commune_input.isalpha():
        messagebox.showwarning("Invalid Commune", "Please enter a valid commune (only alphabetic characters).")
        return

    # Validate Appartement Type (F2, F3, or F4)
    if appartement_input not in ['F2', 'F3', 'F4']:
        messagebox.showwarning("Invalid Appartement", "Please select a valid appartement type (F2, F3, or F4).")
        return

    # If all fields are valid
    is_valid = True
    messagebox.showinfo("Validation", "All inputs are valid!")

    # Enable the Send button after successful validation
    send_button.config(state=tk.NORMAL)

def send_data():
    global is_valid  # Access the global variable

    # Only proceed to send data if validation has passed
    if not is_valid:
        messagebox.showwarning("Validation Required", "Please validate the inputs first.")
        return

    # Get inputs again
    nom_input = nom.get()
    prenom_input = prenom.get()
    date_input = date.get()
    email_input = email.get()
    phone_input = phone.get()
    nin_input = nin.get()
    ville_input = ville.get()
    commune_input = commune.get()
    appartement_input = appartement.get()

    # Send data to the database
    insert_data_to_db(nom_input, prenom_input, date_input, email_input, phone_input, nin_input, ville_input, commune_input, appartement_input)

# Create the main window
root = tk.Tk()
root.title("Page principale")
root.geometry("600x600")  # Adjust the geometry

# Create and place labels and input fields with a clean grid layout
label1 = tk.Label(root, text="Bienvenue !", font=("Helvetica", 14))
label1.grid(row=0, column=0, columnspan=2, padx=20, pady=10)

label_nom = tk.Label(root, text="Nom:")
label_nom.grid(row=1, column=0, padx=10, pady=5)
nom = tk.Entry(root)
nom.grid(row=1, column=1, padx=10, pady=5)

label_prenom = tk.Label(root, text="Prenom:")
label_prenom.grid(row=2, column=0, padx=10, pady=5)
prenom = tk.Entry(root)
prenom.grid(row=2, column=1, padx=10, pady=5)

label_date = tk.Label(root, text="Date de naissance (jj/mm/aaaa):")
label_date.grid(row=3, column=0, padx=10, pady=5)
date = tk.Entry(root)
date.grid(row=3, column=1, padx=10, pady=5)

label_email = tk.Label(root, text="Adresse email (Format: exemple@domaine.com):")
label_email.grid(row=4, column=0, padx=10, pady=5)
email = tk.Entry(root)
email.grid(row=4, column=1, padx=10, pady=5)

label_phone = tk.Label(root, text="Numéro Téléphone:")
label_phone.grid(row=5, column=0, padx=10, pady=5)
phone = tk.Entry(root)
phone.grid(row=5, column=1, padx=10, pady=5)

label_nin = tk.Label(root, text="Numéro d'identification nationale (NIN):")
label_nin.grid(row=6, column=0, padx=10, pady=5)
nin = tk.Entry(root)
nin.grid(row=6, column=1, padx=10, pady=5)

label_ville = tk.Label(root, text="Ville:")
label_ville.grid(row=7, column=0, padx=10, pady=5)
ville = ttk.Combobox(root, values=["Alger", "Oran", "Constantine", "Tlemcen", "Annaba"])
ville.grid(row=7, column=1, padx=10, pady=5)

label_commune = tk.Label(root, text="Commune:")
label_commune.grid(row=8, column=0, padx=10, pady=5)
commune = tk.Entry(root)
commune.grid(row=8, column=1, padx=10, pady=5)

label_appartement = tk.Label(root, text="Type d’appartement (F2, F3, F4):")
label_appartement.grid(row=9, column=0, padx=10, pady=5)
appartement = ttk.Combobox(root, values=["F2", "F3", "F4"])
appartement.grid(row=9, column=1, padx=10, pady=5)

# Create the Validate button
validate_button = tk.Button(root, text="Validate", command=validate_input)
validate_button.grid(row=10, column=0, padx=10, pady=10)

# Create the Send button
send_button = tk.Button(root, text="Send", command=send_data, state=tk.DISABLED)
send_button.grid(row=10, column=1, padx=10, pady=10)

# Run the main loop
root.mainloop()
