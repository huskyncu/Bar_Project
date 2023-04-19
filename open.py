import cv2
import webbrowser


def read_code(img):
    qrcode = cv2.QRCodeDetector()                        # 建立 QRCode 偵測器
    data, bbox, rectified = qrcode.detectAndDecode(img)  # 偵測圖片中的 QRCode
    # 如果 bbox 是 None 表示圖片中沒有 QRCode
    if data != "" and 'http' in data:
        webbrowser.get('chrome').open(data, new=1)
        print(data)                # QRCode 的內容
        # print(bbox)                # QRCode 的邊界
        # print(rectified)           # 換成垂直 90 度的陣列
        return 'yes'
    elif 'price' in data:
        print("data",data)
        #以下連接fire base
        return 'yes'

# 指定你的chrome路徑
def open_():
    chromePath = r'C:\Program Files (x86)\Google\Chrome\Application\chrome.exe'

    # 註冊Chrome
    webbrowser.register(
        'chrome', None, webbrowser.BackgroundBrowser(chromePath))

    # 指定Chrome開啟網頁

    # 選擇第二隻攝影機
    cap = cv2.VideoCapture(0)
        # 從攝影機擷取一張影像
    
    # cv2.waitKey(0)
    while True:
        ret,frame = cap.read()
        cv2.imshow('frame',frame)
        if read_code(frame)=='yes':
            break
        # 顯示圖片

        # 若按下 q 鍵則離開迴圈
        if cv2.waitKey(1) & 0xFF == ord('q'):
            break

    # 釋放攝影機
    
    cap.release()

    # 關閉所有 OpenCV 視窗
    cv2.destroyAllWindows()