import socket
from picarx import Picarx
import time
from robot_hat import Music,TTS
from pydoc import text
from vilib import Vilib
from time import sleep, time, strftime, localtime
import threading
import readchar
import os

px = Picarx()
#px = Picarx(ultrasonic_pins=['D2','D3'], grayscale_pins=['A0', 'A1', 'A2'])
tts = TTS()
music = Music()
current_state = None
px_power = 10
offset = 20
last_state = "stop"

def pass_to_correct_function(input_string):
    parts = input_string.split(':')
    if parts[0].lstrip('0') == '1':
        return direction_servo(int(parts[1]))
    elif parts[0].lstrip('0') == '2':
        return motor_forward(int(parts[1]))
    elif parts[0].lstrip('0') == '3':
        return motor_backward(int(parts[1]))
    elif parts[0].lstrip('0') == '4':
        return camera_tilt(int(parts[1]))
    elif parts[0].lstrip('0') == '5':
        return camera_pan(int(parts[1]))
    elif parts[0].lstrip('0') == '6':
        return start_video_stream(int(parts[1]))
    elif parts[0].lstrip('0') == '7':
        return stop_video_stream()
    elif parts[0].lstrip('0') == '8':
        return send_ultrasonic_reading()
    elif parts[0].lstrip('0') == '9':
        return send_grayscale_reading
    elif parts[0].lstrip('0') == '10':
        return tts_speak(parts[1], parts[2], parts[3])
    elif parts[0].lstrip('0') == '11':
        return tts_play(parts[1], int(parts[2]))

def tts_speak(text, volume, lang):
    tts.lang(lang)
    tts.music_set_volume(volume)
    tts.say(text)
    print(f"Speaking: {text}")
    return "1"    

def tts_play(file, volume):
    music.music_set_volume(volume)
    music.sound_play(file)
    print(f"Playing: {file}")
    return "1"

def send_ultrasonic_reading():
    #read ultrasonic sensor and send to socket
    distance = round(px.ultrasonic.read(), 2)
    print(f"sending ultrasonic sensor reading: {distance}")
    return distance

def send_grayscale_reading():
    #read grayscale module data
    gm_val_list = px.get_grayscale_data()
    gm_state = get_status(gm_val_list)
    _state = px.get_line_status(val_list)
    cliff_status = px.get_cliff_status(gm_val_list)
    print("sending grayscale module reading")
    return f"{gm_state}:{_state}:{cliff_status}"

def direction_servo(angle):
    px.set_dir_servo_angle(angle)
    print(f"Direction servo angle: {angle}")
    return "1"

def motor_forward(speed):
    px.forward(speed)
    print(f"Motor forward speed: {speed}")
    return "1"

def motor_backward(speed):
    px.backward(speed)
    print(f"Motor backward speed: {speed}")
    return "1"

def camera_tilt(angle):
    px.set_camera_servo2_angle(angle)
    print(f"Camera tilt angle: {angle}")
    return "1"

def camera_pan(angle):
    px.set_camera_servo1_angle(angle)
    print(f"Camera pan angle: {angle}")
    return "1"

def start_video_stream(port):
    Vilib.camera_start(vflip=False,hflip=False)
    Vilib.display(local=True,web=True)
    print(f"Video stream started on port {port}")
    return "not yet implemented"

def stop_video_stream():
    print("Video stream stopped")
    return "1"

port = 5000
s = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
s.bind(('', port))
s.listen(1)
conn, addr = s.accept()
print('Connected by', addr)
#init connection
print("sending init")
initmsg = "init\n"
conn.sendall(initmsg.encode())
while True:
    try:
        data = conn.recv(1024).decode()
        if data:
            response = str(pass_to_correct_function(data))
            response += "\n"
            conn.sendall(response.encode())
    except:
        break
conn.close()



