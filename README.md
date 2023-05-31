# 專題架構圖

###### tags: `資工`

## 主要架構與技術
分成硬體與軟體

### 硬體
* arduino
* linklt 7697
* 鏡頭
* 超音波感測器
* 伺服馬達
* 共陽極LED燈
### 軟體
* Server：python GUI
* Client：android app
* Database：firebase


### 使用技術
* android
    * app
    * socket client
    * speech to text and text to speech
    * 註冊跟VIP辨識
* python
    * socket server
    * 點擊.bat檔即可執行server程式。
    * tkinter
    * open cv 掃qrcode
    * opencv 人臉辨識
    * read qr code
    * python download csv
* database
    * Firebase

## 硬體架構
```mermaid
graph TD;
實體酒吧-->實體arduino;
實體arduino-->大門;
大門-->感測開門-->|偵測坐下來的距離大於5|使用伺服馬達開門-->|亮LED燈|帶位;
帶位-->到座位上後-->|偵測坐下來的距離小於5|LED關燈-->|python_socket|顧客手機app;
到座位上後-->傳送坐下來的訊號到FireBase儲存;
感測開門-->|偵測坐下來的距離小於5|使用伺服馬達關門;
```

## 點餐App 
### 程式架構
```mermaid
graph TD;
顧客手機app-->|socket|鏡頭轉向該顧客-->偵測有沒有坐下;
偵測有沒有坐下-->註冊與登入-->點餐-->語音化服務-->|socket|pythonserver;
偵測有沒有坐下-->|socket|FireBase查看訊號-->|socket|偵測有沒有坐下;
註冊與登入-->|socket|python-->|模型|鏡頭人臉辨識-->|模型|python-->|socket|註冊與登入;
點餐-->酒單點酒-->|socket|pythonserver;
點餐-->客製化點酒-->|socket|pythonserver;
註冊與登入-->點選資料-->|socket|FireBase-->可以看到自己以前點過的酒與個性資料
```

### 點餐 app 架構

#### 一開始畫面：
坐下來後才能開始使用app

#### 第二個畫面：
註冊輸入名字與登入按鈕

#### 第三個畫面：
* 個人詳細資料按鈕
* 語音化服務按鈕
* 點酒按鈕
* 客製化點酒按鈕
* 登出按鈕
* 顯示你已付金額

#### 語音化服務
透過語音化跟傳送socket到server，再傳到資料庫，然後再透過socket傳送到app。

#### 點擊畫面
* 一般點酒按鈕
    * 顯示菜單(手動)
* 客製化頁面
    * 酸或甜
    * 重還輕
    * 想要有氣泡嗎？
    * 想喝醉嗎？
    * 喜歡水果味嗎？

py->android 用json 傳送
android -> py用文字傳送
## 內場架構
```mermaid
graph TD
冰箱資料表-->|如果材料夠|從酒單挑出酒品並從冰箱扣減材料_firebase-->|socket|顯示上酒-->|FireBase|紀錄個人點酒紀錄;
顯示上酒-->|firebase收入記帳|收支資料表;
收支資料表-->可下載收支excel;
python_server-->|firebase|冰箱資料表-->|如果材料不夠|提醒買缺的材料-->收支資料表-->|錢夠買完放冰箱|冰箱;
python_server-->|firebase|收支資料表
冰箱-->|python介面與qrcode辨識|掃qrcode放冰箱-->歸類-->|firebase|冰箱資料表;

冰箱-->|firebase支出記帳|收支資料表
```


## 時程表

### 第一次開會(5/8)

- [x] 完成 大門與帶位的arduino(功能建立)
- [x] python GUI 撰寫完成
- [x] python GUI 能夠串接firebase資料庫
- [x] python GUI 能下載csv
- [x] python GUI opencv qrcode 圖像辨識

### 第二次開會
- [x] android app 撰寫完成
- [x] ANDROID APP 客製化點酒
- [x] 完成 android app socket 連接 python GUI
- [x] 語音化訊息處理
- [x] arduino 偵測坐下後才能用app點餐
- [x] opencv 人臉辨識

### 期末進度
- [ ] 實行美工，將所有硬體安裝進模型裡面。

## 未來展望
* 搭配line bot
* 天窗
* 音樂

## 製作困難

### 5/8
如何讓客戶知道我們的產品實際上在做什麼？

使用ppt，同步操作demo

### 5/17 

人臉辨識如何儲存隨時有人註冊後更新模型？

解決方式：使用pickle套件

### 5/19
如何在server gui顯示來自socket的訊息？

解決方式：開多執行緒

### 5/30
如何在不同詞彙的狀況下知道使用者的語意？
* 使用中文語意套件SnowNLP偵測
