from firebase import firebase
def insert_ele(db_name,data):
    url = "https://python-database-3b3f8-default-rtdb.firebaseio.com/"
    fdb = firebase.FirebaseApplication(url, None)    # 初始化，第二個參數作用在負責使用者登入資訊，通常設定為 None
    print(data)
    data = dict(subString.split("=") for subString in data.split(";"))
    all = fdb.get('/',None)
    if 'refrige' not in all:
        fdb.put('/'+db_name,data['name'],1)
    else:
        result = fdb.get('/refrige',None)
        if data['name'] in result:
            amount = result[data['name']]
            fdb.put('/'+db_name,data['name'],amount+1)
        else:
            fdb.put('/'+db_name,data['name'],0)
    result = fdb.get('/'+db_name,None)
    print(result)