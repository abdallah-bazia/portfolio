<?php
if ($_SERVER["REQUEST_METHOD"] == "POST") {
    // Retrieve form data
    $sport = $_POST["sportrecherche"];
    $niveau = $_POST["Niveau"];
    $ville = $_POST["ville"];

    // Establish database connection
    $conn = new mysqli('localhost', 'root', '', 'project_finale');
    if ($conn->connect_error) {
        die("Échec de la connexion à la base de données : " . $conn->connect_error);
    }

    // Prepare and execute SQL query with JOIN
    $sql = "SELECT p.nom, p.prenom, p.ville, p.mail, pr.id_sport FROM personne p 
            LEFT JOIN pratique pr ON p.mail = pr.mail AND pr.id_sport = '$sport' AND pr.niveau = '$niveau'
            WHERE p.ville = '$ville'";

    $result = $conn->query($sql);

    if ($result === false) {
        // Handle SQL query eror
        die("Erreur lors de l'exécution de la requête : " . $conn->error);
    }

    if ($result->num_rows > 0) {
        // Display search results
        echo "<div style='margin-top: 20px;margin-bottom: 10px;'>";
        while ($row = $result->fetch_assoc()) {
            $backgroundColor = isset($row['id_sport']) ? 'green' : 'red';
            echo "<div style='border: 1px solid #ccc; padding: 10px; margin-bottom: 10px; background-color: $backgroundColor;'>";
            echo "<strong>Nom:</strong> " . htmlspecialchars($row["nom"]) . "<br>";
            echo "<strong>Prénom:</strong> " . htmlspecialchars($row["prenom"]) . "<br>";
            echo "<strong>Ville:</strong> " . htmlspecialchars($row["ville"]) . "<br>";
            echo "<strong>Email:</strong> " . htmlspecialchars($row["mail"]) . "<br>";
            echo "</div>";
        }
        echo "</div>";
    } else {
        echo "Aucun résultat trouvé.";
    }

    // Close the database connection
    $conn->close();
}
?>
