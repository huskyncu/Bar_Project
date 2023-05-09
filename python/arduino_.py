import serial
from firebase_ import insert_ele
from firebase import firebase
import tkinter as tk
from tkinter import ttk,messagebox




def arduino_change():
    #while True:
        # 读取串口数据
    ser = serial.Serial('COM3', 9600, timeout=1)
    for i in range(2):
        data = ser.readline().decode('utf-8')
        # 打印数据
        if data!="":
            data=data.split()
            dict_data={data[0]:int(data[1])}
            #連接firebase
            insert_ele('arduino',dict_data)
            print(dict_data)
    ser.close()
def look_arduino():
    def close():
        query_wnd.destroy()
        return True
    query_wnd  =  tk. Tk()
    query_wnd. geometry("300x300")
    query_wnd. title("我是arduino")
    url = "https://python-database-3b3f8-default-rtdb.firebaseio.com/"
    fdb = firebase.FirebaseApplication(url, None)

    # 实例化控件，设置表头样式和标题文本
    columns = ("use", "status")
    headers = ("設備",'狀態')
    widthes = (100, 100)
    tv = ttk.Treeview(query_wnd, show="headings", columns=columns)

    for (column, header, width) in zip(columns, headers, widthes):
        tv.column(column, width=width, anchor="w")
        tv.heading(column, text=header, anchor="w")
    contacts = [('大門',0),('座位',0)]
    for i, v in enumerate(contacts):
        tv.insert('', i, values=v)
    def insert_data():
        """插入数据"""
        tv.delete(*tv.get_children())
        arduino_change()
        result = fdb.get('/arduino',None)
        contacts = []
        for k,v in result.items():
            contacts.append((k,v))
        for i, v in enumerate(contacts):
            tv.insert('', i, values=v)
        tv.place(relx=0.5,rely=0.3,anchor="center")
        query_wnd.after(1000, insert_data)
    insert_data()
    close_button = tk.Button(query_wnd, text="close",underline=-1,command=close)
    close_button.place(relx=0.7,rely=0.9,anchor="center")
    query_wnd.mainloop()


if __name__ == '__main__':
    look_arduino()