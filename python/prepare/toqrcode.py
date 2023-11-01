import qrcode
import cv2
good_list = []
with open('element.txt',mode='r',encoding='utf-8') as file:
    lines = file.readlines()
for line in lines:
    line = line.replace('\n','')
    good = f'name={line};ml=100;price=100'
    img = qrcode.make(good)
    img.save(f"{line}.png")
    #img.show()
# img = cv2.imread("qrcode.png")
# qrcode_ = cv2.QRCodeDetector()   
# data, bbox, rectified = qrcode_.detectAndDecode(img)
# dictionary = dict(subString.split("=") for subString in data.split(";"))
 
# # printing the generated dictionary
# print(dictionary)