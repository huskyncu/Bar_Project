import multiprocessing
from gui import main as gui_main
from sockett import main as socket_main
import os
if __name__ == "__main__":
    # try:
    #     previous_daemon_pid = os.getpid()  # 获取之前守护进程的PID
    #     os.kill(previous_daemon_pid, 9)  # 终止之前守护进程
    # except:
    #     print("error")

    ctx = multiprocessing.get_context("spawn")  # 获取多进程上下文

    gui_process = ctx.Process(target=gui_main)
    socket_process = ctx.Process(target=socket_main)

    # 将主进程设置为非守护进程
    multiprocessing.current_process().daemon = False

    gui_process.start()
    socket_process.start()

    gui_process.join()
    socket_process.join()
