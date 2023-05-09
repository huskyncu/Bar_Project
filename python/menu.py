import tkinter as tk
from tkinter import *
from tkinter import ttk,messagebox


def menu():
    query_wnd  =  tk. Tk()
    query_wnd. geometry("600x300")
    query_wnd. title("我是酒單")


    # 实例化控件，设置表头样式和标题文本
    columns = ("酒品名", "所需材料")
    headers = ("酒品名", "所需材料")
    widthes = (100, 500)
    tv = ttk.Treeview(query_wnd, show="headings", columns=columns)

    for (column, header, width) in zip(columns, headers, widthes):
        tv.column(column, width=width, anchor="w")
        tv.heading(column, text=header, anchor="w")
    from firebase import firebase
    url = "https://python-database-3b3f8-default-rtdb.firebaseio.com/"
    fdb = firebase.FirebaseApplication(url, None)
    def inser_data():
        """插入数据"""
        result = fdb.get('/menu',None)
        contacts = []
        gredient = ""
        for k,v in result.items():
            for n,va in v.items():
                gredient+=str(n)+" "+str(va)+"ml "
            contacts.append((k,gredient))
            gredient=""
        for i, vv in enumerate(contacts):
            tv.insert('', i, values=vv)
    tv.pack()

    inser_data()
    close_button = tk.Button(query_wnd, text="close",underline=-1,command=query_wnd.destroy)
    close_button.place(relx=0.7,rely=0.9,anchor="center")
    query_wnd.mainloop()