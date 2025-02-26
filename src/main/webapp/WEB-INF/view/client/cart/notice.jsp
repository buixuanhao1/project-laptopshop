<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Order Placed Successfully!</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f4f4f4;
            text-align: center;
            margin: 0;
            padding: 0;
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
        }

        .container {
            background: white;
            padding: 30px;
            border-radius: 10px;
            box-shadow: 0px 4px 10px rgba(0, 0, 0, 0.1);
            width: 350px;
        }

        h1 {
            color: #2ecc71;
            font-size: 24px;
        }

        p {
            font-size: 16px;
            color: #555;
        }

        .success-icon {
            font-size: 50px;
            color: #2ecc71;
        }

        .btn {
            display: inline-block;
            margin-top: 15px;
            padding: 10px 20px;
            font-size: 16px;
            color: white;
            background-color: #3498db;
            text-decoration: none;
            border-radius: 5px;
            transition: 0.3s;
        }

        .btn:hover {
            background-color: #2980b9;
        }
    </style>
</head>

<body>

    <div class="container">
        <div class="success-icon">✔</div>
        <h1>Order Placed Successfully!</h1>
        <p>Thank you for your purchase. Your order will be processed soon.</p>
        <a href="/" class="btn">Continue Shopping</a>
    </div>

</body>

</html>