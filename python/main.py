from gui import main as gui_main
from sockett import main as socket_main
from arduino_ import arduino_main as arduino_main
from ppt import ppt as ppt_main
import threading

def main():
    # 建立兩個 thread 物件，分別代表 run_socket() 與 run_gui()。
    thread3 = threading.Thread(target=arduino_main)
    thread1 = threading.Thread(target=socket_main)
    thread2 = threading.Thread(target=gui_main)
    
    thread4 = threading.Thread(target=ppt_main)
    # 開始執行這兩個 threads。
    thread3.start()
    thread1.start()
    thread2.start()
    thread4.start()
    # 主程式會在這裡等待所有 threads 完成後再繼續。
    thread3.join()
    thread1.join()
    thread2.join()
    thread4.join()

if __name__ == "__main__":
    main()