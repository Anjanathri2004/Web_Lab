<?php
session_start();
if (!isset($_SESSION['user_id'])) {
    header("Location: login.html?error=unauthorized");
    exit;
}

require 'db.php';

if ($_SERVER['REQUEST_METHOD'] === 'POST') {
    try {
        $user_id = filter_var($_POST['user_id'], FILTER_VALIDATE_INT);
        $order_details = filter_var($_POST['order_details'], FILTER_SANITIZE_STRING);
        $total_price = filter_var($_POST['total_price'], FILTER_VALIDATE_INT);

        if (!$user_id || !$order_details || !$total_price) {
            header("Location: menu.php?error=Invalid order details.");
            exit;
        }

        // Insert order into database
        $stmt = $conn->prepare("INSERT INTO orders (user_id, order_details, total_price) VALUES (:user_id, :order_details, :total_price)");
        $stmt->bindParam(':user_id', $user_id, PDO::PARAM_INT);
        $stmt->bindParam(':order_details', $order_details, PDO::PARAM_STR);
        $stmt->bindParam(':total_price', $total_price, PDO::PARAM_INT);

        if ($stmt->execute()) {
            header("Location: success.php?message=" . urlencode("Your gadget order has been placed successfully!"));
            exit;
        } else {
            header("Location: menu.php?error=Failed to place the order.");
            exit;
        }
    } catch (PDOException $e) {
        error_log("Database error: " . $e->getMessage(), 3, "errors.log");
        header("Location: menu.php?error=" . urlencode("An unexpected error occurred."));
        exit;
    }
} else {
    header("Location: menu.php?error=Invalid request.");
    exit;
}
?>
