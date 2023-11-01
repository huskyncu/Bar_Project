with open('like.txt',mode='r',encoding='utf-8') as file:
    lines = file.readlines()

for i in range(len(lines)):
    lines = lines[i].split()
    {lines[i][0]:[lines[i][j] for j in range(len(lines))]}