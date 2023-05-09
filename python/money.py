from firebase import firebase
import datetime
# def insert_ele(db_name,data):
url = "https://python-database-3b3f8-default-rtdb.firebaseio.com/"
fdb = firebase.FirebaseApplication(url, None) 
# result = fdb.get('/',None)
# amount = result[data['name']]
fdb.put('/money',"total",1000000)
result = fdb.get('/money/total',None)
fdb.put('/money',"total",result+1000000)
now = datetime.datetime.now(tz=datetime.timezone(datetime.timedelta(hours=8)))
fdb.put('/money/event',str(now.strftime('%Y,%m,%d %H:%M:%S')),{'type':'income','event':'入帳','value':1000000})