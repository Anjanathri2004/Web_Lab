-- Database: `gadget_store_db`

-- --------------------------------------------------------

-- Table structure for table `gadgets`
CREATE TABLE `gadgets` (
  `id` int(11) NOT NULL,
  `gadget_name` varchar(100) DEFAULT NULL,
  `description` text DEFAULT NULL,
  `price` decimal(10,2) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Dumping data for table `gadgets`
INSERT INTO `gadgets` (`id`, `gadget_name`, `description`, `price`) VALUES
(1, 'Smartphone', 'Latest Android smartphone with 128GB storage.', 29999.99),
(2, 'Laptop', '15-inch laptop with 16GB RAM and 512GB SSD.', 74999.99),
(3, 'Bluetooth Headphones', 'Wireless over-ear headphones with noise cancellation.', 5999.99),
(4, 'Smartwatch', 'Fitness tracking smartwatch with AMOLED display.', 19999.99),
(5, 'Wireless Charger', 'Fast charging pad compatible with Qi-enabled devices.', 1999.99);

-- --------------------------------------------------------

-- Table structure for table `orders`
CREATE TABLE `orders` (
  `id` int(11) NOT NULL,
  `user_id` int(11) DEFAULT NULL,
  `order_details` text DEFAULT NULL,
  `total_price` decimal(10,2) DEFAULT NULL,
  `order_date` timestamp NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Dumping data for table `orders`
INSERT INTO `orders` (`id`, `user_id`, `order_details`, `total_price`, `order_date`) VALUES
(1, 1, '1x Smartphone, 1x Wireless Charger', 31999.98, '2024-11-20 15:26:31'),
(2, 2, '2x Laptop', 149999.98, '2024-11-20 15:26:31'),
(3, 3, '1x Bluetooth Headphones, 1x Smartwatch', 25999.98, '2024-11-20 15:26:31'),
(4, 1, '1x Wireless Charger', 1999.99, '2024-11-20 15:26:31');

-- --------------------------------------------------------

-- Table structure for table `users`
CREATE TABLE `users` (
  `id` int(11) NOT NULL,
  `name` varchar(50) DEFAULT NULL,
  `email` varchar(50) DEFAULT NULL,
  `password` varchar(100) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Dumping data for table `users`
INSERT INTO `users` (`id`, `name`, `email`, `password`) VALUES
(1, 'John Doe', 'john@example.com', 'password123'),
(2, 'Jane Smith', 'jane@example.com', 'securepassword'),
(3, 'Mike Brown', 'mike@example.com', 'mikepassword');

-- Indexes for tables
ALTER TABLE `gadgets`
  ADD PRIMARY KEY (`id`);

ALTER TABLE `orders`
  ADD PRIMARY KEY (`id`),
  ADD KEY `user_id` (`user_id`);

ALTER TABLE `users`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `email` (`email`);

-- Auto-increment for tables
ALTER TABLE `gadgets`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

ALTER TABLE `orders`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

ALTER TABLE `users`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

-- Constraints
ALTER TABLE `orders`
  ADD CONSTRAINT `orders_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`);
