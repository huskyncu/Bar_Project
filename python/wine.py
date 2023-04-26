import tkinter as tk
from tkinter import *
from tkinter import ttk,messagebox


def refrige():
    query_wnd  =  tk. Tk()
    query_wnd. geometry("400x400")
    query_wnd. title("我是酒單")


    # 实例化控件，设置表头样式和标题文本
    columns = ("name", "tel", "email", "company")
    headers = ("姓名", "电话", "邮箱", "公司")
    widthes = (120, 120, 250, 250)
    tv = ttk.Treeview(query_wnd, show="headings", columns=columns)

    for (column, header, width) in zip(columns, headers, widthes):
        tv.column(column, width=width, anchor="w")
        tv.heading(column, text=header, anchor="w")

    def inser_data():
        """插入数据"""
        contacts =[
            ('张三', '1870591xxxx', 'zhang@qq.com', '腾讯'),
            ('李斯', '1589928xxxx', 'lisi@google.com', '谷歌'),
            ('王武', '1340752xxxx', 'wangwu@baidu.com', '微软'),
            ('麻溜儿', '1361601xxxx', 'maliur@alibaba.com', '阿里'),
            ('郑和', '1899986xxxx', 'zhenghe@163.com', '网易'),
        ]
        for i, person in enumerate(contacts):
            tv.insert('', i, values=person)

    tv.pack()

    inser_data()

    query_wnd.mainloop()