import qrcode
import cv2
good = 'name=sex on the beach;price=300'

img = qrcode.make(good)
img.save("qrcode.png")
img.show()
# img = cv2.imread("qrcode.png")
qrcode_ = cv2.QRCodeDetector()   
# data, bbox, rectified = qrcode_.detectAndDecode(img)
# dictionary = dict(subString.split("=") for subString in data.split(";"))
 
# # printing the generated dictionary
# print(dictionary)