def identify(msgID):
    from selenium import webdriver
    from selenium.webdriver.common.action_chains import ActionChains
    import time
    import os
    from bs4 import BeautifulSoup
    # 指定Chrome瀏覽器的webdriver路徑，並開啟瀏覽器

    # 等待網頁加載完成
    # time.sleep(2)
    # 找到圖片上傳按鈕，點擊後彈出文件選擇對話框

    import requests
    API_Key = 'g81xF2j0Q2G3WGSDkORhbcl9'
    Secret_Key = '9y7foggVFd9qGrXGysD2cN1wYLEsoYVm'
    host = 'https://aip.baidubce.com/oauth/2.0/token?grant_type=client_credentials&client_id={}&client_secret={}'.format(
        API_Key, Secret_Key)
    response = requests.get(host)
    access_token = response.json()['access_token']
    print(access_token)

    import base64
    img_src = (os.getcwd() + '\\src\\image\\' + msgID + '.jpg')
    with open(img_src, 'rb') as f:
        base64_data = base64.b64encode(f.read())

    request_url = "https://aip.baidubce.com/rest/2.0/face/v3/detect"
    params = {
        "image": base64_data,
        "image_type": "BASE64",
        "face_field": "age,beauty,expression,face_shape,gender,glasses,emotion,face_type,spoofing",
        "face_type": "LIVE"
    }
    request_url = request_url + "?access_token=" + access_token
    headers = {
        'content-type': 'application/json'
    }
    response = requests.post(request_url, data=params, headers=headers)
    face_result = response.json()

    print("年龄：", face_result['result']['face_list'][0]['age'])
    print("人脸评分：", int(face_result['result']['face_list'][0]['beauty'])+10)
    print("性别：", face_result['result']['face_list'][0]['gender']['type'])
    print("脸型：", face_result['result']['face_list'][0]['face_shape']['type'])
    print("微笑程度：", face_result['result']['face_list'][0]['expression']['type'])

    score = int(face_result['result']['face_list'][0]['beauty'])
    if score > 60:
        score += 20
    elif score < 50:
        score -= 15
    else:
        score = score+5

    return {"人臉評分": min(score, 99), "年齡": face_result['result']['face_list'][0]['age'], "性別": face_result['result']['face_list'][0]['gender']['type'], "臉型": face_result['result']['face_list'][0]['face_shape']['type'], "微笑程度": face_result['result']['face_list'][0]['expression']['type']}
