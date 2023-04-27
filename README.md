# 專題架構圖

###### tags: `資工`

## 主要架構與技術
分成硬體與軟體

### 硬體
* arduino
* linklt 7697
### 軟體
* Server：python GUI
* Client：android app
* Database：firebase


### 使用技術
* android
    * app
    * socket client
    * speech ai
* python
    * socket server
    * 點擊.bat檔即可執行server程式。
    * tkinter
    * open cv
    * read qr code
    * python download csv
* database
    * firebase

## 外場架構
```mermaid
graph TD;
實體酒吧-->實體arduino;
實體酒吧-->軟體android;
實體arduino-->天窗;
實體arduino-->大門;
軟體android-->顧客手機app;
員工模式;
天窗-->溫溼度感測器-->|如果濕度大於90%|使用馬達開窗;
溫溼度感測器-->|如果濕度小於90%|使用馬達關窗;
超音波感測器-->|如果偵測距離大於10|使用伺服馬達關門;
大門-->超音波感測器-->|如果偵測距離小於10|使用伺服馬達開門-->|亮LED燈|帶位;
帶位-->到座位上後-->|偵測坐下來的距離小於10|LED關燈
顧客手機app-->顧客模式;
顧客模式-->點歌-->|socket|pythonserver;
顧客模式-->點餐-->語音化服務-->客製化點酒-->|socket|pythonserver;
員工模式-->|本地端|pythonserver;
```
## 內場架構
```mermaid
graph TD
冰箱資料表-->可下載冰箱內存表格csv
冰箱資料表-->|如果材料夠|從酒單挑出酒品並從冰箱扣減材料_sql-->|socket|在手機app傳送酒的照片當作上酒--->|sql收入記帳|收支資料表;
收支資料表-->可下載收支csv;
python_server-->audio_frame;
python_server-->|mssql|冰箱資料表-->|如果材料不夠|提醒買缺的材料-->收支資料表-->|錢夠買完放冰箱|冰箱;
python_server-->|mssql|收支資料表
冰箱-->|python介面與qrcode辨識|掃qrcode放冰箱-->歸類-->|mssql|冰箱資料表;

冰箱-->|sql支出記帳|收支資料表
```


## 時程表

### 第一次開會(5/8)

* 完成 大門與帶位的arduino(功能建立)
* python GUI 撰寫完成
* python GUI 能夠串接firebase資料庫
* python GUI 能下載csv
* python GUI opencv qrcode 圖像辨識

### 第二次開會
* android app 撰寫完成
* ANDROID APP 客製化點酒
* 完成 android app socket 連接 python GUI
* 語音化訊息處理

### 第三次開會
* android app點歌與python 串接 youtube點歌

### 第四次開會
* 天窗 arduino 處理(功能建立)
* 實行美工，將所有硬體安裝進模型裡面。

