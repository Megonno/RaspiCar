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

def direction_servo(angle):
    print(angle)

def motor_right(speed):
    print(f"Motor right speed: {speed}")

def motor_left(speed):
    print(f"Motor left speed: {speed}")

def camera_tilt(angle):
    print(f"Camera tilt angle: {angle}")

def camera_pan(angle):
    print(f"Camera pan angle: {angle}")

def start_video_stream(port):
    print(f"Video stream started on port {port}")

def stop_video_stream():
    print("Video stream stopped")

pass_to_correct_function("6:1")

