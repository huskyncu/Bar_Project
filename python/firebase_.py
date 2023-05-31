from firebase import firebase
import datetime
def insert_ele(db_name,data):
    url = "https://python-database-3b3f8-default-rtdb.firebaseio.com/"
    fdb = firebase.FirebaseApplication(url, None)    # 初始化，第二個參數作用在負責使用者登入資訊，通常設定為 None
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
def insert_wine(user,data):
    url = "https://python-database-3b3f8-default-rtdb.firebaseio.com/"
    fdb = firebase.FirebaseApplication(url, None) 
    wine_result = fdb.get('/sale',None)
    print(user,data)
    refrige = fdb.get('/refrige',None)
    element = fdb.get('/menu/'+data,None)
    print(element)
    flag = True
    for name,num in element.items():
        try:
            if refrige[name]-num>0:
                flag=True
        except:
            flag = False
            mes = fdb.get('togui/now',None)
            fdb.put('togui','now',f'{name}不夠了，請先購買材料。')
            fdb.put('togui','tmp',mes)
            return f'{name}的材料有缺，訂購失敗'
    if flag:
        for name,num in element.items():
            fdb.put('/refrige',name,refrige[name]-num)
        income=0
        for wine,price in wine_result.items():
            if wine==data:
                income=price
        money_result = fdb.get('/money/total',None)
        now = datetime.datetime.now(tz=datetime.timezone(datetime.timedelta(hours=8)))
        fdb.put('/user_data/'+user+'/buy',str(now.strftime('%Y,%m,%d %H:%M:%S')),{data:price})
        fdb.put('/money',"total",money_result+income)
        fdb.put('/money/event',str(now.strftime('%Y,%m,%d %H:%M:%S')),{'type':'income','event':'賣出'+data,'value':income})
        return '訂購成功'
    
def check():
    url = "https://python-database-3b3f8-default-rtdb.firebaseio.com/"
    fdb = firebase.FirebaseApplication(url, None) 
    refrige = fdb.get('/refrige',None)
    element = fdb.get('/menu',None)
    for name,ele in element.items():
        for name_ele,num in ele.items():
            try:
                if refrige[name_ele]<num:
                    mes = fdb.get('togui','now',None)
                    fdb.put('/togui','now',f'{name_ele}不夠了，請先購買材料。')
                    fdb.put('/togui','tmp',mes)
            except:
                mes = fdb.get('togui','now',None)
                fdb.put('/togui','now',f'{name_ele}不夠了，請先購買材料。')
                fdb.put('/togui','tmp',mes)
    
