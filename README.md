# RaspiCar

Commands:
- 1:angle; set steering servo angle; response: 1 if successful
- 2:speed; set right motor speed; response: 1 if successful
- 3:speed; set left motor speed; response: 1 if successful
- 4:angle; set camera tilt angle; response: 1 if successful
- 5:angle; set camera pan angle; response: 1 if successful
- 6:port; start video stream on specified port; response: link and port to stream (not yet implemented)
- 7:0; stop video stream; response: 1 if successful
- 8:0; request ultrasonic sensor reading; response: distance
- 9:0; request grayscale sensor reading; response: grayscale_status:line_status:cliff_status
- 10:text:lang; speak text with custom language; response: 1 if successful
- 11:file:volume; play audio file (file must be in the same folder as the server script); response: 1 if successful

Usage:

1. start the server script on the car.
2. start the client script on the computer that will control the car
3. using the client, send the specified commands
