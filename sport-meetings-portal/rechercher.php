<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Recherche</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 0;
            padding: 0;
            background-image: url('photo_5882107963169162428_y(1).jpg');
            background-repeat: repeat;
            color: #333;
            display: flex;
            justify-content: left; /* Horizontally align content */
            align-items: center; /* Vertically align content */
            height: 100vh; /* Ful viewport height */
        }

        h1 {
            text-align: center;
            color: black;
        }

        .form-container {
            width: 22%;
            margin:auto;
         
            padding: 20px;
            border-radius: 8px;
            box-shadow: 2px 4px rgba(0, 0, 0, 0.1);
            background-color: rgba(255, 255, 255, 0.8); /* Semi-transparent white background */
        }

        h2 {
            font-size: 18px;
            margin-bottom: 10px;
            color: darkred;
        }

        label {
            display: block;
            margin-bottom: 8px;
            font-weight: bold;
            color: black;
        }

        input[type="text"],
        input[type="email"],
        select {
            width: 100%;
            padding: 10px;
            margin-bottom: 15px;
            border: 1px solid #ccc;
            border-radius: 4px;
            box-sizing: border-box;
        }

        button[type="submit"],
        button[type="reset"] {
            display: block;
            width: 100%;
            padding: 10px;
            border: none;
            border-radius: 4px;
            font-size: 16px;
            cursor: pointer;
            background-color: grey;
            color: black;
        }

        button[type="submit"]:hover,
        button[type="reset"]:hover {
            background-color: green;
        }
    </style>
</head>
<body>
    <h1><strong><center></center></strong></h1>
    
    <div class="form-container">
        <form name="myForm1" method="POST" action="process.php">

            <h2>Vos critères de recherche</h2>

            <label for="sportrecherche">Sport pratiqué:</label>
            <select name="sportrecherche">
                <option value="">Choisir un sport</option>
                <?php
                // Establish database connection
                $conn = new mysqli('localhost', 'root', '', 'project_finale');
                if ($conn->connect_error) {
                    die("Échec de la connexion à la base de données : " . $conn->connect_error);
                }

                // Retrieve sports from the database
                $sql_sports = "SELECT id_sport, nom_s FROM sport";
                $result_sports = $conn->query($sql_sports);

                if ($result_sports->num_rows > 0) {
                    while ($row = $result_sports->fetch_assoc()) {
                        $id_sport = $row['id_sport'];
                        $nom_sport = htmlspecialchars($row['nom_s']);
                        echo "<option value='$id_sport'>$nom_sport</option>";
                    }
                }

                // Close the database connection
                $conn->close();
                ?>
            </select>

            <label for="Niveau">Niveau:</label>
            <select name="Niveau">
                <option value="Debutant">Débutant</option>
                <option value="confirme">Confirmé</option>
                <option value="pro">Pro</option>
                <option value="supporter">Supporter</option>
            </select>

            <label for="ville">Ville:</label>
            <select name="ville">
                <option value="">Choisir une ville</option>
                <?php
                // Retrieve cities (villes) from the database
                $conn = new mysqli('localhost', 'root', '', 'project_finale');
                if ($conn->connect_error) {
                    die("Échec de la connexion à la base de données : " . $conn->connect_error);
                }

                $sql_villes = "SELECT DISTINCT ville FROM personne";
                $result_villes = $conn->query($sql_villes);

                if ($result_villes->num_rows > 0) {
                    while ($row = $result_villes->fetch_assoc()) {
                        $ville = htmlspecialchars($row['ville']);
                        echo "<option value='$ville'>$ville</option>";
                    }
                }

                // Close the database connection
                $conn->close();
                ?>
            </select>

            <button type="submit">Rechercher</button>
            
        </form>
    </div>
</body>
</html>
