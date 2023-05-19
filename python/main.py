import multiprocessing
from gui import main as gui_main
from sockett import main as socket_main
from queue import Queue
import os
if __name__ == "__main__":
    
    #message_queue = multiprocessing.Queue()
    # try:
    #     previous_daemon_pid = os.getpid()  # 获取之前守护进程的PID
    #     os.kill(previous_daemon_pid, 9)  # 终止之前守护进程
    # except:
    #     print("error")

    # ctx = multiprocessing.get_context("spawn")  # 获取多进程上下文
    # # manager = ctx.Manager()
    # # message_queue = manager.Queue()  # 使用Manager创建队列
    
    gui_process = multiprocessing.Process(target=gui_main) #,args=(message_queue,))
    socket_process = multiprocessing.Process(target=socket_main) #,args=(message_queue,))

    # 将主进程设置为非守护进程
    multiprocessing.current_process().daemon = False

    gui_process.start()
    socket_process.start()

    gui_process.join()
    socket_process.join()
