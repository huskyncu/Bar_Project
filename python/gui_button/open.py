import cv2
import webbrowser
import firebase_
import tkinter as tk
from tkinter import ttk,messagebox
from firebase import firebase

def read_code(img):
    qrcode = cv2.QRCodeDetector()                        # 建立 QRCode 偵測器
    data, bbox, rectified = qrcode.detectAndDecode(img)  # 偵測圖片中的 QRCode
    if 'price' in data:
        print("data",data)
        firebase_.insert_ele('refrige',data)
        ans = messagebox.askyesno("訊息",'已新增進資料庫，請問是否繼續新增？')
        if ans == False:
            return ans

    


def open_():
    """url = "https://python-database-3b3f8-default-rtdb.firebaseio.com/"
    fdb = firebase.FirebaseApplication(url, None)
    fdb.put('/','ppt',14)"""
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
        if cv2.waitKey(1)==ord('q'):
            cv2.destroyAllWindows()
            break

    # 釋放攝影機
    
    cap.release()
    #fdb.put('/','ppt',8)
    # 關閉所有 OpenCV 視窗
    cv2.destroyAllWindows()
    
if __name__ == '__main__':
    open_()
    