import socket

host = 'localhost'
port = 5000

s = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
s.connect((host, port))

print("Welcome to the client. Check out the README to learn about the possible commands.")

if s.recv(1024).decode() == "init":
    print("connection established")

    while True:
        try:
            s.sendall(input("Send command: ").encode())
            data = s.recv(1024).decode()
            print(f"Response: {data}")
        except Exception as e:
            print(e)
s.close()