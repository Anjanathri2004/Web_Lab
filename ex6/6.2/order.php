<?php
$menuXML = simplexml_load_file('menu.xml') or die("Error: Cannot load menu.xml");
session_start();
?>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Gadget Store Menu</title>
    <style>
        body {
            font-family: 'Arial', sans-serif;
            margin: 0;
            padding: 0;
            background-color: #f4f4f4;
            color: #333;
        }

        h1 {
            text-align: center;
            margin: 20px 0;
            color: #444;
        }

        .container {
            width: 80%;
            margin: 0 auto;
            background-color: #fff;
            padding: 20px;
            box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
            border-radius: 10px;
        }

        .menu-item {
            display: flex;
            justify-content: space-between;
            align-items: center;
            padding: 10px;
            border-bottom: 1px solid #ddd;
        }

        .menu-item:last-child {
            border-bottom: none;
        }

        .menu-item h3 {
            margin: 0;
            color: #333;
        }

        .menu-item p {
            margin: 5px 0;
            color: #666;
        }

        input[type="number"], input[type="checkbox"] {
            width: 50px;
            padding: 5px;
            font-size: 14px;
            margin-right: 10px;
        }

        button {
            background-color: #4CAF50;
            color: #fff;
            padding: 10px 15px;
            border-radius: 5px;
            border: none;
            cursor: pointer;
            transition: background-color 0.3s ease;
        }

        button:hover {
            background-color: #45a049;
        }

        .error-message {
            text-align: center;
            color: red;
            font-weight: bold;
            margin: 10px 0;
        }
    </style>
</head>
<body>
    <h1>Gadget Store Menu</h1>
    <?php if (isset($_GET['error'])): ?>
        <p class="error-message"><?php echo htmlspecialchars($_GET['error']); ?></p>
    <?php endif; ?>

    <form method="post" action="order1.php">
        <?php foreach ($menuXML->item as $item): ?>
            <div class="menu-item">
                <div>
                    <label>
                        <input type="checkbox" name="selected_items[]" value="<?php echo $item->name; ?>"> 
                        <strong><?php echo $item->name; ?> - ₹<?php echo number_format((float)$item->price, 2); ?></strong>
                    </label>
                    <p><?php echo $item->description; ?></p>
                </div>
                <div>
                    <label>
                        Quantity: 
                        <input type="number" name="quantity[<?php echo $item->name; ?>]" min="0" value="0">
                    </label>
                </div>
            </div>
        <?php endforeach; ?>
        <hr>
        <button type="submit">Place Order</button>
    </form>
</body>
</html>
