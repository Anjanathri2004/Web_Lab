<?php
session_start();
$orderDetails = $_SESSION['order_details'] ?? [];
$total = $_SESSION['total'] ?? 0;
session_unset();
session_destroy();
?>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Order Confirmation</title>
    <style>
        body {
            font-family: 'Arial', sans-serif;
            background-color: #f4f4f4;
            color: #333;
            text-align: center;
        }

        .container {
            width: 50%;
            background-color: #fff;
            padding: 20px;
            box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
            margin: 30px auto;
            border-radius: 10px;
        }

        h1 {
            color: #333;
        }

        .order-details {
            margin: 20px 0;
            text-align: left;
        }

        .order-details ul {
            list-style-type: none;
            padding-left: 0;
        }

        .order-details li {
            padding: 5px 0;
        }

        .total {
            font-size: 1.2em;
            color: #333;
            margin-top: 20px;
        }

        a {
            text-decoration: none;
            background-color: #4CAF50;
            color: #fff;
            padding: 10px 20px;
            border-radius: 5px;
            margin-top: 20px;
        }

        a:hover {
            background-color: #45a049;
        }
    </style>
</head>
<body>
    <h1>Order Confirmation</h1>
    <?php if (!empty($orderDetails)): ?>
        <div class="container">
            <h2>Your Order:</h2>
            <div class="order-details">
                <ul>
                    <?php foreach ($orderDetails as $detail): ?>
                        <li><?php echo $detail; ?></li>
                    <?php endforeach; ?>
                </ul>
            </div>
            <p class="total">Total: ₹<?php echo number_format($total, 2); ?></p>
            <p>Thank you for your order!</p>
            <a href="menu1.php">Back to Menu</a>
        </div>
    <?php else: ?>
        <p>No items selected. Please <a href="menu1.php">go back to the menu</a> to place your order.</p>
    <?php endif; ?>
</body>
</html>
