import socket
import threading
import json
from firebase import firebase 
from facedetect import register,login
import multiprocessing

# 服务器配置
HOST = '192.168.10.70'  # 服务器IP地址
PORT = 7100  # 服务器端口号

# 用于存储连接的客户端信息
clients = []
# 创建锁，用于同步访问clients列表
lock = threading.Lock()

def handle_client(client_socket, client_address):
    while True:
        try:
            data = client_socket.recv(1024)  # 接收客户端发送的数据
            if data:
                # 处理接收到的数据
                print(f"Received data from {client_address}: {data.decode()}")
                command=data.decode().split()
                if(command[0]=="startprogram"):
                    v=""
                    url = "https://python-database-3b3f8-default-rtdb.firebaseio.com/"
                    fdb = firebase.FirebaseApplication(url, None) 
                    status = fdb.get('/arduino/座位',None)
                    print("status:",status)
                    if status==0:
                        v='1'
                    else:
                        v='0'
                        
                    #firebase sit 0 or 1 ,v=0,v=1         
                    with lock:
                    # 向所有其他客户端广播数据
                        for client in clients:
                            if client == client_socket:
                                my_dict = {"startprogram": v}  # create your dictionary here
                                json_data = json.dumps(my_dict)  # convert dictionary to json
                                json_data +='\n'
                                client.sendall(json_data.encode('utf-8'))  # send json data
                    #queue.put(f'{client_address} get in')
                elif (command[0]=="register"):
                    v = register(command[1])
                    
                    with lock:
                    # 向所有其他客户端广播数据
                        for client in clients:
                            if client == client_socket:
                                my_dict = {"register": v}  # create your dictionary here
                                json_data = json.dumps(my_dict)  # convert dictionary to json
                                json_data +='\n'
                                client.sendall(json_data.encode('utf-8'))  # send json data
                    #queue.put(v)
                elif (command[0]=="login"):
                    v = login()
                    
                    with lock:
                    # 向所有其他客户端广播数据
                        for client in clients:
                            if client == client_socket:
                                my_dict = {"login": v}  # create your dictionary here
                                json_data = json.dumps(my_dict)  # convert dictionary to json
                                json_data +='\n'
                                client.sendall(json_data.encode('utf-8'))  # send json data
                    #queue.put(v)
            else:
                # 客户端断开连接
                remove_client(client_socket)
                print(f"Client {client_address} disconnected")
                break
        except ConnectionResetError:
            # 客户端强制断开连接
            remove_client(client_socket)
            print(f"Client {client_address} forcibly disconnected")
            break

def broadcast_data(data, sender_socket):
    # 向除了发送者之外的所有客户端广播数据
    with lock:
        print(111)
        for client in clients:
            if client != sender_socket:
                message = data.decode('utf-8').strip()
                message +='\n'
                print(client,data)
                client.sendall(message.encode('utf-8'))

def remove_client(client_socket):
    # 从列表中移除断开连接的客户端
    with lock:
        if client_socket in clients:
            clients.remove(client_socket)

def main():
    server_socket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
    server_socket.bind((HOST, PORT))
    server_socket.listen(5)

    print(f"Server started on {HOST}:{PORT}")

    while True:
        client_socket, client_address = server_socket.accept()
        with lock:
            clients.append(client_socket)
        print(f"New client connected: {client_address}")
        client_thread = threading.Thread(target=handle_client, args=(client_socket, client_address))
        client_thread.start()

if __name__ == '__main__':
    #queue = multiprocessing.Queue()
    main()