import cv2
import webbrowser
import firebase_
import tkinter as tk
from tkinter import ttk,messagebox

def read_code(img):
    qrcode = cv2.QRCodeDetector()                        # 建立 QRCode 偵測器
    data, bbox, rectified = qrcode.detectAndDecode(img)  # 偵測圖片中的 QRCode
    if 'price' in data:
        print("data",data)
        firebase_.insert_ele('refrige',data)
        ans = messagebox.askyesno("訊息",'已新增進資料庫，請問是否繼續新增？')
        if ans == False:
            return ans
        
            
    

# 指定你的chrome路徑
def open_():
    # 選擇第二隻攝影機
    cap = cv2.VideoCapture(0)
        # 從攝影機擷取一張影像
    
    # cv2.waitKey(0)
    while True:
        ret,frame = cap.read()
        cv2.imshow('frame',frame)
        # 顯示圖片
        # 若按下 q 鍵則離開迴圈
        if cv2.waitKey(1) and read_code(frame)==False:
            break

    # 釋放攝影機
    
    cap.release()

    # 關閉所有 OpenCV 視窗
    cv2.destroyAllWindows()