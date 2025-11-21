<?php

// Function to establish a database connection
function calldatabase() {
    $base = new mysqli('localhost', 'root', '', 'project_finale');
    if ($base->connect_error) {
        die("Échec de la connexion à la base de données : " . $base->connect_error);
    }
    return $base;
}

// Check if the form has been submitted
if ($_SERVER["REQUEST_METHOD"] == "POST") {
    // Retrieve the email submitted by the user
    $email = $_POST["email"];

    // Establish a database connection
    $conn = calldatabase();

    // Query to check if the email exists in the database
    $sql = "SELECT nom FROM personne WHERE mail = '$email'";
    $result = $conn->query($sql);

    if ($result && $result->num_rows > 0) {
        // Email exists in the database, fetch the corresponding name
        $row = $result->fetch_assoc();
        $nom = $row['nom'];
        echo "<p><h4>Bienvenue, $nom !</h4></p>";
        echo "<p>Si vous voulez vous inscrire dans un nouveau sport, <a href='inscripsiton.php'>inscrivez-vous maintenant </a>.</p>";
    }
     else {
        // Email  not exist in the database, redirect to inscription.php
        header("Location: inscripsiton.php");
        exit; // Stop further execution
    }

    // Close the database connection
    $conn->close();
}

// Retrieve the list of available sports
$conn = calldatabase();
$sql_sports = "SELECT nom_s FROM sport";
$result_sports = $conn->query($sql_sports);

?>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Le site des rencontres sportives</title>
    <style>
        body {
            font-family: Arial, sans-serif;
    margin:50;
    padding: 0;
    background-image: url('photo_5882107963169162427_x.jpg');
    background-repeat: no-repeat;
    background-position: center top 50px;
    
    color: #333; /* Text color for better contrast */
        }
        h1 {
            text-align: center;
            color: black;
        }
        form {
            max-width: 400px;
            margin: 0 auto;
            background-color: gery;
            padding: 0px;
            border-radius: 8px;
            box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
          
        }
        label {
            display: block;
            font-weight: bolder;
            margin-bottom: 8px;
            color: grey;
        }
        input {
            width: 100%;
            padding: 7px;
            margin-bottom: 15px;
            border: 1px solid #ccc;
            border-radius: 4px;
            box-sizing: border-box;
        }
        button[type="submit"] {
            display: block;
            width: 100%;
            padding: 12px;
            border: bold;
            border-radius: 4px;
            background-color: green;
            color: black;
            font-size: 16px;
            cursor: pointer;
        }
        button[type="submit"]:hover {
            background-color: grey;
        }
        ul {
            list-style-type: none;
            padding: 0;
        }
        li {
            
            content: counter(item) ". "; /* Display custom numbering (e.g., 1., 2., 3., ...) */
    color: green; /* Set color for numbering */
    font-weight: bold; /* Optionally, make numbering bold */
        }
        
    </style>
</head>
<body>

    <h1>Le site des rencontres sportives</h1>

    <!-- Display list of available sports -->
    <?php if ($result_sports && $result_sports->num_rows > 0) : ?>
        <h2>Sports disponibles :</h2>
        <ul>
            <?php while ($row = $result_sports->fetch_assoc()) : ?>
                <li>
                <a href='#'><h3><?php echo htmlspecialchars($row['nom_s']); ?></a></h3>
                </li>
            <?php endwhile; ?>
        </ul>
    <?php else : ?>
        <p>Aucun sport trouvé.</p>
    <?php endif; ?>

    <!-- Links to other pages -->
    
    
    <center> <p >si vous n'êtes pas encore inscrit, inscrivez-vous <a href="inscripsiton.php">ici</a></p></center>
   
    <!-- Form to capture email for identification -->
    <form method="POST" action="indexx.php" name="myForm1">
        <label for="email">Email :</label>
        <input type="email" id="email" name="email" placeholder="Saisissez votre email" required>
        <label for="email">mot de pass :</label>
        <input type="password" id="pass" name="pass" placeholder="Saisissez votre mot de pass" required>
        <button type="submit">Se connecter</button>
        <button type="submit" > <a href="rechercher.php">Rechrecher</a></button>
    </form>
   <center> <p >si vous n'êtes pas encore inscrit, inscrivez-vous <a href="inscripsiton.php">ici</a></p></center>
   
</body>
</html>

<?php

// Close the database connection after rendering the HTML
$conn->close();

?>
