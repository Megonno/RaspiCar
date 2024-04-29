import socket

def pass_to_correct_function(input_string):
    parts = input_string.split(':')
    if parts[0].lstrip('0') == '1':
        direction_servo(parts[1])
    elif parts[0].lstrip('0') == '2':
        motor_right(parts[1])
    elif parts[0].lstrip('0') == '3':
        motor_left(parts[1])
    elif parts[0].lstrip('0') == '4':
        camera_tilt(parts[1])
    elif parts[0].lstrip('0') == '5':
        camera_pan(parts[1])
    elif parts[0].lstrip('0') == '6':
        start_video_stream(parts[1])
    elif parts[0].lstrip('0') == '7':
        stop_video_stream()
    elif parts[0].lstrip('0') == '8':
        send_ultrasonic_reading()
    elif parts[0].lstrip('0') == '9':
        send_grayscale_reading

def send_ultrasonic_reading():
    #read ultrasonic sensor and send to socket
    print("sending ultrasonic sensor reading")
    return "ultrasonic sensor reading"

def send_grayscale_reading():
    #read grayscale module data
    print("sending grayscale module reading")
    return "grayscale module reading"

def direction_servo(angle):
    print(angle)
    return "success"

def motor_right(speed):
    print(f"Motor right speed: {speed}")
    return "success"

def motor_left(speed):
    print(f"Motor left speed: {speed}")
    return "success"

def camera_tilt(angle):
    print(f"Camera tilt angle: {angle}")
    return "success"

def camera_pan(angle):
    print(f"Camera pan angle: {angle}")
    return "success"

def start_video_stream(port):
    print(f"Video stream started on port {port}")
    return "success"

def stop_video_stream():
    print("Video stream stopped")
    return "success"

host = '127.0.0.1'
port = 5000
s = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
s.bind((host, port))
s.listen(1)
conn, addr = s.accept()
print('Connected by', addr)
while True:
    try:
        data = conn.recv(1024).decode()
        if data:
            response = pass_to_correct_function(data)
            conn.sendall(response.encode())
    except:
        break
conn.close()



