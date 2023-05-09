from firebase import firebase
import datetime
def insert_ele(db_name,data):
    url = "https://python-database-3b3f8-default-rtdb.firebaseio.com/"
    fdb = firebase.FirebaseApplication(url, None)    # 初始化，第二個參數作用在負責使用者登入資訊，通常設定為 None
    if db_name=='refrige':
        print(data)
        data = dict(subString.split("=") for subString in data.split(";"))
        print(data)
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
    elif db_name=='arduino':
        for k,v in data.items():
            if v!=0:
                fdb.put('/arduino',k,1)
            else:
                fdb.put('/arduino',k,0)
        