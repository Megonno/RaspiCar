import socket
from picarx import Picarx
import time
from robot_hat import Music,TTS
from vilib import Vilib
from time import sleep, time, strftime, localtime
import threading
import readchar
import os

px = Picarx()
#px = Picarx(ultrasonic_pins=['D2','D3'], grayscale_pins=['A0', 'A1', 'A2'])
tts = TTS()
music = Music()

import asyncio

async def pass_to_correct_function(input_string):
    try:
        parts = input_string.split(':')
        if parts[0].lstrip('0') == '1':
            return await direction_servo(int(parts[1]))
        elif parts[0].lstrip('0') == '2':
            return await motor_forward(int(parts[1]))
        elif parts[0].lstrip('0') == '3':
            return await motor_backward(int(parts[1]))
        elif parts[0].lstrip('0') == '4':
            return await camera_tilt(int(parts[1]))
        elif parts[0].lstrip('0') == '5':
            return await camera_pan(int(parts[1]))
        elif parts[0].lstrip('0') == '6':
            return await start_video_stream(int(parts[1]))
        elif parts[0].lstrip('0') == '7':
            exit()
        elif parts[0].lstrip('0') == '8':
            return await send_ultrasonic_reading()
        elif parts[0].lstrip('0') == '9':
            return await send_grayscale_reading()
        elif parts[0].lstrip('0') == '10':
            return await tts_speak(parts[1], parts[2])
        elif parts[0].lstrip('0') == '11':
            return await tts_play(parts[1], int(parts[2]))
    except Exception as e:
        return e

async def tts_speak(text, lang):
    try:
        tts.lang(lang)
        tts.say(text)
        print(f"Speaking: {text}")
        return "1"
    except Exception as e:
        return e

async def tts_play(file, volume):
    try:
        music.music_set_volume(volume)
        music.sound_play(file)
        print(f"Playing: {file}")
        return "1"
    except Exception as e:
        return e

async def send_ultrasonic_reading():
    try:
        #read ultrasonic sensor and send to socket
        distance = round(px.ultrasonic.read(), 2)
        print(f"sending ultrasonic sensor reading: {distance}")
        return distance
    except Exception as e:
        return e

async def send_grayscale_reading():
    try:
        #read grayscale module data
        gm_val_list = px.get_grayscale_data()
        grayscale_data = ':'.join(map(str, gm_val_list))
        print("sending grayscale module reading")
        return grayscale_data
    except Exception as e:
        return e

async def direction_servo(angle):
    try:
        px.set_dir_servo_angle(angle)
        print(f"Direction servo angle: {angle}")
        return "1"
    except Exception as e:
        return e

async def motor_forward(speed):
    try:
        px.forward(speed)
        print(f"Motor forward speed: {speed}")
        return "1"
    except Exception as e:
        return e

async def motor_backward(speed):
    try:
        px.backward(speed)
        print(f"Motor backward speed: {speed}")
        return "1"
    except Exception as e:
        return e

async def camera_tilt(angle):
    try:
        px.set_cam_tilt_angle(angle)
        print(f"Camera tilt angle: {angle}")
        return "1"
    except Exception as e:
        return e

async def camera_pan(angle):
    try:
        px.set_cam_pan_angle(angle)
        print(f"Camera pan angle: {angle}")
        return "1"
    except Exception as e:
        return e

async def start_video_stream(port):
    print(port)
    try:
        Vilib.camera_start(vflip=False,hflip=False)
        return Vilib.display(local=True,web=True)
        #return "not yet implemented"
    except Exception as e:
        return e

#def stop_video_stream():
#    try:
#        print("Video stream stopped")
#        return "1"
#    except Exception as e:
#        return e

port = 5000
s = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
s.setsockopt(socket.SOL_SOCKET, socket.SO_REUSEADDR, 1)
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
            response = str(await pass_to_correct_function(data))
            response += "\n"
            conn.sendall(response.encode())
    except:
        break
conn.close()
