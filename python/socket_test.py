import socket

HOST = "127.0.0.1"  # 監聽所有網絡接口
PORT = 7100  # 設置端口號

with socket.socket(socket.AF_INET, socket.SOCK_STREAM) as s:
    s.bind((HOST, PORT))
    s.listen(4)
    print('Server listening on', (HOST, PORT))
    conn, addr = s.accept()
    print(1)
    with conn:
        print('Connected by', addr)
        while True:
            data = conn.recv(1024)
            if not data:
                print(1)
                break
            print('Received:', data.decode())
        conn.close()
