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
    <title>Order Confirmation - Gadget Store</title>
    <style>
        body {
            font-family: 'Arial', sans-serif;
            margin: 0;
            padding: 0;
            background-color: #f4f5f7;
            color: #333;
        }

        h1 {
            text-align: center;
            margin: 20px 0;
            color: #2c5282;
        }

        .container {
            width: 30%;
            margin: 40px auto;
            background-color: #fff;
            padding: 20px;
            box-shadow: 0 4px 10px rgba(0, 0, 0, 0.2);
            border-radius: 10px;
            text-align: center;
        }

        ul {
            list-style-type: none;
            padding: 0;
            margin: 20px 0;
        }

        ul li {
            margin-bottom: 10px;
            font-size: 1.1em;
            color: #555;
            border-bottom: 1px dashed #ccc;
            padding-bottom: 5px;
        }

        .total {
            font-size: 1.5em;
            font-weight: bold;
            color: #2c5282;
            margin-top: 20px;
        }

        a {
            display: inline-block;
            text-decoration: none;
            color: #fff;
            background-color: #3182ce;
            padding: 10px 20px;
            border-radius: 5px;
            margin-top: 20px;
            font-size: 1em;
        }

        a:hover {
            background-color: #2b6cb0;
        }

        p {
            font-size: 1.2em;
            margin-top: 20px;
        }
    </style>
</head>
<body>
    <h1>Order Confirmation</h1>
    <?php if (!empty($orderDetails)): ?>
    <div class="container">
        <h2>Gadget Order Details</h2>
        <ul>
            <?php foreach ($orderDetails as $detail): ?>
                <li><?php echo htmlspecialchars($detail); ?></li>
            <?php endforeach; ?>
        </ul>
        <h3 class="total">Total: ₹<?php echo htmlspecialchars(number_format($total, 2)); ?></h3>
        <p>Thank you for purchasing from the Gadget Store!</p>
        <a href="gadget_menu.php">Back to Gadget Menu</a>
    </div>
    <?php else: ?>
        <div class="container">
            <p>No gadget order details found.</p>
            <a href="gadget_menu.php">Back to Gadget Menu</a>
        </div>
    <?php endif; ?>
</body>
</html>
