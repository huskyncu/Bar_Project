import serial
from firebase_ import insert_ele
from firebase import firebase
import tkinter as tk
from tkinter import ttk,messagebox
import time
url = "https://python-database-3b3f8-default-rtdb.firebaseio.com/"
fdb = firebase.FirebaseApplication(url, None)
def arduino_main():
    def control_servo(pos):
        arduino.write((pos).to_bytes(1, 'big')) # 將整數轉換為字節，然後發送到Arduino
    arduino = serial.Serial('COM3', 9600) # 開啟串列通訊，將'COM3'換成你的Arduino的串列埠
    #time.sleep(1) # 等待Arduino啟動
    # ser = serial.Serial('COM3', 9600, timeout=1)
    while True:
        for i in range(2):
            data = arduino.readline().decode('utf-8')
            #print(data[0],data[1])
            # 打印数据
            if data!="":
                data=data.split()
                dict_data={data[0]:int(data[1])}
                print(dict_data)
                #連接firebase
                fdb.put('/arduino',data[0],int(data[1]))
                if int(data[1])==1 and data[0]=='大門':
                    fdb.put('/','ppt',2)
                    time.sleep(1)
                    fdb.put('/','ppt',3)
                    time.sleep(1)
                    fdb.put('/','ppt',8)
                
        if fdb.get('/arduino/座位',None)==1:
            result = fdb.get('/user/nowip',None)
            if result=="192.168.10.130":
                control_servo(0) # 將馬達轉向90度
            else:
                control_servo(180) # 將馬達轉向0度

if __name__ == '__main__':
    arduino_main()
    #look_arduino()
    