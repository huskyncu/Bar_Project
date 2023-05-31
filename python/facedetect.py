import cv2
import numpy as np
from sklearn import svm
from sklearn.model_selection import train_test_split
import pickle
import os

# 儲存所有的訓練數據
face_data = []
face_labels = []
# 建立一個用戶名到整數標籤的映射
user_name_to_label = {}
next_label = 0
# 建立臉部辨識模型
face_recognizer = cv2.face.LBPHFaceRecognizer_create()

# 載入人臉識別器
face_cascade = cv2.CascadeClassifier(cv2.data.haarcascades + "haarcascade_frontalface_default.xml")
# 在所有全局變數後面添加這些行
user_data_file = "user_data.pkl"
model_file = "face_model.yml"
# 如果文件存在，則讀取會員資料和訓練模型
if os.path.exists(user_data_file):
    with open(user_data_file, "rb") as f:
        user_name_to_label, next_label, face_data, face_labels = pickle.load(f)

if os.path.exists(model_file):
    face_recognizer.read(model_file)

def train_model():
    if not face_data:
        return
    face_recognizer.train(np.asarray(face_data), np.array(face_labels))
    # 在註冊完成後保存會員資料和訓練模型
    with open(user_data_file, "wb") as f:
        pickle.dump((user_name_to_label, next_label, face_data, face_labels), f)

    face_recognizer.write(model_file)

def register(user_name):
    #print(user_name_to_label)
    global next_label
    cap = cv2.VideoCapture(0)
    count = 0
    islog=0
    while count < 10:
        # 讀取視訊串流的每一幀
        ret, frame = cap.read()

        # 將影像轉為灰階
        gray = cv2.cvtColor(frame, cv2.COLOR_BGR2GRAY)

        # 進行臉部識別
        faces = face_cascade.detectMultiScale(gray, 1.3, 5)

        for (x,y,w,h) in faces:
            # 繪製出臉部的方框
            cv2.rectangle(frame,(x,y),(x+w,y+h),(255,0,0),2)

            # 儲存臉部的影像
            roi_gray = cv2.resize(gray[y:y+h, x:x+w], (200, 200))  # 將每張圖片調整為200x200的大小
            userimg=cv2.resize(frame[y:y+h, x:x+w], (200, 200))
            if user_name_to_label:
                label, confidence = face_recognizer.predict(roi_gray)
                if confidence<60:
                    for user_name, user_label in user_name_to_label.items():
                        if user_label == label:
                            print(user_name,"has been registed")
                            islog=1
                            break
                else:
                    islog=0
            if islog:
                break
            if user_name not in user_name_to_label:
                user_name_to_label[user_name] = next_label
                next_label += 1
            face_data.append(roi_gray)
            face_labels.append(user_name_to_label[user_name])
            if count == 1:
                cv2.imwrite(f"user_images/{user_name}.jpg", userimg)
            count += 1
            if count >= 10: 
                break

        # 顯示視窗
        cv2.imshow('frame', frame)

        # 按下 'q' 鍵離開迴圈
        if cv2.waitKey(1) & 0xFF == ord('q'):
            break
        if islog:
            break
    cap.release()
    cv2.destroyAllWindows()

    # 更新臉部辨識模型
    if islog==0:
        train_model()
        
        return user_name 
    else:
        return "error"

def login():
    if not face_data:
        print("無資料")
        return "No Data"
    cap = cv2.VideoCapture(0)
    islog=1
    while islog:
        ret, frame = cap.read()
        gray = cv2.cvtColor(frame, cv2.COLOR_BGR2GRAY)
        faces = face_cascade.detectMultiScale(gray, 1.3, 5)

        for (x,y,w,h) in faces:
            cv2.rectangle(frame,(x,y),(x+w,y+h),(255,0,0),2)
            roi_gray = gray[y:y+h, x:x+w]
            userimg=frame[y:y+h, x:x+w]
            label, confidence = face_recognizer.predict(roi_gray)
            if confidence<60:
                for user_name, user_label in user_name_to_label.items():
                    if user_label == label:
                        print("Logged in as", user_name)
                        cv2.imwrite(f"user_images/{user_name}.jpg", userimg)
                        message = "Logged in as "+user_name
                        islog=0
                        break
            else:
                print("查無會員")
                message = "no user"
                islog=0
                break
        cv2.imshow('frame', frame)
        if cv2.waitKey(1) & 0xFF == ord('q'):
            break
    cap.release()
    cv2.destroyAllWindows()
    if message=="no user":
        return "error"
    else:
        return user_name

def delete_user(user_name):
    if user_name not in user_name_to_label:
        print("該會員不存在")
        return

    # 獲取要刪除的用戶的標籤
    label_to_delete = user_name_to_label[user_name]

    # 從會員資料中刪除該用戶
    del user_name_to_label[user_name]

    # 從訓練數據中刪除該用戶的所有人臉
    face_data[:] = [data for data, label in zip(face_data, face_labels) if label != label_to_delete]
    face_labels[:] = [label for label in face_labels if label != label_to_delete]

    # 重新訓練模型
    train_model()

    # 保存會員資料和訓練模型
    with open(user_data_file, "wb") as f:
        pickle.dump((user_name_to_label, next_label, face_data, face_labels), f)

    face_recognizer.write(model_file)
    print("刪除成功")

if __name__ == '__main__':
    while True:
        user_input = input("Enter '1' for register or '2' for login or '3' for delete: ")
        if user_input == "1":
            user_name = input("Enter user name: ")
            register(user_name)
        elif user_input == "2":
            login()
        elif user_input == "3":
            user_name = input("Enter user name: ")
            delete_user(user_name)
        else:
            print("Invalid input, please try again.")
            break