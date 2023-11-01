import tkinter as tk
from tkinter import ttk,messagebox
from firebase import firebase
from gui_button.refrige import refrige
from gui_button.menu import menu
from gui_button.open import open_
from gui_button.check import check
from gui_button.user import user_data

def main():
    global text
    wnd = tk.Tk()
    wnd.geometry("400x400")
    wnd.title("酒吧後台")
    refrige_button = tk.Button(wnd,text="查看冰箱",underline=-1,command=refrige)
    refrige_button.place(relx=0.2,rely=0.6,anchor="center")
    qrcode_button = tk.Button(wnd, text="放材料",underline=-1,command=open_)
    qrcode_button.place(relx=0.5,rely=0.6,anchor="center")
    wine_db_button = tk.Button(wnd, text="看酒單",underline=-1,command=menu)
    wine_db_button.place(relx=0.8,rely=0.6,anchor="center")
    check_button = tk.Button(wnd, text="查看收支",underline=-1,command=check)
    check_button.place(relx=0.2,rely=0.9,anchor="center")
    label = tk.Label(wnd,text='我是通知框')
    label.place(relx=0.5,rely=0.25,anchor="center")
    text  =  tk.Text(wnd,width='50',height='5' )
    text. place(relx=0.5,rely=0.3,anchor='n')
    label_q = tk.Label(wnd,text='若不掃描請按q')
    label_q.place(relx=0.5,rely=0.7,anchor="center")
    user_button = tk.Button(wnd, text="查看使用者資料",underline=-1,command=user_data)
    user_button.place(relx=0.5,rely=0.9,anchor="center")
    close_button = tk.Button(wnd, text="close",underline=-1,command=wnd.destroy)
    close_button.place(relx=0.8,rely=0.9,anchor="center")
    def update_gui():
        url = "https://python-database-3b3f8-default-rtdb.firebaseio.com/"
        fdb = firebase.FirebaseApplication(url, None)
        message = fdb.get('/togui/now',None)
        previous = fdb.get('/togui/tmp',None)
        if message!=previous:
            text.insert('end',message+'\n')
            fdb.put('/togui','tmp',message)
        wnd.after(1000,update_gui)
        
    update_gui()
    wnd.mainloop()
if __name__ == '__main__':
    main()