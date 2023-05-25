from firebase import firebase
import datetime
def insert_ele(db_name,data):
    url = "https://python-database-3b3f8-default-rtdb.firebaseio.com/"
    fdb = firebase.FirebaseApplication(url, None)    # 初始化，第二個參數作用在負責使用者登入資訊，通常設定為 None
    print(data)
    data = dict(subString.split("=") for subString in data.split(";"))
    print(data)
    all = fdb.get('/',None)
    result = fdb.get('/refrige',None)
    if data['name'] in result:
        amount = result[data['name']]
        fdb.put('/'+db_name,data['name'],amount+int(data['ml']))
    else:
        fdb.put('/'+db_name,data['name'],int(data['ml']))
    now_money = fdb.get('/money/total',None)
    now_money -= int(data['price'])
    fdb.put('/money',"total",now_money)                                  
    now = datetime.datetime.now(tz=datetime.timezone(datetime.timedelta(hours=8)))
    fdb.put('/money/event',str(now.strftime('%Y,%m,%d %H:%M:%S')),{"type":"outcome","event":"買"+str(data['name'])+"100ml","value":int(data['price'])})
    print(result)
def insert_wine(data):
    url = "https://python-database-3b3f8-default-rtdb.firebaseio.com/"
    fdb = firebase.FirebaseApplication(url, None) 
    wine_result = fdb.get('/sale',None)
    income=0
    for wine,price in wine_result.items():
        if wine==data:
            income=price
    money_result = fdb.get('/money/total',None)
    fdb.put('/money',"total",money_result+income)
    now = datetime.datetime.now(tz=datetime.timezone(datetime.timedelta(hours=8)))
    fdb.put('/money/event',str(now.strftime('%Y,%m,%d %H:%M:%S')),{'type':'income','event':'入帳','value':income})
    #delete 酒材料from 冰箱
#def delete