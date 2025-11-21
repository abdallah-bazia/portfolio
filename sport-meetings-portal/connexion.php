<?php
function calldatabase(){
$base=new mysqli('localhost','root','','project_finale');
if ($base->connect_error) {
    die("Échec de la connexion à la base de données : " . $base->connect_error);
}
return $base;
}
?>
  <?php
    // Include the file containing the database connection function
    include 'connexion.php';

    // Call the function to establish a database connection
    $conn = calldatabase();

    // Query to retrieve the list of available sports
    $sql = "SELECT nom_s FROM sport";
    $result = $conn->query($sql);

    if ($result && $result->num_rows > 0) {
        echo "<h2>Sports disponibles :</h2>";
        echo "<ul>";
        // Display each sport in a list
        while ($row = $result->fetch_assoc()) {
            echo "<li>" . htmlspecialchars($row['nom_s']) . "</li>";
        }
        echo "</ul>";
    } else {
        echo "Aucun sport trouvé.";
    }

    // Close the database connection
    $conn->close();
    ?>
    <?php
// Vérifier si le formulaire a été soumis
if ($_SERVER["REQUEST_METHOD"] == "POST") {
    // Récupérer l'email saisi par l'utilisateur
    $email = $_POST["email"];

    // Inclure le fichier contenant la fonction de connexion à la base de données
    include 'connexion.php';

    // Appeler la fonction calldatabase() pour obtenir la connexion à la base de données
    $conn = calldatabase();

    // Requête SQL pour vérifier si l'email existe dans la base de données
    $sql = "SELECT nom FROM personne WHERE mail = '$email'";
    $result = $conn->query($sql);

    if ($result && $result->num_rows > 0) {
        // L'email existe dans la base de données, afficher un message de bienvenue
        $row = $result->fetch_assoc();
        $nom = $row['nom'];
        echo "<p><h1>Bienvenue, $nom !</h1></p>";
    } else {
        // L'email n'existe pas dans la base de données, afficher un message d'erreur
        echo "<p>Email non trouvé. Veuillez vous inscrire.</p>";
    }

    // Fermer la connexion à la base de données
    $conn->close();
}
?>