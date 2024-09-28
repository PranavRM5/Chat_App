# 𝐂𝐡𝐚𝐭 𝐀𝐩𝐩

# Overview of Chat Application
# Architecture:

Client-Server Model: The application consists of a server that listens for incoming client connections and multiple clients that connect to this server to exchange messages.
Socket Programming: Use Java Sockets for communication. The server uses ServerSocket to accept incoming client connections, while clients use Socket to connect to the server.

# Components:

1. Server:
Responsible for handling client connections.
Reads messages from clients and broadcasts them to all connected clients.
GUI using JavaFX to display messages and manage connections.

2. Client:
Connects to the server and sends/receives messages.
GUI using JavaFX to display messages and provide an input field for sending messages.


# Implementation Steps

1. Set Up Server:
Create a ServerSocket to listen for incoming connections.
Use threads to handle each client connection, allowing multiple clients to communicate simultaneously.
Maintain a list of connected clients for broadcasting messages.

2. Set Up Client:
Create a Socket to connect to the server.
Implement a thread to listen for incoming messages from the server.
Provide a GUI for user interaction (sending and displaying messages).

3. JavaFX GUI:
Create a simple layout with a TextArea for displaying messages and a TextField for message input.
Use buttons or key events (e.g., pressing Enter) to send messages.
