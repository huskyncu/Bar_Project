
import open
import tkinter as tk
from tkinter import *
from tkinter import ttk,messagebox

wnd = tk.Tk()
wnd.geometry("400x400")
wnd.title("QR_CODE")
add_button = tk.Button(wnd,text="OPEN_QRCODE",underline=-1,command=open.open_)
add_button.place(relx=0.5,rely=0.5,anchor="center")
close_button = tk.Button(wnd, text="close",underline=-1,command=wnd.destroy)
close_button.place(relx=0.5,rely=0.9,anchor="center")
wnd.mainloop()