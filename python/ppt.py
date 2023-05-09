import win32com.client as win32

# 開啟PowerPoint應用程式
app = win32.Dispatch("PowerPoint.Application")

# 開啟一個PPT文件
presentation = app.Presentations.Open("D:\GitHub\Bar_Project\python\presentation.pptx")

# 設定展示模式
slide_show_settings = presentation.SlideShowSettings
slide_show_settings.Run()

# 設定頁面顯示時間
try:
    slide_show_settings.AdvanceMode = 2  # 每張投影片顯示完後暫停
    slide_show_settings.AdvanceTime = 5  # 設定顯示時間為5秒
except AttributeError:
    pass

# 設定當前頁面
while 1:
    slide_index=int(input("要跳轉到:"))
    if slide_index==0:
        break
    presentation.SlideShowWindow.View.GotoSlide(slide_index)

    # 等待展示完畢
    while presentation.SlideShowWindow.View.CurrentShowPosition != presentation.SlideShowWindow.View.Slide.SlideIndex:
        pass

    # 暫停展示
    try:
        presentation.SlideShowWindow.View.Pause()
    except AttributeError:
        pass

# 關閉PPT文件
presentation.Close()
