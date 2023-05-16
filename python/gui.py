import tkinter as tk
from tkinter import ttk,messagebox
import refrige
import menu
import open
import check
wnd = tk.Tk()
wnd.geometry("400x400")
wnd.title("酒吧後台")
refrige_button = tk.Button(wnd,text="查看冰箱",underline=-1,command=refrige.refrige)
refrige_button.place(relx=0.2,rely=0.6,anchor="center")
qrcode_button = tk.Button(wnd, text="放材料",underline=-1,command=open.open_)
qrcode_button.place(relx=0.5,rely=0.6,anchor="center")
wine_db_button = tk.Button(wnd, text="看酒單",underline=-1,command=menu.menu)
wine_db_button.place(relx=0.8,rely=0.6,anchor="center")
check_button = tk.Button(wnd, text="查看收支",underline=-1,command=check.check)
check_button.place(relx=0.3,rely=0.9,anchor="center")
label = tk.Label(wnd,text='我是通知框')
label.place(relx=0.5,rely=0.25,anchor="center")
text  =  tk.Text(wnd,width='50',height='5')
label_q = tk.Label(wnd,text='若不掃描請按q')
label_q.place(relx=0.5,rely=0.7,anchor="center")
text. place(relx=0.5,rely=0.3,anchor='n')
close_button = tk.Button(wnd, text="close",underline=-1,command=wnd.destroy)
close_button.place(relx=0.7,rely=0.9,anchor="center")
wnd.mainloop()