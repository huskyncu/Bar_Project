import tkinter as tk
from tkinter import *
from tkinter import ttk,messagebox

from firebase import firebase
def refrige():
    def close():
        fdb.put('/','ppt',8)
        query_wnd.destroy()
    query_wnd  =  tk. Tk()
    query_wnd. geometry("400x300")
    query_wnd. title("我是冰箱")
    url = "https://python-database-3b3f8-default-rtdb.firebaseio.com/"
    fdb = firebase.FirebaseApplication(url, None)
    fdb.put('/','ppt',12)
    # 实例化控件，设置表头样式和标题文本
    columns = ("name", "amount")
    headers = ("材料名稱",'剩餘(ml)')
    widthes = (250, 250)
    tv = ttk.Treeview(query_wnd, show="headings", columns=columns)
    for (column, header, width) in zip(columns, headers, widthes):
        tv.column(column, width=width, anchor="w")
        tv.heading(column, text=header, anchor="w")
    def inser_data():
        """插入数据"""
        result = fdb.get('/refrige',None)
        contacts = []
        for k,v in result.items():
            contacts.append((k,v))
        for i, v in enumerate(contacts):
            tv.insert('', i, values=v)
    tv.pack()
    inser_data()
    close_button = tk.Button(query_wnd, text="close",underline=-1,command=close)
    close_button.place(relx=0.7,rely=0.9,anchor="center")
    query_wnd.mainloop()