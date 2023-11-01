from gui_button.firebase_ import insert_wine
from firebase import firebase
def reply(user,mes):
    url = "https://python-database-3b3f8-default-rtdb.firebaseio.com/"
    fdb = firebase.FirebaseApplication(url, None)
    dic = {"伏特加萊姆":"1000","伏特加七喜":"1010","柯夢波丹":"1101","海風":"00001","琴通寧":"00000","龍舌蘭日出":"0100","威士忌可樂":"1011","長島冰茶":"1111","性慾海灘":"0101"}
    dic2 = {"伏特加萊姆":16,"伏特加七喜":17,"柯夢波丹":24,"海風":18,"琴通寧":20,"龍舌蘭日出":22,"威士忌可樂":23,"長島冰茶":21,"性慾海灘":19}
    if "點酒" in mes:
        return "請問要直接點還是不確定呢？如果不確定可以說客製化點酒。"
    elif "客製化" in mes:
        fdb.put('/user_data/'+user,'speech_custom',"")
        return  "請問您要甜的還是酸的？"
    elif "直接點" in mes:
        return "請問您要甚麼酒呢？"
    # 點酒
    a=""
    tmp=""
    for key,values in dic.items():
        if key in mes:
            a = insert_wine(user,key)
            tmp=key
            if a!='訂購成功':
                return a
    if a=='訂購成功':
        print("tmp",tmp)
        fdb.put('/','ppt',dic2[tmp])
        return tmp

    # 客製化點酒
    from snownlp import SnowNLP
    
    now = fdb.get(f'/user_data/{user}/speech_custom',"")
    if "酸" in mes or "甜" in mes:
        if "酸" in mes:
            fdb.put(f'/user_data/{user}','speech_custom',now+"1")
        elif "甜" in mes:
            fdb.put(f'/user_data/{user}','speech_custom',now+"0")
        return "您想要喝醉嗎？"
    elif len(now)==1:
        if SnowNLP(mes).sentiments>0.5:
            fdb.put(f'/user_data/{user}','speech_custom',now+"1")
        else:
            fdb.put(f'/user_data/{user}','speech_custom',now+"0")
        return "喜不喜歡氣泡?"
    elif len(now)==2:
        if SnowNLP(mes).sentiments>0.5:
            fdb.put(f'/user_data/{user}','speech_custom',now+"1")
        else:
            fdb.put(f'/user_data/{user}','speech_custom',now+"0")
        return "要貴的還是便宜的?"
    elif "貴" in mes or "便宜" in mes:
        if SnowNLP(mes).sentiments>0.5:
            fdb.put(f'/user_data/{user}','speech_custom',now+"0")
            now+='0'
        else:
            fdb.put(f'/user_data/{user}','speech_custom',now+"1")
            now+='1'
        for k,v in dic.items():
            print(now,v)
            if v==now:
                return "您點的是"+k+" 請問要訂購嗎？"
        return "喜不喜歡水果味"
    elif len(now)==4 and now not in list(dic.values()):
        if SnowNLP(mes).sentiments>0.5:
            now = fdb.get(f'/user_data/{user}','speech_custom',now+"1")
        else:
            now = fdb.get(f'/user_data/{user}','speech_custom',now+"0")
        for k,v in dic.items():
            print(now)
            if v==now:
                return "您點的是"+k+" 請問要訂購嗎？"
        fdb.put('/','speech_custom',"")
        return "對不起，沒有您喜好的品項，請重新選擇。"
    elif "要" in mes or "不要" in mes:       
        if "不要" in mes:
            return "那你要幹嘛？"
        else:
            for k,v in dic.items():
                if v==now:
                    a=insert_wine(user,k)
                    tmp=k
                    if a!='訂購成功':
                        return a
            fdb.put(f'/user_data/{user}','speech_custom',"")
            fdb.put('/','ppt',dic2[tmp])
            return tmp
            
    else:
        return "對不起，請再說一次"
    
        
    
        