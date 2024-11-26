<?php
session_start();

if (!isset($_SESSION['user_id'])) {
    header("Location: login.html?error=unauthorized");
    exit;
}

require 'db.php';
?>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Gadget Store</title>
    <style>
        body {
            font-family: 'Arial', sans-serif;
            background: linear-gradient(to bottom right, #1e1e2f, #27273f);
            color: white;
            margin: 0;
            padding: 0;
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
            text-align: center;
        }
        .container {
            background-color: rgba(0, 0, 0, 0.6);
            padding: 30px;
            border-radius: 15px;
            width: 90%;
            max-width: 1200px;
        }
        h1, h2 {
            color: #f4f4f4;
            font-size: 2.5em;
            margin-bottom: 15px;
        }
        .gadget-item {
            background-color: rgba(0, 0, 0, 0.7);
            margin: 20px 0;
            padding: 20px;
            border-radius: 10px;
            box-shadow: 0 4px 6px rgba(0, 0, 0, 0.3);
        }
        .gadget-item h3 {
            color: #f4f4f4;
        }
        .gadget-item p {
            color: #d3d3d3;
            font-size: 1.2em;
        }
        button {
            padding: 10px 20px;
            background-color: #007bff;
            color: white;
            border: none;
            border-radius: 5px;
            font-size: 1.1em;
            cursor: pointer;
            transition: background-color 0.3s ease;
        }
        button:hover {
            background-color: #0056b3;
        }
    </style>
</head>
<body>
    <h1>Welcome, <?php echo htmlspecialchars($_SESSION['name']); ?>!</h1>
    <h2>Gadget Store</h2>

    <div class="container">
        <!-- Example of a Gadget Item -->
        <div class="gadget-item">
            <h3>Smartphone X - ₹70,000</h3>
            <p>High-performance smartphone with stunning features.</p>
            <form action="order.php" method="POST">
                <input type="hidden" name="user_id" value="<?php echo htmlspecialchars($_SESSION['user_id']); ?>">
                <input type="hidden" name="order_details" value="1x Smartphone X">
                <input type="hidden" name="total_price" value="70000">
                <button type="submit">Order Now</button>
            </form>
        </div>

        <div class="gadget-item">
            <h3>Wireless Headphones - ₹15,000</h3>
            <p>Noise-canceling wireless headphones for an immersive experience.</p>
            <form action="order.php" method="POST">
                <input type="hidden" name="user_id" value="<?php echo htmlspecialchars($_SESSION['user_id']); ?>">
                <input type="hidden" name="order_details" value="1x Wireless Headphones">
                <input type="hidden" name="total_price" value="15000">
                <button type="submit">Order Now</button>
            </form>
        </div>

        <!-- Add more gadgets -->
    </div>
</body>
</html>
