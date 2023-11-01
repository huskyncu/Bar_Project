import tkinter as tk
from tkinter import *
from tkinter import ttk,messagebox

from firebase import firebase
def user_data():
    def close():
        fdb.put('/','ppt',8)
        query_wnd.destroy()
    query_wnd  =  tk. Tk()
    query_wnd. geometry("600x300")
    query_wnd. title("我是資訊王")
    url = "https://python-database-3b3f8-default-rtdb.firebaseio.com/"
    fdb = firebase.FirebaseApplication(url, None)
    fdb.put('/','ppt',25)
    # 实例化控件，设置表头样式和标题文本
    columns = ("名字" ,"年紀",'性別','登入ip','在酒吧','點餐紀錄')
    headers = ("名字" ,"年紀",'性別','登入ip','在酒吧','點餐紀錄')
    widthes = (50,50,50,100,50,300)
    tv = ttk.Treeview(query_wnd, show="headings", columns=columns)
    for (column, header, width) in zip(columns, headers, widthes):
        tv.column(column, width=width, anchor="w")
        tv.heading(column, text=header, anchor="w")
    def inser_data():
        """插入数据"""
        result = fdb.get('/user_data',None)
        contacts = []
        for k,v in result.items():
            #print(k,v)
            if v['nowlogin']==1:
                v['nowlogin']='是'
            else:
                v['nowlogin']='否'
            t=[k,v['score']['年齡'],v['score']['性別'],v['ip'],v['nowlogin']]
            s = {' '}
            s.remove(' ')
            for event in v['buy'].values():
                for name,price in event.items():
                    s.add(name)
            strr=""
            for i in s:
                strr+=i+" "
            t.append(strr)
            contacts.append(tuple(t))
        for i, v in enumerate(contacts):
            tv.insert('', i, values=v)
    tv.pack()
    inser_data()
    close_button = tk.Button(query_wnd, text="close",underline=-1,command=close)
    close_button.place(relx=0.7,rely=0.9,anchor="center")
    query_wnd.mainloop()
if __name__ == '__main__':
    user_data()
    