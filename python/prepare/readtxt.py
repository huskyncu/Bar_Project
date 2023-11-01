dicts = {}
element = []
from firebase import firebase
url = "https://python-database-3b3f8-default-rtdb.firebaseio.com/"
fdb = firebase.FirebaseApplication(url, None)
with open('wine_.txt',mode='r',encoding='utf-8') as file:
    lines = file.readlines()
for i in range(len(lines)):
    lines[i] = lines[i].split()
    if len(lines[i])==1:
        lines[i][0]=lines[i][0].rstrip(':')
        dicts[lines[i][0]]={}
        tmp = lines[i][0]
    elif len(lines[i])==3:
        del lines[i][2]
        dicts[tmp][lines[i][0]]=int(lines[i][1])
        if lines[i][0] not in element:
            element.append(lines[i][0])
with open("element.txt",mode='w',encoding='utf-8') as file:
    for i in element:
        file.write(i+"\n")
#fdb.put('/',"menu",dicts)
    
        
    