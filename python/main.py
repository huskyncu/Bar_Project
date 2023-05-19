from gui import main as gui_main
from sockett import main as socket_main
import threading

def main():
    # 建立兩個 thread 物件，分別代表 run_socket() 與 run_gui()。
    thread1 = threading.Thread(target=socket_main)
    thread2 = threading.Thread(target=gui_main)

    # 開始執行這兩個 threads。
    thread1.start()
    thread2.start()

    # 主程式會在這裡等待所有 threads 完成後再繼續。
    thread1.join()
    thread2.join()

if __name__ == "__main__":
    main()