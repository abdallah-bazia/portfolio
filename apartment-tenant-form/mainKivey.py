import re
import threading
from functools import partial

import mysql.connector
from mysql.connector import Error

from kivy.app import App
from kivy.uix.label import Label
from kivy.uix.textinput import TextInput
from kivy.uix.spinner import Spinner
from kivy.uix.boxlayout import BoxLayout
from kivy.uix.gridlayout import GridLayout
from kivy.uix.button import Button
from kivy.uix.popup import Popup
from kivy.core.window import Window

Window.size = (600, 700)


def show_popup(title, message):
    box = BoxLayout(orientation='vertical', padding=10, spacing=10)
    box.add_widget(Label(text=message))
    btn = Button(text='OK', size_hint=(1, 0.25))
    box.add_widget(btn)
    popup = Popup(title=title, content=box, size_hint=(0.8, 0.45))
    btn.bind(on_release=popup.dismiss)
    popup.open()


class TenantForm(BoxLayout):
    def __init__(self, **kwargs):
        super().__init__(orientation='vertical', padding=20, spacing=10, **kwargs)

        self.is_valid = False

        title = Label(text="Formulaire Locataire", font_size=24, size_hint=(1, None), height=50)
        self.add_widget(title)

        grid = GridLayout(cols=2, row_force_default=True, row_default_height=40, spacing=8, size_hint_y=None)
        grid.bind(minimum_height=grid.setter('height'))

        # Fields
        grid.add_widget(Label(text="Nom:"))
        self.nom = TextInput(multiline=False)
        grid.add_widget(self.nom)

        grid.add_widget(Label(text="Prénom:"))
        self.prenom = TextInput(multiline=False)
        grid.add_widget(self.prenom)

        grid.add_widget(Label(text="Date de naissance (jj/mm/aaaa):"))
        self.date = TextInput(multiline=False, hint_text="jj/mm/aaaa")
        grid.add_widget(self.date)

        grid.add_widget(Label(text="Adresse email:"))
        self.email = TextInput(multiline=False)
        grid.add_widget(self.email)

        grid.add_widget(Label(text="Numéro Téléphone (05 55 55 55 55):"))
        self.phone = TextInput(multiline=False)
        grid.add_widget(self.phone)

        grid.add_widget(Label(text="NIN (16 chiffres):"))
        self.nin = TextInput(multiline=False)
        grid.add_widget(self.nin)

        grid.add_widget(Label(text="Ville:"))
        self.ville = Spinner(text="Sélectionner", values=["Alger", "Oran", "Constantine", "Tlemcen", "Annaba"])
        grid.add_widget(self.ville)

        grid.add_widget(Label(text="Commune:"))
        self.commune = TextInput(multiline=False)
        grid.add_widget(self.commune)

        grid.add_widget(Label(text="Type d’appartement (F2, F3, F4):"))
        self.appartement = Spinner(text="Sélectionner", values=["F2", "F3", "F4"])
        grid.add_widget(self.appartement)

        self.add_widget(grid)

        # Buttons layout
        btn_layout = BoxLayout(size_hint=(1, None), height=50, spacing=10)
        self.validate_btn = Button(text="Valider")
        self.validate_btn.bind(on_release=self.validate_inputs)
        btn_layout.add_widget(self.validate_btn)

        self.send_btn = Button(text="Envoyer", disabled=True)
        self.send_btn.bind(on_release=self.send_data)
        btn_layout.add_widget(self.send_btn)

        self.add_widget(btn_layout)

    def validate_inputs(self, instance=None):
        nom = self.nom.text.strip()
        prenom = self.prenom.text.strip()
        date_input = self.date.text.strip()
        email_input = self.email.text.strip()
        phone_input = self.phone.text.strip()
        nin_input = self.nin.text.strip()
        ville_input = self.ville.text if self.ville.text != "Sélectionner" else ""
        commune_input = self.commune.text.strip()
        appartement_input = self.appartement.text if self.appartement.text != "Sélectionner" else ""

        # Nom and Prenom: alphabetic (allowing spaces and hyphens)
        if not re.match(r'^[A-Za-zÀ-ÖØ-öø-ÿ\s-]+$', nom) or nom == "":
            show_popup("Nom invalide", "Veuillez entrer un nom valide (lettres seulement).")
            return

        if not re.match(r'^[A-Za-zÀ-ÖØ-öø-ÿ\s-]+$', prenom) or prenom == "":
            show_popup("Prénom invalide", "Veuillez entrer un prénom valide (lettres seulement).")
            return

        # Date: dd/mm/yyyy
        if not re.match(r'^\d{2}/\d{2}/\d{4}$', date_input):
            show_popup("Date invalide", "Veuillez entrer la date au format jj/mm/aaaa.")
            return

        # Email: basic check
        if not re.match(r'^[\w\.-]+@[\w\.-]+\.\w+$', email_input):
            show_popup("Email invalide", "Veuillez entrer une adresse email valide.")
            return

        # Phone: 05 55 55 55 55 (exact format)
        if not re.match(r'^\d{2} \d{2} \d{2} \d{2} \d{2}$', phone_input):
            show_popup("Téléphone invalide", "Numéro de téléphone invalide. Format: 05 55 55 55 55")
            return

        # NIN: 16 digits
        if not re.match(r'^\d{16}$', nin_input):
            show_popup("NIN invalide", "Le NIN doit contenir exactement 16 chiffres.")
            return

        # Ville
        if not ville_input:
            show_popup("Ville manquante", "Veuillez sélectionner une ville.")
            return

        # Commune: alphabetic (allow spaces)
        if not re.match(r'^[A-Za-zÀ-ÖØ-öø-ÿ\s-]+$', commune_input) or commune_input == "":
            show_popup("Commune invalide", "Veuillez entrer une commune valide (lettres seulement).")
            return

        # Appartement
        if appartement_input not in ["F2", "F3", "F4"]:
            show_popup("Appartement invalide", "Veuillez sélectionner F2, F3 ou F4.")
            return

        self.is_valid = True
        self.send_btn.disabled = False
        show_popup("Validation ok", "Toutes les données sont valides !")

    def send_data(self, instance=None):
        if not self.is_valid:
            show_popup("Validation requise", "Veuillez valider les données avant d'envoyer.")
            return

        data = (
            self.nom.text.strip(),
            self.prenom.text.strip(),
            self.date.text.strip(),
            self.email.text.strip(),
            self.phone.text.strip(),
            self.nin.text.strip(),
            self.ville.text.strip(),
            self.commune.text.strip(),
            self.appartement.text.strip(),
        )

        # Run DB insert in a separate thread to avoid freezing UI
        threading.Thread(target=partial(self.insert_into_db, data), daemon=True).start()

    def insert_into_db(self, data):
        try:
            conn = mysql.connector.connect(
                host="localhost",
                user="root",      # change if needed
                password="",      # change if needed
                database="apartment_rentals"
            )

            cursor = conn.cursor()
            insert_query = """
                INSERT INTO tenants
                (nom, prenom, date_de_naissance, email, phone, nin, ville, commune, appartement_type)
                VALUES (%s, %s, %s, %s, %s, %s, %s, %s, %s)
            """
            cursor.execute(insert_query, data)
            conn.commit()

            # verify insertion (optional)
            verify_query = """
                SELECT id FROM tenants WHERE nom=%s AND prenom=%s AND date_de_naissance=%s AND email=%s
            """
            cursor.execute(verify_query, (data[0], data[1], data[2], data[3]))
            result = cursor.fetchone()
            if result:
                show_popup("Succès", "Données insérées et vérifiées avec succès !")
            else:
                show_popup("Échec", "Insertion réussie mais vérification a échoué.")

        except Error as e:
            show_popup("Erreur base de données", f"Erreur : {e}")

        except Exception as ex:
            show_popup("Erreur inattendue", f"Erreur : {ex}")

        finally:
            try:
                cursor.close()
                conn.close()
            except:
                pass


class TenantApp(App):
    def build(self):
        return TenantForm()


if __name__ == '__main__':
    TenantApp().run()
