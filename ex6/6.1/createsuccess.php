<?php
session_start();
if (!isset($_SESSION['user_id'])) {
    header("Location: login.html?error=unauthorized");
    exit;
}

$message = isset($_GET['message']) ? $_GET['message'] : "Your gadget order has been placed successfully!";
?>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Order Success - Gadget Store</title>
    <style>
        body {
            font-family: 'Arial', sans-serif;
            background: linear-gradient(to bottom right, #1a202c, #2d3748);
            color: #f7fafc;
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
            margin: 0;
        }
        .container {
            background: rgba(0, 0, 0, 0.8);
            padding: 20px;
            border-radius: 10px;
            text-align: center;
            width: 90%;
            max-width: 600px;
            box-shadow: 0 4px 10px rgba(0, 0, 0, 0.5);
        }
        h1 {
            color: #38b2ac;
            margin-bottom: 20px;
        }
        p {
            font-size: 1.2em;
            margin: 15px 0;
        }
        a {
            color: #63b3ed;
            text-decoration: none;
            font-weight: bold;
            transition: color 0.3s;
        }
        a:hover {
            color: #4299e1;
        }
        .button {
            display: inline-block;
            margin-top: 20px;
            padding: 10px 20px;
            background: #38b2ac;
            color: white;
            border: none;
            border-radius: 5px;
            font-size: 1em;
            cursor: pointer;
            text-decoration: none;
            transition: background 0.3s;
        }
        .button:hover {
            background: #2c7a7b;
        }
    </style>
</head>
<body>
    <div class="container">
        <h1><?php echo htmlspecialchars($message); ?></h1>
        <p>Thank you for purchasing from the Gadget Store! We hope you enjoy your new gadget.</p>
        <p><a href="gadget_store.php" class="button">Go Back to Store</a></p>
    </div>
</body>
</html>
