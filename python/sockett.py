import socket

# 綁定 IP 地址和端口號
HOST = '192.168.8.199'
PORT = 7100

with socket.socket(socket.AF_INET, socket.SOCK_STREAM) as s:
    s.bind((HOST, PORT))
    s.listen()
    while True:
        conn, addr = s.accept()
        with conn:
            print('Connected by', addr)
            while True:
                data = conn.recv(1024)
                print(data)
                if not data:
                    break
                message = data.decode('utf-8').strip()
                print(f'Received message: {message}')
                message += '\n'  # 在消息的末尾添加分隔符
                conn.sendall(message.encode('utf-8'))