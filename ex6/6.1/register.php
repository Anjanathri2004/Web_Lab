<?php
require 'db.php';

if ($_SERVER["REQUEST_METHOD"] == "POST") {
    $name = $_POST['name'];
    $email = $_POST['email'];
    $password = password_hash($_POST['password'], PASSWORD_BCRYPT);

    $sql = "INSERT INTO Users (name, email, password) VALUES (:name, :email, :password)";
    $stmt = $conn->prepare($sql);

    try {
        $stmt->execute(['name' => $name, 'email' => $email, 'password' => $password]);
        header("Location: login.html?success=registered");
        exit;
    } catch (PDOException $e) {
        echo "Error: " . $e->getMessage();
    }
}
?>
